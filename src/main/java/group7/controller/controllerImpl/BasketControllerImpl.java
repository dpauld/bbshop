package group7.controller.controllerImpl;

import group7.controller.BasketController;
import group7.dto.BasketItemDto;
import group7.service.serviceImpl.BasketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basket")
public class BasketControllerImpl implements BasketController {

    private BasketServiceImpl basketService;

    @Autowired
    public BasketControllerImpl(BasketServiceImpl basketService) {
        this.basketService = basketService;
    }

    @Override
    @GetMapping
    public String viewBasket(Model model, @RequestParam(value = "error", required = false) String error, @RequestParam(value = "errorBeverageId", required = false) Long errorBeverageId) {
        List<BasketItemDto> beverages = basketService.getItemsInBasket();
        model.addAttribute("beverages", beverages);
        model.addAttribute("totalPrice", basketService.getTotalPrice());
        if (error != null && errorBeverageId != null) {
            model.addAttribute("error", error);
            model.addAttribute("errorBeverageId", errorBeverageId);
        }
        return "basket";
    }

    @Override
    @PostMapping("/add")
    public String addBeverageToBasket(@RequestParam Long beverageId, RedirectAttributes redirectAttributes) {
        basketService.addItemToBasket(beverageId);
        redirectAttributes.addFlashAttribute("success", "Beverage added to Basket!");
        return "redirect:/beverages";
    }

    @Override
    @PostMapping("/remove")
    public String removeBeverageFromBasket(@RequestParam Long beverageId) {
        basketService.removeItemFromBasket(beverageId);
        return "redirect:/basket";
    }

    @Override
    @PostMapping("/updateQuantity")
    public String updateBeverageQuantity(@RequestParam Long beverageId, @RequestParam int quantity, RedirectAttributes redirectAttributes) {
        boolean success = basketService.updateItemQuantity(beverageId, quantity);
        if (!success) {
            redirectAttributes.addAttribute("error", "Quantity exceeds available stock.");
            redirectAttributes.addAttribute("errorBeverageId", beverageId);
        }
        return "redirect:/basket";
    }

    @Override
    @PostMapping("/clear")
    public String clearBasket() {
        basketService.clearBasket();
        return "redirect:/basket";
    }

//    @PostMapping("/checkout")
//    public String checkout(RedirectAttributes redirectAttributes) {
//        List<BasketItemDto> beverages = basketService.getItemsInBasket();
//        boolean hasError = false;
//
//        for (BasketItemDto item : beverages) {
//            BeverageResponseDto beverage = item.getBeverage();
//            int quantity = item.getQuantity();
//            if (quantity > beverage.getInStock()) {
//                redirectAttributes.addAttribute("error", "Quantity exceeds available stock.");
//                redirectAttributes.addAttribute("errorBeverageId", beverage.getId());
//                hasError = true;
//                break;
//            }
//        }
//
//        if (hasError) {
//            return "redirect:/basket";
//        }
//
//        orderService.createOrder();
//        return "redirect:/orders";
//    }
}