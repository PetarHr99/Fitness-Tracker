package bg.softuni.finalproject.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workouts")
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
    private List<Exercise> exercises;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User workoutAddedBy;

    public Workout() {
        this.exercises = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getWorkoutAddedBy() {
        return workoutAddedBy;
    }

    public void setWorkoutAddedBy(User workoutAddedBy) {
        this.workoutAddedBy = workoutAddedBy;
    }
}
