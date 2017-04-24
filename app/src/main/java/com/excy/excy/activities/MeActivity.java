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
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.excy.excy.R;
import com.excy.excy.models.Workout;
import com.excy.excy.utilities.AppUtilities;
import com.excy.excy.utilities.WorkoutUtilities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.excy.excy.activities.PlayActivity.getContext;

public class MeActivity extends AppCompatActivity {
    private ArrayList<Workout> workoutList;
    private int workoutListSize = 5;
    private int count = 0;
    RVAdapter mAdapter;

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
        mAdapter = new RVAdapter(workoutList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance()
                .getReference(AppUtilities.TABLE_NAME_WORKOUTS + "/" + userId);

        mDatabaseRef.addChildEventListener(new ChildEventListener() {
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

        Button quickStatsBtn = (Button) findViewById(R.id.btnQuickStats);

        quickStatsBtn.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent),
                PorterDuff.Mode.MULTIPLY);

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

class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    ArrayList<Workout> workouts;

    RVAdapter(ArrayList<Workout> workoutList){
        this.workouts = workoutList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_card, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.workoutTitle.setText(workouts.get(position).getWorkoutTitle());
        holder.workoutDate.setText(workouts.get(position).getDateCompleted());
        holder.totalTime.setText(workouts.get(position).getTotalTime());
        holder.minTemp.setText("min: " + String.valueOf(workouts.get(position).getMinTemp()));
        holder.maxTemp.setText("max: " + String.valueOf(workouts.get(position).getMaxTemp()));
        holder.caloriesBurned.setText(String.valueOf(workouts.get(position).getCaloriesBurned()));

        // Set enjoyment data
        String enjoyment = workouts.get(position).getEnjoyment();
        if (!TextUtils.isEmpty(enjoyment)) {
            holder.enjoyment.setText(enjoyment);

            switch (enjoyment) {
                case WorkoutUtilities.AMAZING:
                case WorkoutUtilities.GREAT:
                    holder.faceImage.setImageDrawable(getContext().getResources()
                            .getDrawable(R.drawable.ic_smilie_happy));
                    break;
                case WorkoutUtilities.GOOD:
                    holder.faceImage.setImageDrawable(getContext().getResources()
                            .getDrawable(R.drawable.ic_smilie_satisfied));
                    break;
                case WorkoutUtilities.BAD:
                case WorkoutUtilities.AWFUL:
                    holder.faceImage.setImageDrawable(getContext().getResources()
                            .getDrawable(R.drawable.ic_smilie_sad));
                    break;
                default:
                    holder.faceImage.setImageDrawable(getContext().getResources()
                            .getDrawable(R.drawable.ic_smilie_happy));
                    break;
            }
        }

        // Set location data
        String location = workouts.get(position).getLocation();
        if (!TextUtils.isEmpty(location)) {
            holder.location.setText(location);

            switch (location) {
                case WorkoutUtilities.AT_HOME:
                    holder.locationImage.setImageDrawable(getContext().getResources()
                            .getDrawable(R.drawable.account_home));
                    break;
                case WorkoutUtilities.AT_WORK:
                    holder.locationImage.setImageDrawable(getContext().getResources()
                            .getDrawable(R.drawable.account_work));
                    break;
                case WorkoutUtilities.TRAVELING:
                    holder.locationImage.setImageDrawable(getContext().getResources()
                            .getDrawable(R.drawable.account_traveling));
                    break;
                case WorkoutUtilities.ON_THE_GO:
                    holder.locationImage.setImageDrawable(getContext().getResources()
                            .getDrawable(R.drawable.account_on_the_go));
                    break;
                default:
                    holder.locationImage.setImageDrawable(getContext().getResources()
                            .getDrawable(R.drawable.account_home));
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView workoutTitle;
        TextView workoutDate;
        TextView totalTime;
        TextView minTemp;
        TextView maxTemp;
        TextView caloriesBurned;
        TextView enjoyment;
        TextView location;
        ImageView faceImage;
        ImageView locationImage;

        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cvWorkout);
            workoutTitle = (TextView) itemView.findViewById(R.id.tvCardWorkoutName);
            workoutDate = (TextView) itemView.findViewById(R.id.tvCardWorkoutDate);
            totalTime = (TextView) itemView.findViewById(R.id.tvWorkoutTime);
            minTemp = (TextView) itemView.findViewById(R.id.tvMin);
            maxTemp = (TextView) itemView.findViewById(R.id.tvMax);
            caloriesBurned = (TextView) itemView.findViewById(R.id.tvBurn);
            enjoyment = (TextView) itemView.findViewById(R.id.tvFace);
            location = (TextView) itemView.findViewById(R.id.tvWorkoutLocation);
            faceImage = (ImageView) itemView.findViewById(R.id.ivFace);
            locationImage = (ImageView) itemView.findViewById(R.id.ivLocation);
        }
    }
}
