package bg.softuni.finalproject.web.dto;

import bg.softuni.finalproject.exercises.ExerciseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDTO {
    @NotEmpty
    @NotBlank
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
