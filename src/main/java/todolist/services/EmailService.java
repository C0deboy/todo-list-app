package todolist.services;
import org.springframework.mail.SimpleMailMessage;

import javax.naming.NamingException;
import java.util.Properties;

public interface EmailService {
    void sendSimpleEmail(SimpleMailMessage message) throws NamingException;
}
