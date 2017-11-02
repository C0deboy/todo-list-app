package todolist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;
  private final String from = "email@gmail.com";

  @Autowired
  public EmailServiceImpl(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public void sendSimpleEmail(SimpleMailMessage message) {
    System.out.println(message);
    //mailSender.send(message);
  }

  @Override
  public SimpleMailMessage prepareMessage(String to, String subject, String messageText) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(to);
    message.setSubject(subject);
    message.setText(messageText);

    return message;
  }
}
