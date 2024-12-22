package group7.controller.impl;

import group7.controller.UserController;
import group7.dto.CreateUserRequestDTO;
import group7.dto.UserResponseDTO;
import group7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String loginUser(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        UserResponseDTO userResponseDTO = userService.loginUser(username, password);
        if (userResponseDTO != null) {
            model.addAttribute("user", userResponseDTO);
            return "redirect:/beverages";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute CreateUserRequestDTO createUserRequestDTO, Model model) {
        try {
            userService.registerUser(createUserRequestDTO);
            model.addAttribute("success", "Registration successful! Please log in.");
            return "redirect:/user/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
