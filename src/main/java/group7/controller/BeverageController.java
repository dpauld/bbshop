package group7.controller;

import group7.controllerImpl.BeverageControllerImpl;
import group7.serviceImpl.BeverageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BeverageController implements BeverageControllerImpl {

    private final BeverageServiceImpl beverageService;

    @Autowired
    public BeverageController(BeverageServiceImpl beverageService) {
        this.beverageService = beverageService;
    }

    @GetMapping("/beverages")
    public String getAllBeverages(Model model) {
        model.addAttribute("beverages", beverageService.getDemoBeverages());
        return "beverages";
    }
}
