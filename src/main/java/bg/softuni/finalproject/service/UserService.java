package bg.softuni.finalproject.service;

import bg.softuni.finalproject.Entity.enums.Gender;
import bg.softuni.finalproject.Entity.enums.TargetGoal;
import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.config.UserSession;
import bg.softuni.finalproject.repo.UserRepository;
import bg.softuni.finalproject.web.dto.LoginDTO;
import bg.softuni.finalproject.web.dto.UserRegisterDTO;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSession userSession;
    private final ModelMapper modelMapper;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserSession userSession, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
        this.modelMapper = modelMapper;
    }

    public boolean register(UserRegisterDTO data) {
        Optional<User> existingUser = userRepository
                .findByUsernameOrEmail(data.getUsername(), data.getEmail());

        if (existingUser.isPresent()) {
            return false;
        }

        User user = modelMapper.map(data, User.class);
        user.setPassword(passwordEncoder.encode(data.getPassword()));
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

    public void updateCurrentWeight(String username, double currentWeight) {
        User user = findByUsername(username);
        user.setCurrentWeight(currentWeight);
        user.setCurrentCalorieIntake(calculateRecommendedCalories(user));
        userRepository.save(user);
    }

    private Double calculateRecommendedCalories(User user) {
        int age = user.getAge();
        double currentWeight = user.getCurrentWeight();
        double recommendedCalories;

        if (user.getGender() == Gender.MALE) {
            recommendedCalories = 10 * currentWeight + 6.25 * user.getHeight() - 5 * age + 5;
        } else {
            recommendedCalories = 10 * currentWeight + 6.25 * user.getHeight() - 5 * age - 161;
        }

        if (user.getTargetGoal() == TargetGoal.LOSS) {
            recommendedCalories -= 300;
        } else if (user.getTargetGoal() == TargetGoal.GAIN) {
            recommendedCalories += 300;
        }

        return recommendedCalories;
    }


    public void updateCurrentCalorieIntake(String username, double currentCalorieIntake) {
        User user = findByUsername(username);
        user.setCurrentCalorieIntake(currentCalorieIntake);
        userRepository.save(user);
    }
}
