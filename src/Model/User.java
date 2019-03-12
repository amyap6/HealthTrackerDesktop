package Model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class User extends Profile {

    private String userName;
    private String password;
    private int sex;           // Male = 0, Female = 1
    private int height;
    private double weight;
    private double activityLevel;
    private int bmi;
    private int idealWeight;
    private int age;
    private LocalDate dob;
    private ArrayList<Goal> goals;
    private int fat;
    private int protein;
    private int carbs;

    public User(String userName) {
        this.userName = userName;
        goals = new ArrayList<>();
    }

    public void addGoal(Goal g) {
        goals.add(g);
    }

    public void removeGoal(Goal g) {
        goals.remove(g);
    }

    public ArrayList<Goal> getGoals() {
        return goals;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setActivityLevel(double activityLevel) {
        this.activityLevel = activityLevel;
    }

    public void setBmi(int bmi) {
        this.bmi = bmi;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDob(LocalDate birth) {
        this.dob = birth;
        this.age = Period.between(birth, LocalDate.now()).getYears();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdealWeight() {
        return idealWeight;
    }

    public void setIdealWeight(int idealWeight) {
        this.idealWeight = idealWeight;
    }


    public String getUserName(){
        return userName;
    }

    public int getSex() {
        return this.sex;
    }

    public int getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public double getActivityLevel() {
        return this.activityLevel;
    }

    public int getBmi() {
        return bmi;
    }

    public int getAge() {
        return age;
    }


    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }


    public int getAllowedCalories() {
        double metaRate = Calculator.metabolicRate(getWeight(), getHeight(), getAge());
        int extremity = Calculator.getWeightLossExtremity(getGoals().get(0));

        return Calculator.targetCalories(metaRate, getActivityLevel(), extremity);
    }

    @Override
    public String toString() {
        String s = super.toString() + "\n" + "Age: " + age + "\n" +
                "Height: " + height + " - Weight: " + weight + " - BMI: "
                + bmi +" - Sex: "+ sex + " - Activity Level: "+activityLevel;
        return s;
    }
}
