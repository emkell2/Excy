package com.app.excy.fragments

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView

import com.app.excy.R
import com.app.excy.activities.WorkoutActivity
import com.app.excy.utilities.WorkoutUtilities

class ExerciseSummaryFragment : Fragment() {

    private var mResId: Int = 0
    private var audioResId: Int = 0
    private var timeInMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mResId = arguments!!.getInt(RES_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_exercise_summary, container, false)

        if (mResId > 0) {
            val workoutImage = view.findViewById<ImageView>(R.id.ivWorkoutImage)
            val workoutButtons = view.findViewById<ImageView>(R.id.ivWorkoutButtons)
            val workoutSummary = view.findViewById<ImageView>(R.id.ivWorkoutSummary)
            val workoutGraph = view.findViewById<ImageView>(R.id.ivWorkoutGraph)

            when (mResId) {
                R.id.ibArmCandy -> {
                    workoutImage.setImageResource(R.drawable.wb_arm_candy)
                    workoutButtons.setImageResource(R.drawable.arm_candy_description_btns)
                    workoutSummary.setImageResource(R.drawable.arm_candy_description_summary)
                    workoutGraph.setImageResource(R.drawable.pz_arm_candy_graph)

                    timeInMillis = WorkoutUtilities.armCandyTimeMS
                    audioResId = R.raw.arm_candy
                }
                R.id.ibSuperCycleCardio -> {
                    workoutImage.setImageResource(R.drawable.wb_super_cycle_cardio)
                    workoutButtons.setImageResource(R.drawable.super_cardio_description_btns)
                    workoutSummary.setImageResource(R.drawable.super_cardio_description_summary)
                    workoutGraph.setImageResource(R.drawable.pz_super_cycle_graph)

                    timeInMillis = WorkoutUtilities.superCycleCardioTimeMS
                    audioResId = R.raw.super_cycle
                }
                R.id.ibCycleLegBlast -> {
                    workoutImage.setImageResource(R.drawable.wb_cycle_leg_blast)
                    workoutButtons.setImageResource(R.drawable.cycle_blast_description_btns)
                    workoutSummary.setImageResource(R.drawable.cycle_blast_description_summary)
                    workoutGraph.setImageResource(R.drawable.pz_cycle_leg_blast_graph)

                    timeInMillis = WorkoutUtilities.cycleLegBlastTimeMS
                    audioResId = R.raw.leg_blast
                }
                R.id.ibCoreFloorExplosion -> {
                    workoutImage.setImageResource(R.drawable.wb_core_floor_explosion)
                    workoutButtons.setImageResource(R.drawable.core_floor_description_btns)
                    workoutSummary.setImageResource(R.drawable.core_floor_description_summary)
                    workoutGraph.setImageResource(R.drawable.pz_core_floor_explosion_graph)

                    timeInMillis = WorkoutUtilities.coreFloorExplosionTimeMS
                    audioResId = R.raw.core_floor
                }
                R.id.ibArmBlast -> {
                    workoutImage.setImageResource(R.drawable.wb_arm_blast)
                    workoutButtons.setImageResource(R.drawable.arm_blast_description_btns)
                    workoutSummary.setImageResource(R.drawable.arm_blast_description_summary)
                    workoutGraph.setImageResource(R.drawable.pz_arm_blast_graph)

                    timeInMillis = WorkoutUtilities.armBlastTimeMS
                    audioResId = R.raw.arm_blast
                }
                R.id.ibUltimateArmAndLegToning -> {
                    workoutImage.setImageResource(R.drawable.wb_ultimate_arm_leg_tone)
                    workoutButtons.setImageResource(R.drawable.arm_and_leg_description_btns)
                    workoutSummary.setImageResource(R.drawable.arm_leg_description_summary)
                    workoutGraph.setImageResource(R.drawable.pz_ultimate_arm_and_leg_toning_graph)

                    timeInMillis = WorkoutUtilities.ultimateArmAndLegTimeMS
                    audioResId = R.raw.ultimate_arm_leg
                }
            }
        }

        val startBtn = view.findViewById<Button>(R.id.btnStart)
        startBtn.background.setColorFilter(resources.getColor(R.color.colorExcyGreen),
                PorterDuff.Mode.MULTIPLY)
        startBtn.setOnClickListener {
            val intent = Intent(context, WorkoutActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT

            intent.putExtra(WorkoutUtilities.WORKOUT_DATA_RES_ID, mResId)
            intent.putExtra(WorkoutUtilities.WORKOUT_DATA_TIME_MILLIS, timeInMillis)
            intent.putExtra(WorkoutUtilities.WORKOUT_DATA_AUDIO_RES_ID, audioResId)
            startActivity(intent)
        }

        return view
    }

    companion object {
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val RES_ID = "resId"

        fun newInstance(resId: Int): ExerciseSummaryFragment {
            val fragment = ExerciseSummaryFragment()
            val args = Bundle()
            args.putInt(RES_ID, resId)
            fragment.arguments = args
            return fragment
        }
    }
}
