package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.RegisterDto;
import itbrains.az.edu.vegetables.models.User;
import itbrains.az.edu.vegetables.repositories.UserRepository;
import itbrains.az.edu.vegetables.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean registerUser(RegisterDto registerDto) {
        User findUser=userRepository.findByUsername(registerDto.getUsername());
        if(findUser !=null){
            return false;
        }

        User user=new User();

        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());


        String password=passwordEncoder.encode(registerDto.getPassword());
        user.setPassword(password);
        userRepository.save(user);
        return true;
    }

    @Override
    public User findUser(String email) {
        return userRepository.findByUsername(email);
    }
}
