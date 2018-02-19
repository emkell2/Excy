package com.app.excy.timers;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.excy.R;
import com.app.excy.activities.PlayActivity;
import com.app.excy.utilities.PlayUtilities;

/**
 * Created by erin.kelley on 6/25/16.
 */
public class PlayTimer {

    private CountDownTimer timer;
    private static long timeRemaining;
    private static int currSeconds = 0;
    private static int prevSeconds = 0;
    private static int fastIntCtr = 0;
    private static int slowIntCtr = 0;
    private static boolean currentInterval;     // false = slow, true = fast

    public boolean finished;

    public PlayTimer(long startTime) {
        timeRemaining = startTime;
    }

    public void startTimer(final TextView tvTimer, final TextView progressBar,
                           boolean setCurrentInterval) {
        int fastInt = PlayActivity.getFastInterval();
        int slowInt = PlayActivity.getSlowInterval();

        if (setCurrentInterval) {
            if (slowInt == 0 && fastInt == 0) {
                PlayActivity.setRunningManImageAndText(R.drawable.burst_play_grey, 0);
            } else if (slowInt >= fastInt) {
                if (!PlayActivity.isForwardBackwardWorkout()) {
                    PlayActivity.setRunningManImageAndText(R.drawable.burst_play_blue, R.string.slow_it_down);
                } else {
                    PlayActivity.setRunningManImageAndText(R.drawable.burst_play_blue, R.string.backwards);
                }
            } else {
                if (!PlayActivity.isForwardBackwardWorkout()) {
                    PlayActivity.setRunningManImageAndText(R.drawable.burst_play_red, R.string.push_yourself);
                } else {
                    PlayActivity.setRunningManImageAndText(R.drawable.burst_play_red, R.string.forwards);
                }
            }

            currentInterval = (slowInt >= fastInt) ? false : true;
        }

        if (!progressBar.isShown()) {
            progressBar.setVisibility(View.VISIBLE);
        }

        timer = new CountDownTimer(timeRemaining, 100) {
            int seconds = 0;
            long ms = 0;
            boolean start = true;

            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                ms += 100;

                // Saved current seconds for a new timer to ensure accuracy
                if (!start) {
                    prevSeconds = seconds;
                } else {
                    prevSeconds = currSeconds;
                    start = false;
                }

                // Need to do this conditional or else CountDownTimer will spit out 1 second twice
                // from not being completely accurate. Also knocked down the tick rate to 100ms
                // from 1000ms.
                if (Math.round((float) millisUntilFinished / 1000.0f) != seconds) {
                    seconds = Math.round((float) millisUntilFinished / 1000.0f);
                    int minutes = seconds / 60;
                    seconds %= 60;
                    currSeconds = seconds;

                    String newTime = PlayUtilities.createTimerString(minutes, seconds);
                    if (!TextUtils.isEmpty(newTime)) {
                        tvTimer.setText(newTime);
                    }

//                    System.out.println("ms=" + millisUntilFinished + " mins=" + minutes
//                            + " secs=" + seconds + " prevSecs=" + prevSeconds
//                            + " slowCtr=" + slowIntCtr + " fastCtr=" + fastIntCtr);

                    if (seconds != prevSeconds) {
                        handleIntervals();
                    }

                    PlayActivity.updateTime(minutes, seconds);

                    // Update progress approximately every half a second
                    if (ms >= 500) {
                        updateProgressBar(progressBar, minutes, seconds);
                        ms = 0;
                    }
                }
            }

            @Override
            public void onFinish() {
                tvTimer.setText("00:00");
                cancelTimer(true);
                finished = true;
            }
        }.start();
    }

    public void cancelTimer(boolean reset) {
        if (timer != null) {
            timer.cancel();
            timer = null;
            finished = true;
        }

        if (reset) {
            slowIntCtr = 0;
            fastIntCtr = 0;
            prevSeconds = 0;
            currSeconds = 0;
        }
    }

    private void updateProgressBar(TextView progressBar, int minutes, int seconds) {
        int totalSeconds = (minutes * 60) + seconds;
        int startingTime = (int) (PlayActivity.getStartingTime() / 1000);

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

    private void handleIntervals() {
        int slowInt = PlayActivity.getSlowInterval();
        int fastInt = PlayActivity.getFastInterval();

        if (slowInt == 0 || fastInt == 0) {
            return;
        }

        if (!currentInterval) {
            slowIntCtr++;

//            if (slowIntCtr == 1) {
//                PlayActivity.updateFastInterval(fastInt);
//            }
//
//            PlayActivity.updateSlowInterval(slowInt - slowIntCtr);

            if (slowIntCtr == slowInt) {
                slowIntCtr = 0;
                currentInterval = true;
                PlayActivity.changeIntervalImage(currentInterval);
            }
        } else {
            fastIntCtr++;

//            if (fastIntCtr == 1) {
//                PlayActivity.updateSlowInterval(slowInt);
//            }
//
//            PlayActivity.updateFastInterval(fastInt - fastIntCtr);

            if (fastIntCtr == fastInt) {
                fastIntCtr = 0;
                currentInterval = false;
                PlayActivity.changeIntervalImage(currentInterval);
            }
        }
    }

    public static long getRemainingTime() {
        return timeRemaining;
    }

    public static void resetIntervalCounter(boolean slowOrFast) {
        if (slowOrFast && currentInterval) {
            fastIntCtr = 0;
        } else if (!slowOrFast && !currentInterval) {
            slowIntCtr = 0;
        }
    }
}
