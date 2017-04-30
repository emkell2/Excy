package com.excy.excy.models;

/**
 * Created by erin.kelley on 4/18/17.
 */

public class User {
    private String username;
    private String email;
    private String gender;
    private int weight;
    private int height;
    private int age;
    private String profileImageUrl;
    private String inspiringImage1;
    private String inspiringImage2;
    private String inspiringImage3;
    private String manifesto;
    private String calorieGoal;
    private String workoutGoal;
    private String memberSince;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String gender, int weight, int height, int age, String username, String email) {
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.username = username;
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getInspiringImage1() {
        return inspiringImage1;
    }

    public void setInspiringImage1(String inspiringImage1) {
        this.inspiringImage1 = inspiringImage1;
    }

    public String getInspiringImage2() {
        return inspiringImage2;
    }

    public void setInspiringImage2(String inspiringImage2) {
        this.inspiringImage2 = inspiringImage2;
    }

    public String getInspiringImage3() {
        return inspiringImage3;
    }

    public void setInspiringImage3(String inspiringImage3) {
        this.inspiringImage3 = inspiringImage3;
    }

    public String getManifesto() {
        return manifesto;
    }

    public void setManifesto(String manifesto) {
        this.manifesto = manifesto;
    }

    public String getCalorieGoal() {
        return calorieGoal;
    }

    public void setCalorieGoal(String calorieGoal) {
        this.calorieGoal = calorieGoal;
    }

    public String getWorkoutGoal() {
        return workoutGoal;
    }

    public void setWorkoutGoal(String workoutGoal) {
        this.workoutGoal = workoutGoal;
    }

    public String getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(String memberSince) {
        this.memberSince = memberSince;
    }
}
