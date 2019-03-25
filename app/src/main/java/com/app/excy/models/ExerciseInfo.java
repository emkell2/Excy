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
        XCS200_ARMS("Arms"),
        XCS200_LEGS("Legs"),
        XCR300_ARMS("Arms"),
        XCR300_LEGS("Legs"),
        XCS200_LOWER_BODY("Lower Body"),
        XCS200_UPPER_BODY("Upper Body"),
        XCS200_TOTAL_BODY("Total Body"),
        XCR300_LOWER_BODY("Lower Body"),
        XCR300_UPPER_BODY("Upper Body");

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
