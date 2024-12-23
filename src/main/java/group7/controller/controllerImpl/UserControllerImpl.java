package group7.controller.controllerImpl;

import group7.controller.UserController;
import group7.dto.AddAddressRequestDTO;
import group7.dto.CreateUserRequestDTO;
import group7.dto.UserResponseDTO;
import group7.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession httpSession) {
        try {
            UserResponseDTO userResponseDTO = userService.loginUser(username, password);
            httpSession.setAttribute("user", userResponseDTO);
            return "redirect:/beverages";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        AddAddressRequestDTO addAddressRequestDTO = new AddAddressRequestDTO();
        CreateUserRequestDTO createUserRequestDTO = new CreateUserRequestDTO();
        model.addAttribute("addAddressRequestDTO", addAddressRequestDTO);
        model.addAttribute("createUserRequestDTO", createUserRequestDTO);
        return "register";
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute CreateUserRequestDTO createUserRequestDTO, @ModelAttribute AddAddressRequestDTO addAddressRequestDTO, Model model) {
        try {
            var addresses = List.of(addAddressRequestDTO);
            createUserRequestDTO.setBillingAddresses(new ArrayList<>(addresses));
            createUserRequestDTO.setDeliveryAddresses(new ArrayList<>(addresses));
            userService.registerUser(createUserRequestDTO);
            model.addAttribute("success", "Registration successful! Please log in.");
            return "redirect:/user/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
