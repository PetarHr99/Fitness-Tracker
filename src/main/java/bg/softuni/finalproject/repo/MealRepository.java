package bg.softuni.finalproject.repo;

import bg.softuni.finalproject.Entity.Meal;
import bg.softuni.finalproject.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    Meal findTopByMealsAddedByOrderByIdDesc(User user);
}
