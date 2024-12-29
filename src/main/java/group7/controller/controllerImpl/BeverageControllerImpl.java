package group7.controller.controllerImpl;

import group7.controller.BeverageController;
import group7.dto.AddBottleRequestDTO;
import group7.dto.AddCrateRequestDTO;
import group7.dto.BeverageResponseDTO;
import group7.service.BeverageService;
import group7.service.BasketService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.validation.Errors;
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

    @GetMapping("/beverages")
    public String getAllBeverages(Model model) {
        model.addAttribute("bottles", beverageService.getAllBottles());
        model.addAttribute("crates", beverageService.getAllCrates());
        return "beverages";
    }

    @PostMapping("/beverages/add/{id}")
    public String addBeverageToBasket(@PathVariable Long id, HttpSession session) {
        basketService.addBeverageToBasketById(id, session); // pass HttpSession directly
        return "redirect:/beverages"; // redirect back to beverages page after adding the beverage to the basket
    }

    @GetMapping("/admin")
    public String getAllBeverageEditUI(Model model) {
        model.addAttribute("bottles", beverageService.getAllBottles());
        model.addAttribute("crates", beverageService.getAllCrates());
        if (!model.containsAttribute("addBottleRequestDTO"))
            model.addAttribute("addBottleRequestDTO", new AddBottleRequestDTO());
        if (!model.containsAttribute("addCrateRequestDTO"))
            model.addAttribute("addCrateRequestDTO", new AddCrateRequestDTO());
        return "admin";
    }

    @PostMapping("/admin/addBottle")
    public String createNewBottle(@Valid AddBottleRequestDTO addBottleRequestDTO, Errors errors, Model model) {
        System.out.println(errors);
        if (errors.hasErrors()) {
            return getAllBeverageEditUI(model);
        }
        beverageService.addBottle(addBottleRequestDTO);
        return "redirect:/admin";
    }

    @PostMapping("/admin/addCrate")
    public String createNewCrate(@Valid @ModelAttribute AddCrateRequestDTO addCrateRequestDTO, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return getAllBeverageEditUI(model);
        }
        beverageService.addCrate(addCrateRequestDTO);
        return "redirect:/admin";
    }
}
