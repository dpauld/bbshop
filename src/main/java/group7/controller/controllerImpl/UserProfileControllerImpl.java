package group7.controller.controllerImpl;

import group7.controller.UserProfileController;
import group7.dto.AddressRequestDto;
import group7.entity.Order;
import group7.entity.User;
import group7.service.AddressService;
import group7.service.OrderService;
import group7.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/profile")
public class UserProfileControllerImpl implements UserProfileController {

    private final AddressService addressService;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public UserProfileControllerImpl(AddressService addressService, UserService userService, OrderService orderService) {
        this.addressService = addressService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping(value = "/{username}")
    @Override
    public String getProfile(@AuthenticationPrincipal User user, Model model) {
        return "redirect:/profile";
    }

    @Override
    @GetMapping(value = "")
    @Transactional
    public String getUserProfile(@AuthenticationPrincipal User user, Model model) {
        User userData = userService.findByUsername(user.getUsername());
        //used to show the addresses
        model.addAttribute("user", user);
        model.addAttribute("billingAddresses", userData.getBillingAddresses());
        model.addAttribute("deliveryAddresses", userData.getDeliveryAddresses());
        //to create a new address
        model.addAttribute("newAddress", new AddressRequestDto());
        return "profile";
    }

    //for test
    @GetMapping(value = "/json")
    @Transactional
    public ResponseEntity<List<Order>> getUserProfileJson(Model model, @AuthenticationPrincipal User user) {
        User userData = userService.findByUsername(user.getUsername());
        //model.addAttribute("billingAddresses", userData.getBillingAddresses());
        //model.addAttribute("deliveryAddresses", userData.getDeliveryAddresses());
        return new ResponseEntity<>(userData.getOrders(), HttpStatus.OK);
    }

    /** addresses under user **/
    @Override
    @PostMapping("/addresses")
    public String addAddress(@RequestParam("type") String type, @ModelAttribute("newAddress") @Valid AddressRequestDto addressReqDto,
                             BindingResult result, Model model, @AuthenticationPrincipal User user) {
        if (result.hasErrors()) {
            // If there are errors, add billing and delivery addresses again to the model to re-render the existing data
            model.addAttribute("billingAddresses", userService.findByUsername(user.getUsername()).getBillingAddresses());
            model.addAttribute("deliveryAddresses", userService.findByUsername(user.getUsername()).getDeliveryAddresses());
            // Add the form object so it can be re-bound with errors
            model.addAttribute("newAddress", addressReqDto);

            // Return the profile page to re-render the modal with the validation errors
            return "profile";
        }
        // Otherwise, process the address based on type (billing or delivery)
        userService.addAddressByType(user.getId(),type,addressReqDto);
        return "redirect:/profile";
    }

    @Override
    @DeleteMapping("/addresses/{addressId}")
    public String deleteAddress(@PathVariable("addressId") Long addressId, @AuthenticationPrincipal User user) {
        userService.removeAddress(addressId, user.getId());
        return "redirect:/profile";
    }

    //orders under user
    @Override
    @GetMapping(value="/orders")
    public String getOrdersByUser(Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal User user) {
        //LazyIntitalization error
        //List<Order> orders = userService.getUsersOrderById(user.getId());
        List<Order> orders = orderService.findOrdersByUserIdWithItems(user.getId());
        model.addAttribute("orders", orders);
        return "orders";  // Render the order list view
    }

    //test
    //issue:2025-01-08T16:33:39.211+01:00  WARN 139786 --- [group7] [nio-8080-exec-1] .m.m.a.ExceptionHandlerExceptionResolver : Resolved [org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: group7.entity.User.billingAddresses: could not initialize proxy - no Session]
//    @GetMapping(value="/ordersjson",produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<List<Order>> getOrdersByUserr(Model model, RedirectAttributes redirectAttributes, @AuthenticationPrincipal User user) {
//        log.info("getOrdersByUserr");
//        List<Order> orders = orderService.findOrdersByUserIdWithItems(user.getId());
//        //User userData = userService.getUserById(user.getId());
//        //List<Order> orders = userData.getOrders();
//        //User userD = userService.findByIdWithOrders(user.getId());
//        //List<Order> orders = userD.getOrders();
//        log.info(orders.toString());
//        //model.addAttribute("orders", userData.getOrders());
//        return new ResponseEntity<>(orders,HttpStatus.OK); // Render the order list view
//    }
}
