package bg.softuni.finalproject.service;


import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.Entity.Workout;
import bg.softuni.finalproject.repo.ExerciseRepository;
import bg.softuni.finalproject.repo.WorkoutRepository;
import bg.softuni.finalproject.web.dto.WorkoutDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final ExerciseService exerciseService;

    public WorkoutService(WorkoutRepository workoutRepository, ExerciseService exerciseService) {
        this.workoutRepository = workoutRepository;
        this.exerciseService = exerciseService;
    }

    public List<Workout> findByUser(User user) {
        return workoutRepository.findByWorkoutAddedBy(user);
    }

    public Workout saveWorkout(WorkoutDTO workoutDTO, User user) {
        Workout workout = new Workout();
        workout.setTitle(workoutDTO.getTitle());
        workout.setWorkoutAddedBy(user);

        workoutRepository.save(workout);
        return workout;
    }
    @Transactional
    public void deleteWorkout(Long workoutId) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid workout Id:" + workoutId));
        exerciseService.deleteByWorkout(workout);
        workoutRepository.delete(workout);
    }
}
