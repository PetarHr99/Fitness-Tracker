package bg.softuni.finalproject.service;

import bg.softuni.finalproject.Entity.Activity;
import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.repo.ActivityRepository;
import bg.softuni.finalproject.repo.UserRepository;
import bg.softuni.finalproject.web.dto.ActivityDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final ModelMapper modelMapper;

    public ActivityService(ActivityRepository activityRepository, ModelMapper modelMapper) {
        this.activityRepository = activityRepository;
        this.modelMapper = modelMapper;
    }
    public Activity saveActivity(ActivityDTO activityDTO, User currentUser) {
        Activity activity = modelMapper.map(activityDTO, Activity.class);
        activity.setAddedByUser(currentUser);
        return activityRepository.save(activity);
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
