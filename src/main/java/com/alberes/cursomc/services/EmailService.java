package com.alberes.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.alberes.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido ped);
	
	void sendEmail(SimpleMailMessage msg);
}
