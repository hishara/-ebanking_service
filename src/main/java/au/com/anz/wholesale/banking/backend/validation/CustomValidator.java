package au.com.anz.wholesale.banking.backend.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Customer validator annotation.
 */
@Documented
@Constraint(validatedBy = IdValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomValidator {
    String message() default "Invalid Identifier";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
