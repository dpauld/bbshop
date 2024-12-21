package group7.controllerImpl;

import group7.controller.BeverageController;
import group7.service.BeverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
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
