package com.excy.excy.activities;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.excy.excy.R;

public class SurveyActivity extends AppCompatActivity {
    private int submitCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        final TextView useSilderTV = (TextView) findViewById(R.id.tvUseSlider);
        final ImageView surveyImage = (ImageView) findViewById(R.id.ivSurveyImage);

        RadioButton radio1Btn = (RadioButton) findViewById(R.id.radio1);
        radio1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (submitCount) {
                    case 0:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey1_awful));
                        break;
                    case 1:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey2_home_tv));
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
                switch (submitCount) {
                    case 0:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey1_bad));
                        break;
                    case 1:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey2_home_no_tv));
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
                switch (submitCount) {
                    case 0:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey1_good));
                        break;
                    case 1:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey2_work));
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
                switch (submitCount) {
                    case 0:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey1_great));
                        break;
                    case 1:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey2_traveling));
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
                switch (submitCount) {
                    case 0:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey1_amazing));
                        break;
                    case 1:
                        surveyImage.setImageDrawable(getResources().getDrawable(R.drawable.survey2_on_the_go));
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
            }
        });
    }
    
    private void submitSurvey() {
        // TODO: Implement this method
    }
}
