package com.excy.excy.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.excy.excy.R;
import com.excy.excy.timers.WorkoutTimer;
import com.excy.excy.utilities.AppUtilities;
import com.excy.excy.utilities.WorkoutUtilities;

import static android.view.View.GONE;

public class WorkoutActivity extends AppCompatActivity {
    private static int[] powerZoneArr = {0};

    private static int progressStartingWidth;

    private static int minutes = 00;
    private static int seconds = 00;
    private static int currZoneCtr = 0;

    private static Resources mResources;

    MediaPlayer player;
    WorkoutTimer timerRef; // Needed to have a reference to the timer

    TextView timerTV;
    TextView progressBar;
    ImageView audioIcon;

    static ImageView targetZoneIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        mResources = getResources();

        final Intent workoutListData = getIntent();

        timerTV = (TextView) findViewById(R.id.tvTimer);
        progressBar = (TextView) findViewById(R.id.tvProgressBar);
        targetZoneIV = (ImageView) findViewById(R.id.ivTargetZone);
        audioIcon = (ImageView) findViewById(R.id.ivAudioIcon);

        player = MediaPlayer.create(getBaseContext(), workoutListData.getIntExtra(
                WorkoutUtilities.WORKOUT_DATA_AUDIO_RES_ID, 0));
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);

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
                        long timeInMillis = workoutListData.getLongExtra(WorkoutUtilities.WORKOUT_DATA_TIME_MILLIS, 0);
                        final WorkoutTimer timer = new WorkoutTimer(timeInMillis);
                        timer.startTimer(timerTV, progressBar);
                        timerRef = timer;

                        // Start media audio
                        audioIcon.setVisibility(View.VISIBLE);
                        player.start();

                        progressBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
        );

        // Create Layout
        AppUtilities.setBottomNavBarIconActive(this, R.id.action_workouts);

        int workoutResId = workoutListData.getIntExtra(WorkoutUtilities.WORKOUT_DATA_RES_ID, 0);
        setWorkoutImages(workoutResId);

        // Button layout
        Button pauseBtn = (Button) findViewById(R.id.btnPause);
        pauseBtn.getBackground().setColorFilter(getResources().getColor(R.color.colorPauseBtn),
                PorterDuff.Mode.MULTIPLY);
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRef != null) {
                    timerRef.cancelTimer();
                }

                player.pause();
                audioIcon.setVisibility(GONE);
            }
        });

        Button stopBtn = (Button) findViewById(R.id.btnStop);
        stopBtn.getBackground().setColorFilter(getResources().getColor(R.color.colorStopBtn),
                PorterDuff.Mode.MULTIPLY);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                audioIcon.setVisibility(GONE);
                if (timerRef != null) {
                    timerRef.cancelTimer();
                }
                finish();
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

    private void setWorkoutImages(int workoutResId) {
        if (workoutResId > 0) {
            ImageView workoutImage = (ImageView) findViewById(R.id.ivWorkoutImage);
            ImageView powerZoneImage = (ImageView) findViewById(R.id.ivZone);

            switch (workoutResId) {
                case R.id.ibArmCandy:
                    workoutImage.setImageResource(R.drawable.wb_arm_candy);
                    powerZoneImage.setImageResource(R.drawable.pz_arm_candy_graph);
                    powerZoneArr = WorkoutUtilities.PZ_ARM_CANDY_ARR;
                    break;
                case R.id.ibSuperCycleCardio:
                    workoutImage.setImageResource(R.drawable.wb_super_cycle_cardio);
                    powerZoneImage.setImageResource(R.drawable.pz_super_cycle_graph);
                    powerZoneArr = WorkoutUtilities.PZ_SUPER_CYCLE_CARDIO_ARR;
                    break;
                case R.id.ibCycleLegBlast:
                    workoutImage.setImageResource(R.drawable.wb_cycle_leg_blast);
                    powerZoneImage.setImageResource(R.drawable.pz_cycle_leg_blast_graph);
                    powerZoneArr = WorkoutUtilities.PZ_CYCLE_LEG_BLAST_ARR;
                    break;
                case R.id.ibCoreFloorExplosion:
                    workoutImage.setImageResource(R.drawable.wb_core_floor_explosion);
                    powerZoneImage.setImageResource(R.drawable.pz_core_floor_explosion_graph);
                    powerZoneArr = WorkoutUtilities.PZ_CORE_FLOOR_EXPLOSION_ARR;
                    break;
                case R.id.ibArmBlast:
                    workoutImage.setImageResource(R.drawable.wb_arm_blast);
                    powerZoneImage.setImageResource(R.drawable.pz_arm_blast_graph);
                    powerZoneArr = WorkoutUtilities.PZ_ARM_BLAST_ARR;
                    break;
                case R.id.ibUltimateArmAndLegToning:
                    workoutImage.setImageResource(R.drawable.wb_ultimate_arm_leg_tone);
                    powerZoneImage.setImageResource(R.drawable.pz_ultimate_arm_and_leg_toning_graph);
                    powerZoneArr = WorkoutUtilities.PZ_ULTIMATE_ARM_LEG_TONING_ARR;
                    break;
            }

            // Set beginning target zone image
            setTargetPowerZoneImage(powerZoneArr[currZoneCtr]);
        }
    }

    private static void setTargetPowerZoneImage(int currZone) {
        Drawable zone;;
        switch (currZone) {
            case 1:
                zone = mResources.getDrawable(R.drawable.zone_intensity_1);
                break;
            case 2:
                zone = mResources.getDrawable(R.drawable.zone_intensity_2);
                break;
            case 3:
                zone = mResources.getDrawable(R.drawable.zone_intensity_3);
                break;
            case 4:
                zone = mResources.getDrawable(R.drawable.zone_intensity_4);
                break;
            case 5:
                zone = mResources.getDrawable(R.drawable.zone_intensity_5);
                break;
            default:
                zone = mResources.getDrawable(R.drawable.zone_intensity_1);
                break;
        }
        targetZoneIV.setBackground(zone);
    }

    public static void updateTime(int newMinutes, int newSeconds) {
        minutes = newMinutes;
        seconds = newSeconds;

        if (minutes != 0 && seconds != 0) {
            updatePowerZone();
        }
    }

    public static int getProgressBarStartingWidth() {
        return progressStartingWidth * 3;
    }

    private static void updatePowerZone() {
        setTargetPowerZoneImage(++currZoneCtr);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        currZoneCtr = 0;
        minutes = 0;
        seconds = 0;
        player.stop();

        if (timerRef != null) {
            timerRef.cancelTimer();
        }

        finish();
    }
}
