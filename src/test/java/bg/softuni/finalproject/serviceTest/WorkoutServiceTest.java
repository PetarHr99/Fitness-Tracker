package bg.softuni.finalproject.serviceTest;

import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.Entity.Workout;
import bg.softuni.finalproject.repo.WorkoutRepository;
import bg.softuni.finalproject.service.ExerciseService;
import bg.softuni.finalproject.service.WorkoutService;
import bg.softuni.finalproject.web.dto.WorkoutDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WorkoutServiceTest {

    @Mock
    private WorkoutRepository workoutRepository;

    @Mock
    private ExerciseService exerciseService;

    @InjectMocks
    private WorkoutService workoutService;

    private User user;
    private Workout workout;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        workout = new Workout();
        workout.setTitle("Morning Routine");
        workout.setWorkoutAddedBy(user);
    }

    @Test
    public void testFindByUser() {
        List<Workout> workouts = new ArrayList<>();
        workouts.add(workout);
        when(workoutRepository.findByWorkoutAddedBy(user)).thenReturn(workouts);

        List<Workout> found = workoutService.findByUser(user);
        assertEquals(1, found.size());
        verify(workoutRepository).findByWorkoutAddedBy(user);
    }

    @Test
    public void testSaveWorkout() {
        when(workoutRepository.save(any(Workout.class))).thenReturn(workout);
        WorkoutDTO workoutDTO = new WorkoutDTO();
        workoutDTO.setTitle("Morning Routine");

        Workout saved = workoutService.saveWorkout(workoutDTO, user);
        assertNotNull(saved);
        assertEquals("Morning Routine", saved.getTitle());
        verify(workoutRepository).save(any(Workout.class));
    }

    @Test
    public void testDeleteWorkoutWithNullId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            workoutService.deleteWorkout(null);
        });
        // Correct the expected message to match what the service method actually produces
        assertEquals("Invalid workout Id:null", exception.getMessage());
    }


    @Test
    public void testDeleteWorkoutWithInvalidId() {
        Long invalidId = 999L; // Assume this ID does not exist
        when(workoutRepository.findById(invalidId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            workoutService.deleteWorkout(invalidId);
        });
        assertEquals("Invalid workout Id:" + invalidId, exception.getMessage());
    }
}