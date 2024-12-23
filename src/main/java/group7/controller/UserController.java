package group7.controller;

import group7.dto.AddAddressRequestDTO;
import group7.dto.CreateUserRequestDTO;
import jakarta.servlet.http.HttpSession;
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
     * @param httpSession The session associated with the current request.
     * @return The name of the page to redirect to after successful authentication or if there's an error.
     */
    public String loginUser(String username, String password, Model model, HttpSession httpSession);

    /**
     * Displays the registration form.
     * @param model The model used to pass form object prototypes.
     *
     * @return The name of the HTML page that shows the registration form.
     */
    public String showRegisterForm(Model model);

    /**
     * Registers a new user.
     *
     * @param createUserRequestDTO The data transfer object containing the new user's details.
     * @param addAddressRequestDTO The data transfer object to create singletons for @createUserRequestDTO.billingAddresses@ and @createUserRequestDTO.deliveryAddresses@.
     * @param model The model used to display messages related to the registration process.
     * @return The name of the page to redirect to after successful registration or if there's an error.
     */
    public String registerUser(CreateUserRequestDTO createUserRequestDTO, AddAddressRequestDTO addAddressRequestDTO, Model model);
}
