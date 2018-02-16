package com.app.excy.models;

/**
 * Created by erin.kelley on 4/21/17.
 */

public class Workout {
    String id;
    String workoutTitle;
    String dateCompleted;
    String totalTime;
    int minTemp;
    int maxTemp;
    int caloriesBurned;
    String enjoyment;
    String location;

    public Workout() {
        // Empty constructor
    }

    public Workout(String name, String date, String time, int minTemp, int maxTemp, int caloriesBurned,
                   String enjoyment, String location) {
        this.workoutTitle = name;
        this.dateCompleted = date;
        this.totalTime = time;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.caloriesBurned = caloriesBurned;
        this.enjoyment = enjoyment;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkoutTitle() {
        return workoutTitle;
    }

    public void setWorkoutTitle(String workoutTitle) {
        this.workoutTitle = workoutTitle;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public String getEnjoyment() {
        return enjoyment;
    }

    public void setEnjoyment(String enjoyment) {
        this.enjoyment = enjoyment;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
