package com.excy.excy.activities;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.excy.excy.R;
import com.excy.excy.utilities.AppUtilities;
import com.excy.excy.utilities.WorkoutUtilities;

public class WorkoutActivity extends AppCompatActivity {
    int workoutResId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_workouts);

        workoutResId = getIntent().getIntExtra(WorkoutUtilities.WORKOUT_INTENT_DATA, 0);

        setWorkoutImage();
        setWorkoutPowerZoneImage();

        // Button layout
        Button pauseBtn = (Button) findViewById(R.id.btnPause);
        pauseBtn.getBackground().setColorFilter(getResources().getColor(R.color.colorPauseBtn),
                PorterDuff.Mode.MULTIPLY);

        Button stopBtn = (Button) findViewById(R.id.btnStop);
        stopBtn.getBackground().setColorFilter(getResources().getColor(R.color.colorStopBtn),
                PorterDuff.Mode.MULTIPLY);
    }

    private void setWorkoutImage() {
        if (workoutResId > 0) {
            ImageView workoutImage = (ImageView) findViewById(R.id.ivWorkoutImage);

            switch (workoutResId) {
                case R.id.ibArmCandy:
                    workoutImage.setImageResource(R.drawable.wb_arm_candy);
                    break;
                case R.id.ibSuperCycleCardio:
                    workoutImage.setImageResource(R.drawable.wb_super_cycle_cardio);
                    break;
                case R.id.ibCycleLegBlast:
                    workoutImage.setImageResource(R.drawable.wb_cycle_leg_blast);
                    break;
                case R.id.ibCoreFloorExplosion:
                    workoutImage.setImageResource(R.drawable.wb_core_floor_explosion);
                    break;
                case R.id.ibArmBlast:
                    workoutImage.setImageResource(R.drawable.wb_arm_blast);
                    break;
                case R.id.ibUltimateArmAndLegToning:
                    workoutImage.setImageResource(R.drawable.wb_ultimate_arm_leg_tone);
                    break;
            }
        }
    }

    private void setWorkoutPowerZoneImage() {
        if (workoutResId > 0) {
            ImageView powerZoneImage = (ImageView) findViewById(R.id.ivZone);

            switch (workoutResId) {
                case R.id.ibArmCandy:
                    powerZoneImage.setImageResource(R.drawable.pz_arm_candy_graph);
                    break;
                case R.id.ibSuperCycleCardio:
                    powerZoneImage.setImageResource(R.drawable.pz_super_cycle_graph);
                    break;
                case R.id.ibCycleLegBlast:
                    powerZoneImage.setImageResource(R.drawable.pz_cycle_leg_blast_graph);
                    break;
                case R.id.ibCoreFloorExplosion:
                    powerZoneImage.setImageResource(R.drawable.pz_core_floor_explosion_graph);
                    break;
                case R.id.ibArmBlast:
                    powerZoneImage.setImageResource(R.drawable.pz_arm_blast_graph);
                    break;
                case R.id.ibUltimateArmAndLegToning:
                    powerZoneImage.setImageResource(R.drawable.pz_ultimate_arm_and_leg_toning_graph);
                    break;
            }
        }
    }

    private int[] getPowerZoneArray(int workoutResId) {
        int[] powerZoneArr = {0};
        if (workoutResId > 0) {
            switch (workoutResId) {
                case R.id.ibArmCandy:
                    powerZoneArr = WorkoutUtilities.PZ_ARM_CANDY_ARR;
                    break;
                case R.id.ibSuperCycleCardio:
                    powerZoneArr = WorkoutUtilities.PZ_SUPER_CYCLE_CARDIO_ARR;
                    break;
                case R.id.ibCycleLegBlast:
                    powerZoneArr = WorkoutUtilities.PZ_CYCLE_LEG_BLAST_ARR;
                    break;
                case R.id.ibCoreFloorExplosion:
                    powerZoneArr = WorkoutUtilities.PZ_CORE_FLOOR_EXPLOSION_ARR;
                    break;
                case R.id.ibArmBlast:
                    powerZoneArr = WorkoutUtilities.PZ_ARM_BLAST_ARR;
                    break;
                case R.id.ibUltimateArmAndLegToning:
                    powerZoneArr = WorkoutUtilities.PZ_ULTIMATE_ARM_LEG_TONING_ARR;
                    break;
            }
        }

        return powerZoneArr;
    }
}
