package bg.softuni.finalproject.web.dto;

import bg.softuni.finalproject.Entity.Exercise;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDTO {
    @NotEmpty(message = "Title is required")
    private String title;

    private List<Exercise> exercises;

    public WorkoutDTO() {
        this.exercises = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
