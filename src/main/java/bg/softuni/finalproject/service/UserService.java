package bg.softuni.finalproject.service;

import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.config.UserSession;
import bg.softuni.finalproject.repo.UserRepository;
import bg.softuni.finalproject.web.dto.LoginDTO;
import bg.softuni.finalproject.web.dto.UserRegisterDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSession userSession;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserSession userSession) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
    }

    public boolean register(UserRegisterDTO data) {
        Optional<User> existingUser = userRepository
                .findByUsernameOrEmail(data.getUsername(), data.getEmail());

        if (existingUser.isPresent()) {
            return false;
        }

        User user = new User();

        user.setUsername(data.getUsername());
        user.setEmail(data.getEmail());
        user.setAge(data.getAge());
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        user.setSubscriptionPlan(data.getSubscriptionPlan());
        user.setHeight(data.getHeight());
        user.setWeight(data.getWeight());
        user.setGender(data.getGender());
        user.setTargetGoal(data.getTargetGoal());

        this.userRepository.save(user);

        return true;
    }


    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean validateUser(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());

        if (user != null && passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            userSession.login(user.getId(), loginDTO.getUsername());
            return true;
        }

        return false;
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
