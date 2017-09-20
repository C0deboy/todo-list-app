package todolist.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import todolist.services.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

   @Autowired
   UserService userService;

   public void initialize(ValidEmail constraint) {
   }

   public boolean isValid(String email, ConstraintValidatorContext context) {
          return userService.isEmailAvailable(email);
    }
}
