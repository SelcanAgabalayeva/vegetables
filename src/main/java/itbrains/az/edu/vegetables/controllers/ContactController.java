package itbrains.az.edu.vegetables.controllers;

import itbrains.az.edu.vegetables.dtos.ContactDto;
import itbrains.az.edu.vegetables.models.Cart;
import itbrains.az.edu.vegetables.repositories.CartRepository;
import itbrains.az.edu.vegetables.services.ContactService;
import itbrains.az.edu.vegetables.services.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ContactController {
    private final ContactService contactService;
    private final EmailService emailService;
    private final CartRepository cartRepository;
    public ContactController(ContactService contactService, EmailService emailService, CartRepository cartRepository) {
        this.contactService = contactService;
        this.emailService = emailService;
        this.cartRepository = cartRepository;
    }
    @PostMapping("/contact")
    public String addContact(@ModelAttribute("contact") ContactDto contactDto,
                             Principal principal) {

        contactService.addContact(contactDto);

        String toEmail = contactDto.getEmail();
        String subject = "Təşəkkürlər - Bizimlə əlaqə saxladığınız üçün";

        String username = (principal != null) ? principal.getName() : null;

        StringBuilder productNames = new StringBuilder();
        if (username != null) {
            List<Cart> cartItems = cartRepository.findByUserUsername(username);
            for (Cart item : cartItems) {
                productNames.append("- ").append(item.getProduct().getName()).append("\n");
            }
        } else {
            productNames.append("Səbət məlumatınız mövcud deyil. Zəhmət olmasa hesabınıza daxil olun.");
        }

        String body = "Salam " + contactDto.getName() + ",\n\n" +
                "Mesajınız: " + contactDto.getMessage() + "\n\n" +
                "Səbətinizdə olan məhsullar:\n" + productNames.toString() + "\n" +
                "Bizə yazdığınız üçün təşəkkür edirik. Əlavə suallarınız olarsa, bizimlə əlaqə saxlaya bilərsiniz.\n\n" +
                "Hörmətlə,\nKomanda";

        emailService.sendSimpleEmail(toEmail, subject, body);

        return "redirect:/contact";
    }
}
