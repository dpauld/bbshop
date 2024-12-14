package group7.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import group7.validation.validator.BirthdayValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = BirthdayValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBirthday {

    String message() default "Birthday must be after 1.1.1900";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
