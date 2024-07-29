package bg.softuni.finalproject.web;

import bg.softuni.finalproject.Entity.Quote;
import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.config.UserSession;
import bg.softuni.finalproject.repo.UserRepository;
import bg.softuni.finalproject.service.UserService;
import bg.softuni.finalproject.web.dto.LoginDTO;
import bg.softuni.finalproject.web.dto.UserRegisterDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    private final UserService userService;
    private final UserSession userSession;

    public UserController(UserService userService, UserSession userSession) {
        this.userService = userService;
        this.userSession = userSession;

    }

    @ModelAttribute("registerData")
    public UserRegisterDTO registerDTO() {
        return new UserRegisterDTO();
    }

    @ModelAttribute("loginData")
    public LoginDTO loginDTO() {
        return new LoginDTO();
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(
            @Valid UserRegisterDTO data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {


        if (data == null || bindingResult == null || redirectAttributes == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        // Validate passwords match
        if (!data.getPassword().equals(data.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.registerData", "Passwords do not match");

        }

        // Check if username exists
        if (userService.existsByUsername(data.getUsername())) {
            bindingResult.rejectValue("username", "error.registerData", "Username is already occupied");

        }

        // Check if email exists
        if (userService.existsByEmail(data.getEmail())) {
            bindingResult.rejectValue("email", "error.registerData", "Email is already registered");
        }

        // Handle validation errors
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerData", data);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.registerData", bindingResult);
            return "redirect:/register";
        }

        // Attempt to register the user
        boolean success;
        try {
            success = userService.register(data);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("registrationError", "Registration failed due to an unexpected error. Please try again.");
            return "redirect:/register";
        }

        // Handle registration failure
        if (!success) {
            redirectAttributes.addFlashAttribute("registrationError", "Registration failed. Please try again.");
            return "redirect:/register";
        }

        // Redirect to login on successful registration
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(
            @Valid LoginDTO loginDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        if (!userService.validateUser(loginDTO, session)) {
            bindingResult.reject("loginError", "Invalid username or password");
            session.setAttribute("loginError", "Invalid username or password");
        }else {
            session.removeAttribute("loginError");
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("loginData", loginDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.loginData", bindingResult);
            return "redirect:/login";
        }


        return "redirect:/home";
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        if (!userSession.isLoggedIn()){
            return "redirect:/login";
        }

        User user = userService.findByUsername(userSession.getUsername());

        System.out.println(user.isDailyQuoteShown());

        if (user.isDailyQuoteShown() == false){
            Quote quote = userService.getRandomQuote();
            model.addAttribute("showMessage", true);
            model.addAttribute("dailyQuote", quote.getText());
            userService.updateUserDailyQuoteStatus(user);

        } else {
            model.addAttribute("showMessage", false);
        }

        model.addAttribute("username", userSession.getUsername());

        return "/home";
    }

    @GetMapping("/logout")
    public String logout() {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }
        userSession.logout();
        return "redirect:/";
    }

}
