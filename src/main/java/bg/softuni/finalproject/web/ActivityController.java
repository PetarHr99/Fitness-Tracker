package bg.softuni.finalproject.web;

import bg.softuni.finalproject.Entity.Activity;
import bg.softuni.finalproject.Entity.User;
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

    @Autowired
    public ActivityController(ActivityService activityService, UserService userService) {
        this.activityService = activityService;
        this.userService = userService;
    }
    @ModelAttribute("activityDTO")
    public ActivityDTO activityDTO() {
        return new ActivityDTO();
    }


    @GetMapping("/activity-all/activity")
    public String viewActivitiesPage(Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("activities", activityService.getActivitiesByUser(currentUser));
        return "/activity-all/activity";
    }

    @GetMapping("/activity-all/add-activity")
    public String showAddActivityForm(Model model) {
        if (userService.getCurrentUser() == null) {
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

        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        Activity activity = activityService.saveActivity(activityDTO, currentUser);
        return "redirect:/activity-all/activity";
    }

    @PostMapping("/activity/delete/{id}")
    public String deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return "redirect:/activity-all/activity";
    }
}

