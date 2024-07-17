package bg.softuni.finalproject.service;

import bg.softuni.finalproject.Entity.Exercise;
import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.Entity.Workout;
import bg.softuni.finalproject.repo.ExerciseRepository;
import bg.softuni.finalproject.repo.WorkoutRepository;
import bg.softuni.finalproject.web.dto.WorkoutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Workout> getWorkoutsByUser(User user) {
        return workoutRepository.findByWorkoutAddedBy(user);
    }

    public Workout saveWorkout(WorkoutDTO workoutDTO, User user) {
        Workout workout = new Workout();
        workout.setTitle(workoutDTO.getTitle());
        workout.setWorkoutAddedBy(user);

        List<Exercise> exercises = workoutDTO.getExercises().stream().map(dto -> {
            Exercise exercise = new Exercise();
            exercise.setWorkout(workout);
            exercise.setName(dto.getName());
            exercise.setSets(dto.getSets());
            exercise.setReps(dto.getReps());
            exercise.setWeight(dto.getWeight());
            return exercise;
        }).collect(Collectors.toList());

        workout.setExercises(exercises);

        return workoutRepository.save(workout);
    }

    public void deleteWorkout(Long id) {
        workoutRepository.deleteById(id);
    }
}
