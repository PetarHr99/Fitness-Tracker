package bg.softuni.finalproject.web;

import bg.softuni.finalproject.Entity.Workout;
import bg.softuni.finalproject.service.WorkoutService;
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
@RequestMapping("/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    @Autowired
    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping
    public String showWorkoutsPage(Model model) {
        model.addAttribute("workouts", workoutService.getAllWorkouts());
        return "workouts";
    }

    @GetMapping("/add")
    public String showAddWorkoutPage(Model model) {
        model.addAttribute("workoutDTO", new WorkoutDTO());
        return "add-workout";
    }

    @PostMapping("/add")
    public String addWorkout(@Valid @ModelAttribute("workoutDTO") WorkoutDTO workoutDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "add-workout";
        }
        Workout savedWorkout = workoutService.saveWorkout(workoutDTO);
        return "redirect:/workouts/" + savedWorkout.getId();
    }

    @GetMapping("/edit/{id}")
    public String showEditWorkoutPage(@PathVariable Long id, Model model) {
        Optional<Workout> workoutOpt = workoutService.getWorkoutById(id);
        if (workoutOpt.isPresent()) {
            model.addAttribute("workout", workoutOpt.get());
            return "edit-workout";
        }
        return "redirect:/workouts";
    }

    @PostMapping("/edit/{id}")
    public String editWorkout(@PathVariable Long id,
                              @Valid @ModelAttribute("workout") Workout workout,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "edit-workout";
        }
        workoutService.updateWorkout(workout);
        return "redirect:/workouts/" + workout.getId();
    }

    @PostMapping("/delete/{id}")
    public String deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return "redirect:/workouts";
    }
}

