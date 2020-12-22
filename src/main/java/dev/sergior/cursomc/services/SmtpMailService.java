package dev.sergior.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpMailService extends AbstractEmailService {

    @Autowired
    private MailSender sender;

    private static final Logger logger = LoggerFactory.getLogger(MockEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage message) {
        logger.info("Simulando envio de email...");
        logger.info(message.toString());
        sender.send(message);
        logger.info("Email enviado");
    }

}
