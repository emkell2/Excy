package com.excy.excy.utilities;

import android.app.Activity;
import android.support.design.widget.BottomNavigationView;
import android.view.View;

import com.excy.excy.R;

/**
 * Created by erin.kelley on 2/26/17.
 */

public class WorkoutUtilities {
    public static final String WORKOUT_INTENT_DATA = "WORKOUT_INTENT_DATA";

    // PowerZone int Arrays
    public static final int[] PZ_ARM_CANDY_ARR = {3, 5, 3, 5, 3, 5, 3};
    public static final int[] PZ_SUPER_CYCLE_CARDIO_ARR = {2, 3, 3, 4, 4, 5, 5, 3, 3, 5, 5, 3, 3, 5, 5, 3, 3, 4, 4, 5, 5, 4, 3};
    public static final int[] PZ_CYCLE_LEG_BLAST_ARR = {3, 3, 4, 4, 5, 3, 4, 4, 5, 3, 3, 4, 4, 5, 3};
    public static final int[] PZ_CORE_FLOOR_EXPLOSION_ARR = {3, 5, 3, 5, 3, 5, 3, 5, 3, 5};
    public static final int[] PZ_ARM_BLAST_ARR = {3, 5, 3, 5, 3, 5, 3, 5, 3, 5};
    public static final int[] PZ_ULTIMATE_ARM_LEG_TONING_ARR = {3, 5, 3, 5, 3, 5, 3};

    public static void setBottomNavBarIconActive(Activity activity, int resId) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                activity.findViewById(R.id.bottomNavigationView);

        // Need to manually perform a click action on the icon that was clicked in previous activity
        // or else bottom nav bar will reset actively selected icon upon inflation of this layout
        View view = bottomNavigationView.findViewById(resId);
        view.performClick();
    }

}
