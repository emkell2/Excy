package com.app.excy.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.app.excy.R;
import com.app.excy.fragments.ExerciseSummaryFragment;
import com.app.excy.fragments.KnowZonesFragment;
import com.app.excy.fragments.LearnExercisesFragment;
import com.app.excy.fragments.MoreBaseFragment;
import com.app.excy.fragments.TipsFragment;
import com.app.excy.fragments.WatchWorkoutsFragment;
import com.app.excy.utilities.AppUtilities;

public class MoreActivity extends AppCompatActivity implements
        MoreBaseFragment.OnFragmentInteractionListener,
        LearnExercisesFragment.OnFragmentInteractionListener,
        ExerciseSummaryFragment.OnFragmentInteractionListener,
        WatchWorkoutsFragment.OnFragmentInteractionListener,
        KnowZonesFragment.OnFragmentInteractionListener,
        TipsFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_more);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment moreBaseFrag = new MoreBaseFragment();
        fragmentManager.beginTransaction().replace(R.id.more_fragment_container, moreBaseFrag,
                getResources().getString(R.string.more_base_frag)).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottomNavigationView);

        AppUtilities.removeShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.action_play:
                        intent = new Intent(getBaseContext(), PlayActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        break;
                    case R.id.action_workouts:
                        intent = com.app.excy.activities.WorkoutActivity.sisActive
                                ? new Intent(getBaseContext(), WorkoutActivity.class)
                                : new Intent(getBaseContext(), WorkoutListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        break;
                    case R.id.action_me:
                        intent = new Intent(getBaseContext(), MeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_more);
    }

    @Override
    public void onBaseFragCreated(Uri uri) {
        // Do Nothing
    }

    @Override
    public void onExerciseSelected(Uri uri) {
        // Do Nothing
    }

    @Override
    public void onWorkoutSelected(Uri uri) {
        // Do Nothing
    }

    @Override
    public void onZoneSelected(Uri uri) {
        // Do Nothing
    }

    @Override
    public void onTipSelected(Uri uri) {
        // Do Nothing
    }

    @Override
    public void onSummaryRead(Uri uri) {
        // Do Nothing
    }
}
