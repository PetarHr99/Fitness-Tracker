package bg.softuni.finalproject.repo;

import bg.softuni.finalproject.Entity.Workout;
import bg.softuni.finalproject.Entity.Exercise;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByWorkout(Workout workout);

    @Transactional
    void deleteByWorkout(Workout workout);
}
