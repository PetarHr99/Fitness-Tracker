package bg.softuni.finalproject.service;

import bg.softuni.finalproject.Entity.Exercise;
import bg.softuni.finalproject.Entity.Workout;
import bg.softuni.finalproject.repo.ExerciseRepository;
import bg.softuni.finalproject.web.dto.ExerciseDTO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final ModelMapper modelMapper;

    public ExerciseService(ExerciseRepository exerciseRepository, ModelMapper modelMapper) {
        this.exerciseRepository = exerciseRepository;
        this.modelMapper = modelMapper;
    }

    public void saveExercise(Workout currentWorkout, List<ExerciseDTO> exerciseDTOList) {
        for (ExerciseDTO exerciseDTO : exerciseDTOList){
            Exercise exercise = modelMapper.map(exerciseDTO, Exercise.class);
            exercise.setWorkout(currentWorkout);
            exerciseRepository.save(exercise);
        }
    }

    public List<Exercise> findByWorkout(Workout workout) {
        return exerciseRepository.findByWorkout(workout);
    }

    @Transactional
    public void deleteByWorkout(Workout workout) {
        exerciseRepository.deleteByWorkout(workout);
    }

}
