package todolist.validators;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.*;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UsernameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@NotEmpty
@Pattern(regexp = "^[a-zA-Z0-9]*$")
@Length.List({
        @Length(min = 3 , message = "{Length.min.user.username}"),
        @Length(max = 30, message = "{Length.max.user.username}")
})
public @interface ValidUsername {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };
}
