package itbrains.az.edu.vegetables.controllers;

import itbrains.az.edu.vegetables.dtos.RegisterDto;
import itbrains.az.edu.vegetables.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    @GetMapping("/register")
    public String register(Model model) {


        return "register.html";
    }
    @PostMapping("/register")
    public String register(RegisterDto registerDto) {
        boolean result=userService.registerUser(registerDto);
        if(result){
            return "redirect:/register";
        }
        return "redirect:/register";

    }
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        Model model) {
        if (error != null) {
            model.addAttribute("loginError", "Kod məlumatları səhvdir.");
        }
        return "register.html";
    }



}
