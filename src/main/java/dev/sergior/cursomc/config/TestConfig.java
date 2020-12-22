package dev.sergior.cursomc.config;

import dev.sergior.cursomc.services.DBService;
import dev.sergior.cursomc.services.EmailService;
import dev.sergior.cursomc.services.MockEmailService;
import dev.sergior.cursomc.services.SmtpMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile(value = "test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        dbService.instantiateTestDatabase();
        return true;
    }

    @Bean
    public EmailService emailService() {
//        return new MockEmailService();
        return new SmtpMailService();
    }

}
