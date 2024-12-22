package group7.controllerImpl;

import group7.controller.OrderController;
import group7.dto.CreateOrderRequestDTO;
import group7.dto.OrderResponseDTO;
import group7.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderControllerImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    @GetMapping
    public String getAllOrders(Model model) {
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order/list";  // Render the order list view
    }




}
