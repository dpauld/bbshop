package group7.validation.annotation.validator;

import group7.service.UserService;
import group7.validation.annotation.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;
@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private UserService userService;

    @Autowired
    public UniqueUsernameValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null) {
            return true; // Assume null values are valid (can add additional checks if needed)
        }
        //System.out.println(username);
        String lowerCaseUsername = username.toLowerCase(); // Normalize email to lowercase for comparison, although handled by sql in repo.

        //if user exists then returns false, otherwise true indicating no user exist with that username.
        return this.userService.isUsernameAvailable(lowerCaseUsername);
    }
}