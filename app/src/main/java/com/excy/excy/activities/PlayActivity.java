package com.excy.excy.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.excy.excy.PlayTimer;
import com.excy.excy.R;
import com.excy.excy.utilities.AppUtilities;
import com.excy.excy.utilities.PlayUtilities;

import info.hoang8f.widget.FButton;

public class PlayActivity extends AppCompatActivity {
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

    private PlayTimer timer;
    private static long startingTime = 0;
    private static int minutes = 00;
    private static int seconds = 00;
    private static int fastInterval;    // in seconds
    private static int slowInterval;    // in seconds

    private static int progressStartingWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        context = this;

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

        ImageButton minPlus = (ImageButton) findViewById(R.id.ibMinPlus);
        minPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (minutes < 60) {
                    minutes++;
                    timerTV.setText(createTimerString());
                    updateTimer();
                }
            }
        });

        final ImageButton minMinus = (ImageButton) findViewById(R.id.ibMinMinus);
        minMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (minutes > 0) {
                    minutes--;
                    timerTV.setText(createTimerString());
                    updateTimer();
                }
            }
        });

        ImageButton secPlus = (ImageButton) findViewById(R.id.ibSecPlus);
        secPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seconds < 60) {
                    seconds++;
                    timerTV.setText(createTimerString());
                    updateTimer();
                }
            }
        });

        ImageButton secMinus = (ImageButton) findViewById(R.id.ibSecMinus);
        secMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seconds > 0) {
                    seconds--;
                    timerTV.setText(createTimerString());
                    updateTimer();
                }
            }
        });

        /* Set up intervals view */
        final TextView graphTV = (TextView) findViewById(R.id.tvGraph);
        graphTV.setTypeface(dosisBold);

        slowIntervalTV = (TextView) findViewById(R.id.tvSlowInterval);
        slowIntervalTV.setTypeface(dosisMedium);

        TextView slowTV = (TextView) findViewById(R.id.tvSlow);
        slowTV.setTypeface(dosisBold);

        fastIntervalTV = (TextView) findViewById(R.id.tvFastInterval);
        fastIntervalTV.setTypeface(dosisMedium);

        TextView fastTV = (TextView) findViewById(R.id.tvFast);
        fastTV.setTypeface(dosisBold);

        ImageButton slowMinus = (ImageButton) findViewById(R.id.ibSlowMinus);
        slowMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String slowText = slowIntervalTV.getText().toString();

                if (TextUtils.isEmpty(slowText)) {
                    return;
                }

                int slowInt = Integer.valueOf(slowText).intValue();

                if (slowInt > 0) {
                    slowInt--;
                    slowInterval = slowInt;

                    if (slowInt == 0) {
                        slowIntervalTV.setText("000");
                    } else {
                        slowIntervalTV.setText(String.valueOf(slowInt));
                    }

                    PlayTimer.resetIntervalCounters();
                }
            }
        });

        ImageButton slowPlus = (ImageButton) findViewById(R.id.ibSlowPlus);
        slowPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slowInt = Integer.valueOf(slowIntervalTV.getText().toString()).intValue();

                slowInt++;
                slowInterval = slowInt;
                slowIntervalTV.setText(String.valueOf(slowInt));
                PlayTimer.resetIntervalCounters();
            }
        });

        ImageButton fastMinus = (ImageButton) findViewById(R.id.ibFastMinus);
        fastMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fastText = fastIntervalTV.getText().toString();

                if (TextUtils.isEmpty(fastText)) {
                    return;
                }

                int fastInt = Integer.valueOf(fastText).intValue();

                if (fastInt > 0) {
                    fastInt--;
                    fastInterval = fastInt;

                    if (fastInt == 0) {
                        fastIntervalTV.setText("000");
                    } else {
                        fastIntervalTV.setText(String.valueOf(fastInt));
                    }

                    PlayTimer.resetIntervalCounters();
                }
            }
        });

        ImageButton fastPlus = (ImageButton) findViewById(R.id.ibFastPlus);
        fastPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fastInt = Integer.valueOf(fastIntervalTV.getText().toString()).intValue();

                fastInt++;
                fastInterval = fastInt;
                fastIntervalTV.setText(String.valueOf(fastInt));
                PlayTimer.resetIntervalCounters();
            }
        });

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

        progressBar.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // gets called after layout has been done but before display
                        // so we can get the height then hide the view

                        progressStartingWidth = PlayUtilities.dpFromPx(context, progressBar.getWidth());
                        System.out.println("startWidth=" + progressStartingWidth);

                        progressBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        progressBar.setVisibility(View.GONE);
                    }
                }
        );

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                mainPlayLayout.setWeightSum(3);

                long startTime = convertStringTimeToMillis(timerTV.getText().toString());

                startingTime = startTime;
                getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                timer = new PlayTimer(startTime);
                timer.startTimer(timerTV, progressBar, true);

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
                reset();
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
                    case R.id.action_workouts:
                        intent = new Intent(getContext(), WorkoutListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_me:
                        intent = new Intent(getContext(), MeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_more:
                        intent = new Intent(getContext(), MoreActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (timer != null) {
            timer.cancelTimer(true);
            timer = null;
        }

        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    }

    private void updateTimer() {
        if (timer != null && !timer.finished) {
            timer.cancelTimer(false);
            timer = null;

            long startTime = convertStringTimeToMillis(timerTV.getText().toString());
            timer = new PlayTimer(startTime);
            startingTime = startTime;

            timer.startTimer(timerTV, progressBar, false);
        }
    }

    private void displayResumeDialog(final TextView timerTV) {
        new AlertDialog.Builder(this)
                .setTitle("Continue?")
                .setMessage("healthy is... finishing strong!")
                .setPositiveButton("resume", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        timer = new PlayTimer(timer.getRemainingTime());
                        timer.startTimer(timerTV, progressBar, false);
                    }
                })
                .setNegativeButton("exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reset();
                    }
                }).show();
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
        Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (currentInteveral) {
            setRunningManImageAndText(R.drawable.burst_play_red, R.string.push_yourself);
        } else {
            setRunningManImageAndText(R.drawable.burst_play_blue, R.string.slow_it_down);
        }

        vibe.vibrate(1000);
    }

    public static void setRunningManImageAndText(int drawableId, int stringId) {
        runningManIV.setImageResource(drawableId);

        if (stringId != 0) {
            intervalTextTV.setText(stringId);

            if (stringId == R.string.slow_it_down) {
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
        minutes = 00;
        seconds = 00;
        timerTV.setText(PlayUtilities.createTimerString(minutes, seconds));

        /* Reset Intervals */
        fastInterval = 0;
        slowInterval = 0;
        slowIntervalTV.setText("000");
        fastIntervalTV.setText("000");

        /* Reset ProgressBar */
        progressBar.setWidth(progressStartingWidth);

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

        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static Context getContext() {
        return context;
    }

    public static int getProgressBarStartingWidth() {
        return progressStartingWidth * 3;
    }
}
