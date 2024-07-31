package bg.softuni.finalproject.web;

import bg.softuni.finalproject.config.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String nonLoggedInIndex(){
        return "index";
    }

    @GetMapping("/features")
    public String showFeaturesPage() {
        return "features";
    }

    @GetMapping("/pricing")
    public String showPricingPage(){
        return "pricing";
    }

    @GetMapping("/contact")
    public String showContactPage(){
        return "contact";
    }
}
