package group7.controllerImpl;

import group7.controller.BeverageController;
import group7.dto.BottleRequestDTO;
import group7.dto.CrateRequestDTO;
import group7.service.BeverageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class BeverageControllerImpl implements BeverageController {

    private final BeverageService beverageService;

    @Autowired
    public BeverageControllerImpl(BeverageService beverageService) {
        this.beverageService = beverageService;
    }

    @GetMapping("/beverages")
    public String getAllBeverages(Model model) {
        model.addAttribute("bottles", beverageService.getAllBottles());
        model.addAttribute("crates", beverageService.getAllCrates());
        return "sold_beverages";
    }

    @GetMapping("/admin")
    public String getAllBeverageEditUI(Model model) {
        model.addAttribute("bottles", beverageService.getAllBottles());
        model.addAttribute("crates", beverageService.getAllCrates());
        // Form prototypes:
        model.addAttribute("bottle", new BottleRequestDTO());
        model.addAttribute("crate", new CrateRequestDTO());
        return "admin_beverages";
    }

    @PostMapping("/admin/addBottle")
    public String createNewBottle(@Valid BottleRequestDTO bottle, Errors errors, Model model) {
        // TODO
        return "redirect:/admin";
    }

    @PostMapping("/admin/addCrate")
    public String createNewCrate(@Valid CrateRequestDTO crate, Errors errors, Model model) {
        // TODO
        return "redirect:/admin";
    }
}
