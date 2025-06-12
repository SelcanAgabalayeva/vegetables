package itbrains.az.edu.vegetables.services;

import itbrains.az.edu.vegetables.dtos.RegisterDto;
import itbrains.az.edu.vegetables.models.User;

public interface UserService {
    boolean registerUser(RegisterDto registerDto);
    User findUser(String email);
}
