package itbrains.az.edu.vegetables.services.impls;

import itbrains.az.edu.vegetables.dtos.RegisterDto;
import itbrains.az.edu.vegetables.models.Role;
import itbrains.az.edu.vegetables.models.User;
import itbrains.az.edu.vegetables.repositories.RoleRepository;
import itbrains.az.edu.vegetables.repositories.UserRepository;
import itbrains.az.edu.vegetables.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

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
        Role userRole = getOrCreateRole("ROLE_USER");
        user.getRoles().add(userRole);
        user.setPassword(password);
        userRepository.save(user);
        return true;
    }

    @Override
    public User findUser(String email) {
        return userRepository.findByUsername(email);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("İstifadəçi tapılmadı: " + username);
        }

        Role role = getOrCreateRole(roleName);
        user.getRoles().add(role);
    }
    private Role getOrCreateRole(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(null, roleName, new ArrayList<>())));
    }
    @Override
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("İstifadəçi tapılmadı: " + username);
        }

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())) // "ROLE_ADMIN", "ROLE_USER"
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı"));
        user.getRoles().clear();
        userRepository.save(user);

        userRepository.deleteById(id);
    }

    @Override
    public void updateUserRole(Long id, String name) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().clear();
        user.getRoles().add(role);

        userRepository.save(user);
    }
    }



