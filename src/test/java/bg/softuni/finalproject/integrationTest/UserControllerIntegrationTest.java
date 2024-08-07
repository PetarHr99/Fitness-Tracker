package bg.softuni.finalproject.integrationTest;

import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.repo.UserRepository;
import bg.softuni.finalproject.service.UserService;
import bg.softuni.finalproject.web.dto.UserRegisterDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testShowRegisterPage() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void testDoRegister_Successful() throws Exception {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("newUser");
        userRegisterDTO.setEmail("new@example.com");
        userRegisterDTO.setAge(25);
        userRegisterDTO.setPassword("password");
        userRegisterDTO.setConfirmPassword("password");
        userRegisterDTO.setHeight(180);
        userRegisterDTO.setWeight(75.0);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterDTO))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        User user = userRepository.findByUsername("newUser");
        assertNotNull(user);
        assertEquals("new@example.com", user.getEmail());
    }

    @Test
    void testDoRegister_UserExists() throws Exception {
        User user = new User();
        user.setUsername("existingUser");
        user.setEmail("existing@example.com");
        userRepository.save(user);

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("existingUser");
        userRegisterDTO.setEmail("new@example.com");
        userRegisterDTO.setPassword("password");
        userRegisterDTO.setConfirmPassword("password");

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterDTO))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"))
                .andExpect(flash().attributeExists("registerData"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.registerData"));
    }

    @Test
    void testShowLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @WithMockUser(username = "testUser")
    void testShowHomePage_QuoteShown() throws Exception {
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setDailyQuoteShown(false);
        userRepository.save(user);

        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("showMessage"))
                .andExpect(model().attributeExists("dailyQuote"))
                .andExpect(model().attribute("showMessage", true))
                .andExpect(view().name("/home"));

        User updatedUser = userRepository.findByUsername("testUser");
        assertNotNull(updatedUser, "User should not be null");
        assertTrue(updatedUser.isDailyQuoteShown(), "Daily quote should be shown");
    }

    @Test
    @WithMockUser(username = "testUser")
    void testShowHomePage_QuoteNotShown() throws Exception {
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setDailyQuoteShown(true);
        userRepository.save(user);

        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("showMessage"))
                .andExpect(model().attribute("showMessage", false))
                .andExpect(view().name("/home"));
    }
}