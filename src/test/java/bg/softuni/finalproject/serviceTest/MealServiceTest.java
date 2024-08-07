package bg.softuni.finalproject.serviceTest;

import bg.softuni.finalproject.Entity.Meal;
import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.repo.MealRepository;
import bg.softuni.finalproject.service.MealService;
import bg.softuni.finalproject.web.dto.MealDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MealServiceTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MealService mealService;

    private User user;
    private Meal meal;
    private MealDTO mealDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        meal = new Meal();
        meal.setId(1L);
        meal.setBreakfast("Oatmeal");
        meal.setLunch("Chicken Salad");
        meal.setDinner("Steak and Vegetables");
        meal.setSnack("Yogurt");
        meal.setTotalCalories(1200);
        meal.setMealsAddedBy(user);

        mealDTO = new MealDTO();
        mealDTO.setBreakfast("Oatmeal");
        mealDTO.setLunch("Chicken Salad");
        mealDTO.setDinner("Steak and Vegetables");
        mealDTO.setSnack("Yogurt");
        mealDTO.setTotalCalories(1200);

       lenient().when(modelMapper.map(mealDTO, Meal.class)).thenReturn(meal);
    }

    @Test
    void testSaveMeal() {
        when(mealRepository.save(any(Meal.class))).thenReturn(meal);
        Meal savedMeal = mealService.saveMeal(mealDTO, user);

        assertNotNull(savedMeal);
        assertEquals(meal.getTotalCalories(), savedMeal.getTotalCalories());
        verify(mealRepository).save(meal);
    }

    @Test
    void testDeleteMeal() {
        doNothing().when(mealRepository).deleteById(anyLong());
        mealService.deleteMeal(1L);
        verify(mealRepository).deleteById(1L);
    }

    @Test
    void testFindLastMealByUser() {
        when(mealRepository.findTopByMealsAddedByOrderByIdDesc(any(User.class))).thenReturn(meal);
        Meal foundMeal = mealService.findLastMealByUser(user);

        assertNotNull(foundMeal);
        assertEquals(meal.getId(), foundMeal.getId());
        verify(mealRepository).findTopByMealsAddedByOrderByIdDesc(user);
    }

    @Test
    void testGetMealsByUser() {
        List<Meal> expectedMeals = new ArrayList<>();
        expectedMeals.add(meal);
        when(mealRepository.findByMealsAddedBy(any(User.class))).thenReturn(expectedMeals);

        List<Meal> meals = mealService.getMealsByUser(user);

        assertNotNull(meals);
        assertFalse(meals.isEmpty());
        assertEquals(1, meals.size());
        assertEquals(meal, meals.get(0));
        verify(mealRepository).findByMealsAddedBy(user);
    }
}
