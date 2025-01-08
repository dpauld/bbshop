package group7.controller.controllerImpl;

import group7.controller.OrderController;
import group7.entity.Order;
import group7.service.OrderService;
import group7.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderControllerImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public String createOrder(RedirectAttributes redirectAttributes, @AuthenticationPrincipal User user) {
        Order order = orderService.createOrder(user.getId());
        redirectAttributes.addFlashAttribute("message", "Order created successfully!");
        return "redirect:/orders";
    }

    @Override
    @GetMapping(value="")
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders";  // Render the order list view
    }

//    @GetMapping(value="/json")
//    public ResponseEntity<List<Order>> getAllOrdersJson(Model model) {
//        List<Order> orders = orderService.getAllOrders();
//        //model.addAttribute("orders", orders);
//        return new ResponseEntity<>(orders,HttpStatus.OK);  // Render the order list view
//    }
//
    @DeleteMapping(value="/{id}")
    public String deleteOrderById(Model model, @PathVariable Long id) {
        Boolean status = orderService.deleteOrderById(id);
        model.addAttribute("message", status);
        return "redirect:/orders";  // Render the order list view
    }

    @GetMapping(value="/{id}")
    public String getOrderById(Model model, @PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "order";  // Render the order list view
    }
}
