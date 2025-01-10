package group7.controller.controllerImpl;

import group7.configuration.customClasses.CustomModelMapper;
import group7.controller.AdminController;
import group7.dto.BeverageCreateDto;
import group7.dto.BeverageResponseDto;
import group7.entity.Order;
import group7.properties.PaginationProperties;
import group7.service.BeverageService;
import group7.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/* request url with prefix admin, there are many others without admin prefix
inside other controllers, giving access to only admin roles  */

@Controller
@RequestMapping("admin/")
public class AdminControllerImpl implements AdminController {
    private final BeverageService beverageService;
    //private final BasketService basketService;
    private final PaginationProperties paginationProperties;
    private final CustomModelMapper modelMapper;
    private final OrderService orderService;

    @Autowired
    public AdminControllerImpl(BeverageService beverageService, PaginationProperties paginationProperties, CustomModelMapper modelMapper, OrderService orderService) {
        this.beverageService = beverageService;
        this.paginationProperties = paginationProperties;
        this.modelMapper = modelMapper;
        this.orderService = orderService;
    }

    @Override
    @GetMapping("/beverages")
    public String getAllBeverages(Model model) {
        //List<Beverage> beverages = beverageService.getAllBeverages();
        //model.addAttribute("beverages", beverageService.getAllBeverages());
        model.addAttribute("beverageCreateDto", new BeverageCreateDto());
        //model.addAttribute("bottleCreateDto", new BottleCreateDto());
        //model.addAttribute("crateCreateDto", new CrateCreateDto());
//        model.addAttribute("bottles", beverageService.getAllBottles());
//        model.addAttribute("crates", beverageService.getAllCrates());
//        return "test";

        List<BeverageResponseDto> beverages = beverageService.getAllBeveragesWithDetails();

        model.addAttribute("beverages", beverages);
        return "bevManagement";
    }

    @Override
    @GetMapping(value="/orders")
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders";  // Render the order list view
    }
}
