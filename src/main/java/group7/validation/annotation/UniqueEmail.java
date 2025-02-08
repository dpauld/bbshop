package group7.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import group7.validation.annotation.validator.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "User with this email already exists.";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}