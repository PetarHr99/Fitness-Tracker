package bg.softuni.finalproject.exercises;

import bg.softuni.finalproject.Entity.Workout;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;

    }

    public void saveExercise(Workout currentWorkout, List<ExerciseDTO> exerciseDTOList) {
        for (ExerciseDTO exerciseDTO : exerciseDTOList){
            Exercise exercise = new Exercise();
            exercise.setName(exerciseDTO.getName());
            exercise.setSets(exerciseDTO.getSets());
            exercise.setReps(exerciseDTO.getReps());
            exercise.setWeight(exerciseDTO.getWeight());
            exercise.setWorkout(currentWorkout);

            exerciseRepository.save(exercise);
        }
    }

    public List<Exercise> findByWorkout(Workout workout) {
        return exerciseRepository.findByWorkout(workout);
    }
}
