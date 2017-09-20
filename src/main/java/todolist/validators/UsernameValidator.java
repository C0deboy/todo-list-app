package todolist.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import todolist.services.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {
    @Autowired
    UserService userService;

   public void initialize(ValidUsername constraint) {
   }

   public boolean isValid(String login, ConstraintValidatorContext context) {
       return userService.isUsernameAvailable(login);
   }
}
