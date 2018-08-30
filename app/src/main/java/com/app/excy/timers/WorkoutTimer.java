package com.app.excy.timers;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.excy.activities.WorkoutActivity;
import com.app.excy.utilities.PlayUtilities;

/**
 * Created by erin.kelley on 3/22/17.
 */

public class WorkoutTimer {
    private CountDownTimer timer;
    private static long timeRemaining;

    public boolean finished;

    public WorkoutTimer(long startTime) {
        timeRemaining = startTime;
    }

    public void startTimer(final TextView tvTimer, final TextView progressBar) {

        if (!progressBar.isShown()) {
            progressBar.setVisibility(View.VISIBLE);
        }

        timer = new CountDownTimer(timeRemaining, 100) {
            int seconds = 0;
            long ms = 0;
            long zoneMs = 0;
            long offsetMs = 0;
            boolean hasUpdatedImage = false;
            boolean start = true;

            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                ms += 100;
                zoneMs += 100;
                offsetMs += 100;

                if (!start) {
                    start = false;
                }

                // Need to do this conditional or else CountDownTimer will spit out 1 second twice
                // from not being completely accurate. Also knocked down the tick rate to 100ms
                // from 1000ms.
                if (Math.round((float) millisUntilFinished / 1000.0f) != seconds) {
                    seconds = Math.round((float) millisUntilFinished / 1000.0f);
                    int minutes = seconds / 60;
                    seconds %= 60;

                    String newTime = PlayUtilities.createTimerString(minutes, seconds);
                    if (!TextUtils.isEmpty(newTime)) {
                        tvTimer.setText(newTime);
                    }

                    // Update progress approximately every half a second
                    if (ms >= 500) {
                        WorkoutActivity.updateTime(minutes, seconds);
                        updateProgressBar(progressBar, minutes, seconds);
                        ms = 0;
                    }

                    //                    System.out.println("ms=" + millisUntilFinished + " mins=" + minutes
//                            + " secs=" + seconds + " offsetMS " + offsetMs);
                    // Try to update the power zone image about once a second. This logic
                    // is really stupid but needed to not skip a zone.
                    if (seconds == 0 && offsetMs > 1000) {
                        WorkoutActivity.updatePowerZone();
                        offsetMs = 0;
                    }
                }
            }

            @Override
            public void onFinish() {
                tvTimer.setText("00:00");
                cancelTimer();
                finished = true;
            }
        }.start();
    }

    public void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            finished = true;
        }
    }

    private void updateProgressBar(TextView progressBar, int minutes, int seconds) {
        int totalSeconds = (minutes * 60) + seconds;
        int startingTime = (int) (WorkoutActivity.getOriginalStartTime() / 1000);

        float progress;
        if (startingTime != 0) {
            progress = 100 - (((float) totalSeconds / (float) startingTime) * 100);
        } else {
            progress = 0;
        }

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) progressBar.getLayoutParams();
        if (params.width != 0) {
            params.width = 0;
        }
        params.weight = progress;
        progressBar.setLayoutParams(params);
    }

    public static long getRemainingTime() {
        return timeRemaining;
    }
}
