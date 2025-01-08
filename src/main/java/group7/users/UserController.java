package group7.users;

import group7.dto.AddressRequestDto;
import group7.entity.Address;
import group7.repository.AddressRepository;
import group7.service2.AddressService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping(value = "users")
public class UserController {

    private final UserService userService;
    private final AddressService addressService;
    private UserRepository userRepo;
    private AddressRepository addressRepo;

    @Autowired
    public UserController(UserRepository userRepo, UserService userService, AddressService addressService) {
        this.userRepo = userRepo;
        this.userService = userService;
        this.addressService = addressService;
    }

    @GetMapping
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
