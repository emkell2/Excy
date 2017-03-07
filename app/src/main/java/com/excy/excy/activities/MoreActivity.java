package com.excy.excy.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.excy.excy.R;
import com.excy.excy.fragments.KnowZonesFragment;
import com.excy.excy.fragments.LearnExercisesFragment;
import com.excy.excy.fragments.TipsFragment;
import com.excy.excy.fragments.WatchWorkoutsFragment;
import com.excy.excy.utilities.AppUtilities;

import java.util.ArrayList;
import java.util.List;

public class MoreActivity extends AppCompatActivity implements
        LearnExercisesFragment.OnFragmentInteractionListener,
        WatchWorkoutsFragment.OnFragmentInteractionListener,
        KnowZonesFragment.OnFragmentInteractionListener,
        TipsFragment.OnFragmentInteractionListener{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_more);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tlTabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LearnExercisesFragment(),
                getResources().getString(R.string.learn_exercises));
        adapter.addFragment(new WatchWorkoutsFragment(),
                getResources().getString(R.string.watch_workouts));
        adapter.addFragment(new KnowZonesFragment(), getResources().getString(R.string.know_zones));
        adapter.addFragment(new TipsFragment(), getResources().getString(R.string.tips));
        viewPager.setAdapter(adapter);
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



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
