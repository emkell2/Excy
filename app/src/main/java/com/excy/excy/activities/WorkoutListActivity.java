package com.excy.excy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    }

    private void startWorkoutActivity(int resId) {
        Intent intent = new Intent(getBaseContext(), WorkoutActivity.class);

        intent.putExtra(WorkoutUtilities.WORKOUT_INTENT_DATA, resId);
        startActivity(intent);
    }
}
