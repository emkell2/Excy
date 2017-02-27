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

    public static void setBottomNavBarIconActive(Activity activity, int resId) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                activity.findViewById(R.id.bottomNavigationView);

        // Need to manually perform a click action on the icon that was clicked in previous activity
        // or else bottom nav bar will reset actively selected icon upon inflation of this layout
        View view = bottomNavigationView.findViewById(resId);
        view.performClick();
    }
}
