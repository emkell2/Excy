package com.excy.excy.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by erin.kelley on 2/26/17.
 */

public class WorkoutUtilities {
    public static final String WORKOUT_DATA = "WORKOUT DATA";
    public static final String SHARED_PREFS = "Shared Prefs";

    // Workout Keys
    public static final String WORKOUT_DATA_RES_ID = "WORKOUT_DATA_RES_ID";
    public static final String WORKOUT_DATA_TIME_MILLIS = "WORKOUT_DATA_TIME_MILLIS";
    public static final String WORKOUT_DATA_AUDIO_RES_ID = "WORKOUT_DATA_AUDIO_RES_ID";

    // Intent Keys
    public static final String INTENT_SET_INTERVAL = "INTENT SET INTERVAL";

    // Intent Filters
    public static final String INTENT_START_PLAY_TIMER = "INTENT START PLAY TIMER";
    public static final String INTENT_START_WORKOUT_TIMER = "INTENT START WORKOUT TIMER";

    // Persist Keys
    public static final String KEY_MEMBER_SINCE = "Key Member Since";
    public static final String KEY_USER_EMAIL = "Key User Email";
    public static final String KEY_HEALTHY_DESC = "Key Healthy Description";
    public static final String KEY_CALS_PER_WEEK = "Key Calories a week";
    public static final String KEY_WORKOUTS_PER_WEEK = "Key Workouts a week";
    public static final String KEY_MY_INSPIRATION = "Key My Inspiration";

    // Workout Names
    public static final String WORKOUT_ARM_CANDY = "Arm Candy";
    public static final String WORKOUT_SUPER_CYCLE_CARDIO = "Super Cycle Cardio";
    public static final String WORKOUT_CYCLE_LEG_BLAST = "Cycle Leg Blast";
    public static final String WORKOUT_CORE_FLOOR_EXPLOSION = "Core Floor Explosion";
    public static final String WORKOUT_ARM_BLAST = "Arm Blast";
    public static final String WORKOUT_ULTIMATE_ARM_LEG_TONING = "Ultimate Arm & Leg Toning";
    public static final String WORKOUT_INTERVAL = "Interval Workout";

    // PowerZone int Arrays
    public static final int[] PZ_ARM_CANDY_ARR = {3, 5, 3, 5, 3, 5, 3};
    public static final int[] PZ_SUPER_CYCLE_CARDIO_ARR = {2, 3, 3, 4, 4, 5, 5, 3, 3, 5, 5, 3, 3, 5, 5, 3, 3, 4, 4, 5, 5, 4, 3};
    public static final int[] PZ_CYCLE_LEG_BLAST_ARR = {3, 3, 4, 4, 5, 3, 4, 4, 5, 3, 3, 4, 4, 5, 3};
    public static final int[] PZ_CORE_FLOOR_EXPLOSION_ARR = {3, 5, 3, 5, 3, 5, 3, 5, 3, 5};
    public static final int[] PZ_ARM_BLAST_ARR = {3, 5, 3, 5, 3, 5, 3, 5, 3, 5};
    public static final int[] PZ_ULTIMATE_ARM_LEG_TONING_ARR = {3, 5, 3, 5, 3, 5, 3};

    // Enjoyment Strings
    public static final String AMAZING = "amazing";
    public static final String GREAT = "great";
    public static final String GOOD = "good";
    public static final String BAD = "bad";
    public static final String AWFUL = "awful";

    // Location Strings
    public static final String AT_HOME = "at home";
    public static final String ON_THE_GO = "on the go";
    public static final String TRAVELING = "traveling";
    public static final String AT_WORK = "at work";

    // Workout times in milliseconds
    public static long armCandyTimeMS = 420000;
    public static long superCycleCardioTimeMS = 1380000;
    public static long cycleLegBlastTimeMS = 900000;
    public static long coreFloorExplosionTimeMS = 600000;
    public static long armBlastTimeMS = 600000;
    public static long ultimateArmAndLegTimeMS = 420000;

    public static String getElapsedTime(long originalStartTime, int min, int sec) {
        int elaspedSeconds = calculateElapsedTime(originalStartTime, min, sec);
        int totalMin = elaspedSeconds / 60;
        int totalSec = elaspedSeconds % 60;

        return PlayUtilities.createTimerString(totalMin, totalSec);
    }

    public static int calculateCaloriesBurned(long originalStartTime, int min, int sec) {
        int totalSecs = calculateElapsedTime(originalStartTime, min, sec);
        return ((totalSecs / 60) * 12);
    }

    public static int calculateElapsedTime(long originalStartTime, int min, int sec) {
        int startTimeSecs = (int) (originalStartTime / 1000);

        int secondsRemaining = (min * 60) + sec;
        int elaspedSeconds = startTimeSecs - secondsRemaining;

        return elaspedSeconds;
    }

    public static String getWorkoutTimestamp() {
        return new SimpleDateFormat("EEEE hh:mm a").format(new Date());
    }

    public static String getMemberSinceTimestamp() {
        return new SimpleDateFormat("MMM d yyyy").format(new Date());
    }

    public static void persistString(Activity activity, String key, String data) {
        SharedPreferences sharedPref = activity.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, data);
        editor.commit();
    }

    public static String getPersistedString(Activity activity, String key) {
        SharedPreferences sharedPref = activity.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }

    public static void persistInteger(Activity activity, String key, int data) {
        SharedPreferences sharedPref = activity.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, data);
        editor.commit();
    }

    public static int getPersistedInt(Activity activity, String key) {
        SharedPreferences sharedPref = activity.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPref.getInt(key, -1);
    }

    public static void persistBoolean(Activity activity, String key, boolean data) {
        SharedPreferences sharedPref = activity.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, data);
        editor.commit();
    }

    public static boolean getPersistedBoolean(Activity activity, String key) {
        SharedPreferences sharedPref = activity.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, false);
    }
}