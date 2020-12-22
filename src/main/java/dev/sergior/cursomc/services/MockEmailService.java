package dev.sergior.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;


public class MockEmailService extends AbstractEmailService {

    private static final Logger logger = LoggerFactory.getLogger(MockEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage message) {
        logger.info("Simulando envio de email...");
        logger.info(message.toString());
        logger.info("Email enviado");
    }

}
