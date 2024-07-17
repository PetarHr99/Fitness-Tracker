package bg.softuni.finalproject.web.dto;

import bg.softuni.finalproject.Entity.enums.Gender;
import bg.softuni.finalproject.Entity.enums.SubscriptionPlan;
import bg.softuni.finalproject.Entity.enums.TargetGoal;
import jakarta.validation.constraints.*;

public class UserRegisterDTO {
    @NotBlank
    @Size(min = 5, max = 20)
    private String username;

    @NotBlank
    @Email
    private String email;

    @Min(0)
    @Max(100)
    private Integer age;

    @NotBlank
    @Size(min = 5, max = 20)
    private String password;

    private String confirmPassword;

    private SubscriptionPlan subscriptionPlan;

    @Min(0)
    @Max(300)
    private Integer height;
    @Min(0)
    @Max(1000)
    private Double weight;

    private Gender gender;
    private TargetGoal targetGoal;
    public UserRegisterDTO() {
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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
