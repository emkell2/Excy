package com.excy.excy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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
                startWorkoutActivity(R.id.ibArmCandy, WorkoutUtilities.armCandyTimeMS,
                        R.raw.arm_candy);
            }
        });

        ImageButton superCycleCardioBtn = (ImageButton) findViewById(R.id.ibSuperCycleCardio);
        superCycleCardioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkoutActivity(R.id.ibSuperCycleCardio,
                        WorkoutUtilities.superCycleCardioTimeMS,
                        R.raw.super_cycle);
            }
        });

        ImageButton cycleLegBlastBtn = (ImageButton) findViewById(R.id.ibCycleLegBlast);
        cycleLegBlastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkoutActivity(R.id.ibCycleLegBlast, WorkoutUtilities.cycleLegBlastTimeMS,
                        R.raw.leg_blast);
            }
        });

        ImageButton coreFloorExplosionBtn = (ImageButton) findViewById(R.id.ibCoreFloorExplosion);
        coreFloorExplosionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkoutActivity(R.id.ibCoreFloorExplosion,
                        WorkoutUtilities.coreFloorExplosionTimeMS,
                        R.raw.core_floor);
            }
        });

        ImageButton armBlastBtn = (ImageButton) findViewById(R.id.ibArmBlast);
        armBlastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkoutActivity(R.id.ibArmBlast, WorkoutUtilities.armBlastTimeMS,
                        R.raw.arm_blast);
            }
        });

        ImageButton ultimateArmAndLegToningBtn = (ImageButton) findViewById(R.id.ibUltimateArmAndLegToning);
        ultimateArmAndLegToningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorkoutActivity(R.id.ibUltimateArmAndLegToning,
                        WorkoutUtilities.ultimateArmAndLegTimeMS,
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
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        break;
                    case R.id.action_me:
                        intent = new Intent(getBaseContext(), MeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        break;
                    case R.id.action_more:
                        intent = new Intent(getBaseContext(), MoreActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        /* Set up excy link view  */
        TextView excyLinkTV = (TextView) findViewById(R.id.tvLink);
        excyLinkTV.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_workouts);
    }

    private void startWorkoutActivity(int resId, long timeInMillis, int audioResId) {
        Intent intent = new Intent(getBaseContext(), WorkoutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        intent.putExtra(WorkoutUtilities.WORKOUT_DATA_RES_ID, resId);
        intent.putExtra(WorkoutUtilities.WORKOUT_DATA_TIME_MILLIS, timeInMillis);
        intent.putExtra(WorkoutUtilities.WORKOUT_DATA_AUDIO_RES_ID, audioResId);
        startActivity(intent);
    }
}
