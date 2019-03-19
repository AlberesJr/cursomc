package com.alberes.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.alberes.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService{
	
	@Value("${default.sender}")
	private String sender;
	
	public void sendOrderConfirmationEmail(Pedido ped) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(ped);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido ped) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(ped.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: " +ped.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(ped.toString());
		return sm;
	}
}
