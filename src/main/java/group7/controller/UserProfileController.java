package group7.controller;

import group7.dto.AddressRequestDto;
import group7.users.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * This interface defines the API for a User Profile Controller, providing methods
 * for managing a user's profile information, including addresses and orders.
 */
public interface UserProfileController {
    /**
     * Retrieves the user's profile information. This typically redirects to a dedicated
     * profile view where user details can be displayed.
     *
     * @param user The currently authenticated user.
     * @param model The Spring {@code Model} object used to add attributes for view rendering.
     * @return A redirect view name to redirect the user to the profile page.
     */
    String getUserProfile(User user, Model model);
    String getProfile(User user, Model model);

    /**
     * Adds a new address for the user, categorized as either billing or delivery.
     *
     * @param type      The type of address (billing or delivery).
     * @param addressReqDto The address information to be added.
     * @param result     The Spring {@code BindingResult} object containing any validation errors.
     * @param model      The Spring {@code Model} object used to add attributes for view rendering.
     * @param user       The currently authenticated user.
     * @return A redirect view name to redirect the user back to the profile page after address addition (or re-renders the form with errors).
     */
    String addAddress(String type, AddressRequestDto addressReqDto,
                      BindingResult result, Model model, User user);

    /**
     * Deletes an existing address from the user's profile based on the address ID.
     *
     * @param addressId The ID of the address to be deleted.
     * @param user       The currently authenticated user.
     * @return A redirect view name to redirect the user back to the profile page after address deletion.
     */
    String deleteAddress(Long addressId, User user);

    /**
     * Retrieves all orders associated with the currently logged-in user.
     *
     * @param model      The Spring {@code Model} object used to add attributes for view rendering.
     * @param redirectAttributes Used to add flash messages for redirection (optional).
     * @param user       The currently authenticated user.
     * @return A view name representing the view to be rendered to display the user's orders.
     */
    String getOrdersByUser(Model model, RedirectAttributes redirectAttributes, User user);
}