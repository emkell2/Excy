package com.excy.excy.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;

import com.excy.excy.R;
import com.excy.excy.utilities.AppUtilities;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_me);

        Button logOutBtn = (Button) findViewById(R.id.btnLogOut);

        logOutBtn.getBackground().setColorFilter(
                getResources().getColor(R.color.colorLogOutBtn), PorterDuff.Mode.MULTIPLY);

        Button saveChangesBtn = (Button) findViewById(R.id.btnSaveChanges);

        saveChangesBtn.getBackground().setColorFilter(
                getResources().getColor(R.color.colorSaveChangesBtn), PorterDuff.Mode.MULTIPLY);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.action_play:
                        intent = new Intent(getBaseContext(), PlayActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_workouts:
                        intent = new Intent(getBaseContext(), WorkoutListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_more:
                        intent = new Intent(getBaseContext(), MoreActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        AppUtilities.removeShiftMode(bottomNavigationView);
    }
}
