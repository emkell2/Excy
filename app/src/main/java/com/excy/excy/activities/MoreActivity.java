package com.excy.excy.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

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
