package com.excy.excy.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.excy.excy.R;
import com.excy.excy.utilities.AppUtilities;
import com.excy.excy.utilities.WorkoutUtilities;
import com.google.firebase.auth.FirebaseAuth;

public class EditProfileActivity extends AppCompatActivity {

    EditText emailET;
    EditText healthDescET;
    EditText numCalsET;
    EditText numWorkoutsET;
    ImageButton imageLeft;
    ImageButton imageCenter;
    ImageButton imageRight;

    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;

    private int selectedImageButton = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_me);

        // Set Email TextView
        emailET = (EditText) findViewById(R.id.etEmail);
        String userEmail = "";
        String savedEmail = WorkoutUtilities.getPersistedString(this, WorkoutUtilities.KEY_USER_EMAIL);
        if (!TextUtils.isEmpty(savedEmail)) {
            userEmail = savedEmail;
        } else {
            userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            WorkoutUtilities.persistString(this, WorkoutUtilities.KEY_USER_EMAIL, userEmail);
        }

        if (!TextUtils.isEmpty(userEmail)) {
            emailET.setText(userEmail);
        }

        // Set Healthy Description TextView
        healthDescET = (EditText) findViewById(R.id.etHealthyDescription);
        healthDescET.requestFocus();
        String savedDesc = WorkoutUtilities.getPersistedString(this, WorkoutUtilities.KEY_HEALTHY_DESC);
        if (!TextUtils.isEmpty(savedDesc)) {
            healthDescET.setText(savedDesc);
        }

        // Set Goals TextViews
        numCalsET = (EditText) findViewById(R.id.etNumCals);
        String calsPerWeek = WorkoutUtilities.getPersistedString(this, WorkoutUtilities.KEY_CALS_PER_WEEK);
        if (!TextUtils.isEmpty(calsPerWeek)) {
            numCalsET.setText(calsPerWeek);
        }

        numWorkoutsET = (EditText) findViewById(R.id.etNumWorkouts);
        String workoutsPerWeek = WorkoutUtilities.getPersistedString(this, WorkoutUtilities.KEY_WORKOUTS_PER_WEEK);
        if (!TextUtils.isEmpty(workoutsPerWeek)) {
            numWorkoutsET.setText(workoutsPerWeek);
        }

        // Set Images for "My Inspiration" ImageButtons
        imageLeft = (ImageButton) findViewById(R.id.ibImageLeft);
        imageLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImageButton = 1;
                showFileChooser();
            }
        });

        imageCenter = (ImageButton) findViewById(R.id.ibImageCenter);
        imageCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImageButton = 2;
                showFileChooser();

            }
        });

        imageRight = (ImageButton) findViewById(R.id.ibImageRight);
        imageRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImageButton = 3;
                showFileChooser();
            }
        });

        Button logOutBtn = (Button) findViewById(R.id.btnLogOut);

        logOutBtn.getBackground().setColorFilter(
                getResources().getColor(R.color.colorLogOutBtn), PorterDuff.Mode.MULTIPLY);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign user out
                FirebaseAuth.getInstance().signOut();

                // Delete any persisted data
                deleteSharedPreferences();

                // Kill Task stack and go back to Login Activity
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        final Button saveChangesBtn = (Button) findViewById(R.id.btnSaveChanges);

        saveChangesBtn.getBackground().setColorFilter(
                getResources().getColor(R.color.colorSaveChangesBtn), PorterDuff.Mode.MULTIPLY);
        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottomNavigationView);

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

        AppUtilities.removeShiftMode(bottomNavigationView);
    }

    private void saveChanges() {
        String healthyDesc = healthDescET.getText().toString();
        if (!TextUtils.isEmpty(healthyDesc)) {
            WorkoutUtilities.persistString(this, WorkoutUtilities.KEY_HEALTHY_DESC, healthyDesc);
        }

        String numCals = numCalsET.getText().toString();
        if (!TextUtils.isEmpty(numCals)) {
            WorkoutUtilities.persistString(this, WorkoutUtilities.KEY_CALS_PER_WEEK, numCals);
        }

        String numWorkouts = numWorkoutsET.getText().toString();
        if (!TextUtils.isEmpty(numWorkouts)) {
            WorkoutUtilities.persistString(this, WorkoutUtilities.KEY_WORKOUTS_PER_WEEK, numWorkouts);
        }

        finish();
    }

    private void deleteSharedPreferences() {
        SharedPreferences sharePrefs = getPreferences(Context.MODE_PRIVATE);
        sharePrefs.edit()
                .remove(WorkoutUtilities.KEY_USER_EMAIL)
                .remove(WorkoutUtilities.KEY_MEMBER_SINCE)
                .remove(WorkoutUtilities.KEY_CALS_PER_WEEK)
                .remove(WorkoutUtilities.KEY_WORKOUTS_PER_WEEK)
                .remove(WorkoutUtilities.KEY_HEALTHY_DESC)
                .remove(WorkoutUtilities.KEY_MY_INSPIRATION)
                .apply();
    }

    private void showFileChooser() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if ((requestCode == REQUEST_CAMERA) || (requestCode == SELECT_FILE)) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();

                ImageButton imageButton;
                switch (selectedImageButton) {
                    case 1:
                        imageButton = imageLeft;
                        break;
                    case 2:
                        imageButton = imageCenter;
                        break;
                    case 3:
                        imageButton = imageRight;
                        break;
                    default:
                        imageButton = imageLeft;
                        break;
                }
                
                imageButton.setImageURI(selectedImage);
            }
        }
    }
}
