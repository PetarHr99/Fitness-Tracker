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
    private final ModelMapper modelMapper;

    public MealService(MealRepository mealRepository, ModelMapper modelMapper) {
        this.mealRepository = mealRepository;
        this.modelMapper = modelMapper;
    }

    public Meal saveMeal(MealDTO mealDTO, User currentUser){
        Meal meal = modelMapper.map(mealDTO, Meal.class);
        meal.setMealsAddedBy(currentUser);
       return mealRepository.save(meal);
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
