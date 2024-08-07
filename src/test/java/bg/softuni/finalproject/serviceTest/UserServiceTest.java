package bg.softuni.finalproject.serviceTest;

import bg.softuni.finalproject.Entity.Quote;
import bg.softuni.finalproject.Entity.Role;
import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.Entity.enums.Gender;
import bg.softuni.finalproject.Entity.enums.RoleEnum;
import bg.softuni.finalproject.Entity.enums.TargetGoal;
import bg.softuni.finalproject.repo.QuoteRepository;
import bg.softuni.finalproject.repo.UserRepository;
import bg.softuni.finalproject.service.UserService;
import bg.softuni.finalproject.web.dto.UserRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private QuoteRepository quoteRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_UserAlreadyExists() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("existingUser");
        userRegisterDTO.setEmail("existing@example.com");

        when(userRepository.findByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.of(new User()));

        boolean result = userService.register(userRegisterDTO);

        assertFalse(result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegister_Successful() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("newUser");
        userRegisterDTO.setEmail("new@example.com");
        userRegisterDTO.setPassword("password");

        User user = new User();
        when(userRepository.findByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.empty());
        when(modelMapper.map(any(UserRegisterDTO.class), eq(User.class)))
                .thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        boolean result = userService.register(userRegisterDTO);

        assertTrue(result);
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals("encodedPassword", user.getPassword());
        assertEquals(RoleEnum.ADMIN, user.getRoles().get(0).getRoleEnum());
    }

    @Test
    void testRegister_SuccessfulNonAdmin() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("newUser");
        userRegisterDTO.setEmail("new@example.com");
        userRegisterDTO.setPassword("password");

        User user = new User();
        when(userRepository.findByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.empty());
        when(modelMapper.map(any(UserRegisterDTO.class), eq(User.class)))
                .thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.findAll()).thenReturn(Collections.singletonList(new User()));

        boolean result = userService.register(userRegisterDTO);

        assertTrue(result);
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals("encodedPassword", user.getPassword());
        assertEquals(RoleEnum.USER, user.getRoles().get(0).getRoleEnum());
    }

    @Test
    void testUpdateCurrentWeight() {
        User user = new User();
        user.setUsername("testUser");
        user.setAge(25);
        user.setHeight(180);
        user.setGender(Gender.MALE);
        user.setTargetGoal(TargetGoal.MAINTAIN);
        user.setCurrentWeight(80.0);

        when(userRepository.findByUsername("testUser")).thenReturn(user);

        userService.updateCurrentWeight("testUser", 85.0);

        assertEquals(85.0, user.getCurrentWeight());
        assertNotNull(user.getCurrentCalorieIntake());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateCurrentCalorieIntake() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findByUsername("testUser")).thenReturn(user);

        userService.updateCurrentCalorieIntake("testUser", 2000.0);

        assertEquals(2000.0, user.getCurrentCalorieIntake());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testResetDailyLoginStatus() {
        User user1 = new User();
        user1.setDailyQuoteShown(true);

        User user2 = new User();
        user2.setDailyQuoteShown(true);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        userService.resetDailyLoginStatus();

        assertFalse(user1.isDailyQuoteShown());
        assertFalse(user2.isDailyQuoteShown());
        verify(userRepository, times(2)).save(any(User.class));
    }

    @Test
    void testGetRandomQuote() {
        Quote quote1 = new Quote();
        quote1.setText("Quote 1");

        Quote quote2 = new Quote();
        quote2.setText("Quote 2");

        when(quoteRepository.count()).thenReturn(2L);
        when(quoteRepository.findAll()).thenReturn(Arrays.asList(quote1, quote2));

        Quote randomQuote = userService.getRandomQuote();

        assertNotNull(randomQuote);
        assertTrue(randomQuote.getText().equals("Quote 1") || randomQuote.getText().equals("Quote 2"));
    }
}
