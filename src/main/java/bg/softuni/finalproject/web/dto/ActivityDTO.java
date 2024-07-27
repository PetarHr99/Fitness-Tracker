package bg.softuni.finalproject.web.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class ActivityDTO {
    @NotBlank(message = "The field must not be empty")
    @Size(min = 2, max = 50, message = "Must be between 2 and 50 letters")
    private String typeOfActivity;

    @NotNull(message = "The date of activity must not be null")
    private LocalDate dateOfActivity;

    @Min(value = 0, message = "Enter a number between 0 and 10000")
    @Max(value = 10000, message = "Enter a number between 0 and 10000")
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
