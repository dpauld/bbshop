package group7.controller.controllerImpl;

import group7.entity.Beverage;
import group7.entity.Bottle;
import group7.entity.Crate;
import group7.service.BeverageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeControllerImpl {
    BeverageService beverageService;

    public HomeControllerImpl(BeverageService beverageService) {
        this.beverageService = beverageService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("alcoholicBeverages", beverageService.getAlcoholicBeverages());
        model.addAttribute("crates",beverageService.getAllCrates());
        model.addAttribute("bottles", beverageService.getAllBottles());
        //model.addAttribute("suppliers", beverageService.getAllSuppliers());
        return "home";
    }
}
