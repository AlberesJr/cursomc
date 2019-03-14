package com.alberes.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alberes.cursomc.domain.Pedido;
import com.alberes.cursomc.repositories.PedidoRepository;
import com.alberes.cursomc.services.exceptions.ObjectNotFoudException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	public Pedido find(Integer id) {
		Pedido obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoudException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName());
		}
		return obj;
	}

}
