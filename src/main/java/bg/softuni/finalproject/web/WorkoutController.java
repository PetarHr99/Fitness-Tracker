package bg.softuni.finalproject.web;

import bg.softuni.finalproject.Entity.Meal;
import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.Entity.Workout;
import bg.softuni.finalproject.config.UserSession;
import bg.softuni.finalproject.service.UserService;
import bg.softuni.finalproject.service.WorkoutService;
import bg.softuni.finalproject.web.dto.MealDTO;
import bg.softuni.finalproject.web.dto.WorkoutDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class WorkoutController {
    private final WorkoutService workoutService;
    private final UserService userService;
    private final UserSession userSession;

    @Autowired
    public WorkoutController(WorkoutService workoutService, UserService userService, UserSession userSession) {
        this.workoutService = workoutService;
        this.userService = userService;
        this.userSession = userSession;
    }

    @ModelAttribute("workoutDTO")
    public WorkoutDTO workoutDTO(){
        return new WorkoutDTO();
    }

    @GetMapping("/workout-all/workouts")
    public String showWorkoutsPage(Model model) {
        String username = userSession.getUsername();
        User currentUser = userService.findByUsername(username);
        model.addAttribute("workouts", workoutService.getWorkoutsByUser(currentUser));
        return "/workout-all/workouts";
    }

    @GetMapping("/workout-all/add-workout")
    public String showAddWorkoutPage(Model model) {
        model.addAttribute("workout", new WorkoutDTO());
        return "/workout-all/add-workout";
    }

    @PostMapping("/workout-all/add-workout")
    public String saveWorkout(@Valid WorkoutDTO workoutDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            System.out.println("Binding error");
            return "/workout-all/add-workout";
        }
        String username = userSession.getUsername();
        if (username == null || !userSession.isLoggedIn()) {
            return "redirect:/login";
        }

        User currentUser = userService.findByUsername(username);
        System.out.println("Received MealDTO: " + workoutDTO);
        Workout workout = workoutService.saveWorkout(workoutDTO, currentUser);
        return "redirect:/workout-all/workouts";
    }


    @PostMapping("/workouts/delete/{id}")
    public String deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return "redirect:/workout-all/workouts";
    }
}

