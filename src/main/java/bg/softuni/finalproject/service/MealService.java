package bg.softuni.finalproject.service;

import bg.softuni.finalproject.Entity.Meal;
import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.repo.MealRepository;
import bg.softuni.finalproject.web.dto.MealDTO;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealService {
    private final MealRepository mealRepository;
    private final ModelMapper modelMapperl;

    public MealService(MealRepository mealRepository, ModelMapper modelMapperl) {
        this.mealRepository = mealRepository;
        this.modelMapperl = modelMapperl;
    }

    public Meal saveMeal(MealDTO mealDTO, User currentUser){
        Meal meal = modelMapperl.map(mealDTO, Meal.class);
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
