package bg.softuni.finalproject.web.dto;

import bg.softuni.finalproject.Entity.enums.Gender;
import bg.softuni.finalproject.Entity.enums.SubscriptionPlan;
import bg.softuni.finalproject.Entity.enums.TargetGoal;
import jakarta.validation.constraints.*;

public class UserRegisterDTO {
    @NotBlank(message = "Username must not be blank")
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    private String username;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Age must not be null")
    @Min(value = 0, message = "Age must be at least 0")
    @Max(value = 100, message = "Age must be less than or equal to 100")
    private Integer age;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 5, max = 20, message = "Password must be between 5 and 20 characters")
    private String password;

    private String confirmPassword;

    private SubscriptionPlan subscriptionPlan;

    @NotNull(message = "Height must not be null")
    @Min(value = 0, message = "Height must be at least 0 cm")
    @Max(value = 300, message = "Height must be less than or equal to 300 cm")
    private Integer height;

    @NotNull(message = "Weight must not be null")
    @Min(value = 0, message = "Weight must be at least 0 kg")
    @Max(value = 1000, message = "Weight must be less than or equal to 1000 kg")
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
