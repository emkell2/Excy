package com.app.excy.models;

/**
 * Created by erin.kelley on 10/16/17.
 */

public class ExerciseInfo {
    String description;
    String url;
    int id;
    ExerciseType exerciseType;

    public enum ExerciseType {
        WORKOUT("Workout"),
        ARMS("Arms"),
        LEGS("Legs"),
        LOWER_BODY("Lower Body"),
        UPPER_BODY("Upper Body"),
        TOTAL_BODY("Total Body");

        private final String exercise;

        ExerciseType(final String exercise) {
            this.exercise = exercise;
        }

        @Override
        public String toString() {
            return exercise;
        }
    }

    public ExerciseInfo(String description, String url, int id, ExerciseType type) {
        this.description = description;
        this.url = url;
        this.id = id;
        this.exerciseType = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
