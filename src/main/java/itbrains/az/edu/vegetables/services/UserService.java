package itbrains.az.edu.vegetables.services;

import itbrains.az.edu.vegetables.dtos.RegisterDto;
import itbrains.az.edu.vegetables.models.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean registerUser(RegisterDto registerDto);
    User findUser(String email);

    void addRoleToUser(String username, String roleName);

    String getCurrentUsername();


    List<User> getAllUsers();


    UserDetails loadUserByUsername(String username);

    void deleteUserById(Long id);

    void updateUserRole(Long id, String role);
}
