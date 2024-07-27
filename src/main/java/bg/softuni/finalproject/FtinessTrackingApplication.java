package bg.softuni.finalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FtinessTrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(FtinessTrackingApplication.class, args);
    }

}
