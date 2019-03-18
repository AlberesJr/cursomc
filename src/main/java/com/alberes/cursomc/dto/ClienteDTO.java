package com.alberes.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.alberes.cursomc.domain.Cliente;
import com.alberes.cursomc.services.validation.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message="Prenchimento obrigatório")
	@Length(min=5, max=120, message="Não pode ser menor que 5 e maior que 120")
	private String nome;
	
	@NotEmpty
	@Email
	private String email;
	
	public ClienteDTO() {
		
	}
	
public ClienteDTO(Cliente cli) {
		id = cli.getId();
		nome = cli.getNome();
		email =  cli.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
