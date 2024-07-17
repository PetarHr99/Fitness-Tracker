package bg.softuni.finalproject.repo;

import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.Entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findByWorkoutAddedBy(User user);
}
