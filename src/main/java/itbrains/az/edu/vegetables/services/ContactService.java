package itbrains.az.edu.vegetables.services;

import itbrains.az.edu.vegetables.dtos.contact.ContactDto;
import itbrains.az.edu.vegetables.dtos.contact.ContactDashboardDto;

import java.util.List;

public interface ContactService {
    void addContact(ContactDto contactDto);

    List<ContactDashboardDto> findAllContacts();

}
