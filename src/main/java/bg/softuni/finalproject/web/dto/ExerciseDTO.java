package bg.softuni.finalproject.web.dto;

import jakarta.validation.constraints.*;

public class ExerciseDTO {
    @NotBlank(message = "Exercise name must not be empty")
    @Size(min = 2, max = 50, message = "Must be between 2 and 50 letters")
    private String name;

    @Min(value = 0, message = "Sets must be at least 0")
    @Max(value = 100, message = "Sets must be less than or equal to 100")
    private int sets;

    @Min(value = 0, message = "Reps must be at least 0")
    @Max(value = 100, message = "Reps must be less than or equal to 100")
    private int reps;

    @Min(value = 0, message = "Weight must be at least 0")
    @Max(value = 10000, message = "Weight must be less than or equal to 10000")
    private double weight;

    public ExerciseDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


}
