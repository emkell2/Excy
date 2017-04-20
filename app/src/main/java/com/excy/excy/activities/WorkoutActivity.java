package com.excy.excy.activities;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.excy.excy.R;
import com.excy.excy.dialogs.MaxTemperatureDialog;
import com.excy.excy.dialogs.WarmUpDialog;
import com.excy.excy.timers.WorkoutTimer;
import com.excy.excy.utilities.AppUtilities;
import com.excy.excy.utilities.PlayUtilities;
import com.excy.excy.utilities.WorkoutUtilities;

import java.util.HashMap;

import static android.view.View.GONE;

public class WorkoutActivity extends AppCompatActivity {
    private static int[] powerZoneArr = {0};

    private static long originalStartTime = 0;
    private static int progressStartingWidth;

    private static int minutes = 00;
    private static int seconds = 00;
    private static int currZoneCtr = 0;

    private static Resources mResources;

    private String workoutName = "";
    HashMap<String, Object> workout;

    MediaPlayer player;
    WorkoutTimer timerRef; // Needed to have a reference to the timer

    TextView timerTV;
    TextView progressBar;
    ImageView audioIcon;

    static ImageView targetZoneIV;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            workout = (HashMap<String, Object>) intent.getSerializableExtra(WorkoutUtilities.WORKOUT_DATA);
            startTimer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        mResources = getResources();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(WorkoutUtilities.INTENT_START_WORKOUT_TIMER));

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
                        originalStartTime = timeInMillis;
                        final WorkoutTimer timer = new WorkoutTimer(timeInMillis);
                        timerRef = timer;

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
                player.pause();
                audioIcon.setVisibility(GONE);

                if (timerRef != null) {
                    timerRef.cancelTimer();
                }

                displayResumeDialog(timerTV);
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

                String timeRemaining = PlayUtilities.createTimerString(minutes, seconds);
                endWorkout(timeRemaining);
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

        /* Set up excy link view  */
        TextView excyLinkTV = (TextView) findViewById(R.id.tvLink);
        excyLinkTV.setMovementMethod(LinkMovementMethod.getInstance());

        HashMap<String, Object> workout = new HashMap<>();
        WarmUpDialog.newInstance(false, WorkoutUtilities.INTENT_START_WORKOUT_TIMER, workout)
                .show(getFragmentManager(), WarmUpDialog.WARM_UP_DIALOG);
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
                    workoutName = WorkoutUtilities.WORKOUT_ARM_CANDY;
                    break;
                case R.id.ibSuperCycleCardio:
                    workoutImage.setImageResource(R.drawable.wb_super_cycle_cardio);
                    powerZoneImage.setImageResource(R.drawable.pz_super_cycle_graph);
                    powerZoneArr = WorkoutUtilities.PZ_SUPER_CYCLE_CARDIO_ARR;
                    workoutName = WorkoutUtilities.WORKOUT_SUPER_CYCLE_CARDIO;
                    break;
                case R.id.ibCycleLegBlast:
                    workoutImage.setImageResource(R.drawable.wb_cycle_leg_blast);
                    powerZoneImage.setImageResource(R.drawable.pz_cycle_leg_blast_graph);
                    powerZoneArr = WorkoutUtilities.PZ_CYCLE_LEG_BLAST_ARR;
                    workoutName = WorkoutUtilities.WORKOUT_CYCLE_LEG_BLAST;
                    break;
                case R.id.ibCoreFloorExplosion:
                    workoutImage.setImageResource(R.drawable.wb_core_floor_explosion);
                    powerZoneImage.setImageResource(R.drawable.pz_core_floor_explosion_graph);
                    powerZoneArr = WorkoutUtilities.PZ_CORE_FLOOR_EXPLOSION_ARR;
                    workoutName = WorkoutUtilities.WORKOUT_CORE_FLOOR_EXPLOSION;
                    break;
                case R.id.ibArmBlast:
                    workoutImage.setImageResource(R.drawable.wb_arm_blast);
                    powerZoneImage.setImageResource(R.drawable.pz_arm_blast_graph);
                    powerZoneArr = WorkoutUtilities.PZ_ARM_BLAST_ARR;
                    workoutName = WorkoutUtilities.WORKOUT_ARM_BLAST;
                    break;
                case R.id.ibUltimateArmAndLegToning:
                    workoutImage.setImageResource(R.drawable.wb_ultimate_arm_leg_tone);
                    powerZoneImage.setImageResource(R.drawable.pz_ultimate_arm_and_leg_toning_graph);
                    powerZoneArr = WorkoutUtilities.PZ_ULTIMATE_ARM_LEG_TONING_ARR;
                    workoutName = WorkoutUtilities.WORKOUT_ULTIMATE_ARM_LEG_TONING;
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
    }

    public static int getProgressBarStartingWidth() {
        return progressStartingWidth * 3;
    }

    public static void updatePowerZone() {
        if ((minutes > 0) && (seconds % 60 == 0) && (currZoneCtr < powerZoneArr.length)) {
            setTargetPowerZoneImage(powerZoneArr[++currZoneCtr]);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        currZoneCtr = 0;
        minutes = 0;
        seconds = 0;
        progressStartingWidth = 0;
        player.stop();

        if (timerRef != null) {
            timerRef.cancelTimer();
        }

        finish();
    }

    private void displayResumeDialog(final TextView timerTV) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.resume_continue)
                .setMessage(R.string.finish_strong)
                .setPositiveButton(R.string.resume, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        timerRef = new WorkoutTimer(timerRef.getRemainingTime());
                        startTimer();
                    }
                })
                .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        player.stop();
                        audioIcon.setVisibility(GONE);
                        if (timerRef != null) {
                            timerRef.cancelTimer();
                        }
                        finish();
                    }
                }).show();
    }

    public static long getOriginalStartTime() {
        return originalStartTime;
    }

    private void endWorkout(String timeRemaining) {
        workout.put("workoutTitle", workoutName);
        MaxTemperatureDialog.newInstance(timeRemaining, workoutName).show(getFragmentManager(),
                MaxTemperatureDialog.MAX_TEMP_DIALOG);
    }

    public void startTimer() {
        timerRef.startTimer(timerTV, progressBar);

        // Start media audio
        audioIcon.setVisibility(View.VISIBLE);
        player.start();
    }

    private String calculateElapsedTime(long startTimeMillis, int min, int sec) {
        int startTimeSecs = (int) (startTimeMillis / 1000);

        int secondsRemaining = (min * 60) + sec;
        int elaspedSeconds = startTimeSecs - secondsRemaining;
        int totalMin = elaspedSeconds / 60;
        int totalSec = elaspedSeconds % 60;

        return PlayUtilities.createTimerString(totalMin, totalSec);
    }
}
