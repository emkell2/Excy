package com.excy.excy.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.excy.excy.R;
import com.excy.excy.models.User;
import com.excy.excy.models.Workout;
import com.excy.excy.models.WorkoutsAdapter;
import com.excy.excy.utilities.AppUtilities;
import com.excy.excy.utilities.NonScrollableLinearLayoutManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.excy.excy.activities.PlayActivity.getContext;

public class MeActivity extends AppCompatActivity {

    User user;
    private ArrayList<Workout> workoutList;
    private int workoutListSize = 5;
    private int count = 0;
    WorkoutsAdapter mAdapter;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    ImageView profileImage;
    ImageView inspirationImage1;
    ImageView inspirationImage2;
    ImageView inspirationImage3;

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
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        break;
                    case R.id.action_workouts:
                        intent = WorkoutActivity.sisActive
                                ? new Intent(getBaseContext(), WorkoutActivity.class)
                                : new Intent(getBaseContext(), WorkoutListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        break;
                    case R.id.action_more:
                        intent = new Intent(getBaseContext(), MoreActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        if (workoutList == null) {
            workoutList = new ArrayList<>(workoutListSize);
        }

        // Setup RecyclerView
        NonScrollableLinearLayoutManager nonScrollableLinearLayoutManager
                = new NonScrollableLinearLayoutManager(getContext());
        nonScrollableLinearLayoutManager.setScrollEnabled(false);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rvRecentWorkouts);
        mRecyclerView.setLayoutManager(nonScrollableLinearLayoutManager);
        mAdapter = new WorkoutsAdapter(getBaseContext(), workoutList);
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
                workoutList.add(0, workout);
                count++;

                if (count > workoutListSize) {
                    workoutList.remove(workoutList.size() - 1);
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

        // Set fields from User data
        final TextView userNameTV = (TextView) findViewById(R.id.tvUserName);
        final TextView memberSinceTV = (TextView) findViewById(R.id.tvFriendSince);
        final TextView healthyDescTV = (TextView) findViewById(R.id.tvHealthyDescription);
        final TextView calsPerWeekTV = (TextView) findViewById(R.id.tvNumCalsPerWeek);
        final TextView workoutsPerWeekTV = (TextView) findViewById(R.id.tvNumWorkoutsPerWeek);

        profileImage = (ImageView) findViewById(R.id.ivProfileImage);
        inspirationImage1 = (ImageView) findViewById(R.id.ivInspirationOne);
        inspirationImage2 = (ImageView) findViewById(R.id.ivInspirationTwo);
        inspirationImage3 = (ImageView) findViewById(R.id.ivInspirationThree);

        DatabaseReference mUserDatabaseRef = database
                .getReference(AppUtilities.TABLE_NAME_USERS + "/" + userId);

        mUserDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);

                String userName = user.getUsername();
                if (!TextUtils.isEmpty(userName)) {
                    userNameTV.setText(userName);
                }

                String friendSince = user.getMemberSince();
                if (!TextUtils.isEmpty(friendSince)) {
                    memberSinceTV.setText(memberSinceTV.getText() + " " + friendSince);
                }

                String healthDesc = user.getManifesto();
                if (!TextUtils.isEmpty(healthDesc)) {
                    healthyDescTV.setText(healthDesc);
                }

                String numCalsPerWeek = user.getCalorieGoal();
                if (!TextUtils.isEmpty(numCalsPerWeek)) {
                    calsPerWeekTV.setText(numCalsPerWeek);
                }

                String numWorkoutsPerWeek = user.getWorkoutGoal();
                if (!TextUtils.isEmpty(numWorkoutsPerWeek)) {
                    workoutsPerWeekTV.setText(numWorkoutsPerWeek);
                }

                if (ContextCompat.checkSelfPermission(MeActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MeActivity.this, new String[] {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    displayImages();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button quickStatsBtn = (Button) findViewById(R.id.btnQuickStats);

        quickStatsBtn.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent),
                PorterDuff.Mode.MULTIPLY);
        quickStatsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), QuickStatsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_me);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    displayImages();
                } else {
                    Toast.makeText(MeActivity.this, R.string.grant_perm_to_load_images,
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void displayImages() {
        StorageReference storageRef = storage.getReference();

        if (storageRef != null) {
            String profileImageStr = "images/" + getUserId() + "_" + "profileImage";
            downloadImages(profileImageStr, profileImage, storageRef);

            String inspirationOneStr = "images/" + getUserId() + "_" + "inspiringImage1";
            downloadImages(inspirationOneStr, inspirationImage1, storageRef);

            String inspirationTwoStr = "images/" + getUserId() + "_" + "inspiringImage2";
            downloadImages(inspirationTwoStr, inspirationImage2, storageRef);

            String inspirationThreeStr = "images/" + getUserId() + "_" + "inspiringImage3";
            downloadImages(inspirationThreeStr, inspirationImage3, storageRef);
        }
    }

    private void downloadImages(final String url, final ImageView imageView,
                                final StorageReference storageRef) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                storageRef.child(url).getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'images/...'
                        if (uri != null) {
                            // Retrieve image from Firebase with Picasso
                            Picasso.with(getBaseContext())
                                    .load(uri.toString())
                                    .fit()
                                    .centerCrop()
                                    .into(imageView);
                            setupImage(imageView);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }
        }).start();
    }

    private void setupImage(ImageView image) {
        image.setPadding(0,0,0,0);
    }

    private String getUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        return user.getUid();
    }
}

