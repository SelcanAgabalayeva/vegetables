package itbrains.az.edu.vegetables.repositories;

import itbrains.az.edu.vegetables.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);



}

