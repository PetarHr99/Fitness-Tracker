package bg.softuni.finalproject.repo;

import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.Entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByWorkoutAddedById(Long userId);

    List<Workout> findByWorkoutAddedBy(User user);
}
