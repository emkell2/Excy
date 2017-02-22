package com.excy.excy;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by erin.kelley on 6/14/16.
 */
public class ExcyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Dosis-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
