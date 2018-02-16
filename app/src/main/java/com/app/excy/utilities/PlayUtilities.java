package com.app.excy.utilities;

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

}
