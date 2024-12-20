package group7.controller.impl;

import group7.controller.BeverageController;
import group7.service.BeverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BeverageControllerImpl implements BeverageController {

    private final BeverageService beverageService;

    @Autowired
    public BeverageControllerImpl(BeverageService beverageService) {
        this.beverageService = beverageService;
    }

    @GetMapping("/beverages")
    public String getAllBeverages(Model model) {
        model.addAttribute("beverages", beverageService.getDemoBeverages());
        return "beverages";
    }
}
