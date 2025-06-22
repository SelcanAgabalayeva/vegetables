package itbrains.az.edu.vegetables.controllers;

import itbrains.az.edu.vegetables.dtos.contact.ContactDto;
import itbrains.az.edu.vegetables.models.Cart;
import itbrains.az.edu.vegetables.models.User;
import itbrains.az.edu.vegetables.repositories.CartRepository;
import itbrains.az.edu.vegetables.repositories.UserRepository;
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
    private final UserRepository userRepository;
    public ContactController(ContactService contactService, EmailService emailService, CartRepository cartRepository, UserRepository userRepository) {
        this.contactService = contactService;
        this.emailService = emailService;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }
    @PostMapping("/contact")
    public String addContact(@ModelAttribute("contact") ContactDto contactDto,
                             Principal principal) {

        contactService.addContact(contactDto);

        String toEmail = contactDto.getEmail();
        String subject = "Təşəkkürlər - Bizimlə əlaqə saxladığınız üçün";

        StringBuilder productNames = new StringBuilder();

        if (principal != null) {
            String username = principal.getName();
            User user = userRepository.findByUsername(username);

            // Əgər istifadəçinin sistemdəki emaili ilə formdakı email eynidirsə
            if (user != null && user.getEmail().equalsIgnoreCase(toEmail)) {
                List<Cart> cartItems = cartRepository.findByUserUsername(username);
                if (!cartItems.isEmpty()) {
                    productNames.append("Səbətinizdə olan məhsullar:\n");
                    for (Cart item : cartItems) {
                        productNames.append("- ").append(item.getProduct().getName()).append("\n");
                    }
                } else {
                    productNames.append("Səbətiniz hazırda boşdur.");
                }
            } else {
                productNames.append("Səbət məlumatınız mövcud deyil – email uyğun gəlmir.");
            }
        } else {
            productNames.append("Səbət məlumatınız mövcud deyil – sistemə daxil deyilsiniz.");
        }

        String body = "Salam " + contactDto.getName() + ",\n\n" +
                "Mesajınız: " + contactDto.getMessage() + "\n\n" +
                productNames.toString() + "\n\n" +
                "Bizə yazdığınız üçün təşəkkür edirik. Əlavə suallarınız olarsa, bizimlə əlaqə saxlaya bilərsiniz.\n\n" +
                "Hörmətlə,\nKomanda";

        emailService.sendSimpleEmail(toEmail, subject, body);

        return "redirect:/contact";
    }
}
