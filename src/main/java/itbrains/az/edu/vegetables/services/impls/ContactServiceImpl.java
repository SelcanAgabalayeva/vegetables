package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.ContactDto;
import itbrains.az.edu.vegetables.models.Contact;
import itbrains.az.edu.vegetables.repositories.ContactRepository;
import itbrains.az.edu.vegetables.services.ContactService;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public void addContact(ContactDto contactDto) {
        Contact contact=new Contact();
        contact.setName(contactDto.getName());
        contact.setEmail(contactDto.getEmail());
        contact.setMessage(contactDto.getMessage());
        contactRepository.save(contact);
    }
}
