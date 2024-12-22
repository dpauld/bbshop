package group7.controller.controllerImpl;

import group7.controller.BeverageController;
import group7.dto.BeverageResponseDTO;
import group7.service.BeverageService;
import group7.service.BasketService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BeverageControllerImpl implements BeverageController {

    private final BeverageService beverageService;
    private final BasketService basketService;

    @Autowired
    public BeverageControllerImpl(BeverageService beverageService, BasketService basketService) {
        this.beverageService = beverageService;
        this.basketService = basketService;
    }

    // This method will be executed immediately after the object is created
    @PostConstruct
    public void init() {
        List<BeverageResponseDTO> beverages = getDemoBeverages();
    }



    @GetMapping("/beverages")
    public String getAllBeverages(Model model) {
        model.addAttribute("beverages", beverageService.getDemoBeverages());
        return "beverages";
    }

    @PostMapping("/beverages/add/{id}")
    public String addBeverageToBasket(@PathVariable Long id, HttpSession session) {
        basketService.addBeverageToBasketById(id, session); // pass HttpSession directly
        return "redirect:/beverages"; // redirect back to beverages page after adding the beverage to the basket
    }

    @Override
    public List<BeverageResponseDTO> getDemoBeverages() {
        return beverageService.getDemoBeverages();
    }


}
