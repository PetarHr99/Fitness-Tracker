package bg.softuni.finalproject.repo;

import bg.softuni.finalproject.Entity.Meal;
import bg.softuni.finalproject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    Meal findTopByMealsAddedByOrderByIdDesc(User user);
    List<Meal> findByMealsAddedBy(User mealsAddedBy);
}
