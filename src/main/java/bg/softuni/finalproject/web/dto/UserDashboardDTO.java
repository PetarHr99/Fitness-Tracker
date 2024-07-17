package bg.softuni.finalproject.web.dto;

public class UserDashboardDTO {
    private String targetGoal;
    private Integer age;
    private Double weight;
    private Integer height;
    private String gender;
    private Double currentWeight;
    private Double weightDifference;
    private Double recommendedCalories;
    private MealDTO lastMeal;
    private ActivityDTO lastActivity;


    public UserDashboardDTO() {
    }


    public Double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public String getTargetGoal() {
        return targetGoal;
    }

    public void setTargetGoal(String targetGoal) {
        this.targetGoal = targetGoal;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public MealDTO getLastMeal() {
        return lastMeal;
    }

    public void setLastMeal(MealDTO lastMeal) {
        this.lastMeal = lastMeal;
    }

    public Double getWeightDifference() {
        return weightDifference;
    }

    public void setWeightDifference(Double weightDifference) {
        this.weightDifference = weightDifference;
    }

    public Double getRecommendedCalories() {
        return recommendedCalories;
    }

    public void setRecommendedCalories(Double recommendedCalories) {
        this.recommendedCalories = recommendedCalories;
    }

    public ActivityDTO getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(ActivityDTO lastActivity) {
        this.lastActivity = lastActivity;
    }
}
