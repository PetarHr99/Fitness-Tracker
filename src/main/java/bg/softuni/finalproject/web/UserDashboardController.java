package bg.softuni.finalproject.web;

import bg.softuni.finalproject.Entity.Gender;
import bg.softuni.finalproject.Entity.Meal;
import bg.softuni.finalproject.Entity.TargetGoal;
import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.config.UserSession;
import bg.softuni.finalproject.service.MealService;
import bg.softuni.finalproject.service.UserService;
import bg.softuni.finalproject.web.dto.MealDTO;
import bg.softuni.finalproject.web.dto.UserDashboardDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class UserDashboardController {
    private final UserService userService;
    private final MealService mealService;
    private final UserSession userSession;

    public UserDashboardController(UserService userService, MealService mealService, UserSession userSession) {
        this.userService = userService;
        this.mealService = mealService;
        this.userSession = userSession;
    }

    @GetMapping
    public ResponseEntity<UserDashboardDTO> getUserDashboard() {
        String username = userSession.getUsername();
        User user = userService.findByUsername(username);

        UserDashboardDTO dashboardDTO = new UserDashboardDTO();
        dashboardDTO.setTargetGoal(user.getTargetGoal().toString());
        dashboardDTO.setAge(user.getAge());
        dashboardDTO.setWeight(user.getWeight());
        dashboardDTO.setHeight(user.getHeight());
        dashboardDTO.setGender(user.getGender().toString());
        dashboardDTO.setCurrentWeight(user.getCurrentWeight());


        // Calculate recommended calorie intake
        int age = user.getAge();
        double recommendedCalories;
        double currentWeight = user.getCurrentWeight();
        if (user.getGender() == Gender.MALE) {
            recommendedCalories = 10 * currentWeight + 6.25 * user.getHeight() - 5 * age + 5;
        } else {
            recommendedCalories = 10 * currentWeight + 6.25 * user.getHeight() - 5 * age - 161;
        }
        if (user.getTargetGoal() == TargetGoal.LOSS){
            recommendedCalories = recommendedCalories - 300;
        } else if (user.getTargetGoal() == TargetGoal.GAIN) {
            recommendedCalories = recommendedCalories + 300;
        }
        dashboardDTO.setRecommendedCalories(recommendedCalories);

        Meal lastMeal = mealService.findLastMealByUser(user);
        if (lastMeal != null) {
            MealDTO mealDTO = new MealDTO();
            mealDTO.setBreakfast(lastMeal.getBreakfast());
            mealDTO.setLunch(lastMeal.getLunch());
            mealDTO.setDinner(lastMeal.getDinner());
            mealDTO.setSnack(lastMeal.getSnack());
            mealDTO.setTotalCalories(lastMeal.getTotalCalories());
            dashboardDTO.setLastMeal(mealDTO);
        }

        return new ResponseEntity<>(dashboardDTO, HttpStatus.OK);
    }
    @PostMapping("/current-data")
    public ResponseEntity<UserDashboardDTO> updateCurrentWeight(@RequestParam double currentWeight) {
        String username = userSession.getUsername();
        userService.updateCurrentWeight(username, currentWeight);

        User user = userService.findByUsername(username);

        UserDashboardDTO dashboardDTO = new UserDashboardDTO();
        dashboardDTO.setTargetGoal(user.getTargetGoal().toString());
        dashboardDTO.setWeight(currentWeight);
        dashboardDTO.setHeight(user.getHeight());
        dashboardDTO.setGender(user.getGender().toString());
        dashboardDTO.setCurrentWeight(user.getCurrentWeight());


        // Calculate weight difference
        double startWeight = user.getWeight(); // Assuming you have start weight in the User entity
        double weightDifference = currentWeight - startWeight;
        dashboardDTO.setWeightDifference(weightDifference);

        // Calculate recommended calorie intake
        int age = user.getAge();
        double recommendedCalories;
        if (user.getGender() == Gender.MALE) {
            recommendedCalories = 10 * currentWeight + 6.25 * user.getHeight() - 5 * age + 5;
        } else {
            recommendedCalories = 10 * currentWeight + 6.25 * user.getHeight() - 5 * age - 161;
        }
        if (user.getTargetGoal() == TargetGoal.LOSS){
            recommendedCalories = recommendedCalories - 300;
        } else if (user.getTargetGoal() == TargetGoal.GAIN) {
            recommendedCalories = recommendedCalories + 300;
        }
        userService.updateCurrentCalorieIntake(username, recommendedCalories);
        dashboardDTO.setRecommendedCalories(recommendedCalories);

        return new ResponseEntity<>(dashboardDTO, HttpStatus.OK);
    }
}
