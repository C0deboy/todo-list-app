package todolist.validators;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
  public void initialize(ValidPassword constraint) {
  }

  public boolean isValid(String password, ConstraintValidatorContext context) {
    Resource resource = new ClassPathResource("100-common-passwords.txt");

    try (BufferedReader commonPasswords = new BufferedReader(
        new InputStreamReader(resource.getInputStream()))) {

      String commonPassword;

      while ((commonPassword = commonPasswords.readLine()) != null) {
        if (password.toLowerCase().contains(commonPassword)) {
          return false;
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }


    return true;
  }
}
