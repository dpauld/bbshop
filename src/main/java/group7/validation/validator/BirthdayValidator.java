package group7.validation.validator;

import group7.validation.annotation.ValidBirthday;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class BirthdayValidator implements ConstraintValidator<ValidBirthday, LocalDate> {

    private static final LocalDate MIN_DATE = LocalDate.of(1900, 1, 1);

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Allow null values; use @NotNull to enforce non-null
        }
        return !value.isBefore(MIN_DATE); // Date must be on or after 1.1.1900
    }
}
