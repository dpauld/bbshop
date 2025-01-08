package group7.validation.annotation.validator;

import group7.users.User;
import group7.users.UserRepository;
import group7.validation.annotation.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;
@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null) {
            return true; // Assume null values are valid (can add additional checks if needed)
        }
        //System.out.println(username);
        String lowerCaseUsername = username.toLowerCase(); // Normalize email to lowercase for comparison

        User user = this.userRepository.findByUsername(lowerCaseUsername);

        //if user exists then returns false, otherwise true indicating no user exist with that username.
        return user==null;
    }
}