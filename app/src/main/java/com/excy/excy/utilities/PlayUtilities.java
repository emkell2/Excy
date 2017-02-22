package com.excy.excy.utilities;

import android.content.Context;

/**
 * Created by erin.kelley on 7/7/16.
 */
public class PlayUtilities {

    public static String createTimerString(int minutes, int seconds) {
        String secs = String.valueOf(seconds);
        String mins = String.valueOf(minutes);

        if (minutes < 10) {
            mins = "0" + String.valueOf(minutes);
        }

        if (seconds < 10) {
            secs = "0" + String.valueOf(seconds);
        }

        return mins + ":" + secs;
    }

    public static int dpFromPx(final Context context, final float px) {
        return (int) (px / context.getResources().getDisplayMetrics().density);
    }
}
