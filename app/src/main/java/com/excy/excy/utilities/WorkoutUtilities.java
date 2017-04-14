package com.excy.excy.utilities;

/**
 * Created by erin.kelley on 2/26/17.
 */

public class WorkoutUtilities {
    public static final String WORKOUT_DATA_RES_ID = "WORKOUT_DATA_RES_ID";
    public static final String WORKOUT_DATA_TIME_MILLIS = "WORKOUT_DATA_TIME_MILLIS";
    public static final String WORKOUT_DATA_AUDIO_RES_ID = "WORKOUT_DATA_AUDIO_RES_ID";

    // Intent Keys
    public static final String INTENT_TIME_REMAINING = "INTENT TIME REMAINING";
    public static final String INTENT_SET_INTERVAL = "INTENT SET INTERVAL";

    // Intent Filters
    public static final String INTENT_START_TIMER = "INTENT START TIMER";

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
}
