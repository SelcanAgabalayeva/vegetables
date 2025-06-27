package itbrains.az.edu.vegetables;

import itbrains.az.edu.vegetables.models.Role;
import itbrains.az.edu.vegetables.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        Stream.of("ROLE_ADMIN", "ROLE_EDITOR")
                .filter(r -> !roleRepository.existsByName(r))
                .forEach(r -> roleRepository.save(new Role(null, r, new ArrayList<>())));
    }
}

