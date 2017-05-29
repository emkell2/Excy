package com.excy.excy.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import com.excy.excy.R;
import com.excy.excy.timers.WarmupTimer;

public class WarmUpActivity extends AppCompatActivity {
    private static Activity activity;

    TextView timerTV;
    TextView progressBar;

    WarmupTimer timerRef; // Needed to have a reference to the timer

    private static long origintalStartTimeMillis = 120000;
    private static int minutes = 00;
    private static int seconds = 00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warm_up);

        activity = this;

        timerTV = (TextView) findViewById(R.id.tvTimer);
        progressBar = (TextView) findViewById(R.id.tvProgressBar);
        progressBar.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // TODO: Remove this code out of the observer, no longer needed.
                        // gets called after layout has been done but before display
                        // so we can get the height then hide the view

                        // Start Timer, needed to put this code in here to get progressBar width
                        if (timerRef == null) {
                            final WarmupTimer timer = new WarmupTimer(origintalStartTimeMillis);
                            timerRef = timer;
                        }
                        timerRef.startTimer(timerTV, progressBar);

                        progressBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    }
                }
        );

        Button pauseBtn = (Button) findViewById(R.id.btnPause);

        pauseBtn.getBackground().setColorFilter(getResources().getColor(R.color.colorPauseBtn),
                PorterDuff.Mode.MULTIPLY);
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRef != null) {
                    timerRef.cancelTimer();
                }

                displayResumeDialog(timerTV);
            }
        });

        Button skipBtn = (Button) findViewById(R.id.btnSkip);
        skipBtn.getBackground().setColorFilter(getResources().getColor(R.color.colorStopBtn),
                PorterDuff.Mode.MULTIPLY);
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setResult(RESULT_OK);
                finish();
                return;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        minutes = 0;
        seconds = 0;

        if (timerRef != null) {
            timerRef.cancelTimer();
            timerRef = null;
        }
    }

    private void displayResumeDialog(final TextView timerTV) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.resume_continue)
                .setMessage(R.string.finish_strong)
                .setPositiveButton(R.string.resume, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        timerRef = new WarmupTimer(timerRef.getRemainingTime());
                        if (timerRef !=null) {
                            timerRef.startTimer(timerTV, progressBar);
                        }
                    }
                })
                .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (timerRef != null) {
                            timerRef.cancelTimer();
                            timerRef = null;
                        }
                        activity.setResult(RESULT_OK);
                        finish();
                        return;
                    }
                }).show();
    }

    public static void updateTime(int newMinutes, int newSeconds) {
        minutes = newMinutes;
        seconds = newSeconds;

        if ((minutes == 0) && (seconds == 0)) {
            activity.setResult(RESULT_OK);
            activity.finish();
            return;
        }
    }

    public static long getOriginalStartTime() {
        return origintalStartTimeMillis;
    }
}
