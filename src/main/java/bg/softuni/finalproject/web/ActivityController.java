package bg.softuni.finalproject.web;

import bg.softuni.finalproject.Entity.Activity;
import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.config.UserSession;
import bg.softuni.finalproject.service.ActivityService;
import bg.softuni.finalproject.service.UserService;
import bg.softuni.finalproject.web.dto.ActivityDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ActivityController {
    private final ActivityService activityService;
    private final UserService userService;
    private final UserSession userSession;

    @Autowired
    public ActivityController(ActivityService activityService, UserService userService, UserSession userSession) {
        this.activityService = activityService;
        this.userService = userService;
        this.userSession = userSession;
    }
    @ModelAttribute("activityDTO")
    public ActivityDTO activityDTO() {
        return new ActivityDTO();
    }

    @GetMapping("/activity-all/activity")
    public String viewActivitiesPage(Model model) {
        model.addAttribute("activities", activityService.getAllActivities());
        return "/activity-all/activity";
    }

    @GetMapping("/activity/add-activity")
    public String showAddActivityForm(Model model) {
        model.addAttribute("activity", new ActivityDTO());
        return "/activity-all/add-activity";
    }

    @PostMapping("/activity-all/add-activity")
    public String saveActivity(@Valid ActivityDTO activityDTO,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()){

            System.out.println("Binding error");
            System.out.println(bindingResult.getFieldErrorCount());
            System.out.println(bindingResult.getFieldErrors());
            bindingResult.getFieldErrors().forEach(err -> System.out.println(err.getField()));

            return "/activity-all/add-activity";
        }

        String username = userSession.getUsername();
        if (username == null || !userSession.isLoggedIn()) {
            return "redirect:/login";
        }

        User currentUser = userService.findByUsername(username);
        System.out.println("Received ActivityDTO: " + activityDTO);
        Activity activity = activityService.saveActivity(activityDTO, currentUser);
        return "redirect:/activity-all/activity";
    }

    @PostMapping("/activity/delete/{id}")
    public String deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return "redirect:/activity-all/activity";
    }
}

