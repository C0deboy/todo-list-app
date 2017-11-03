package todolist.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class SpringDataTestConfiguration {
  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setPackagesToScan("todolist.entities");
    sessionFactory.setConfigLocation(new ClassPathResource("hibernate.testcfg.xml"));
    return sessionFactory;
  }

  @Bean
  @Autowired
  public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    transactionManager.setSessionFactory(sessionFactory);
    return transactionManager;
  }

  @Bean
  public JavaMailSenderImpl mailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);
    mailSender.setUsername("");
    mailSender.setPassword("");

    Properties javaMailProperties = new Properties();
    javaMailProperties.setProperty("mail.transport.protocol", "smtp");
    javaMailProperties.setProperty("mail.smtp.auth", "true");
    javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
    javaMailProperties.setProperty("mail.debug", "true");
    mailSender.setJavaMailProperties(javaMailProperties);

    return mailSender;
  }
}
