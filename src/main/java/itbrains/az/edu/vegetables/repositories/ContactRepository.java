package itbrains.az.edu.vegetables.repositories;

import itbrains.az.edu.vegetables.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact,Long> {


}
