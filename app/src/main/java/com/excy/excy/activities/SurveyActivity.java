package com.excy.excy.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.excy.excy.R;
import com.excy.excy.utilities.AppUtilities;
import com.excy.excy.utilities.WorkoutUtilities;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SurveyActivity extends AppCompatActivity {
    private int submitCount = 0;
    private int option = 3;         // Radio Button selected
    private String enjoyment;
    private String location;
    private int[] optionsSelected;  // Store all selected user feedback options
    private HashMap<String, Object> workout;

    public static final String SURVEY_TAG = "SURVEY TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        workout = (HashMap<String, Object>) getIntent().getSerializableExtra(WorkoutUtilities.WORKOUT_DATA);

        optionsSelected = new int[3];

        final TextView useSilderTV = (TextView) findViewById(R.id.tvUseSlider);
        final ImageView surveyImage = (ImageView) findViewById(R.id.ivSurveyImage);

        RadioButton radio1Btn = (RadioButton) findViewById(R.id.radio1);
        radio1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option = 1;
                switch (submitCount) {
                    case 0:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey1_awful));
                        enjoyment = "awful";
                        break;
                    case 1:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey2_home_tv));
                        location = "at home";
                        break;
                    case 2:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey3_unaccomplished));
                        break;
                }
            }
        });

        RadioButton radio2Btn = (RadioButton) findViewById(R.id.radio2);
        radio2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option = 2;
                switch (submitCount) {
                    case 0:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey1_bad));
                        enjoyment = "bad";
                        break;
                    case 1:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey2_home_no_tv));
                        location = "at home";
                        break;
                    case 2:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey3_satisfied));
                        break;
                }
            }
        });

        RadioButton radio3Btn = (RadioButton) findViewById(R.id.radio3);
        radio3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option = 3;
                switch (submitCount) {
                    case 0:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey1_good));
                        enjoyment = "good";
                        break;
                    case 1:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey2_work));
                        location = "at work";
                        break;
                    case 2:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey3_healthier));
                        break;
                }
            }
        });

        RadioButton radio4Btn = (RadioButton) findViewById(R.id.radio4);
        radio4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option = 4;
                switch (submitCount) {
                    case 0:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey1_great));
                        enjoyment = "great";
                        break;
                    case 1:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey2_traveling));
                        location = "traveling";
                        break;
                    case 2:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey3_proud));
                        break;
                }
            }
        });

        RadioButton radio5Btn = (RadioButton) findViewById(R.id.radio5);
        radio5Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option = 5;
                switch (submitCount) {
                    case 0:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey1_amazing));
                        enjoyment = "amazing";
                        break;
                    case 1:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey2_on_the_go));
                        location = "on the go";
                        break;
                    case 2:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey3_energy));
                        break;
                }
            }
        });

        final Button submitBtn = (Button) findViewById(R.id.btnSubmit);
        submitBtn.getBackground().setColorFilter(
                getResources().getColor(R.color.colorExcyGreen), PorterDuff.Mode.MULTIPLY);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (submitCount <= 2) {
                    optionsSelected[submitCount] = option;
                    submitCount++;

                    switch (submitCount) {
                        case 1:
                            useSilderTV.setText(getString(R.string.feedback_location));
                            surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey2_work));
                            break;
                        case 2:
                            useSilderTV.setText(getString(R.string.feedback_how_you_feel));
                            surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey3_healthier));
                            submitBtn.setText(getString(R.string.feedback_btn_complete));
                            break;
                        case 3:
                            submitSurvey();
                            break;
                    }

                    option = 3;
                }
            }
        });
    }

    private void submitSurvey() {
        workout.put("location", location);
        workout.put("enjoyment", enjoyment);

        String userId = workout.get("uid").toString();
        DatabaseReference mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        mDataBaseReference.child(AppUtilities.TABLE_NAME_WORKOUTS).child(userId).push()
                .setValue(workout, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Log.d(SURVEY_TAG, "Database Error message: " + databaseError.getMessage());
                            Log.d(SURVEY_TAG, "Database Error details : " + databaseError.getDetails());
                        }
                        Intent intent = new Intent(getBaseContext(), WorkoutListActivity.class);
                        startActivity(intent);
                    }
                });
    }
}
