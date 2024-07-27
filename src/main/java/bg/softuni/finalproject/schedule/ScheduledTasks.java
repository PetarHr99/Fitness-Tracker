package bg.softuni.finalproject.schedule;

import bg.softuni.finalproject.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private final UserService userService;

    public ScheduledTasks(UserService userService) {
        this.userService = userService;
    }

    // Schedule to run once a day at midnight
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetDailyLoginStatus() {
        userService.resetDailyLoginStatus();
    }
}
