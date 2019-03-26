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
        XCS200_ARMS("XCS200 Arms"),
        XCS200_LEGS("XCS200 Legs"),
        XCR300_ARMS("XCR300 Arms"),
        XCR300_LEGS("XCR300 Legs"),
        XCS200_LOWER_BODY("XCS200 Lower Body"),
        XCS200_UPPER_BODY("XCS200 Upper Body"),
        XCS200_TOTAL_BODY("XCS200 Total Body"),
        XCR300_LOWER_BODY("XCR300 Lower Body"),
        XCR300_UPPER_BODY("XCR300 Upper Body");

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
