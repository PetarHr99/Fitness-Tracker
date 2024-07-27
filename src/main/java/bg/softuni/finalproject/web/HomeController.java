package bg.softuni.finalproject.web;

import bg.softuni.finalproject.config.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final UserSession userSession;

    public HomeController(UserSession userSession) {
        this.userSession = userSession;
    }

    @GetMapping("/")
    public String nonLoggedInIndex(){
        if (userSession.isLoggedIn()){
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/features")
    public String showFeaturesPage() {
        if (userSession.isLoggedIn()){
            return "redirect:/home";
        }
        return "features";
    }

    @GetMapping("/pricing")
    public String showPricingPage(){
        if (userSession.isLoggedIn()){
            return "redirect:/home";
        }
        return "pricing";
    }

    @GetMapping("/contact")
    public String showContactPage(){
        return "contact";
    }
}
