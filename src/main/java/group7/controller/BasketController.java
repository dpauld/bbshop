package group7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public interface BasketController {


    /**
     * Displays the items in the basket.
     *
     * This method retrieves the items in the user's basket from the BasketService
     * and adds them to the model to be displayed in the "basket.html" page.
     */
    @RequestMapping("/basket")
    public String showBasket(HttpSession session, Model model);

    /**
     * Adds a beverage to the basket.
     *
     * This method adds a beverage to the user's basket using its ID. The basket is managed
     * per user session, ensuring that each user has their own basket.
     */
    @RequestMapping("/addToBasket/{beverageId}")
    public String addBeverageToBasket(@PathVariable("beverageId") Long beverageId, HttpSession session);

    /**
     * Clears all items in the basket.
     *
     * This method clears all the beverages in the user's basket.
     */
    @RequestMapping("/clearBasket")
    public String clearBasket(HttpSession session);

    /**
     * Creates an order from the items in the basket.
     *
     * This method creates an order from the items currently in the user's basket. It uses
     * the user's ID and the session to transfer the basket items into an order.
     */
    public String createOrder(HttpSession session, Long userId);

    // Create an order from the basket
    @PostMapping("/order")
    String createOrder(Model model, HttpSession session);
}
