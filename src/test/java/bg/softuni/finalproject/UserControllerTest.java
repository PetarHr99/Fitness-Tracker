package bg.softuni.finalproject;

import bg.softuni.finalproject.config.UserSession;
import bg.softuni.finalproject.service.UserService;
import bg.softuni.finalproject.web.UserController;
import bg.softuni.finalproject.web.dto.LoginDTO;
import bg.softuni.finalproject.web.dto.UserRegisterDTO;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserSession userSession;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Mockito.reset(userService, userSession);
//        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService, userSession)).build();
    }

    @Test
    void testShowRegisterPage() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void testDoRegisterWithValidData() throws Exception {
        when(userService.register(any(UserRegisterDTO.class))).thenReturn(true);

        mockMvc.perform(post("/register")
                        .param("username", "testuser")
                        .param("email", "test@example.com")
                        .param("password", "password")
                        .param("confirmPassword", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService, times(1)).register(any(UserRegisterDTO.class));
    }

    @Test
    void testDoRegisterWithInvalidData() throws Exception {
        when(userService.existsByUsername(anyString())).thenReturn(true);

        mockMvc.perform(post("/register")
                        .param("username", "testuser")
                        .param("email", "test@example.com")
                        .param("password", "password")
                        .param("confirmPassword", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"));

        verify(userService, times(0)).register(any(UserRegisterDTO.class));
    }

    @Test
    void testShowLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testDoLoginWithValidData() throws Exception {
        when(userService.validateUser(any(LoginDTO.class))).thenReturn(true);

        mockMvc.perform(post("/login")
                        .param("username", "testuser")
                        .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

        verify(userService, times(1)).validateUser(any(LoginDTO.class));
    }

    @Test
    void testDoLoginWithInvalidData() throws Exception {
        when(userService.validateUser(any(LoginDTO.class))).thenReturn(false);

        mockMvc.perform(post("/login")
                        .param("username", "testuser")
                        .param("password", "wrongpassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService, times(1)).validateUser(any(LoginDTO.class));
    }

    @Test
    void testShowHomePage() throws Exception {
        when(userSession.getUsername()).thenReturn("testuser");

        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("username", "testuser"));
    }

    @Test
    void testLogout() throws Exception {
        when(userSession.isLoggedIn()).thenReturn(true);

        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(userSession, times(1)).logout();
    }

    @Test
    void testLogoutWhenNotLoggedIn() throws Exception {
        when(userSession.isLoggedIn()).thenReturn(false);

        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(userSession, times(0)).logout();
    }
}
