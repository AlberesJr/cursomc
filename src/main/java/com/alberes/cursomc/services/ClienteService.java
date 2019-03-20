package com.alberes.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alberes.cursomc.domain.Cidade;
import com.alberes.cursomc.domain.Cliente;
import com.alberes.cursomc.domain.Endereco;
import com.alberes.cursomc.domain.enums.TipoCliente;
import com.alberes.cursomc.dto.ClienteDTO;
import com.alberes.cursomc.dto.ClienteNewDTO;
import com.alberes.cursomc.repositories.CidadeRepository;
import com.alberes.cursomc.repositories.ClienteRepository;
import com.alberes.cursomc.repositories.EnderecoRepository;
import com.alberes.cursomc.services.exceptions.DataIntegrityException;
import com.alberes.cursomc.services.exceptions.ObjectNotFoudException;

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private ClienteRepository repo;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		Cliente cli = repo.findOne(id);
		if (cli == null) {
			throw new ObjectNotFoudException("Objeto não encontrado! Id: "
					+ id + ", Tipo: " + Cliente.class.getName() );
		}
		return cli;
	}
	
	public Cliente insert(Cliente cli) {
		cli.setId(null);
		cli = repo.save(cli);
		enderecoRepository.save(cli.getEnderecos());
		return cli;
	}
	
	public Cliente update(Cliente cli) {
		Cliente newCli = find(cli.getId());
		updateData(newCli, cli);
		return repo.save(newCli);
	}

	public void delete(Integer id) {
		try {
			repo.delete(find(id));
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um Cliente que contém pedidos.");
		}
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO cliDto) {
		return new Cliente(cliDto.getId(), cliDto.getNome(), cliDto.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO cliDto) {
		Cliente cli = new Cliente(null, cliDto.getNome(), cliDto.getEmail(), cliDto.getCpfOuCnpj(), TipoCliente.toEnum(cliDto.getTipo()), passwordEncoder.encode(cliDto.getSenha()));
		Cidade cid = cidadeRepository.findOne(cliDto.getCidadeId());
		Endereco end = new Endereco(null, cliDto.getLogradouro(), cliDto.getNumero(), cliDto.getComplemento(), cliDto.getBairro(), cliDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(cliDto.getTelefone1());
		
		if (cliDto.getTelefone2() != null) {
			cli.getTelefones().add(cliDto.getTelefone2());
		}
		if (cliDto.getTelefone3() != null) {
			cli.getTelefones().add(cliDto.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newCli, Cliente cli) {
		newCli.setNome(cli.getNome());
		newCli.setEmail(cli.getEmail());
		
	}
	
}
