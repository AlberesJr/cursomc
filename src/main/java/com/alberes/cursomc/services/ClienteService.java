package com.alberes.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alberes.cursomc.domain.Cliente;
import com.alberes.cursomc.repositories.ClienteRepository;
import com.alberes.cursomc.services.exceptions.ObjectNotFoudException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id) {
		Cliente obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoudException("Objeto não encontrado! Id: "
					+ id + ", Tipo: " + Cliente.class.getName() );
		}
		return obj;
	}
	
}
