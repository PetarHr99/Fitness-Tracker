package bg.softuni.finalproject.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.sql.Time;
import java.time.LocalDate;

public class ActivityDTO {
    @NotBlank
    @Size(min = 2, max = 50)
    private String typeOfActivity;
    private LocalDate dateOfActivity;
    private int calories;
    private String timeOfTraining;

    public ActivityDTO() {
    }

    public String getTypeOfActivity() {
        return typeOfActivity;
    }

    public void setTypeOfActivity(String typeOfActivity) {
        this.typeOfActivity = typeOfActivity;
    }

    public LocalDate getDateOfActivity() {
        return dateOfActivity;
    }

    public void setDateOfActivity(LocalDate dateOfActivity) {
        this.dateOfActivity = dateOfActivity;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getTimeOfTraining() {
        return timeOfTraining;
    }

    public void setTimeOfTraining(String timeOfTraining) {
        this.timeOfTraining = timeOfTraining;
    }
}
