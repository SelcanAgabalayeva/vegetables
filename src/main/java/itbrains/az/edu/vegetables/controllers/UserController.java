package itbrains.az.edu.vegetables.controllers;

import itbrains.az.edu.vegetables.models.User;
import itbrains.az.edu.vegetables.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user-list")
    public String showUserList(Model model, Principal principal) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);


        if (principal != null) {
            String currentUsername = principal.getName();
            model.addAttribute("currentUsername", currentUsername);

            User currentUser = userService.findUser(currentUsername);
            model.addAttribute("currentUser", currentUser);
        }
        return "user-list";
    }
}
