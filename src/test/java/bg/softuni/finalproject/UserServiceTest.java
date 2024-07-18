package bg.softuni.finalproject;

import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.Entity.enums.Gender;
import bg.softuni.finalproject.Entity.enums.TargetGoal;
import bg.softuni.finalproject.config.UserSession;
import bg.softuni.finalproject.repo.UserRepository;
import bg.softuni.finalproject.service.UserService;
import bg.softuni.finalproject.web.dto.LoginDTO;
import bg.softuni.finalproject.web.dto.UserRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserSession userSession;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userSession = mock(UserSession.class);
        userService = new UserService(userRepository, passwordEncoder, userSession);
    }

    //testRegisterUserSuccess: Tests that a new user can be registered successfully.
    @Test
    public void testRegisterUserSuccess() {
        UserRegisterDTO registerDTO = new UserRegisterDTO();
        registerDTO.setUsername("testuser");
        registerDTO.setPassword("password");
        registerDTO.setEmail("test@example.com");
        registerDTO.setAge(25);
        registerDTO.setHeight(180);
        registerDTO.setWeight(75.0);
        registerDTO.setGender(Gender.MALE);
        registerDTO.setTargetGoal(TargetGoal.MAINTAIN);

        when(userRepository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        boolean result = userService.register(registerDTO);

        assertTrue(result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    //testRegisterUserFailExistingUser:
    // Tests that a user cannot be registered if a user with the same username or email already exists.
    @Test
    public void testRegisterUserFailExistingUser() {
        UserRegisterDTO registerDTO = new UserRegisterDTO();
        registerDTO.setUsername("testuser");
        registerDTO.setPassword("password");
        registerDTO.setEmail("test@example.com");

        when(userRepository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.of(new User()));

        boolean result = userService.register(registerDTO);

        assertFalse(result);
        verify(userRepository, times(0)).save(any(User.class));
    }

    //testExistsByUsername: Tests that the existsByUsername method returns true if a user with the given username exists.
    @Test
    public void testExistsByUsername() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        boolean exists = userService.existsByUsername("testuser");

        assertTrue(exists);
        verify(userRepository, times(1)).existsByUsername("testuser");
    }

    //testExistsByEmail: Tests that the existsByEmail method returns true if a user with the given email exists.
    @Test
    public void testExistsByEmail() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        boolean exists = userService.existsByEmail("test@example.com");

        assertTrue(exists);
        verify(userRepository, times(1)).existsByEmail("test@example.com");
    }

    //testValidateUserSuccess: Tests that a user is validated successfully if the username and password match.
    @Test
    public void testValidateUserSuccess() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("testuser");
        loginDTO.setPassword("password");

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        boolean isValid = userService.validateUser(loginDTO);

        assertTrue(isValid);
        verify(userSession, times(1)).login(anyLong(), anyString());
    }

    //testValidateUserFail: Tests that a user is not validated if the username does not exist.
    @Test
    public void testValidateUserFail() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("testuser");
        loginDTO.setPassword("password");

        when(userRepository.findByUsername("testuser")).thenReturn(null);

        boolean isValid = userService.validateUser(loginDTO);

        assertFalse(isValid);
        verify(userSession, times(0)).login(anyLong(), anyString());
    }

    //testFindByUsername: Tests that a user can be found by username.
    @Test
    public void testFindByUsername() {
        User user = new User();
        user.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User foundUser = userService.findByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }

    //testUpdateCurrentWeight: Tests that the current weight of a user is updated correctly.
    @Test
    public void testUpdateCurrentWeight() {
        User user = new User();
        user.setUsername("testuser");
        user.setAge(25);
        user.setHeight(180);
        user.setWeight(75.0);
        user.setCurrentWeight(75.0);
        user.setGender(Gender.MALE);
        user.setTargetGoal(TargetGoal.MAINTAIN);

        when(userRepository.findByUsername("testuser")).thenReturn(user);

        userService.updateCurrentWeight("testuser", 70.0);

        assertEquals(70.0, user.getCurrentWeight());
        verify(userRepository, times(1)).save(user);
    }

    //testUpdateCurrentCalorieIntake: Tests that the current calorie intake of a user is updated correctly.
    @Test
    public void testUpdateCurrentCalorieIntake() {
        User user = new User();
        user.setUsername("testuser");
        user.setCurrentCalorieIntake(2500.0);

        when(userRepository.findByUsername("testuser")).thenReturn(user);

        userService.updateCurrentCalorieIntake("testuser", 2300.0);

        assertEquals(2300.0, user.getCurrentCalorieIntake());
        verify(userRepository, times(1)).save(user);
    }


    //testSessionStateWhenNoUserLoggedIn: This test checks that the session state is correctly
    // identified as not logged in when no user has logged in. It ensures that the isLoggedIn method returns false,
    // the id method returns 0, and the getUsername method returns null.
    @Test
    void testSessionStateWhenNoUserLoggedIn() {
        assertFalse(userSession.isLoggedIn());
        assertEquals(0, userSession.id());
        assertNull(userSession.getUsername());
    }
}
