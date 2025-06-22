package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.contact.ContactDto;
import itbrains.az.edu.vegetables.dtos.contact.ContactDashboardDto;
import itbrains.az.edu.vegetables.models.Contact;
import itbrains.az.edu.vegetables.repositories.ContactRepository;
import itbrains.az.edu.vegetables.services.ContactService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    public ContactServiceImpl(ContactRepository contactRepository, ModelMapper modelMapper) {
        this.contactRepository = contactRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addContact(ContactDto contactDto) {
        Contact contact=new Contact();
        contact.setName(contactDto.getName());
        contact.setEmail(contactDto.getEmail());
        contact.setMessage(contactDto.getMessage());
        contactRepository.save(contact);
    }

    @Override
    public List<ContactDashboardDto> findAllContacts() {
        List<ContactDashboardDto> contacts = contactRepository.findAll().stream().map(x->modelMapper.map(x,ContactDashboardDto.class)).collect(Collectors.toList());
        return contacts;
    }
}
