package com.excy.excy.activities;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.excy.excy.R;

public class WorkoutListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottomNavigationView);

        // Need to manually perform a click action on the icon that was clicked in previous activity
        // or else bottom nav bar will reset actively selected icon upon inflation of this layout
        View view = bottomNavigationView.findViewById(R.id.action_workouts);
        view.performClick();
    }
}
