package bg.softuni.finalproject.web;

import bg.softuni.finalproject.Entity.Meal;
import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.config.UserSession;
import bg.softuni.finalproject.service.MealService;
import bg.softuni.finalproject.service.UserService;
import bg.softuni.finalproject.web.dto.MealDTO;
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
public class MealController {
    private final MealService mealService;
    private final UserService userService;
    private final UserSession userSession;

    @Autowired
    public MealController(MealService mealService, UserService userService, UserSession userSession) {
        this.mealService = mealService;
        this.userService = userService;
        this.userSession = userSession;
    }
    @ModelAttribute("mealDTO")
    public MealDTO mealDTO() {
        return new MealDTO();
    }

    @GetMapping("/meals-all/meals")
    public String viewActivitiesPage(Model model) {
        model.addAttribute("meals", mealService.getAllMeals());
        return "/meals-all/meals";
    }

    @GetMapping("/meals-all/add-meals")
    public String showAddActivityForm(Model model) {
        model.addAttribute("meal", new MealDTO());
        return "/meals-all/add-meals";
    }

    @PostMapping("/meals-all/add-meals")
    public String saveMeal(@Valid MealDTO mealDTO,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            System.out.println("Binding error");
            System.out.println(bindingResult.getFieldErrorCount());
            System.out.println(bindingResult.getFieldErrors());
            bindingResult.getFieldErrors().forEach(err -> System.out.println(err.getField()));

            return "/meals-all/add-meals";
        }

        String username = userSession.getUsername();
        if (username == null || !userSession.isLoggedIn()) {
            return "redirect:/login";
        }

        User currentUser = userService.findByUsername(username);
        System.out.println("Received MealDTO: " + mealDTO);
        Meal meal = mealService.saveMeal(mealDTO, currentUser);
        return "redirect:/meals-all/meals";
    }

    @PostMapping("/meals/delete/{id}")
    public String deleteMeal(@PathVariable Long id) {
        mealService.deleteMeal(id);
        return "redirect:/meals-all/meals";
    }
}
