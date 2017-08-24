package todolist.validators;

import org.springframework.beans.factory.annotation.Autowired;
import todolist.services.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
   @Autowired
   UserService userService;

   public void initialize(ValidEmail constraint) {
   }

   public boolean isValid(String email, ConstraintValidatorContext context) {
      try {
          return userService.isEmailAvailable(email);
      }
      catch (NullPointerException e){
          return true;
      }

   }
}
