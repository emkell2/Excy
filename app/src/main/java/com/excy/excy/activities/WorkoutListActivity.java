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

    private static long armCandyTimeMS = 420000;
    private static long superCycleCardioTimeMS = 1380000;
    private static long cycleLegBlastTimeMS = 900000;
    private static long coreFloorExplosionTimeMS = 600000;
    private static long armBlastTimeMS = 600000;
    private static long ultimateArmAndLegTimeMS = 420000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_workouts);

        ImageButton armCandyBtn = (ImageButton) findViewById(R.id.ibArmCandy);
        armCandyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkoutActivity(R.id.ibArmCandy, armCandyTimeMS, R.raw.arm_candy);
            }
        });

        ImageButton superCycleCardioBtn = (ImageButton) findViewById(R.id.ibSuperCycleCardio);
        superCycleCardioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkoutActivity(R.id.ibSuperCycleCardio, superCycleCardioTimeMS,
                        R.raw.super_cycle);
            }
        });

        ImageButton cycleLegBlastBtn = (ImageButton) findViewById(R.id.ibCycleLegBlast);
        cycleLegBlastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkoutActivity(R.id.ibCycleLegBlast, cycleLegBlastTimeMS, R.raw.leg_blast);
            }
        });

        ImageButton coreFloorExplosionBtn = (ImageButton) findViewById(R.id.ibCoreFloorExplosion);
        coreFloorExplosionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkoutActivity(R.id.ibCoreFloorExplosion, coreFloorExplosionTimeMS,
                        R.raw.core_floor);
            }
        });

        ImageButton armBlastBtn = (ImageButton) findViewById(R.id.ibArmBlast);
        armBlastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkoutActivity(R.id.ibArmBlast, armBlastTimeMS, R.raw.arm_blast);
            }
        });

        ImageButton ultimateArmAndLegToningBtn = (ImageButton) findViewById(R.id.ibUltimateArmAndLegToning);
        ultimateArmAndLegToningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkoutActivity(R.id.ibUltimateArmAndLegToning, ultimateArmAndLegTimeMS,
                        R.raw.ultimate_arm_leg);
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
                    case R.id.action_play:
                        intent = new Intent(getBaseContext(), PlayActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_me:
                        intent = new Intent(getBaseContext(), MeActivity.class);
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

    }

    private void startWorkoutActivity(int resId, long timeInMillis, int audioResId) {
        Intent intent = new Intent(getBaseContext(), WorkoutActivity.class);

        intent.putExtra(WorkoutUtilities.WORKOUT_DATA_RES_ID, resId);
        intent.putExtra(WorkoutUtilities.WORKOUT_DATA_TIME_MILLIS, timeInMillis);
        intent.putExtra(WorkoutUtilities.WORKOUT_DATA_AUDIO_RES_ID, audioResId);
        startActivity(intent);
    }
}
