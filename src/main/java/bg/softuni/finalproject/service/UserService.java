package bg.softuni.finalproject.service;

import bg.softuni.finalproject.Entity.Quote;
import bg.softuni.finalproject.Entity.Role;
import bg.softuni.finalproject.Entity.enums.Gender;
import bg.softuni.finalproject.Entity.enums.RoleEnum;
import bg.softuni.finalproject.Entity.enums.TargetGoal;
import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.repo.QuoteRepository;
import bg.softuni.finalproject.repo.UserRepository;
import bg.softuni.finalproject.web.dto.UserRegisterDTO;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final QuoteRepository quoteRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,ModelMapper modelMapper, QuoteRepository quoteRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.quoteRepository = quoteRepository;
    }

    public boolean register(UserRegisterDTO data) {
        Optional<User> existingUser = userRepository
                .findByUsernameOrEmail(data.getUsername(), data.getEmail());

        if (existingUser.isPresent()) {
            return false;
        }

        User user = modelMapper.map(data, User.class);
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        user.setDailyQuoteShown(false);

        Role role;
        if (userRepository.findAll().isEmpty()) {
            role = new Role();
            role.setRoleEnum(RoleEnum.ADMIN);
        } else {
            role = new Role();
            role.setRoleEnum(RoleEnum.USER);
        }

        role.setRolesAddedBy(user);
        user.setRoles(Collections.singletonList(role));

        this.userRepository.save(user);

        return true;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
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

    public void resetDailyLoginStatus() {
        userRepository.findAll().forEach(user -> {
            user.setDailyQuoteShown(false);
            userRepository.save(user);
        });
    }

    public Quote getRandomQuote() {
        long count = quoteRepository.count();
        int index = new Random().nextInt((int) count);
        return quoteRepository.findAll().get(index);
    }

    public void updateUserDailyQuoteStatus(User user){
       user.setDailyQuoteShown(true);
       userRepository.save(user);
    }
}
