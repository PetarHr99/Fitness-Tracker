package bg.softuni.finalproject.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private SubscriptionPlan subscriptionPlan;
    @Column(nullable = false)
    private Integer height;
    @Column(nullable = false)
    private Double weight;
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private TargetGoal targetGoal;
    @OneToMany(mappedBy = "workoutAddedBy", cascade = CascadeType.ALL)
    private List<Workout> addedWorkouts;

    @OneToMany(mappedBy = "addedByUser", cascade = CascadeType.ALL)
    private List<Activity> addedActivities;

    @OneToMany(mappedBy = "mealsAddedBy", cascade = CascadeType.ALL)
    private List<Meal> addedMeals;

    public User() {
        this.addedWorkouts = new ArrayList<>();
        this.addedActivities = new ArrayList<>();
        this.addedMeals = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Workout> getAddedWorkouts() {
        return addedWorkouts;
    }

    public void setAddedWorkouts(List<Workout> addedWorkouts) {
        this.addedWorkouts = addedWorkouts;
    }

    public List<Activity> getAddedActivities() {
        return addedActivities;
    }

    public void setAddedActivities(List<Activity> addedActivities) {
        this.addedActivities = addedActivities;
    }

    public List<Meal> getAddedMeals() {
        return addedMeals;
    }

    public void setAddedMeals(List<Meal> addedMeals) {
        this.addedMeals = addedMeals;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public TargetGoal getTargetGoal() {
        return targetGoal;
    }

    public void setTargetGoal(TargetGoal targetGoal) {
        this.targetGoal = targetGoal;
    }
}
