package bg.softuni.finalproject.serviceTest;

import bg.softuni.finalproject.Entity.Activity;
import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.repo.ActivityRepository;
import bg.softuni.finalproject.service.ActivityService;
import bg.softuni.finalproject.web.dto.ActivityDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ActivityService activityService;

    private User user;
    private ActivityDTO activityDTO;
    private Activity activity;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        activityDTO = new ActivityDTO();
        activityDTO.setTypeOfActivity("Running");
        activityDTO.setDateOfActivity(LocalDate.now());
        activityDTO.setCalories(300);
        activityDTO.setTimeOfTraining("30 minutes");

        activity = new Activity();
        activity.setId(1L);
        activity.setTypeOfActivity("Running");
        activity.setDateOfActivity(LocalDate.now());
        activity.setCalories(300);
        activity.setTimeOfTraining("30 minutes");
        activity.setAddedByUser(user);
    }

    @Test
    void testSaveActivity() {
        when(modelMapper.map(any(ActivityDTO.class), any(Class.class))).thenReturn(activity);
        when(activityRepository.save(any(Activity.class))).thenReturn(activity);

        Activity savedActivity = activityService.saveActivity(activityDTO, user);

        assertNotNull(savedActivity);
        assertEquals("Running", savedActivity.getTypeOfActivity());
        verify(activityRepository, times(1)).save(any(Activity.class));
    }

    @Test
    void testGetActivitiesByUser() {
        when(activityRepository.findByAddedByUser(any(User.class))).thenReturn(Arrays.asList(activity));

        List<Activity> activities = activityService.getActivitiesByUser(user);

        assertNotNull(activities);
        assertEquals(1, activities.size());
        assertEquals("Running", activities.get(0).getTypeOfActivity());
        verify(activityRepository, times(1)).findByAddedByUser(any(User.class));
    }

    @Test
    void testDeleteActivity() {
        doNothing().when(activityRepository).deleteById(anyLong());

        activityService.deleteActivity(1L);

        verify(activityRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testFindLastActivityByUser() {
        when(activityRepository.findTopByAddedByUserOrderByDateOfActivityDesc(any(User.class))).thenReturn(activity);

        Activity lastActivity = activityService.findLastActivityByUser(user);

        assertNotNull(lastActivity);
        assertEquals("Running", lastActivity.getTypeOfActivity());
        verify(activityRepository, times(1)).findTopByAddedByUserOrderByDateOfActivityDesc(any(User.class));
    }
}