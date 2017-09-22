package todolist.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Map;
import java.util.Set;

@Component
public class PropertyValidator<T> {

    private final Validator validator;

    private String property;

    private Set<ConstraintViolation<T>> constraintViolations;

    @Autowired
    public PropertyValidator(Validator validator) {
        this.validator = validator;
    }

    public boolean isPropertyNotValid(String property, T t){
        this.property = property;
        constraintViolations = validator.validateProperty(t, property);

        return !constraintViolations.isEmpty();
    }

    public BindingResult addErrorsForBindingResultIfPresent(BindingResult result) {
        if(constraintViolations != null){

            for (ConstraintViolation<T> violation : constraintViolations) {

                String messageCode = violation.getMessageTemplate().replaceAll("[{}]", "");

                Map<String, Object> attributes = violation.getConstraintDescriptor().getAttributes();
                Object[] args = {property, attributes.get("max"), attributes.get("min")};

                result.rejectValue(property, messageCode, args, violation.getMessage());

            }
        }

        return result;
    }
}
