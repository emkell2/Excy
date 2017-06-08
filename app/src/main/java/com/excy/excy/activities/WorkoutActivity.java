package com.excy.excy.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
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
import com.excy.excy.dialogs.WorkoutCompleteDialog;
import com.excy.excy.timers.WorkoutTimer;
import com.excy.excy.utilities.AppUtilities;
import com.excy.excy.utilities.WorkoutUtilities;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

import static android.view.View.GONE;

public class WorkoutActivity extends AppCompatActivity implements WorkoutCompleteDialog.OnCompleteListener {
    private static int[] powerZoneArr = {0};

    private static long originalStartTime = 0;

    private static int minutes = 00;
    private static int seconds = 00;
    private static int currZoneCtr = 0;

    private static Activity activity;
    private static Resources mResources;

    private static String workoutName = "";
    private static HashMap<String, Object> workout;

    MediaPlayer player;
    WorkoutTimer timerRef; // Needed to have a reference to the timer

    TextView timerTV;
    TextView progressBar;
    TextView startingTemp;
    ImageView audioIcon;

    boolean audioIconEnabled = true;
    boolean warmUpDialogShown;

    static ImageView targetZoneIV;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            workout = (HashMap<String, Object>) intent.getSerializableExtra(WorkoutUtilities.WORKOUT_DATA);
            boolean warmup = intent.getBooleanExtra(WorkoutUtilities.INTENT_WARMUP, false);

            if (workout == null) {
                workout = new HashMap<>();
            }

            if (warmup) {
                Intent launchWarmupIntent = new Intent(WorkoutActivity.this, WarmUpActivity.class);
                startActivityForResult(launchWarmupIntent, 10);
            } else {
                startTimer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        activity = this;
        mResources = getResources();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(WorkoutUtilities.INTENT_START_WORKOUT_TIMER));

        final Intent workoutListData = getIntent();

        timerTV = (TextView) findViewById(R.id.tvTimer);
        progressBar = (TextView) findViewById(R.id.tvProgressBar);
        targetZoneIV = (ImageView) findViewById(R.id.ivTargetZone);

        player = MediaPlayer.create(getBaseContext(), workoutListData.getIntExtra(
                WorkoutUtilities.WORKOUT_DATA_AUDIO_RES_ID, 0));
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        audioIcon = (ImageView) findViewById(R.id.ivAudioIcon);
        audioIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audioIconEnabled) {
                    audioIconEnabled = false;
                    audioIcon.setAlpha(0.30f);
                    player.setVolume(0, 0);
                } else {
                    audioIconEnabled = true;
                    audioIcon.setAlpha(1f);
                    player.setVolume(1, 1);
                }
            }
        });

        progressBar.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // TODO: Take this out of the observer, don't need to be here anymore
                        // gets called after layout has been done but before display
                        // so we can get the height then hide the view
                        progressBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        progressBar.setVisibility(View.GONE);

                        // Start Timer, needed to put this code in here to get progressBar width
                        long timeInMillis = workoutListData.getLongExtra(WorkoutUtilities.WORKOUT_DATA_TIME_MILLIS, 0);
                        originalStartTime = timeInMillis;
                        final WorkoutTimer timer = new WorkoutTimer(timeInMillis);
                        timerRef = timer;
                    }
                }
        );

        // Create Layout
        AppUtilities.setBottomNavBarIconActive(this, R.id.action_workouts);

        int workoutResId = workoutListData.getIntExtra(WorkoutUtilities.WORKOUT_DATA_RES_ID, 0);
        setWorkoutImages(workoutResId);

        startingTemp = (TextView) findViewById(R.id.tvStartingZoneTemp);

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
                player.pause();
                audioIcon.setVisibility(GONE);
                if (timerRef != null) {
                    timerRef.cancelTimer();
                }

                endWorkout(false);
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

        if (!warmUpDialogShown) {
            warmUpDialogShown = true;

            String tag = WarmUpDialog.WARM_UP_DIALOG;
            Fragment frag = WarmUpDialog.newInstance(false, WorkoutUtilities.INTENT_START_WORKOUT_TIMER, workout);

            getFragmentManager().beginTransaction().add(frag, tag).commitAllowingStateLoss();
//            WarmUpDialog.newInstance(false, WorkoutUtilities.INTENT_START_WORKOUT_TIMER, workout)
//                    .show(getFragmentManager(), WarmUpDialog.WARM_UP_DIALOG);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_workouts);
    }

    @Override
    protected void onStop() {
        super.onStop();

        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);

        currZoneCtr = 0;
        minutes = 0;
        seconds = 0;
        originalStartTime = 0;
        powerZoneArr = null;
        player.stop();
        player.reset();

        if (timerRef != null) {
            timerRef.cancelTimer();
            timerRef = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == AppUtilities.REQUEST_CODE_WARMUP) && (resultCode == RESULT_OK)) {
            startTimer();
        } else {
            finish();
            return;
        }
    }

    @Override
    public void onComplete(boolean startTimer) {
        if (startTimer) {
            timerRef = new WorkoutTimer(timerRef.getRemainingTime());
            startTimer();
            player.start();
            audioIcon.setVisibility(View.VISIBLE);
        } else {
            finish();
            Intent intent = new Intent(WorkoutActivity.this, WorkoutListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return;
        }
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

        if ((minutes == 0) && (seconds == 0)) {
            endWorkout(true);
        }
    }

    public static void updatePowerZone() {
        if ((minutes > 0) && (currZoneCtr < powerZoneArr.length)) {
            setTargetPowerZoneImage(powerZoneArr[++currZoneCtr]);
        }
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
                        return;
                    }
                }).show();
    }

    public static long getOriginalStartTime() {
        return originalStartTime;
    }

    private static void endWorkout(boolean workoutComplete) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String date = WorkoutUtilities.getWorkoutTimestamp();
        String totalTime = WorkoutUtilities.getElapsedTime(originalStartTime, minutes, seconds);
        int calsBurned = WorkoutUtilities.calculateCaloriesBurned(originalStartTime, minutes, seconds);

        if (workout == null) {
            workout = new HashMap<>();
        }

        workout.put("uid", userId);
        workout.put("dateCompleted", date);
        workout.put("workoutTitle", workoutName);
        workout.put("totalTime", totalTime);
        workout.put("caloriesBurned", calsBurned);

        activity.getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        String tag = MaxTemperatureDialog.MAX_TEMP_DIALOG;
        Fragment frag = MaxTemperatureDialog.newInstance(workout, workoutComplete);

        activity.getFragmentManager().beginTransaction().add(frag, tag).commitAllowingStateLoss();

//        MaxTemperatureDialog.newInstance(workout, workoutComplete).show(activity.getFragmentManager(),
//                MaxTemperatureDialog.MAX_TEMP_DIALOG);
    }

    public void startTimer() {
        if (timerRef == null) {
            timerRef = new WorkoutTimer(originalStartTime);
        }

        // Set min temperature textview
        if (workout != null) {
            if (workout.get("minTemp") != null) {
                int minTemp = (int) workout.get("minTemp");
                if (minTemp > 0) {
                    String minTempStr = String.valueOf(minTemp);
                    startingTemp.setText(minTempStr);
                }
            }
        }

        timerRef.startTimer(timerTV, progressBar);

        // Start media audio
        audioIcon.setVisibility(View.VISIBLE);
        player.start();

        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
