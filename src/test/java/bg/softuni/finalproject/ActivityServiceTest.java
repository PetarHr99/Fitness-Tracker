package bg.softuni.finalproject;


import bg.softuni.finalproject.Entity.Activity;
import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.repo.ActivityRepository;
import bg.softuni.finalproject.service.ActivityService;
import bg.softuni.finalproject.web.dto.ActivityDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ActivityServiceTest {

    private ActivityRepository activityRepository;
    private ActivityService activityService;

    @BeforeEach
    public void setUp() {
        activityRepository = mock(ActivityRepository.class);
        activityService = new ActivityService(activityRepository, null);
    }

    //testSaveActivity: Tests that an activity can be saved successfully. It checks that the returned activity has the
    // correct values and verifies that the activityRepository.save method is called with the correct argument.
    @Test
    public void testSaveActivity() {
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setTypeOfActivity("Running");
        activityDTO.setDateOfActivity(LocalDate.of(2023, 7, 1));
        activityDTO.setCalories(300);
        activityDTO.setTimeOfTraining("14:00");

        User currentUser = new User();
        currentUser.setUsername("testuser");

        Activity activity = new Activity();
        activity.setId(1L);
        activity.setTypeOfActivity(activityDTO.getTypeOfActivity());
        activity.setDateOfActivity(activityDTO.getDateOfActivity());
        activity.setCalories(activityDTO.getCalories());
        activity.setTimeOfTraining(activityDTO.getTimeOfTraining());
        activity.setAddedByUser(currentUser);

        when(activityRepository.save(any(Activity.class))).thenReturn(activity);

        Activity savedActivity = activityService.saveActivity(activityDTO, currentUser);

        assertNotNull(savedActivity);
        assertEquals("Running", savedActivity.getTypeOfActivity());
        assertEquals(LocalDate.of(2023, 7, 1), savedActivity.getDateOfActivity());
        assertEquals(300, savedActivity.getCalories());
        assertEquals("14:00", savedActivity.getTimeOfTraining());
        assertEquals(currentUser, savedActivity.getAddedByUser());

        ArgumentCaptor<Activity> activityCaptor = ArgumentCaptor.forClass(Activity.class);
        verify(activityRepository, times(1)).save(activityCaptor.capture());
        Activity capturedActivity = activityCaptor.getValue();
        assertEquals("Running", capturedActivity.getTypeOfActivity());
        assertEquals(LocalDate.of(2023, 7, 1), capturedActivity.getDateOfActivity());
        assertEquals(300, capturedActivity.getCalories());
        assertEquals("14:00", capturedActivity.getTimeOfTraining());
        assertEquals(currentUser, capturedActivity.getAddedByUser());
    }

    //testGetAllActivities: Tests that the getAllActivities method returns all activities correctly.
    // It checks the size of the returned list and the values of the activities.
    @Test
    public void testGetAllActivities() {
        Activity activity1 = new Activity();
        activity1.setId(1L);
        activity1.setTypeOfActivity("Running");

        Activity activity2 = new Activity();
        activity2.setId(2L);
        activity2.setTypeOfActivity("Cycling");

        List<Activity> activities = Arrays.asList(activity1, activity2);

        when(activityRepository.findAll()).thenReturn(activities);

        List<Activity> result = activityService.getAllActivities();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Running", result.get(0).getTypeOfActivity());
        assertEquals("Cycling", result.get(1).getTypeOfActivity());
    }

    //testDeleteActivity: Tests that the deleteActivity method calls
    // the activityRepository.deleteById method with the correct argument.
    @Test
    public void testDeleteActivity() {
        Long activityId = 1L;
        activityService.deleteActivity(activityId);
        verify(activityRepository, times(1)).deleteById(activityId);
    }

    //testFindLastActivityByUser: Tests that the findLastActivityByUser method returns the
    // last activity added by the user correctly. It checks the returned activity's values.
    @Test
    public void testFindLastActivityByUser() {
        User user = new User();
        user.setUsername("testuser");

        Activity lastActivity = new Activity();
        lastActivity.setId(1L);
        lastActivity.setTypeOfActivity("Running");

        when(activityRepository.findTopByAddedByUserOrderByDateOfActivityDesc(user)).thenReturn(lastActivity);

        Activity result = activityService.findLastActivityByUser(user);

        assertNotNull(result);
        assertEquals("Running", result.getTypeOfActivity());
        assertEquals(1L, result.getId());
    }
}