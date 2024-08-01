package bg.softuni.finalproject.web;

import bg.softuni.finalproject.Entity.Meal;
import bg.softuni.finalproject.Entity.User;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MealController {
    private final MealService mealService;
    private final UserService userService;

    @Autowired
    public MealController(MealService mealService, UserService userService) {
        this.mealService = mealService;
        this.userService = userService;
    }
    @ModelAttribute("mealDTO")
    public MealDTO mealDTO() {
        return new MealDTO();
    }

    @GetMapping("/meals-all/meals")
    public String viewMealsPage(Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("meals", mealService.getMealsByUser(currentUser));
        return "/meals-all/meals";
    }

    @GetMapping("/meals-all/add-meals")
    public String showAddMealForm(Model model) {
        if (userService.getCurrentUser() == null) {
            return "redirect:/login";
        }
        model.addAttribute("meal", new MealDTO());
        return "/meals-all/add-meals";
    }

    @PostMapping("/meals-all/add-meals")
    public String saveMeal(@Valid MealDTO mealDTO,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("mealDTO", mealDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.mealDTO", bindingResult);
            return "redirect:/meals-all/add-meals";
        }

        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        Meal meal = mealService.saveMeal(mealDTO, currentUser);
        return "redirect:/meals-all/meals";
    }

    @PostMapping("/meals/delete/{id}")
    public String deleteMeal(@PathVariable Long id) {
        mealService.deleteMeal(id);
        return "redirect:/meals-all/meals";
    }
}
