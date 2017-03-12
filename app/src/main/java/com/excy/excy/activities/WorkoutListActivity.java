package com.excy.excy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.excy.excy.R;
import com.excy.excy.utilities.AppUtilities;
import com.excy.excy.utilities.WorkoutUtilities;

public class WorkoutListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_workouts);

        ImageButton armCandyBtn = (ImageButton) findViewById(R.id.ibArmCandy);
        armCandyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkoutActivity(R.id.ibArmCandy);
            }
        });

        ImageButton superCycleCardioBtn = (ImageButton) findViewById(R.id.ibSuperCycleCardio);
        superCycleCardioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkoutActivity(R.id.ibSuperCycleCardio);
            }
        });

        ImageButton cycleLegBlastBtn = (ImageButton) findViewById(R.id.ibCycleLegBlast);
        cycleLegBlastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkoutActivity(R.id.ibCycleLegBlast);
            }
        });

        ImageButton coreFloorExplosionBtn = (ImageButton) findViewById(R.id.ibCoreFloorExplosion);
        coreFloorExplosionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkoutActivity(R.id.ibCoreFloorExplosion);
            }
        });

        ImageButton armBlastBtn = (ImageButton) findViewById(R.id.ibArmBlast);
        armBlastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkoutActivity(R.id.ibArmBlast);
            }
        });

        ImageButton ultimateArmAndLegToningBtn = (ImageButton) findViewById(R.id.ibUltimateArmAndLegToning);
        ultimateArmAndLegToningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkoutActivity(R.id.ibUltimateArmAndLegToning);
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
                        intent = new Intent(getBaseContext(), WorkoutListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_me:
                        intent = new Intent(getBaseContext(), MeActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

    }

    private void startWorkoutActivity(int resId) {
        Intent intent = new Intent(getBaseContext(), WorkoutActivity.class);

        intent.putExtra(WorkoutUtilities.WORKOUT_INTENT_DATA, resId);
        startActivity(intent);
    }
}
