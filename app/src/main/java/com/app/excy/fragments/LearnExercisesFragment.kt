package com.app.excy.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

import com.app.excy.R

class LearnExercisesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_learn_exercises, container, false)

        val fragmentManager = fragmentManager

        val armCandyBtn = view.findViewById<ImageButton>(R.id.ibArmCandy)
        armCandyBtn.setOnClickListener {
            val frag = ExerciseSummaryFragment.newInstance(R.id.ibArmCandy)

            fragmentManager!!.beginTransaction()
                    .replace(R.id.more_fragment_container, frag, STR_ARM_CANDY)
                    .addToBackStack(STR_ARM_CANDY)
                    .commitAllowingStateLoss()
        }

        val superCycleCardioBtn = view.findViewById<ImageButton>(R.id.ibSuperCycleCardio)
        superCycleCardioBtn.setOnClickListener {
            val frag = ExerciseSummaryFragment.newInstance(R.id.ibSuperCycleCardio)

            fragmentManager!!.beginTransaction()
                    .replace(R.id.more_fragment_container, frag, STR_SUPER_CYCLE_CARDIO)
                    .addToBackStack(STR_SUPER_CYCLE_CARDIO)
                    .commitAllowingStateLoss()
        }

        val cycleLegBlastBtn = view.findViewById<ImageButton>(R.id.ibCycleLegBlast)
        cycleLegBlastBtn.setOnClickListener {
            val frag = ExerciseSummaryFragment.newInstance(R.id.ibCycleLegBlast)

            fragmentManager!!.beginTransaction()
                    .replace(R.id.more_fragment_container, frag, STR_CYCLE_LEG_BLAST)
                    .addToBackStack(STR_CYCLE_LEG_BLAST)
                    .commitAllowingStateLoss()
        }

        val coreFloorExplosionBtn = view.findViewById<ImageButton>(R.id.ibCoreFloorExplosion)
        coreFloorExplosionBtn.setOnClickListener {
            val frag = ExerciseSummaryFragment.newInstance(R.id.ibCoreFloorExplosion)

            fragmentManager!!.beginTransaction()
                    .replace(R.id.more_fragment_container, frag, STR_CORE_FLOOR_EXPLOSION)
                    .addToBackStack(STR_CORE_FLOOR_EXPLOSION)
                    .commitAllowingStateLoss()
        }

        val armBlastBtn = view.findViewById<ImageButton>(R.id.ibArmBlast)
        armBlastBtn.setOnClickListener {
            val frag = ExerciseSummaryFragment.newInstance(R.id.ibArmBlast)

            fragmentManager!!.beginTransaction()
                    .replace(R.id.more_fragment_container, frag, STR_ARM_BLAST)
                    .addToBackStack(STR_ARM_BLAST)
                    .commitAllowingStateLoss()
        }

        val ultimateArmLegToningBtn = view.findViewById<ImageButton>(R.id.ibUltimateArmAndLegToning)
        ultimateArmLegToningBtn.setOnClickListener {
            val frag = ExerciseSummaryFragment.newInstance(R.id.ibUltimateArmAndLegToning)

            fragmentManager!!.beginTransaction()
                    .replace(R.id.more_fragment_container, frag, STR_ULTIMATE_ARM_LEG_TONING)
                    .addToBackStack(STR_ULTIMATE_ARM_LEG_TONING)
                    .commitAllowingStateLoss()
        }

        return view
    }

    companion object {
        val STR_ARM_CANDY = "Arm Candy"
        val STR_SUPER_CYCLE_CARDIO = "Super Cycle Cardio"
        val STR_CYCLE_LEG_BLAST = "Cycle Leg Blast"
        val STR_CORE_FLOOR_EXPLOSION = "Core Floor Explosion"
        val STR_ARM_BLAST = "Arm Blast"
        val STR_ULTIMATE_ARM_LEG_TONING = "Ultimate Arm and Leg Toning"

        fun newInstance(): LearnExercisesFragment {
            val fragment = LearnExercisesFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
