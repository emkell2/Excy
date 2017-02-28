package com.excy.excy.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.excy.excy.R;
import com.excy.excy.utilities.AppUtilities;

public class MeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_me);
    }
}
