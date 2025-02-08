package group7.validation.annotation.validator;

import group7.service.UserService;
import group7.validation.annotation.UniqueEmail;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;
@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private UserService userService;

    @Autowired
    public UniqueEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return true; // Assume null values are valid (can add additional checks if needed)
        }
        //System.out.println(username);
        String lowerCaseEmail = email.toLowerCase(); // Normalize email to lowercase for comparison, although handled by sql in repo.

        //if user exists then returns false, otherwise true indicating no user exist with that username.
        return this.userService.isEmailAvailable(lowerCaseEmail);
    }
}