package group7.controller.controllerImpl;

import group7.controller.BasketController;
import group7.dto.UserResponseDTO;
import group7.service.BasketService;
import group7.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/basket")
public class BasketControllerImpl implements BasketController {

    private final BasketService basketService;
    private final OrderItemService orderItemService;

    @Autowired
    public BasketControllerImpl(BasketService basketService, OrderItemService orderItemService) {
        this.basketService = basketService;
        this.orderItemService = orderItemService;
    }

    // Show the contents of the basket
    @GetMapping
    @Override
    public String showBasket(HttpSession session, Model model) {
        model.addAttribute("orderItems",orderItemService.getBeveragesFromBasketAsOrderItems(basketService.getBasket(session)));
        return "basket";
    }

    // Add a beverage to the basket by its ID
    @PostMapping("/add/{beverageId}")
    @Override
    public String addBeverageToBasket(@PathVariable("beverageId") Long beverageId, HttpSession session) {
        basketService.addBeverageToBasketById(beverageId, session);
        return "redirect:/basket";  // Redirect to the basket view
    }

    // Clear the basket
    @PostMapping("/clear")
    @Override
    public String clearBasket(HttpSession session) {
        basketService.clearBasket(session);
        return "redirect:/basket";  // Redirect to the basket view
    }

    @Override
    public String createOrder(HttpSession session, Long userId) {
        basketService.createOrderFromBasket(userId, session);
        return "redirect:/beverages";  // Redirect to the beverages list
    }

    // Create an order from the basket
    @PostMapping("/order")
    @Override
    public String createOrder(Model model, HttpSession session) {
        Object userAttribute = session.getAttribute("user");
        if (userAttribute != null && userAttribute instanceof UserResponseDTO userResponseDTO) {
            basketService.createOrderFromBasket(userResponseDTO.getId(), session);
            return "redirect:/beverages";  // Redirect to the beverages list
        } else {
            return "redirect:/user/login";
        }
    }
}
