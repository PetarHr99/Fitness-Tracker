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

    public UserController(UserService userService) {
        this.userService = userService;
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
        return "/login";
    }


    @GetMapping("/home")
    public String showHomePage(Model model) {


        User user = userService.findByUsername("pepi_dx");

        if (user.isDailyQuoteShown() == false){
            Quote quote = userService.getRandomQuote();
            model.addAttribute("showMessage", true);
            model.addAttribute("dailyQuote", quote.getText());
            userService.updateUserDailyQuoteStatus(user);

        } else {
            model.addAttribute("showMessage", false);
        }

        model.addAttribute("username", user.getUsername());

        return "/home";
    }


}
