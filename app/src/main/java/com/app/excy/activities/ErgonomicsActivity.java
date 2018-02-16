package com.app.excy.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;

import com.app.excy.R;
import com.app.excy.interfaces.OnListFragmentInteractionListener;
import com.app.excy.models.ExerciseTip;
import com.app.excy.models.StickyHeadersAdapter;
import com.app.excy.utilities.AppUtilities;
import com.app.excy.utilities.Constants;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ErgonomicsActivity extends AppCompatActivity implements OnListFragmentInteractionListener {
    ArrayList<ExerciseTip> mTips;
    StickyHeadersAdapter mAdapter;
    RecyclerView mRecyclerView;

    // Click Listener
    protected OnListFragmentInteractionListener mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        setContentView(R.layout.activity_ergonomics);

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_more);

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

        mListener = this;
        mTips = createTipList();

        mRecyclerView = (RecyclerView) findViewById(R.id.rvTips);
        mRecyclerView.setLayoutManager(new StickyHeaderLayoutManager());
        mAdapter = new StickyHeadersAdapter(this, mListener);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setSections(mTips);
    }

    private ArrayList<ExerciseTip> createTipList() {
        ArrayList<ExerciseTip> tips = new ArrayList<>();

        tips.add(new ExerciseTip(getString(R.string.arms1), getString(R.string.link_arms1), 1,
                ExerciseTip.ExerciseType.ARMS));
        tips.add(new ExerciseTip(getString(R.string.arms2), getString(R.string.link_arms2), 2,
                ExerciseTip.ExerciseType.ARMS));
        tips.add(new ExerciseTip(getString(R.string.arms3), getString(R.string.link_arms3), 3,
                ExerciseTip.ExerciseType.ARMS));
        tips.add(new ExerciseTip(getString(R.string.arms4), getString(R.string.link_arms4), 4,
                ExerciseTip.ExerciseType.ARMS));
        tips.add(new ExerciseTip(getString(R.string.arms5), getString(R.string.link_arms5), 5,
                ExerciseTip.ExerciseType.ARMS));
        tips.add(new ExerciseTip(getString(R.string.arms6), getString(R.string.link_arms6), 6,
                ExerciseTip.ExerciseType.ARMS));
        tips.add(new ExerciseTip(getString(R.string.arms7), getString(R.string.link_arms7), 7,
                ExerciseTip.ExerciseType.ARMS));
        tips.add(new ExerciseTip(getString(R.string.arms8), getString(R.string.link_arms8), 8,
                ExerciseTip.ExerciseType.ARMS));
        tips.add(new ExerciseTip(getString(R.string.arms9), getString(R.string.link_arms9), 9,
                ExerciseTip.ExerciseType.ARMS));
        tips.add(new ExerciseTip(getString(R.string.arms10), getString(R.string.link_arms10), 10,
                ExerciseTip.ExerciseType.ARMS));
        tips.add(new ExerciseTip(getString(R.string.arms11), getString(R.string.link_arms11), 11,
                ExerciseTip.ExerciseType.ARMS));
        tips.add(new ExerciseTip(getString(R.string.arms12), getString(R.string.link_arms12), 12,
                ExerciseTip.ExerciseType.ARMS));

        tips.add(new ExerciseTip(getString(R.string.legs1), getString(R.string.link_legs1), 1,
                ExerciseTip.ExerciseType.LEGS));
        tips.add(new ExerciseTip(getString(R.string.legs2), getString(R.string.link_legs2), 2,
                ExerciseTip.ExerciseType.LEGS));
        tips.add(new ExerciseTip(getString(R.string.legs3), getString(R.string.link_legs3), 3,
                ExerciseTip.ExerciseType.LEGS));
        tips.add(new ExerciseTip(getString(R.string.legs4), getString(R.string.link_legs4), 4,
                ExerciseTip.ExerciseType.LEGS));
        tips.add(new ExerciseTip(getString(R.string.legs5), getString(R.string.link_legs5), 5,
                ExerciseTip.ExerciseType.LEGS));
        tips.add(new ExerciseTip(getString(R.string.legs6), getString(R.string.link_legs6), 6,
                ExerciseTip.ExerciseType.LEGS));
        tips.add(new ExerciseTip(getString(R.string.legs7), getString(R.string.link_legs7), 7,
                ExerciseTip.ExerciseType.LEGS));
        tips.add(new ExerciseTip(getString(R.string.legs8), getString(R.string.link_legs8), 8,
                ExerciseTip.ExerciseType.LEGS));
        tips.add(new ExerciseTip(getString(R.string.legs9), getString(R.string.link_legs9), 9,
                ExerciseTip.ExerciseType.LEGS));

        return tips;
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_more);
    }

    @Override
    public void onListFragmentInteraction(ExerciseTip tip) {
        if (tip != null && !TextUtils.isEmpty(tip.getUrl())) {
            Intent webViewIntent = new Intent(this, WebViewActivity.class);
            webViewIntent.putExtra(Constants.WEBVIEW_URL, tip.getUrl());

            startActivity(webViewIntent);
        }
    }
}
