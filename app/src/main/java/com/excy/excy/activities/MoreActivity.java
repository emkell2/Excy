package com.excy.excy.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.excy.excy.R;
import com.excy.excy.fragments.ExerciseSummaryFragment;
import com.excy.excy.fragments.KnowZonesFragment;
import com.excy.excy.fragments.LearnExercisesFragment;
import com.excy.excy.fragments.MoreBaseFragment;
import com.excy.excy.fragments.TipsFragment;
import com.excy.excy.fragments.WatchWorkoutsFragment;
import com.excy.excy.utilities.AppUtilities;

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
                getResources().getString(R.string.more_base_frag)).commit();

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
                        startActivity(intent);
                        break;
                    case R.id.action_workouts:
                        intent = new Intent(getBaseContext(), WorkoutListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_me:
                        intent = new Intent(getBaseContext(), MeActivity.class);
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
