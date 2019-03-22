package com.alberes.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alberes.cursomc.domain.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

	@Transactional(readOnly=true)
	@Query("SELECT cid FROM Cidade cid WHERE cid.estado.id = :estadoId ORDER BY cid.nome")
	public List<Cidade> findCidades(@Param("estadoId") Integer estado_id);
}