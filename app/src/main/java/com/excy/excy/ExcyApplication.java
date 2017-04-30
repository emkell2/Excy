package com.excy.excy;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by erin.kelley on 6/14/16.
 */
public class ExcyApplication extends Application {

    public static final String API_KEY = "AIzaSyDYgPwKMLV0-3f5JHSuFJEtwyQLDskr0bU";
    public static final String APP_ID = "com.excy.excy";
    public static final String DATABASE_URL = "https://excy.firebaseio.com/";

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApiKey(API_KEY)
                .setApplicationId(APP_ID)
                .setDatabaseUrl(DATABASE_URL)
                .build();

        FirebaseApp.initializeApp(this, options, APP_ID);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Dosis-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
