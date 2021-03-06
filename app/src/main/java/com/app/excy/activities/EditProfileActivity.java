package com.app.excy.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.excy.R;
import com.app.excy.utilities.AppUtilities;
import com.app.excy.utilities.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    EditText emailET;
    EditText healthDescET;
    EditText numCalsET;
    EditText numWorkoutsET;
    TextView changeImageTV;
    TextView imageOneTV;
    TextView imageTwoTV;
    TextView imageThreeTV;
    ImageButton userProfile;
    ImageButton imageLeft;
    ImageButton imageCenter;
    ImageButton imageRight;
    ProgressBar progressBar;

    Button saveChangesBtn;

    Bitmap profileBitmap;
    Bitmap inspirationOneBitmap;
    Bitmap inspirationTwoBitmap;
    Bitmap inspirationThreeBitmap;

    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;

    private int selectedImageButton = 0;

    private HashMap<String, Object> map;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_me);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Set user profile image
        userProfile = (ImageButton) findViewById(R.id.ibChangeProfileImage);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImageButton = 4;
                showFileChooser();
            }
        });

        // Set Email TextView
        emailET = (EditText) findViewById(R.id.etEmail);
        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        if (!TextUtils.isEmpty(userEmail)) {
            emailET.setText(userEmail);
        }

        healthDescET = (EditText) findViewById(R.id.etHealthyDescription);
        healthDescET.requestFocus();

        numCalsET = (EditText) findViewById(R.id.etNumCals);
        numWorkoutsET = (EditText) findViewById(R.id.etNumWorkouts);

        changeImageTV = (TextView) findViewById(R.id.tvChangeProfileImage);
        imageOneTV = (TextView) findViewById(R.id.tvImageOne);
        imageTwoTV = (TextView) findViewById(R.id.tvImageTwo);
        imageThreeTV = (TextView) findViewById(R.id.tvImageThree);

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

                // Kill Task stack and go back to Login Activity
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        saveChangesBtn = (Button) findViewById(R.id.btnSaveChanges);

        saveChangesBtn.getBackground().setColorFilter(
                getResources().getColor(R.color.colorSaveChangesBtn), PorterDuff.Mode.MULTIPLY);
        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileBitmap != null) {
                    uploadImage("profileImage", profileBitmap);
                }

                if (inspirationOneBitmap != null) {
                    uploadImage("inspiringImage1", inspirationOneBitmap);
                }

                if (inspirationTwoBitmap != null) {
                    uploadImage("inspiringImage2", inspirationTwoBitmap);
                }

                if (inspirationThreeBitmap != null) {
                    uploadImage("inspiringImage3", inspirationThreeBitmap);
                }
            }
        });

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
                        intent = new Intent(getBaseContext(), WorkoutListActivity.class);
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

        AppUtilities.removeShiftMode(bottomNavigationView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_me);
    }

    private void saveChanges() {
        if (map == null) {
            map = new HashMap<>();
        }

        String healthyDesc = healthDescET.getText().toString();
        if (!TextUtils.isEmpty(healthyDesc)) {
            map.put("manifesto", healthyDesc);
        }

        String numCals = numCalsET.getText().toString();
        if (!TextUtils.isEmpty(numCals)) {
            map.put("calorieGoal", numCals);
        }

        String numWorkouts = numWorkoutsET.getText().toString();
        if (!TextUtils.isEmpty(numWorkouts)) {
            map.put("workoutGoal", numWorkouts);
        }

        String userId = getUserId();

        DatabaseReference mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        mDataBaseReference.child(AppUtilities.TABLE_NAME_USERS).child(userId).updateChildren(map,
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Log.d("EDIT PROFILE DATA", "Database Error message: " + databaseError.getMessage());
                            Log.d("EDIT PROFILE DATA", "Database Error details : " + databaseError.getDetails());
                        }
                        Intent intent = new Intent(getBaseContext(), MeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }
                });

        finish();
    }

    private void showFileChooser() {
        if ((ContextCompat.checkSelfPermission(EditProfileActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(EditProfileActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(EditProfileActivity.this, new String[] {
                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    2);
        } else {
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
            builder.create();
            builder.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if ((requestCode == REQUEST_CAMERA) || (requestCode == SELECT_FILE)) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (map == null) {
                    map = new HashMap<>();
                }

//                Bitmap imageBitmap = getImage(selectedImage);
                Bitmap imageBitmap = null;

                // For older devices, camera pic is stored here
                if (data != null && data.getExtras() != null) {
                    imageBitmap = (Bitmap) data.getExtras().get("data");
                }

                // For newer devices
                if (imageBitmap == null) {
                    imageBitmap = ImagePicker.getImageFromResult(getBaseContext(), resultCode, data);
                }

                if (imageBitmap != null) {
                    ImageButton imageButton;

                    switch (selectedImageButton) {
                        case 1:
                            imageButton = imageLeft;
                            imageOneTV.setText("");
                            inspirationOneBitmap = imageBitmap;
                            break;
                        case 2:
                            imageButton = imageCenter;
                            imageTwoTV.setText("");
                            inspirationTwoBitmap = imageBitmap;
                            break;
                        case 3:
                            imageButton = imageRight;
                            imageThreeTV.setText("");
                            inspirationThreeBitmap = imageBitmap;
                            break;
                        case 4:
                            imageButton = userProfile;
                            changeImageTV.setText("");
                            profileBitmap = imageBitmap;
                            break;
                        default:
                            imageButton = userProfile;
                            changeImageTV.setText("");
                            profileBitmap = imageBitmap;
                            break;
                    }

                    imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageButton.setAdjustViewBounds(false);
                    imageButton.setPadding(0, 0, 0, 0);
                    imageButton.setImageResource(0);
                    imageButton.setImageBitmap(imageBitmap);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    showFileChooser();
                } else {
                    Toast.makeText(EditProfileActivity.this, R.string.grant_perm_to_choose_images,
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private String getUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        return user.getUid();
    }

    private Bitmap getImage(Uri imageUri) {
        String[] filePath = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(imageUri, filePath, null, null, null);
        cursor.moveToFirst();
        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

        cursor.close();

        return bitmap;
    }

    private void uploadImage(String fileNameSuffix, Bitmap bitmap) {
        String fileName = "images/" + getUserId() + "_" + fileNameSuffix;
        StorageReference storageRef = storage.getReference().child(fileName);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                hideProgressBar();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                hideProgressBar();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                if (taskSnapshot.getBytesTransferred() < taskSnapshot.getTotalByteCount()) {
                    Toast.makeText(getBaseContext(), "Uploading images...", Toast.LENGTH_SHORT).show();
                }

                showProgressBar();

            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (storage.getReference().child("images").getActiveUploadTasks().size() == 0) {
                    saveChanges();
                }
            }
        });
    }

    private void showProgressBar() {
        if (!progressBar.isShown()) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
