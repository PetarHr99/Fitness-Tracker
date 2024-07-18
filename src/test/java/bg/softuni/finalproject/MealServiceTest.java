package bg.softuni.finalproject;

import bg.softuni.finalproject.Entity.Meal;
import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.repo.MealRepository;
import bg.softuni.finalproject.service.MealService;
import bg.softuni.finalproject.web.dto.MealDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MealServiceTest {

    private MealRepository mealRepository;
    private MealService mealService;

    @BeforeEach
    public void setUp() {
        mealRepository = mock(MealRepository.class);
        mealService = new MealService(mealRepository);
    }

    //testSaveMeal: Tests that a meal can be saved successfully. It checks that the returned meal has the
    // correct values and verifies that the mealRepository.save method is called with the correct argument.
    @Test
    public void testSaveMeal() {
        MealDTO mealDTO = new MealDTO();
        mealDTO.setBreakfast("Oatmeal");
        mealDTO.setLunch("Salad");
        mealDTO.setDinner("Chicken");
        mealDTO.setSnack("Fruit");
        mealDTO.setTotalCalories(2000);

        User currentUser = new User();
        currentUser.setUsername("testuser");

        Meal meal = new Meal();
        meal.setId(1L);
        meal.setBreakfast(mealDTO.getBreakfast());
        meal.setLunch(mealDTO.getLunch());
        meal.setDinner(mealDTO.getDinner());
        meal.setSnack(mealDTO.getSnack());
        meal.setTotalCalories(mealDTO.getTotalCalories());
        meal.setMealsAddedBy(currentUser);

        when(mealRepository.save(any(Meal.class))).thenReturn(meal);

        Meal savedMeal = mealService.saveMeal(mealDTO, currentUser);

        assertNotNull(savedMeal);
        assertEquals("Oatmeal", savedMeal.getBreakfast());
        assertEquals("Salad", savedMeal.getLunch());
        assertEquals("Chicken", savedMeal.getDinner());
        assertEquals("Fruit", savedMeal.getSnack());
        assertEquals(2000, savedMeal.getTotalCalories());
        assertEquals(currentUser, savedMeal.getMealsAddedBy());

        ArgumentCaptor<Meal> mealCaptor = ArgumentCaptor.forClass(Meal.class);
        verify(mealRepository, times(1)).save(mealCaptor.capture());
        Meal capturedMeal = mealCaptor.getValue();
        assertEquals("Oatmeal", capturedMeal.getBreakfast());
        assertEquals("Salad", capturedMeal.getLunch());
        assertEquals("Chicken", capturedMeal.getDinner());
        assertEquals("Fruit", capturedMeal.getSnack());
        assertEquals(2000, capturedMeal.getTotalCalories());
        assertEquals(currentUser, capturedMeal.getMealsAddedBy());
    }

    //testGetAllMeals: Tests that the getAllMeals method returns
    // all meals correctly. It checks the size of the returned list and the values of the meals.
    @Test
    public void testGetAllMeals() {
        Meal meal1 = new Meal();
        meal1.setId(1L);
        meal1.setBreakfast("Oatmeal");

        Meal meal2 = new Meal();
        meal2.setId(2L);
        meal2.setBreakfast("Pancakes");

        List<Meal> meals = Arrays.asList(meal1, meal2);

        when(mealRepository.findAll()).thenReturn(meals);

        List<Meal> result = mealService.getAllMeals();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Oatmeal", result.get(0).getBreakfast());
        assertEquals("Pancakes", result.get(1).getBreakfast());
    }

    //testDeleteMeal: Tests that the deleteMeal method calls the
    // mealRepository.deleteById method with the correct argument.
    @Test
    public void testDeleteMeal() {
        Long mealId = 1L;
        mealService.deleteMeal(mealId);
        verify(mealRepository, times(1)).deleteById(mealId);
    }

    //testFindLastMealByUser: Tests that the findLastMealByUser method returns the
    // last meal added by the user correctly. It checks the returned meal's values.
    @Test
    public void testFindLastMealByUser() {
        User user = new User();
        user.setUsername("testuser");

        Meal lastMeal = new Meal();
        lastMeal.setId(1L);
        lastMeal.setBreakfast("Oatmeal");

        when(mealRepository.findTopByMealsAddedByOrderByIdDesc(user)).thenReturn(lastMeal);

        Meal result = mealService.findLastMealByUser(user);

        assertNotNull(result);
        assertEquals("Oatmeal", result.getBreakfast());
        assertEquals(1L, result.getId());
    }
}