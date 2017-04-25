package com.excy.excy.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.excy.excy.R;
import com.excy.excy.models.Workout;
import com.excy.excy.models.WorkoutsAdapter;
import com.excy.excy.utilities.AppUtilities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.excy.excy.activities.PlayActivity.getContext;

public class MeActivity extends AppCompatActivity {
    public static final String MEMBER_SINCE = "memberSince";

    private ArrayList<Workout> workoutList;
    private int workoutListSize = 5;
    private int count = 0;
    WorkoutsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_me);

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
                    case R.id.action_more:
                        intent = new Intent(getBaseContext(), MoreActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        if (workoutList == null) {
            workoutList = new ArrayList<>(workoutListSize);
        }

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rvRecentWorkouts);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new WorkoutsAdapter(workoutList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mWorkoutsDatabaseRef = database
                .getReference(AppUtilities.TABLE_NAME_WORKOUTS + "/" + userId);

        mWorkoutsDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Workout workout = dataSnapshot.getValue(Workout.class);
                workout.setId(dataSnapshot.getKey());
                workoutList.add(workout);
                count++;

                if (count > workoutListSize) {
                    workoutList.remove(0);
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Workout workout = dataSnapshot.getValue(Workout.class);
                workoutList.remove(workout);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        // Set friend since TextView
        final TextView memberSinceTV = (TextView) findViewById(R.id.tvFriendSince);

        DatabaseReference mUserDatabaseRef = database
                .getReference(AppUtilities.TABLE_NAME_USERS + "/" + userId);

        mUserDatabaseRef.child(MEMBER_SINCE).
                addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String friendSince = dataSnapshot.getValue().toString();
                if (!TextUtils.isEmpty(friendSince)) {
                    memberSinceTV.setText(memberSinceTV.getText() + " " + friendSince);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (databaseError != null) {
                    Log.d("DB Error", "Error retrieving memberSince field: " + databaseError.getMessage());
                }
            }
        });

        Button quickStatsBtn = (Button) findViewById(R.id.btnQuickStats);

        quickStatsBtn.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent),
                PorterDuff.Mode.MULTIPLY);
        quickStatsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), QuickStatsActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabEditProfile);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();

        params.setAnchorId(R.id.bottomNavigationView);
        params.anchorGravity = Gravity.TOP | Gravity.RIGHT | GravityCompat.END;
        fab.setLayoutParams(params);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}

