package group7.validation.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class BirthdayValidatorTest {

    private BirthdayValidator birthdayValidator;
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        birthdayValidator = new BirthdayValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @Test
    public void testValidBirthday() {
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.of(2077, 1, 1);

        assertThat(birthdayValidator.isValid(date1, context)).isTrue();
        assertThat(birthdayValidator.isValid(date2, context)).isTrue();
        assertThat(birthdayValidator.isValid(null, context)).isTrue();
    }

    @Test
    public void testInvalidBirthday() {
        LocalDate invalidDate = LocalDate.of(1899, 1, 1);

        assertThat(birthdayValidator.isValid(invalidDate, context)).isFalse();
    }
}
