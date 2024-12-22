package group7.controller;

import group7.dto.CreateUserRequestDTO;
import org.springframework.ui.Model;

public interface UserController {

    /**
     * Displays the login form.
     *
     * @return The name of the HTML page that shows the login form.
     */
    public String showLoginForm();

    /**
     * Authenticates the user during login.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @param model The model used to display messages or errors on the form.
     * @return The name of the page to redirect to after successful authentication or if there's an error.
     */
    public String loginUser(String username, String password, Model model);

    /**
     * Displays the registration form.
     *
     * @return The name of the HTML page that shows the registration form.
     */
    public String showRegisterForm();

    /**
     * Registers a new user.
     *
     * @param createUserRequestDTO The data transfer object containing the new user's details.
     * @param model The model used to display messages related to the registration process.
     * @return The name of the page to redirect to after successful registration or if there's an error.
     */
    public String registerUser(CreateUserRequestDTO createUserRequestDTO, Model model);
}
