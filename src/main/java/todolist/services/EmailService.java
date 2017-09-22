package todolist.services;

import org.springframework.mail.SimpleMailMessage;

import javax.naming.NamingException;

public interface EmailService {
    void sendSimpleEmail(SimpleMailMessage message);
}
