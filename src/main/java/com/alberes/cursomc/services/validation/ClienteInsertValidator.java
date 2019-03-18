package com.alberes.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.alberes.cursomc.domain.Cliente;
import com.alberes.cursomc.domain.enums.TipoCliente;
import com.alberes.cursomc.dto.ClienteNewDTO;
import com.alberes.cursomc.repositories.ClienteRepository;
import com.alberes.cursomc.resources.exception.FieldMessage;
import com.alberes.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO cliDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if (cliDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(cliDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido!"));
		}

		if (cliDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(cliDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido!"));
		}
		
		Cliente aux = repo.findByEmail(cliDto.getEmail());
		if(aux != null) {
			list.add(new FieldMessage("email", "email já existente"));
		}
		

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}