package com.excy.excy.models;

/**
 * Created by erin.kelley on 10/16/17.
 */

public class ExerciseTip {
    String tip;
    String url;
    int tipNum;
    ExerciseType exerciseType;

    public enum ExerciseType {
        WORKOUT("Workout"),
        ARMS("Arms"),
        LEGS("Legs");

        private final String exercise;

        ExerciseType(final String exercise) {
            this.exercise = exercise;
        }

        @Override
        public String toString() {
            return exercise;
        }
    }

    public ExerciseTip(String tip, String url, int tipNum, ExerciseType type) {
        this.tip = tip;
        this.url = url;
        this.tipNum = tipNum;
        this.exerciseType = type;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTipNum() {
        return tipNum;
    }

    public void setTipNum(int tipNum) {
        this.tipNum = tipNum;
    }
}
