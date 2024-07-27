package bg.softuni.finalproject.service;

import bg.softuni.finalproject.Entity.Meal;
import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.repo.MealRepository;
import bg.softuni.finalproject.web.dto.MealDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealService {
    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public Meal saveMeal(MealDTO mealDTO, User currentUser){
        Meal meal = new Meal();
        meal.setBreakfast(mealDTO.getBreakfast());
        meal.setLunch(mealDTO.getLunch());
        meal.setDinner(mealDTO.getDinner());
        meal.setSnack(mealDTO.getSnack());
        meal.setTotalCalories(mealDTO.getTotalCalories());
        meal.setMealsAddedBy(currentUser);

       return mealRepository.save(meal);
    }
    public List<Meal> getAllMeals(){
        return mealRepository.findAll();
    }
    public void deleteMeal(Long id) {
        mealRepository.deleteById(id);
    }

    public Meal findLastMealByUser(User user) {
        return mealRepository.findTopByMealsAddedByOrderByIdDesc(user);
    }
    public List<Meal> getMealsByUser(User user) {
        return mealRepository.findByMealsAddedBy(user);
    }
}
