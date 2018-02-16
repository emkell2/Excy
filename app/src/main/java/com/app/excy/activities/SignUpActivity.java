package com.app.excy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.excy.R;
import com.app.excy.models.User;
import com.app.excy.utilities.AppUtilities;
import com.app.excy.utilities.WorkoutUtilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    public static final String AUTH_TAG = "AUTH TAG";
    public static final String MALE = "male";
    public static final String FEMALE = "female";

    private User user =  null;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    String userId = user.getUid();

                    // User is signed in
                    Log.d(AUTH_TAG, "onAuthStateChanged:signed_in:" + userId);

                } else {
                    // User is signed out
                    Log.d(AUTH_TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        final RadioButton maleRB = (RadioButton) findViewById(R.id.rbMale);
        final EditText weightET = (EditText) findViewById(R.id.etWeight);
        final EditText heightET = (EditText) findViewById(R.id.etHeight);
        final EditText ageET = (EditText) findViewById(R.id.etAge);
        final EditText usernameET = (EditText) findViewById(R.id.etUserName);
        final EditText emailET = (EditText) findViewById(R.id.etEmail);
        final EditText passwordET = (EditText) findViewById(R.id.etPassword);

        Button signUpBtn = (Button) findViewById(R.id.btnSignUp);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gender = "";
                int weight = 0;
                int height = 0;
                int age = 0;
                String username = "";
                String email = "";
                String password = "";

                if (maleRB.isChecked()) {
                    gender = MALE;
                } else {
                    gender = FEMALE;
                }


                String str = "";

                str = weightET.getText().toString();
                if (!TextUtils.isEmpty(str)) {
                    weight = Integer.valueOf(str);
                }

                str = heightET.getText().toString();
                if (!TextUtils.isEmpty(str)) {
                    height = Integer.valueOf(str);
                }

                str = ageET.getText().toString();
                if (!TextUtils.isEmpty(str)) {
                    age = Integer.valueOf(str);
                }

                str = usernameET.getText().toString();
                if (!TextUtils.isEmpty(str)) {
                    username = str;
                }

                str = emailET.getText().toString();
                if (!TextUtils.isEmpty(str)) {
                    email = str;
                }

                str = passwordET.getText().toString();
                if (!TextUtils.isEmpty(str)) {
                    password = str;
                }

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    user = new User(gender, weight, height, age, username, email);

                    final HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("gender", gender);
                    map.put("weight", weight);
                    map.put("height", height);
                    map.put("age", age);
                    map.put("memberSince", WorkoutUtilities.getMemberSinceTimestamp());
                    map.put("username", username);
                    map.put("email", email);

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(AUTH_TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, R.string.auth_failed,
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        String userId = user.getUid();

                                        DatabaseReference mDataBaseReference = FirebaseDatabase.getInstance().getReference();
                                        mDataBaseReference.child(AppUtilities.TABLE_NAME_USERS).child(userId).setValue(map,
                                                new DatabaseReference.CompletionListener() {
                                                    @Override
                                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                        if (databaseError != null) {
                                                            Log.d(AUTH_TAG, "Database Error message: " + databaseError.getMessage());
                                                            Log.d(AUTH_TAG, "Database Error details : " + databaseError.getDetails());
                                                        }
                                                        Intent intent = new Intent(getBaseContext(), WorkoutListActivity.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                                        startActivity(intent);
                                                    }
                                                });
                                    }
                                }
                            })
                            .addOnFailureListener(SignUpActivity.this, new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(AUTH_TAG, "Sign up failed: " + e.getMessage());
                                    Toast.makeText(SignUpActivity.this, e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                } else {
                    Toast.makeText(SignUpActivity.this, "Please enter in a valid email address and password.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button loginBtn = (Button) findViewById(R.id.btnLogin);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        /* Set up excy link view  */
        TextView excyLinkTV = (TextView) findViewById(R.id.tvLink);
        excyLinkTV.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
