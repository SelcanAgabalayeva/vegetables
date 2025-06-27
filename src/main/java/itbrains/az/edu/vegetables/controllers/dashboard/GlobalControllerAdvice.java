package itbrains.az.edu.vegetables.controllers.dashboard;

import itbrains.az.edu.vegetables.services.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final UserService userService;

    public GlobalControllerAdvice(UserService userService) {
        this.userService = userService;
    }

    /**  P R O Y E K T İ N  B Ü T Ü N  S Ə H İ F Ə L Ə R İ N Ě */
    @ModelAttribute("username")
    public String addUsername() {
        return userService.getCurrentUsername();
    }
    @ModelAttribute("role")
    public String addUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {

            return authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(role -> role.startsWith("ROLE_"))
                    .findFirst()
                    .map(role -> {
                        switch (role) {
                            case "ROLE_ADMIN": return "Admin";
                            case "ROLE_EDITOR": return "Editor";
                            case "ROLE_USER": return "User";
                            default: return "Bilinməyən";
                        }
                    })
                    .orElse("Bilinməyən");
        }
        return null; // və ya "Qonaq"
    }
}

