package todolist.services;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendSimpleEmail(SimpleMailMessage message);
    SimpleMailMessage prepareMessage(String to, String subject, String message);
}
