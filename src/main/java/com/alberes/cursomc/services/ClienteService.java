package com.alberes.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.alberes.cursomc.domain.Cliente;
import com.alberes.cursomc.dto.ClienteDTO;
import com.alberes.cursomc.repositories.ClienteRepository;
import com.alberes.cursomc.services.exceptions.DataIntegrityException;
import com.alberes.cursomc.services.exceptions.ObjectNotFoudException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Cliente cli = repo.findOne(id);
		if (cli == null) {
			throw new ObjectNotFoudException("Objeto não encontrado! Id: "
					+ id + ", Tipo: " + Cliente.class.getName() );
		}
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
		//return new Cliente(catDto.getId(), catDto.getNome());
		return new Cliente(cliDto.getId(), cliDto.getNome(), cliDto.getEmail(), null, null);
	}
	
	private void updateData(Cliente newCli, Cliente cli) {
		newCli.setNome(cli.getNome());
		newCli.setEmail(cli.getEmail());
		
	}
	
}
