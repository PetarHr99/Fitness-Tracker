package bg.softuni.finalproject.serviceTest;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import bg.softuni.finalproject.service.ExerciseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import bg.softuni.finalproject.Entity.Exercise;
import bg.softuni.finalproject.Entity.Workout;
import bg.softuni.finalproject.repo.ExerciseRepository;
import bg.softuni.finalproject.web.dto.ExerciseDTO;

import java.util.List;
import java.util.ArrayList;
@SpringBootTest
public class ExerciseServiceTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ExerciseService exerciseService;

    private Workout workout;
    private Exercise exercise;

    @BeforeEach
    public void setUp() {
        workout = new Workout();
        workout.setId(1L);
        workout.setTitle("Morning Routine");

        exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Push-up");
        exercise.setWorkout(workout);
    }

    @Test
    public void testSaveExercise() {
        // Setup
        ExerciseDTO exerciseDTO = new ExerciseDTO();
        exerciseDTO.setName("Squat");
        exerciseDTO.setReps(10);
        exerciseDTO.setSets(3);
        exerciseDTO.setWeight(150.0);

        Workout currentWorkout = new Workout();
        currentWorkout.setId(1L);

        Exercise mappedExercise = new Exercise();
        when(modelMapper.map(exerciseDTO, Exercise.class)).thenReturn(mappedExercise);
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(mappedExercise);
        exerciseService.saveExercise(currentWorkout, List.of(exerciseDTO));
        verify(exerciseRepository).save(mappedExercise);
        verify(modelMapper).map(exerciseDTO, Exercise.class);
    }

    @Test
    public void testFindByWorkout() {
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(exercise);
        when(exerciseRepository.findByWorkout(workout)).thenReturn(exercises);

        List<Exercise> found = exerciseService.findByWorkout(workout);
        assertEquals(1, found.size());
        verify(exerciseRepository).findByWorkout(workout);
    }

    @Test
    public void testDeleteByWorkout() {
        doNothing().when(exerciseRepository).deleteByWorkout(workout);
        exerciseService.deleteByWorkout(workout);

        verify(exerciseRepository).deleteByWorkout(workout);
    }
}
