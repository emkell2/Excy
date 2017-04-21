package com.excy.excy.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by erin.kelley on 2/26/17.
 */

public class WorkoutUtilities {
    public static final String WORKOUT_DATA = "WORKOUT DATA";

    // Workout Keys
    public static final String WORKOUT_DATA_RES_ID = "WORKOUT_DATA_RES_ID";
    public static final String WORKOUT_DATA_TIME_MILLIS = "WORKOUT_DATA_TIME_MILLIS";
    public static final String WORKOUT_DATA_AUDIO_RES_ID = "WORKOUT_DATA_AUDIO_RES_ID";

    // Intent Keys
    public static final String INTENT_TIME_REMAINING = "INTENT TIME REMAINING";
    public static final String INTENT_SET_INTERVAL = "INTENT SET INTERVAL";

    // Intent Filters
    public static final String INTENT_START_PLAY_TIMER = "INTENT START PLAY TIMER";
    public static final String INTENT_START_WORKOUT_TIMER = "INTENT START WORKOUT TIMER";

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

    // Workout times in milliseconds
    public static long armCandyTimeMS = 420000;
    public static long superCycleCardioTimeMS = 1380000;
    public static long cycleLegBlastTimeMS = 900000;
    public static long coreFloorExplosionTimeMS = 600000;
    public static long armBlastTimeMS = 600000;
    public static long ultimateArmAndLegTimeMS = 420000;

    public static String calculateElapsedTime(long originalStartTime, int min, int sec) {
        int startTimeSecs = (int) (originalStartTime / 1000);

        int secondsRemaining = (min * 60) + sec;
        int elaspedSeconds = startTimeSecs - secondsRemaining;
        int totalMin = elaspedSeconds / 60;
        int totalSec = elaspedSeconds % 60;

        return PlayUtilities.createTimerString(totalMin, totalSec);
    }

    public static int calculateCaloriesBurned(int min, int sec) {
        int seconds = (min * 60) + sec;
        return ((seconds / 60) * 12);
    }

    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat("EEEE hh:mm a").format(new Date());
    }
}
