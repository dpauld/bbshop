package group7.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import group7.users.UserUpdateForm;

public interface UserController {

    /**
     * Retrieves a list of all users and renders the "users" view.
     *
     * @param model the model to add attributes to
     * @return the name of the view to render ("users")
     */
    String getAllUsers(Model model);

    /**
     * Changes the role of a user.
     *
     * @param username the username of the user
     * @param formData the form data containing the new role
     * @return the redirect URL to the users page
     */
    String changeUserRight(@PathVariable(value = "username") String username, UserUpdateForm formData);

    // String getUserDetails(Model model, Long userId);
    // String deleteUser(Long userId);
    // String updateUser(User user);
}