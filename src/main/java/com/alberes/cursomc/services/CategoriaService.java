package com.alberes.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alberes.cursomc.domain.Categoria;
import com.alberes.cursomc.repositories.CategoriaRepository;
import com.alberes.cursomc.services.exceptions.ObjectNotFoudException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Categoria obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoudException("Objeto não encontrado! Id: "
					+ id + ", Tipo: " +Categoria.class.getName() );
		}
		return obj;
	}
	
}
