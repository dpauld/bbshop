package group7.controller.controllerImpl;

import group7.controller.UserController;
import group7.entity.User;
import group7.repository.AddressRepository;
import group7.repository.UserRepository;
import group7.service.AddressService;
import group7.service.UserService;
import group7.users.UserUpdateForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Slf4j
@Controller
@RequestMapping(value = "users")
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private final AddressService addressService;
    private UserRepository userRepo;
    private AddressRepository addressRepo;

    @Autowired
    public UserControllerImpl(UserRepository userRepo, UserService userService, AddressService addressService) {
        this.userRepo = userRepo;
        this.userService = userService;
        this.addressService = addressService;
    }

    @GetMapping
    @Override
    public String getAllUsers(Model model) {

        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("roles", Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
        model.addAttribute("userUpdateForm", new UserUpdateForm());

        return "users";
    }

    @PostMapping(value = "{username}/roles")
    public String changeUserRight(@PathVariable(value = "username") String username, UserUpdateForm formData) {

        log.info("Change user: " + username + " " + formData.toString());

        User user = this.userRepo.findUserByUsername(username);
        if (user == null || formData.getNewRole().isEmpty()) {
            return "redirect:/";
        }

        user.setRole(formData.getNewRole());
        this.userRepo.save(user);

        return "redirect:/users";
    }

    //might not be needed.
//    @PostMapping("/{username}/update-profile")
//    public String updateProfile(@PathVariable Long username, @ModelAttribute User userData, @AuthenticationPrincipal User user) {
//        //user.setFull(user.getFullName());
//        //existingUser.setBirthday(user.getBirthday());
//        //userService.save(existingUser);
//        return "redirect:/api/users/" + username + "/edit-profile";
//    }

//    @GetMapping("/updateBillingAddress")
//    public String showUpdateBillingAddressForm(Model model) {
//        model.addAttribute("address", new Address());
//        return "update_billing_address";
//    }
//
//    @PostMapping("/updateBillingAddress")
//    public String updateBillingAddress(@RequestParam Long userId, @ModelAttribute Address address, RedirectAttributes redirectAttributes) {
//        userService.updateBillingAddress(userId, address);
//        redirectAttributes.addFlashAttribute("success", "Billing address updated successfully!");
//        return "redirect:/user/profile";
//    }
//
//    @GetMapping("/updateDeliveryAddress")
//    public String showUpdateDeliveryAddressForm(Model model) {
//        model.addAttribute("address", new Address());
//        return "update_delivery_address";
//    }
//
//    @PostMapping("/updateDeliveryAddress")
//    public String updateDeliveryAddress(@RequestParam Long userId, @ModelAttribute Address address, RedirectAttributes redirectAttributes) {
//        userService.updateDeliveryAddress(userId, address);
//        redirectAttributes.addFlashAttribute("success", "Delivery address updated successfully!");
//        return "redirect:/user/profile";
//    }
}
