package bg.softuni.finalproject.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "meals")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String breakfast;
    private String lunch;
    private String dinner;
    private String snack;
    @Column(nullable = false)
    private int totalCalories;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User mealsAddedBy;

    public Meal() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public User getMealsAddedBy() {
        return mealsAddedBy;
    }

    public void setMealsAddedBy(User mealsAddedBy) {
        this.mealsAddedBy = mealsAddedBy;
    }
}
