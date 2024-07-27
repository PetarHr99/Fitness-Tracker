package bg.softuni.finalproject.service;

import bg.softuni.finalproject.Entity.Activity;
import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.repo.ActivityRepository;
import bg.softuni.finalproject.repo.UserRepository;
import bg.softuni.finalproject.web.dto.ActivityDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository, UserRepository userRepository) {
        this.activityRepository = activityRepository;
    }
    public Activity saveActivity(ActivityDTO activityDTO, User currentUser) {
        Activity activity = new Activity();
        activity.setTypeOfActivity(activityDTO.getTypeOfActivity());
        activity.setDateOfActivity(activityDTO.getDateOfActivity());
        activity.setCalories(activityDTO.getCalories());
        activity.setTimeOfTraining(activityDTO.getTimeOfTraining());
        activity.setAddedByUser(currentUser);
        return activityRepository.save(activity);
    }
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public List<Activity> getActivitiesByUser(User user) {
        return activityRepository.findByAddedByUser(user);
    }

    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }
    public Activity findLastActivityByUser(User user) {
        return activityRepository.findTopByAddedByUserOrderByDateOfActivityDesc(user);
    }
}
