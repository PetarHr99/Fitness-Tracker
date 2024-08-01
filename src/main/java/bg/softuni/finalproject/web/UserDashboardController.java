package bg.softuni.finalproject.web;

import bg.softuni.finalproject.Entity.*;
import bg.softuni.finalproject.Entity.enums.Gender;
import bg.softuni.finalproject.Entity.enums.TargetGoal;
import bg.softuni.finalproject.service.ActivityService;
import bg.softuni.finalproject.service.MealService;
import bg.softuni.finalproject.service.UserService;
import bg.softuni.finalproject.web.dto.ActivityDTO;
import bg.softuni.finalproject.web.dto.CurrentDataDTO;
import bg.softuni.finalproject.web.dto.MealDTO;
import bg.softuni.finalproject.web.dto.UserDashboardDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class UserDashboardController {
    private final UserService userService;
    private final MealService mealService;
    private final ActivityService activityService;
    private final ModelMapper modelMapper;
    public UserDashboardController(UserService userService, MealService mealService, ActivityService activityService, ModelMapper modelMapper) {
        this.userService = userService;
        this.mealService = mealService;
        this.activityService = activityService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<UserDashboardDTO> getUserDashboard() {

        User user = userService.getCurrentUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String targetGoal = getString(user);
        String getGender = getGender(user);

        UserDashboardDTO dashboardDTO = modelMapper.map(user, UserDashboardDTO.class);
        dashboardDTO.setTargetGoal(targetGoal);
        dashboardDTO.setGender(getGender);

        Meal lastMeal = mealService.findLastMealByUser(user);
        if (lastMeal != null) {
            MealDTO mealDTO = modelMapper.map(lastMeal, MealDTO.class);
            dashboardDTO.setLastMeal(mealDTO);
        }

        Activity lastActivity = activityService.findLastActivityByUser(user);
        if (lastActivity != null) {
            ActivityDTO activityDTO = modelMapper.map(lastActivity, ActivityDTO.class);
            dashboardDTO.setLastActivity(activityDTO);
        }

        return new ResponseEntity<>(dashboardDTO, HttpStatus.OK);
    }

    private static String getGender(User user) {
        String gender = "";
        if (user.getGender().toString().equals("MALE")){
            gender = "Male";
        }else if (user.getGender().toString().equals("FEMALE")){
            gender = "Female";
        } else {
            gender = "Other";
        }
        return gender;
    }

    private static String getString(User user) {
        String targetGoal = "";
        if (user.getTargetGoal().toString().equals("GAIN")){
            targetGoal = "Gain muscle mass and strength";
        }else if (user.getTargetGoal().toString().equals("LOSS")){
            targetGoal = "Loss of weight";
        } else {
            targetGoal = "Maintain weight";
        }
        return targetGoal;
    }


    @PostMapping("/current-data")
    public ResponseEntity<UserDashboardDTO> updateCurrentWeight(@RequestBody CurrentDataDTO currentDataDTO) {
        User user = userService.getCurrentUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        double currentWeight = currentDataDTO.getCurrentWeight();
        userService.updateCurrentWeight(user.getUsername(), currentWeight);

        User userUpdate = userService.getCurrentUser();

        UserDashboardDTO dashboardDTO = modelMapper.map(userUpdate, UserDashboardDTO.class);


        // Calculate weight difference
        double startWeight = userUpdate.getWeight();
        double weightDifference = currentWeight - startWeight;
        dashboardDTO.setWeightDifference(weightDifference);

        // Calculate recommended calorie intake
        double recommendedCalories = getRecommendedCalories(userUpdate, currentWeight);
        userService.updateCurrentCalorieIntake(user.getUsername(), recommendedCalories);
        dashboardDTO.setRecommendedCalories(recommendedCalories);

        return new ResponseEntity<>(dashboardDTO, HttpStatus.OK);
    }

    private static double getRecommendedCalories(User user, double currentWeight) {
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
        return recommendedCalories;
    }
}
