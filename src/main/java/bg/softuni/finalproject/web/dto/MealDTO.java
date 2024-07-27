package bg.softuni.finalproject.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MealDTO {
    @Size(min = 1, max = 50, message = "Must be between 1 and 50 letters")
    private String breakfast;
    @Size(min = 1, max = 50, message = "Must be between 1 and 50 letters")
    private String lunch;
    @Size(min = 1, max = 50, message = "Must be between 1 and 50 letters")
    private String dinner;
    @Size(min = 1, max = 50, message = "Must be between 1 and 50 letters")
    private String snack;
    @NotNull(message = "The field must not be empty")
    @Min(value = 0, message = "Enter a number between 0 and 10000")
    @Max(value = 10000, message = "Enter a number between 0 and 10000")
    private int totalCalories;

    public MealDTO() {}

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    public String getSnack() {
        return snack;
    }

    public void setSnack(String snack) {
        this.snack = snack;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }
}
