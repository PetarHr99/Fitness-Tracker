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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        if (!userSession.isLoggedIn()){
            return "redirect:/login";
        }
        String username = userSession.getUsername();
        User currentUser = userService.findByUsername(username);

        model.addAttribute("activities", activityService.getActivitiesByUser(currentUser));
        return "/activity-all/activity";
    }

    @GetMapping("/activity-all/add-activity")
    public String showAddActivityForm(Model model) {
        if (!userSession.isLoggedIn()){
            return "redirect:/login";
        }
        model.addAttribute("activity", new ActivityDTO());
        return "/activity-all/add-activity";
    }

    @PostMapping("/activity-all/add-activity")
    public String saveActivity(@Valid ActivityDTO activityDTO,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("activityDTO", activityDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.activityDTO", bindingResult);

            return "redirect:/activity-all/add-activity";
        }

        String username = userSession.getUsername();
        if (username == null || !userSession.isLoggedIn()) {
            return "redirect:/login";
        }

        User currentUser = userService.findByUsername(username);
        Activity activity = activityService.saveActivity(activityDTO, currentUser);
        return "redirect:/activity-all/activity";
    }

    @PostMapping("/activity/delete/{id}")
    public String deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return "redirect:/activity-all/activity";
    }
}

