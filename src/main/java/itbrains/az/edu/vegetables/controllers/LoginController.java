package itbrains.az.edu.vegetables.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class LoginController {
    @GetMapping("/register")
    public String register(Model model) {

        return "register.html";
    }
//    @PostMapping("/register")
//    public String register(RegisterDto registerDto) {
//        boolean result=userService.registerUser(registerDto);
//        if(result){
//            return "redirect:/register";
//        }
//        return "redirect:/register";
//
//    }
    @GetMapping("/login")
    public String login() {
        return "register.html";

    }
}
