package com.app.excy.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.excy.R;
import com.app.excy.activities.interfaces.PlayViewContract;
import com.app.excy.dialogs.MaxTemperatureDialog;
import com.app.excy.dialogs.WarmUpDialog;
import com.app.excy.dialogs.WorkoutCompleteDialog;
import com.app.excy.timers.PlayTimer;
import com.app.excy.utilities.AppUtilities;
import com.app.excy.utilities.PlayUtilities;
import com.app.excy.utilities.WorkoutUtilities;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import info.hoang8f.widget.FButton;

public class PlayActivity extends AppCompatActivity implements PlayViewContract,
        WorkoutCompleteDialog.OnCompleteListener, View.OnKeyListener,
        View.OnTouchListener, View.OnClickListener {
    private static final int BACKWARDS_FORWARDS_TEXT_SIZE = 12;
    private static final int MSG_INC = 0;
    private static final int MSG_DEC = 1;

    private static Activity activity;
    private static Context context;

    private static ImageView excyLogoIV;
    private static TextView burstTV;
    private static ImageView runningManIV;
    private static TextView intervalTextTV;
    private static TextView progressBar;
    private static TextView timerTV;
    private static TextView slowIntervalTV;
    private static TextView fastIntervalTV;
    private static Button startBtn;
    private static Button pauseBtn;
    private static Button stopBtn;

    private TextView slowTV;
    private TextView fastTV;
    private ImageButton slowMinus;
    private ImageButton slowPlus;
    private ImageButton fastMinus;
    private ImageButton fastPlus;
    private ImageButton minPlus;
    private ImageButton minMinus;
    private ImageButton secPlus;
    private ImageButton secMinus;

    private PlayTimer timer;
    private static long startingTime = 0;
    private static int minutes = 00;
    private static int seconds = 00;
    private static int fastInterval;    // in seconds
    private static int slowInterval;    // in seconds

    private int mSlowCounter;
    private int mFastCounter;

    private Handler mHandler;
    private ScheduledExecutorService mUpdater;

    private static long originalStartTime = 0;
    private boolean setInterval;
    private boolean warmUpDialogShown;
    private boolean inWorkoutDialogShowing;

    private ArrayList<ImageButton> mViews;

    private static boolean sForwardsBackwards;
    private static boolean updatingTime;

    private static HashMap<String, Object> workout;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setInterval = intent.getBooleanExtra(WorkoutUtilities.INTENT_SET_INTERVAL, false);
            boolean warmup = intent.getBooleanExtra(WorkoutUtilities.INTENT_WARMUP, false);
            workout = (HashMap<String, Object>) intent.getSerializableExtra(WorkoutUtilities.WORKOUT_DATA);

            if (workout == null) {
                workout = new HashMap<>();
            }

            if (warmup) {
                Intent launchWarmupIntent = new Intent(PlayActivity.this, WarmUpActivity.class);
                startActivityForResult(launchWarmupIntent, AppUtilities.REQUEST_CODE_WARMUP);
            } else {
                startTimer(setInterval);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottomNavigationView);

        AppUtilities.removeShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.action_workouts:
                        intent = com.app.excy.activities.WorkoutActivity.sisActive
                                ? new Intent(getBaseContext(), WorkoutActivity.class)
                                : new Intent(getBaseContext(), WorkoutListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        break;
                    case R.id.action_me:
                        intent = new Intent(getContext(), MeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        break;
                    case R.id.action_more:
                        intent = new Intent(getContext(), MoreActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        if (!checkForCurrentWorkout(false)) {
            activity = this;
            context = this;

            mViews = new ArrayList<>();

            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.arg2) {
                        case MSG_INC:
                            inc(getAssociatedTextViewReady(mViews.get(msg.arg1)));
                            return;
                        case MSG_DEC:
                            dec(getAssociatedTextViewReady(mViews.get(msg.arg1)));
                            return;
                    }
                    super.handleMessage(msg);
                }
            };

            LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                    new IntentFilter(WorkoutUtilities.INTENT_START_PLAY_TIMER));

            Typeface dosisRegular = Typeface.createFromAsset(getAssets(), "fonts/Dosis-Regular.ttf");
            Typeface dosisMedium = Typeface.createFromAsset(getAssets(), "fonts/Dosis-Medium.ttf");
            Typeface dosisBold = Typeface.createFromAsset(getAssets(), "fonts/Dosis-Bold.ttf");

            /* Set font for burst play text */
            burstTV = (TextView) findViewById(R.id.tvBurstPlay);
            burstTV.setTypeface(dosisRegular);

            /* Set font for interval text */
            intervalTextTV = (TextView) findViewById(R.id.tvIntervalText);
            intervalTextTV.setTypeface(dosisBold);

            /* Set up clock view */
            final TextView clockTV = (TextView) findViewById(R.id.tvClock);
            clockTV.setTypeface(dosisBold);

            TextView minTV = (TextView) findViewById(R.id.tvMin);
            minTV.setTypeface(dosisBold);

            timerTV = (TextView) findViewById(R.id.tvTimer);
            timerTV.setTypeface(dosisMedium);

            TextView secTV = (TextView) findViewById(R.id.tvSec);
            secTV.setTypeface(dosisBold);

            minPlus = (ImageButton) findViewById(R.id.ibMinPlus);
            setListeners(minPlus);

            minMinus = (ImageButton) findViewById(R.id.ibMinMinus);
            setListeners(minMinus);

            secPlus = (ImageButton) findViewById(R.id.ibSecPlus);
            setListeners(secPlus);

            secMinus = (ImageButton) findViewById(R.id.ibSecMinus);
            setListeners(secMinus);

            /* Set up intervals view */
            final TextView graphTV = (TextView) findViewById(R.id.tvGraph);
            graphTV.setTypeface(dosisBold);

            slowIntervalTV = (TextView) findViewById(R.id.tvSlowInterval);
            slowIntervalTV.setTypeface(dosisMedium);

            slowTV = (TextView) findViewById(R.id.tvSlow);
            slowTV.setTypeface(dosisBold);

            fastIntervalTV = (TextView) findViewById(R.id.tvFastInterval);
            fastIntervalTV.setTypeface(dosisMedium);

            fastTV = (TextView) findViewById(R.id.tvFast);
            fastTV.setTypeface(dosisBold);

            slowMinus = (ImageButton) findViewById(R.id.ibSlowMinus);
            setListeners(slowMinus);

            slowPlus = (ImageButton) findViewById(R.id.ibSlowPlus);
            setListeners(slowPlus);

            fastMinus = (ImageButton) findViewById(R.id.ibFastMinus);
            setListeners(fastMinus);

            fastPlus = (ImageButton) findViewById(R.id.ibFastPlus);
            setListeners(fastPlus);

            if (getIntent() != null) {
                sForwardsBackwards = getIntent().getBooleanExtra(WorkoutUtilities.INTENT_FORWARDS_BACKWARDS, false);
            }

            if (sForwardsBackwards) {
                setForwardsBackwardsLayout();
            }

            /* Set up excy link view  */
            TextView excyLinkTV = (TextView) findViewById(R.id.tvLink);
            excyLinkTV.setTypeface(dosisRegular);
            excyLinkTV.setMovementMethod(LinkMovementMethod.getInstance());

            /* Set up Start/Pause/Stop buttons */
            startBtn = (FButton) findViewById(R.id.btnStart);
            pauseBtn = (FButton) findViewById(R.id.btnPause);
            stopBtn = (FButton) findViewById(R.id.btnStop);
            excyLogoIV = (ImageView) findViewById(R.id.ivExcyLogo);
            runningManIV = (ImageView) findViewById(R.id.ivRunningMan);
            progressBar = (TextView) findViewById(R.id.tvProgressBar);
            final LinearLayout mainPlayLayout = (LinearLayout) findViewById(R.id.mainPlayLayout);

            progressBar.setVisibility(View.GONE);

            startBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeLayout();

                    long startTime = convertStringTimeToMillis(timerTV.getText().toString());

                    originalStartTime = startTime;
                    startingTime = startTime;
                    getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    timer = new PlayTimer(startTime);
                    WorkoutUtilities.setCurrentWorkoutActivity(activity);

                    HashMap<String, Object> workout = new HashMap<>();

                    if (!warmUpDialogShown) {
                        warmUpDialogShown = true;

                        String tag = WarmUpDialog.WARM_UP_DIALOG;
                        Fragment frag = WarmUpDialog.newInstance(true,
                                WorkoutUtilities.INTENT_START_PLAY_TIMER, workout);

                        getFragmentManager().beginTransaction().add(frag, tag).commitAllowingStateLoss();
                    }
                }
            });

            pauseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (timer != null) {
                        timer.cancelTimer(false);
                    }

                    displayResumeDialog(timerTV);
                }
            });

            stopBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (timer != null) {
                        timer.cancelTimer(false);
                    }

                    endWorkout(false);
                }
            });

            mViews.add(minPlus);
            mViews.add(minMinus);
            mViews.add(secPlus);
            mViews.add(secMinus);
            mViews.add(slowPlus);
            mViews.add(slowMinus);
            mViews.add(fastPlus);
            mViews.add(fastMinus);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_play);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        reset();
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        boolean bForwardsBackwards = false;
        if (intent != null) {
            bForwardsBackwards = intent.getBooleanExtra(WorkoutUtilities.INTENT_FORWARDS_BACKWARDS, false);
        }

        if (!checkForCurrentWorkout(bForwardsBackwards) && bForwardsBackwards) {
            if (!inWorkoutDialogShowing) {
                sForwardsBackwards = bForwardsBackwards;

                setForwardsBackwardsLayout();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == AppUtilities.REQUEST_CODE_WARMUP) && (resultCode == RESULT_OK)) {
            startTimer(setInterval);
        } else {
            showStartButton();
        }
    }

    @Override
    public void onComplete(boolean startTimer) {
        if (startTimer) {
            timer.startTimer(timerTV, progressBar, false);
        } else {
            finish();
            Intent intent = new Intent(PlayActivity.this, WorkoutListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return;
        }
    }

    private void showStartButton() {
        pauseBtn.setClickable(false);
        stopBtn.setClickable(false);
        pauseBtn.setVisibility(View.GONE);
        stopBtn.setVisibility(View.GONE);

        startBtn.setClickable(true);
        startBtn.setVisibility(View.VISIBLE);
    }

    private long convertStringTimeToMillis(String startTimeString) {
        long time = 0;

        String[] splitString = startTimeString.split(":");
        int minutes = Integer.valueOf(splitString[0]);
        int seconds = Integer.valueOf(splitString[1]);

        time = (minutes * 60 * 1000) + (seconds * 1000);

        return time;
    }

    private String createTimerString() {
        return PlayUtilities.createTimerString(minutes, seconds);
    }

    public static void updateTime(int newMinutes, int newSeconds) {
        minutes = newMinutes;
        seconds = newSeconds;

        if ((minutes == 0) && (seconds == 0)) {
            endWorkout(true);
        }
    }

    private void updateTimer() {
        if (timer != null && !timer.finished) {
            timer.cancelTimer(false);
            timer = null;

            long startTime = convertStringTimeToMillis(timerTV.getText().toString());
            timer = new PlayTimer(startTime);
            startingTime = startTime;

            startTimer(false);
        }
    }

//    public static void updateSlowInterval(int value) {
//        slowIntervalTV.setText(String.valueOf(value));
//    }
//
//    public static void updateFastInterval(int value) {
//        fastIntervalTV.setText(String.valueOf(value));
//    }

    private void displayResumeDialog(final TextView timerTV) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.resume_continue)
                .setMessage(R.string.finish_strong)
                .setPositiveButton(R.string.resume, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        timer = new PlayTimer(timer.getRemainingTime());
                        startTimer(false);
                    }
                })
                .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reset();
                    }
                }).show();
    }

    private void displayInAWorkoutDialog(final boolean forwardsBackwards) {
        new AlertDialog.Builder(this)
                .setTitle("You Are In A Workout!")
                .setMessage("You are in the middle of a workout! Please finish the workout " +
                        "before starting a new one.")
                .setPositiveButton("FINISH WORKOUT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // current workout is NOT a play activity, so finish this screen.
                        if (!forwardsBackwards) {
                            Intent intent = new Intent(getBaseContext(), WorkoutActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(intent);
                            finish();
                        }
                    }
                })
                .setCancelable(false)
                .show();

        inWorkoutDialogShowing = true;
    }

    public static long getStartingTime() {
        return startingTime;
    }

    public static int getFastInterval() {
        return fastInterval;
    }

    public static int getSlowInterval() {
        return slowInterval;
    }

    public static void changeIntervalImage(boolean currentInteveral) {
        if (updatingTime) {
            return;
        }

        Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (currentInteveral) {
            if (!sForwardsBackwards) {
                setRunningManImageAndText(R.drawable.burst_play_red, R.string.push_yourself);
            } else {
                setRunningManImageAndText(R.drawable.burst_play_red, R.string.forwards);
            }
        } else {
            if (!sForwardsBackwards) {
                setRunningManImageAndText(R.drawable.burst_play_blue, R.string.slow_it_down);
            } else {
                setRunningManImageAndText(R.drawable.burst_play_blue, R.string.backwards);
            }
        }

        vibe.vibrate(1000);
    }

    public static void setRunningManImageAndText(int drawableId, int stringId) {
        runningManIV.setImageResource(drawableId);

        if (stringId != 0) {

            if (!intervalTextTV.isShown()) {
                intervalTextTV.setVisibility(View.VISIBLE);
            }

            intervalTextTV.setText(stringId);

            if (stringId == R.string.slow_it_down || stringId == R.string.backwards) {
                intervalTextTV.setTextColor(context.getResources().getColor(R.color.colorBlue));
            } else {
                intervalTextTV.setTextColor(context.getResources().getColor(R.color.colorAccent));
            }
        } else {
            intervalTextTV.setVisibility(View.GONE);
        }
    }

    private void reset() {
        if (timer != null) {
            timer.cancelTimer(true);
        }

        /* Reset timer */
        startingTime = 0;
        originalStartTime = 0;
        minutes = 00;
        seconds = 00;
        timerTV.setText(PlayUtilities.createTimerString(minutes, seconds));
        updatingTime = false;

        /* Reset Intervals */
        fastInterval = 0;
        slowInterval = 0;
        slowIntervalTV.setText("000");
        fastIntervalTV.setText("000");

        /* Hide running man image and progress bar*/
        runningManIV.setVisibility(View.GONE);
        intervalTextTV.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        /* Show excy logo and burst play textview */
        excyLogoIV.setVisibility(View.VISIBLE);
        burstTV.setVisibility(View.VISIBLE);

        /* Switch out buttons */
        pauseBtn.setClickable(false);
        stopBtn.setClickable(false);
        pauseBtn.setVisibility(View.GONE);
        stopBtn.setVisibility(View.GONE);

        startBtn.setClickable(true);
        startBtn.setVisibility(View.VISIBLE);

        sForwardsBackwards = false;
        workout = null;

        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static Context getContext() {
        return context;
    }

    private static void endWorkout(boolean workoutComplete) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String date = WorkoutUtilities.getWorkoutTimestamp();
        String totalTime = WorkoutUtilities.getElapsedTime(originalStartTime, minutes, seconds);
        int calsBurned = WorkoutUtilities.calculateCaloriesBurned(originalStartTime, minutes, seconds);

        WorkoutUtilities.setCurrentWorkoutActivity(null);

        if (workout == null) {
            workout = new HashMap<>();
        }

        workout.put("uid", userId);
        workout.put("dateCompleted", date);
        if (!sForwardsBackwards) {
            workout.put("workoutTitle", WorkoutUtilities.WORKOUT_INTERVAL);
        } else {
            workout.put("workoutTitle", WorkoutUtilities.WORKOUT_BACKWARDS_FORWARDS);
        }
        workout.put("totalTime", totalTime);
        workout.put("caloriesBurned", calsBurned);

        String tag = MaxTemperatureDialog.MAX_TEMP_DIALOG;
        Fragment frag = MaxTemperatureDialog.newInstance(workout, workoutComplete);

        activity.getFragmentManager().beginTransaction().add(frag, tag).commitAllowingStateLoss();
    }

    public void startTimer(boolean setCurrentInterval) {
        if (timer != null) {
            timer.startTimer(timerTV, progressBar, setCurrentInterval);
        }
    }

    private void changeLayout() {
        Typeface dosisBold = Typeface.createFromAsset(getAssets(), "fonts/Dosis-Bold.ttf");
        final TextView clockTV = (TextView) findViewById(R.id.tvClock);
        clockTV.setTypeface(dosisBold);

        final TextView graphTV = (TextView) findViewById(R.id.tvGraph);
        graphTV.setTypeface(dosisBold);

        /* Hide excy logo and burst play textview */
        excyLogoIV.setVisibility(View.GONE);
        burstTV.setVisibility(View.GONE);

        /* Setup running man image and progress bar*/
        runningManIV.setVisibility(View.VISIBLE);
        intervalTextTV.setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.VISIBLE);

        /* Change text */
        clockTV.setText(R.string.time_remaining);
        graphTV.setText(R.string.intervals);

        /* Switch out buttons */
        startBtn.setClickable(false);
        startBtn.setVisibility(View.GONE);

        pauseBtn.setClickable(true);
        stopBtn.setClickable(true);
        pauseBtn.setVisibility(View.VISIBLE);
        stopBtn.setVisibility(View.VISIBLE);

        final LinearLayout mainPlayLayout = (LinearLayout) findViewById(R.id.mainPlayLayout);
        mainPlayLayout.setWeightSum(3);
    }

    private boolean checkForCurrentWorkout(boolean forwardsBackwards) {
        Activity currActivity = WorkoutUtilities.getCurrentWorkoutActivity();
        if (currActivity != null) {
            String activityStr = currActivity.getClass().getSimpleName();
            if ((currActivity != null && !activityStr.equals(this.getClass().getSimpleName())) || forwardsBackwards) {
                displayInAWorkoutDialog(forwardsBackwards);
                return true;
            }
        }

        return false;
    }

    public static boolean isForwardBackwardWorkout() {
        return sForwardsBackwards;
    }
    private void setForwardsBackwardsLayout() {
        slowTV.setText(getString(R.string.backwards));
        slowTV.setTextSize(BACKWARDS_FORWARDS_TEXT_SIZE);

        fastTV.setText(getString(R.string.forwards));
        fastTV.setTextSize(BACKWARDS_FORWARDS_TEXT_SIZE);

        int buttonSize = AppUtilities.convertDpToPx(getContext(), 42);

        slowMinus.getLayoutParams().width = buttonSize;
        slowMinus.getLayoutParams().height = buttonSize;

        slowPlus.getLayoutParams().width = buttonSize;
        slowPlus.getLayoutParams().height = buttonSize;

        fastMinus.getLayoutParams().width = buttonSize;
        fastMinus.getLayoutParams().height = buttonSize;

        fastPlus.getLayoutParams().width = buttonSize;
        fastPlus.getLayoutParams().height = buttonSize;
    }

    private void inc(TextView view) {
        if (view == fastIntervalTV) {
            view.setText(String.valueOf(mFastCounter));
            PlayTimer.resetIntervalCounter(true);
        } else if (view == slowIntervalTV) {
            view.setText(String.valueOf(mSlowCounter));
            PlayTimer.resetIntervalCounter(false);
        } else if (view == timerTV) {
            view.setText(createTimerString());
            updateTimer();
        }
    }

    private void dec(TextView view) {
        if (view == fastIntervalTV) {
            if (mFastCounter == 0) {
                view.setText("000");
            } else {
                view.setText(String.valueOf(mFastCounter));
            }

            PlayTimer.resetIntervalCounter(true);
        } else if (view == slowIntervalTV) {
            if (mSlowCounter == 0) {
                view.setText("000");
            } else {
                view.setText(String.valueOf(mSlowCounter));
            }
            PlayTimer.resetIntervalCounter(false);
        } else if (view == timerTV) {
            view.setText(createTimerString());
            updateTimer();
        }
    }

    private void startUpdating(View view, boolean inc) {
        if (mUpdater != null) {
            Log.e(getClass().getSimpleName(), "Another executor is still active");
            return;
        }
        updatingTime = true;
        mUpdater = Executors.newSingleThreadScheduledExecutor();
        mUpdater.scheduleAtFixedRate(new UpdateCounterTask(view, inc), 200, 200,
                TimeUnit.MILLISECONDS);
    }

    private void stopUpdating() {
        updatingTime = false;
        mUpdater.shutdownNow();
        mUpdater = null;
    }

    private TextView getAssociatedTextViewReady(View view) {
        TextView v = null;

        if (view == minPlus) {
            if (minutes < 60) {
                minutes++;
            }
            v = timerTV;
        } else if (view == minMinus) {
            if (minutes > 0) {
                minutes--;
            }
            v = timerTV;
        } else if (view == secPlus) {
            if (seconds < 60) {
                seconds++;
            }
            v = timerTV;
        } else if (view == secMinus) {
            if (seconds > 0) {
                seconds--;
            }
            v = timerTV;
        } else if (view == slowPlus) {
            mSlowCounter = Integer.valueOf(slowIntervalTV.getText().toString()).intValue();

            mSlowCounter++;
            slowInterval = mSlowCounter;
            v = slowIntervalTV;
        } else if (view == slowMinus) {
            String slowText = slowIntervalTV.getText().toString();

            if (!TextUtils.isEmpty(slowText)) {
                mSlowCounter = Integer.valueOf(slowText).intValue();

                if (mSlowCounter > 0) {
                    mSlowCounter--;
                    slowInterval = mSlowCounter;
                }
            }
            v = slowIntervalTV;
        } else if (view == fastPlus) {
            mFastCounter = Integer.valueOf(fastIntervalTV.getText().toString()).intValue();

            mFastCounter++;
            fastInterval = mFastCounter;
            v = fastIntervalTV;
        } else if (view == fastMinus) {
            String fastText = fastIntervalTV.getText().toString();

            if (!TextUtils.isEmpty(fastText)) {
                mFastCounter = Integer.valueOf(fastText).intValue();

                if (mFastCounter > 0) {
                    mFastCounter--;
                    fastInterval = mFastCounter;
                }
            }
            v = fastIntervalTV;
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        if ((v instanceof ImageButton) && (mUpdater == null)) {
            if (isIncrementButton(v)) {
                inc(getAssociatedTextViewReady(v));
            } else if (isDecrementButton(v)) {
                dec(getAssociatedTextViewReady(v));
            }
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        boolean isKeyOfInterest = keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER;
        boolean isReleased = event.getAction() == KeyEvent.ACTION_UP;
        boolean isPressed = event.getAction() == KeyEvent.ACTION_DOWN
                && event.getAction() != KeyEvent.ACTION_MULTIPLE;

        if (isKeyOfInterest && isReleased) {
            stopUpdating();
        } else if (isKeyOfInterest && isPressed) {
            startUpdating(v, isIncrementButton(v));
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean isReleased = event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL;
        boolean isPressed = event.getAction() == MotionEvent.ACTION_DOWN;

        if (isReleased) {
            stopUpdating();
        } else if (isPressed) {
            startUpdating(v, isIncrementButton(v));
        }
        return false;
    }

    private boolean isIncrementButton(View v) {
        return (v == fastPlus) || (v == slowPlus) || (v == minPlus) || (v == secPlus);
    }

    private boolean isDecrementButton(View v) {
        return (v == fastMinus) || (v == slowMinus) || (v == minMinus) || (v == secMinus);
    }

    private void setListeners(View view) {
        view.setOnClickListener(this);
        view.setOnTouchListener(this);
        view.setOnKeyListener(this);
    }

    private class UpdateCounterTask implements Runnable {
        private ImageButton mView;
        private boolean mInc;

        public UpdateCounterTask(View view, boolean inc) {
            if (view instanceof  ImageView) {
                mView = (ImageButton) view;
            }
            mInc = inc;
        }

        public void run() {
            if (mView != null) {
                Message message = new Message();
                int index = 0;
                for (int i = 0; i < mViews.size(); i++) {
                    if (mViews.get(i) == mView) {
                        index = i;
                        break;
                    }
                }
                message.arg1 = index;
                if (mInc) {
                    message.arg2 = MSG_INC;
                    mHandler.sendMessage(message);
                } else {
                    message.arg2 = MSG_DEC;
                    mHandler.sendMessage(message);
                }
            }
        }
    }
}
