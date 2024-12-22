package group7.controller;

import org.springframework.ui.Model;

public interface OrderController {

    /**
     * Retrieves a list of all orders and renders them in a view.
     *
     * @return The name of the HTML page that shows the list of orders.
     */
    String getAllOrders(Model model);

}
