package itbrains.az.edu.vegetables.controllers.dashboard;

import itbrains.az.edu.vegetables.dtos.contact.ContactDashboardDto;
import itbrains.az.edu.vegetables.services.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ContactsController {
    private final ContactService contactService;

    public ContactsController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/admin/contact")
    public String contact(Model model) {
        List<ContactDashboardDto> contacts =contactService.findAllContacts();
        model.addAttribute("contacts", contacts);
        return "/dashboard/contact/index";
    }
}
