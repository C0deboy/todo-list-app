package todolist.validators;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@NotEmpty(message = "{NotEmpty.user.email}")
@Email(message = "{Email.user.email}")
public @interface ValidEmail {
  String message() default "{ValidEmail.user.email}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
