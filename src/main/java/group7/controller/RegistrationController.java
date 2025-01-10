package group7.controller;

import group7.users.RegistrationForm;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This interface defines the contract for the RegistrationController.
 * It specifies methods for handling user registration functionalities.
 */
public interface RegistrationController {

    /**
     * Retrieves the registration form and renders the "register" view.
     *
     * @param model the model to add attributes to
     * @return the name of the view to render ("register")
     */
    @GetMapping
    String getRegistrationForm(Model model);

    /**
     * Processes the submitted registration form data.
     * Validates the data and saves a new user if no errors are found.
     *
     * @param registrationForm the registration form data
     * @param errors any validation errors encountered
     * @return the redirect URL to the login page ("login") on success,
     *         or the "register" view name if errors occur
     */
    @PostMapping
    String createUser(@Valid RegistrationForm registrationForm, Errors errors);
}