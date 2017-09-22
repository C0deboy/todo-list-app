package todolist.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import todolist.services.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    @Autowired
    private UserService userService;

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
