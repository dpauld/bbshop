package group7.controller;

import group7.users.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * This interface defines the API for an Order Controller, providing methods
 * for managing orders in a web application. It outlines operations such as
 * creating, canceling, deleting, and retrieving orders.
 */
public interface OrderController {

    /**
     * Creates a new order for the currently logged-in user.
     *
     * @param redirectAttributes Used to add flash messages for redirection after order creation.
     * @param user               The currently authenticated user making the order request. This is typically
     *                           injected using Spring Security's {@code @AuthenticationPrincipal}.
     * @return A redirect view name (e.g., "redirect:/orders") to redirect the user after order creation.
     */
    String createOrder(RedirectAttributes redirectAttributes, User user);

    /**
     * Cancels an existing order based on its ID. Does not delete order data,
     * only marks it as canceled by changing the orderStatus.
     *
     * @param id                 The ID of the order to be cancelled.
     * @param redirectAttributes Used to add flash messages for redirection after order cancellation.
     * @return A redirect view name (e.g., "redirect:/orders") to redirect the user after order cancellation.
     */
    String cancelOrder(Long id, RedirectAttributes redirectAttributes);

    /**
     * Deletes an order permanently from the system based on its ID.
     *
     * @param model The Spring {@code Model} object, which can be used to add attributes for view rendering
     *              after the deletion. This is optional and might not be needed if using redirects.
     * @param id    The ID of the order to be deleted.
     * @return A view name or redirect URL to navigate the user after the deletion operation.
     */
    String deleteOrderById(Model model, @PathVariable Long id);

    /**
     * Retrieves an order based on its ID.
     *
     * @param model The Spring {@code Model} object, which is used to add the retrieved {@code Order}
     *              object to the model for use in the view.
     * @param id    The ID of the order to be retrieved.
     * @return A view name representing the view to be rendered to display the order details.
     */
    String getOrderById(Model model, @PathVariable Long id);
}
