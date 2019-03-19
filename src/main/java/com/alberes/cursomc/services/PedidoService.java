package com.alberes.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alberes.cursomc.domain.ItemPedido;
import com.alberes.cursomc.domain.PagamentoComBoleto;
import com.alberes.cursomc.domain.Pedido;
import com.alberes.cursomc.domain.enums.EstadoPagamento;
import com.alberes.cursomc.repositories.ItemPedidoRepository;
import com.alberes.cursomc.repositories.PagamentoRepository;
import com.alberes.cursomc.repositories.PedidoRepository;
import com.alberes.cursomc.services.exceptions.ObjectNotFoudException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private EmailService emailService;
	

	public Pedido find(Integer id) {
		Pedido obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoudException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName());
		}
		return obj;
	}
	
	@Transactional
	public Pedido insert(Pedido ped) {
		ped.setId(null);
		ped.setInstante(new Date());
		ped.setCliente(clienteService.find(ped.getCliente().getId()));
		ped.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		ped.getPagamento().setPedido(ped);
		if(ped.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) ped.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, ped.getInstante());
		}
		ped = repo.save(ped);
		pagamentoRepository.save(ped.getPagamento());
		for (ItemPedido ip : ped.getItens()) {
			ip.setDesconto(0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(ped);
		}
		itemPedidoRepository.save(ped.getItens());
		emailService.sendOrderConfirmationHtmlEmail(ped);
		return ped;
	}

}
