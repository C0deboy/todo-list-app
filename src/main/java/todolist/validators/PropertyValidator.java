package todolist.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import todolist.entities.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class PropertyValidator {

    @Autowired
    private Validator validator;

    private User user;
    private String property = "not set";

    private Set<ConstraintViolation<User>> constraintViolations = new HashSet<>();

    public boolean isPropertyNotValid(String property, User user){
        this.property = property;
        this.user = user;
        constraintViolations = validator.validateProperty(user, property);

        return !constraintViolations.isEmpty();
    }

    public BindingResult addErrorsForBindingResultIfPresent(BindingResult result) {
        if(constraintViolations != null){

            for (ConstraintViolation<User> violation : constraintViolations) {

                String messageCode = violation.getMessageTemplate().replaceAll("[\\{}]", "");

                Map<String, Object> attributes = violation.getConstraintDescriptor().getAttributes();
                Object[] args = {property, attributes.get("max"), attributes.get("min")};

                result.rejectValue(property, messageCode, args, "");

            }
        }

        return result;
    }

    public void printInfoAboutCurrentPropertyValidation() {
        String objectName = user != null ? user.getClass().getName() : "none";
        System.out.println("\nObject: " + objectName + "\nProperty: " + property + "\nErrors found: " + constraintViolations.size());
    }


}
