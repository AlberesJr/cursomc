package com.alberes.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alberes.cursomc.domain.Cidade;
import com.alberes.cursomc.domain.Estado;
import com.alberes.cursomc.dto.CidadeDTO;
import com.alberes.cursomc.dto.EstadoDTO;
import com.alberes.cursomc.services.CidadeService;
import com.alberes.cursomc.services.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {
	
	@Autowired
	private EstadoService estadoService;
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity <List<EstadoDTO>> findAll(){
		List<Estado> list = estadoService.findAll();
		List<EstadoDTO> listDto = list.stream().map(est -> new EstadoDTO(est)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/{estadoId}/cidades", method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId){
		List<Cidade> list = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> listDto = list.stream().map(cid -> new CidadeDTO(cid)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
		}

}
