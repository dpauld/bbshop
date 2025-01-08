package group7.controller;

import group7.dto.BasketItemDto;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface BasketController {

    // Method to view the basket
    String viewBasket(Model model, String error, Long errorBeverageId);

    // Method to add a beverage to the basket
    String addBeverageToBasket(Long beverageId);

    // Method to remove a beverage from the basket
    String removeBeverageFromBasket(Long beverageId);

    // Method to update the quantity of a beverage in the basket
    String updateBeverageQuantity(Long beverageId, int quantity, RedirectAttributes redirectAttributes);

    // Method to clear all items in the basket
    String clearBasket();

    // (Optional) Method for checkout logic, if needed
    // String checkout(RedirectAttributes redirectAttributes);
}
