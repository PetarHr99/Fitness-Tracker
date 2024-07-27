package bg.softuni.finalproject.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class WorkoutDTO {
    @NotEmpty(message = "Title must not be empty")
    @NotBlank(message = "Title must not be blank")
    @Size(min = 2, max = 50, message = "Must be between 2 and 50 letters")
    private String title;

    private List<ExerciseDTO> exercises;
    public WorkoutDTO() {}
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public List<ExerciseDTO> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseDTO> exercises) {
        this.exercises = exercises;
    }
}
