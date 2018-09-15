package com.app.excy.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

import com.app.excy.R

class LearnTheExercisesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_learn_the_exercises, container, false)

        val armCandyBtn = view.findViewById<ImageButton>(R.id.ibArmCandyLink)
        armCandyBtn.setOnClickListener { watchYoutubeVideo(getString(R.string.armCandyYoutubeLink)) }

        val superCycleBtn = view.findViewById<ImageButton>(R.id.ibSuperCycleCardioLink)
        superCycleBtn.setOnClickListener { watchYoutubeVideo(getString(R.string.superCycleYoutubeLink)) }

        val cycleLegBtn = view.findViewById<ImageButton>(R.id.ibCycleLegBlastLink)
        cycleLegBtn.setOnClickListener { watchYoutubeVideo(getString(R.string.cycleLegYoutubeLink)) }

        val coreFloorBtn = view.findViewById<ImageButton>(R.id.ibCoreFloorExplosionLink)
        coreFloorBtn.setOnClickListener { watchYoutubeVideo(getString(R.string.coreFloorYoutubeLink)) }

        val armBlastBtn = view.findViewById<ImageButton>(R.id.ibArmBlastLink)
        armBlastBtn.setOnClickListener { watchYoutubeVideo(getString(R.string.armBlastYoutubeLink)) }

        val ultimateArmLegBtn = view.findViewById<ImageButton>(R.id.ivUltimateArmAndLegToningLink)
        ultimateArmLegBtn.setOnClickListener { watchYoutubeVideo(getString(R.string.ultimateArmLegYoutubeLink)) }

        return view
    }

    fun watchYoutubeVideo(id: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val webIntent = Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=$id"))
        try {
            startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            startActivity(webIntent)
        }

    }

    companion object {
        fun newInstance(): LearnTheExercisesFragment {
            val fragment = LearnTheExercisesFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}