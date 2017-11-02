package todolist.validators;

import org.springframework.beans.factory.annotation.Autowired;
import todolist.services.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

  @Autowired
  private UserService userService;

  public void initialize(ValidUsername constraint) {
  }

  public boolean isValid(String login, ConstraintValidatorContext context) {
    try {
      return userService.isUsernameAvailable(login);
    } catch (NullPointerException e) {
      return true;
    }
  }
}
