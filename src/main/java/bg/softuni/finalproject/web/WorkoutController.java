package bg.softuni.finalproject.web;


import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.Entity.Workout;
import bg.softuni.finalproject.Entity.Exercise;
import bg.softuni.finalproject.web.dto.ExerciseDTO;
import bg.softuni.finalproject.service.ExerciseService;
import bg.softuni.finalproject.service.UserService;
import bg.softuni.finalproject.service.WorkoutService;
import bg.softuni.finalproject.web.dto.WorkoutDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;


@Controller
@SessionAttributes({"workoutDTO", "exerciseDTOList"})
public class WorkoutController {
    private final WorkoutService workoutService;
    private final ExerciseService exerciseService;
    private final UserService userService;

    public WorkoutController(WorkoutService workoutService, ExerciseService exerciseService, UserService userService) {
        this.workoutService = workoutService;
        this.exerciseService = exerciseService;
        this.userService = userService;
    }

    @ModelAttribute("workoutDTO")
    public WorkoutDTO workoutDTO(){
        return new WorkoutDTO();
    }

    @ModelAttribute("exerciseDTOList")
    public List<ExerciseDTO> exerciseDTOList() {
        return new ArrayList<>();
    }


    //HOME WORKOUT ----------------------------------------------------------

    @GetMapping("/workout-all/workout-home")
    public String viewHomeWorkout(Model model){
        User user = userService.getCurrentUser();
        if (user == null) {
            return "redirect:/login";
        }
        List<Workout> workoutList = workoutService.findByUser(user);

        Collections.reverse(workoutList);
        Map<Workout, List<Exercise>> workoutExercisesMap = new LinkedHashMap<>();

        for (Workout workout : workoutList) {
            List<Exercise> exercisesForTheWorkout = exerciseService.findByWorkout(workout);
            workoutExercisesMap.put(workout, exercisesForTheWorkout);
        }

        model.addAttribute("workoutExercisesMap", workoutExercisesMap);
        return "/workout-all/workout-home";
    }


    //ADD WORKOUT -----------------------------------------------------------
    @GetMapping("/workout-all/workout-add")
    public String viewAddWorkout(Model model, List<ExerciseDTO> exerciseDTOList){
        if (userService.getCurrentUser() == null) {
            return "redirect:/login";
        }
        if (!model.containsAttribute("workoutDTO")) {
            model.addAttribute("workoutDTO", new WorkoutDTO());
        }

        model.addAttribute("exerciseDTOList", exerciseDTOList);
        return "/workout-all/workout-add";
    }

    @PostMapping("/workout-all/workout-add")
    public String saveWorkout(@Valid WorkoutDTO workoutDTO, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                @SessionAttribute("exerciseDTOList") List<ExerciseDTO> exerciseDTOList){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("workoutDTO", workoutDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.workoutDTO", bindingResult);
            return "redirect:/workout-all/workout-add";
        }

        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        workoutDTO.setExercises(exerciseDTOList);
        Workout currentWorkout = workoutService.saveWorkout(workoutDTO, currentUser);
        exerciseService.saveExercise(currentWorkout, exerciseDTOList);
        exerciseDTOList.clear();
        return "redirect:/workout-all/workout-home";
    }

    //ADD EXERCISE ----------------------------------------------------------
    @GetMapping("/workout-all/exercise-add")
    private String viewAddExercise(Model model){
        if (userService.getCurrentUser() == null) {
            return "redirect:/login";
        }
        if (!model.containsAttribute("exerciseDTO")) {
            model.addAttribute("exerciseDTO", new ExerciseDTO());
        }
        return "/workout-all/exercise-add";
    }

    @PostMapping("workout-all/exercise-add")
    private String saveExercise(@Valid ExerciseDTO exerciseDTO, BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes,
                                    @SessionAttribute("exerciseDTOList") List<ExerciseDTO> exerciseDTOList){
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("exerciseDTO", exerciseDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.exerciseDTO", bindingResult);
            return "redirect:/workout-all/exercise-add";
        }

         exerciseDTOList.add(exerciseDTO);

        return "redirect:/workout-all/workout-add";
    }

    // DELETE WORKOUT --------------------------------------------------------
    @DeleteMapping("/workout-all/workout-delete/{id}")
    public String deleteWorkout(@PathVariable Long id) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        workoutService.deleteWorkout(id);
        return "redirect:/workout-all/workout-home";
    }

}

