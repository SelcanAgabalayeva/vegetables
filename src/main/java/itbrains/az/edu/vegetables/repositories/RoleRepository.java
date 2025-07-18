package itbrains.az.edu.vegetables.repositories;

import itbrains.az.edu.vegetables.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    boolean existsByName(String r);
    Optional<Role> findByName(String name);
}
