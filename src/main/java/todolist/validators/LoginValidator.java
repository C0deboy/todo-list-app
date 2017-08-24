package todolist.validators;

import org.springframework.beans.factory.annotation.Autowired;
import todolist.services.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LoginValidator implements ConstraintValidator<ValidLogin, String> {
    @Autowired
    UserService userService;

   public void initialize(ValidLogin constraint) {
   }

   public boolean isValid(String login, ConstraintValidatorContext context) {
       try {
           return !userService.isUserNameValid(login);
       }
       catch (NullPointerException e){
           return true;
       }
   }
}
