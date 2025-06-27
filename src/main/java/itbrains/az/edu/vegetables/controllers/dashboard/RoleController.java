package itbrains.az.edu.vegetables.controllers.dashboard;

import itbrains.az.edu.vegetables.models.Role;
import itbrains.az.edu.vegetables.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public String listRoles(Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("newRole", new Role());
        return "dashboard/role/role-dashboard";
    }
    @GetMapping("/delete/{id}")
    public String deleteRole(@PathVariable Long id) {
        roleRepository.deleteById(id);
        return "redirect:dashboard/role/role-dashboard";
    }

}
