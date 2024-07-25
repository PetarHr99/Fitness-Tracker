package bg.softuni.finalproject.service;


import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.Entity.Workout;
import bg.softuni.finalproject.repo.WorkoutRepository;
import bg.softuni.finalproject.web.dto.WorkoutDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    public WorkoutService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
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
}
