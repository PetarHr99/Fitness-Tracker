package bg.softuni.finalproject.service;

import bg.softuni.finalproject.Entity.Exercise;
import bg.softuni.finalproject.Entity.Workout;
import bg.softuni.finalproject.repo.ExerciseRepository;
import bg.softuni.finalproject.repo.WorkoutRepository;
import bg.softuni.finalproject.web.dto.WorkoutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public WorkoutService(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository) {
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    public Optional<Workout> getWorkoutById(Long id) {
        return workoutRepository.findById(id);
    }

    public Workout saveWorkout(WorkoutDTO workoutDTO) {
        Workout workout = new Workout();
        workout.setTitle(workoutDTO.getTitle());
        for (Exercise exercise : workoutDTO.getExercises()) {
            exercise.setWorkout(workout);
        }
        workout.setExercises(workoutDTO.getExercises());
        return workoutRepository.save(workout);
    }

    public Workout updateWorkout(Workout workout) {
        for (Exercise exercise : workout.getExercises()) {
            exercise.setWorkout(workout);
        }
        return workoutRepository.save(workout);
    }

    public void deleteWorkout(Long id) {
        workoutRepository.deleteById(id);
    }
}
