package com.excy.excy.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.excy.excy.R;
import com.excy.excy.timers.WorkoutTimer;
import com.excy.excy.utilities.AppUtilities;
import com.excy.excy.utilities.WorkoutUtilities;

public class WorkoutActivity extends AppCompatActivity {
    int workoutResId;

    private static int progressStartingWidth;

    private static int minutes = 00;
    private static int seconds = 00;

    TextView timerTV;
    TextView progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        timerTV = (TextView) findViewById(R.id.tvTimer);
        progressBar = (TextView) findViewById(R.id.tvProgressBar);

        progressBar.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // gets called after layout has been done but before display
                        // so we can get the height then hide the view

                        progressStartingWidth = AppUtilities.dpFromPx(getBaseContext(),
                                progressBar.getWidth());
                        System.out.println("startWidth=" + progressStartingWidth);

                        // Start Timer, needed to put this code in here to get progressBar width
                        long timeInMillis = getIntent().getLongExtra(WorkoutUtilities.WORKOUT_DATA_TIME_MILLIS, 0);
                        WorkoutTimer timer = new WorkoutTimer(timeInMillis);
                        timer.startTimer(timerTV, progressBar);

                        progressBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
        );

        // Create Layout
        AppUtilities.setBottomNavBarIconActive(this, R.id.action_workouts);

        workoutResId = getIntent().getIntExtra(WorkoutUtilities.WORKOUT_DATA_RES_ID, 0);

        setWorkoutImage();
        setWorkoutPowerZoneImage();

        // Button layout
        Button pauseBtn = (Button) findViewById(R.id.btnPause);
        pauseBtn.getBackground().setColorFilter(getResources().getColor(R.color.colorPauseBtn),
                PorterDuff.Mode.MULTIPLY);

        Button stopBtn = (Button) findViewById(R.id.btnStop);
        stopBtn.getBackground().setColorFilter(getResources().getColor(R.color.colorStopBtn),
                PorterDuff.Mode.MULTIPLY);

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
                    case R.id.action_me:
                        intent = new Intent(getBaseContext(), MeActivity.class);
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
    }

    private void setWorkoutImage() {
        if (workoutResId > 0) {
            ImageView workoutImage = (ImageView) findViewById(R.id.ivWorkoutImage);

            switch (workoutResId) {
                case R.id.ibArmCandy:
                    workoutImage.setImageResource(R.drawable.wb_arm_candy);
                    break;
                case R.id.ibSuperCycleCardio:
                    workoutImage.setImageResource(R.drawable.wb_super_cycle_cardio);
                    break;
                case R.id.ibCycleLegBlast:
                    workoutImage.setImageResource(R.drawable.wb_cycle_leg_blast);
                    break;
                case R.id.ibCoreFloorExplosion:
                    workoutImage.setImageResource(R.drawable.wb_core_floor_explosion);
                    break;
                case R.id.ibArmBlast:
                    workoutImage.setImageResource(R.drawable.wb_arm_blast);
                    break;
                case R.id.ibUltimateArmAndLegToning:
                    workoutImage.setImageResource(R.drawable.wb_ultimate_arm_leg_tone);
                    break;
            }
        }
    }

    private void setWorkoutPowerZoneImage() {
        if (workoutResId > 0) {
            ImageView powerZoneImage = (ImageView) findViewById(R.id.ivZone);

            switch (workoutResId) {
                case R.id.ibArmCandy:
                    powerZoneImage.setImageResource(R.drawable.pz_arm_candy_graph);
                    break;
                case R.id.ibSuperCycleCardio:
                    powerZoneImage.setImageResource(R.drawable.pz_super_cycle_graph);
                    break;
                case R.id.ibCycleLegBlast:
                    powerZoneImage.setImageResource(R.drawable.pz_cycle_leg_blast_graph);
                    break;
                case R.id.ibCoreFloorExplosion:
                    powerZoneImage.setImageResource(R.drawable.pz_core_floor_explosion_graph);
                    break;
                case R.id.ibArmBlast:
                    powerZoneImage.setImageResource(R.drawable.pz_arm_blast_graph);
                    break;
                case R.id.ibUltimateArmAndLegToning:
                    powerZoneImage.setImageResource(R.drawable.pz_ultimate_arm_and_leg_toning_graph);
                    break;
            }
        }
    }

    private int[] getPowerZoneArray(int workoutResId) {
        int[] powerZoneArr = {0};
        if (workoutResId > 0) {
            switch (workoutResId) {
                case R.id.ibArmCandy:
                    powerZoneArr = WorkoutUtilities.PZ_ARM_CANDY_ARR;
                    break;
                case R.id.ibSuperCycleCardio:
                    powerZoneArr = WorkoutUtilities.PZ_SUPER_CYCLE_CARDIO_ARR;
                    break;
                case R.id.ibCycleLegBlast:
                    powerZoneArr = WorkoutUtilities.PZ_CYCLE_LEG_BLAST_ARR;
                    break;
                case R.id.ibCoreFloorExplosion:
                    powerZoneArr = WorkoutUtilities.PZ_CORE_FLOOR_EXPLOSION_ARR;
                    break;
                case R.id.ibArmBlast:
                    powerZoneArr = WorkoutUtilities.PZ_ARM_BLAST_ARR;
                    break;
                case R.id.ibUltimateArmAndLegToning:
                    powerZoneArr = WorkoutUtilities.PZ_ULTIMATE_ARM_LEG_TONING_ARR;
                    break;
            }
        }

        return powerZoneArr;
    }

    public static void updateTime(int newMinutes, int newSeconds) {
        minutes = newMinutes;
        seconds = newSeconds;
    }

    public static int getProgressBarStartingWidth() {
        return progressStartingWidth * 3;
    }
}
