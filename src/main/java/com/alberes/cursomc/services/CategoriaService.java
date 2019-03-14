package com.alberes.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.alberes.cursomc.domain.Categoria;
import com.alberes.cursomc.repositories.CategoriaRepository;
import com.alberes.cursomc.services.exceptions.DataIntegrityException;
import com.alberes.cursomc.services.exceptions.ObjectNotFoudException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Categoria obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoudException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName());
		}
		return obj;
	}

	public Categoria insert(Categoria cat) {
		cat.setId(null);
		return repo.save(cat);
	}

	public Categoria update(Categoria cat) {
		find(cat.getId());
		return repo.save(cat);
	}

	public void delete(Integer id) {
		try {
			repo.delete(find(id));
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que contém produtos.");
		}
	}

}
