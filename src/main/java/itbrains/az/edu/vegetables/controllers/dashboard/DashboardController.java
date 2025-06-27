package itbrains.az.edu.vegetables.controllers.dashboard;

import itbrains.az.edu.vegetables.services.UserService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Controller
@PreAuthorize("isAuthenticated()")
public class DashboardController {
    private final UserService userService;


    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String admin(Model model) throws MessagingException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Auth object: " + authentication);
        if (authentication != null) {
            System.out.println("Is authenticated: " + authentication.isAuthenticated());
            System.out.println("Authorities: " + authentication.getAuthorities());
            System.out.println("Name: " + authentication.getName());
        } else {
            System.out.println("Authentication is null!");
        }

        String currentUsername = userService.getCurrentUsername();
        System.out.println("Current username: " + currentUsername);
        if (currentUsername != null) {
            model.addAttribute("username", currentUsername);
        } else {
            model.addAttribute("username", "Qonaq");
        }

        // --- GMAIL MƏSCAJLARININ OXUNMASI ---
        List<SimpleMail> simpleMails = new ArrayList<>();

        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        Session session = Session.getInstance(props, null);

        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", "selcanagabalayeva12@gmail.com", "budp lsfs vpcs kjbt"); // app password

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        Message[] messages = inbox.getMessages();

        int start = Math.max(0, messages.length - 5);
        for (int i = messages.length - 1; i >= start; i--) {
            Message msg = messages[i];

            String from = "";
            if (msg.getFrom() != null && msg.getFrom().length > 0) {
                Address address = msg.getFrom()[0];
                if (address instanceof InternetAddress) {
                    InternetAddress internetAddress = (InternetAddress) address;
                    from = internetAddress.getAddress();
                } else {
                    from = address.toString();
                }
            }

            String subject = msg.getSubject();
            Date sentDate = msg.getSentDate();

            simpleMails.add(new SimpleMail(from, subject, sentDate));
        }

        model.addAttribute("messages", simpleMails);

        inbox.close(false);
        store.close();
        return "/dashboard/index";
    }
    public static class SimpleMail {
        private String from;
        private String subject;
        private Date sentDate;

        public SimpleMail(String from, String subject, Date sentDate) {
            this.from = from;
            this.subject = subject;
            this.sentDate = sentDate;
        }

        public String getFrom() { return from; }
        public String getSubject() { return subject; }
        public Date getSentDate() { return sentDate; }
    }
    @GetMapping("/admin/login")
    public String showAdminLoginPage() {
        return "/dashboard/login";
    }

}

