package com.app.excy.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView

import com.app.excy.R
import com.app.excy.activities.LearnExercisesActivity
import com.app.excy.activities.VideosActivity

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MoreBaseFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MoreBaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoreBaseFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_more_base, container, false)

        val fragmentManager = fragmentManager

        val liveBtn = view.findViewById<ImageButton>(R.id.ibLive)
        liveBtn.setOnClickListener {
            val uriUrl = Uri.parse(EXCY_LIVE_WEBSITE)
            val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
            startActivity(launchBrowser)
        }

        val learnExercisesBtn = view.findViewById<ImageButton>(R.id.ibLearnExercises)
        learnExercisesBtn.setOnClickListener {
            val intent = Intent(context, LearnExercisesActivity::class.java)
            startActivity(intent)
        }

        val videoBtn = view.findViewById<ImageButton>(R.id.ibVideos)
        videoBtn.setOnClickListener {
            val intent = Intent(context, VideosActivity::class.java)
            startActivity(intent)
        }

        val knowZonesBtn = view.findViewById<ImageButton>(R.id.ibKnowZones)
        knowZonesBtn.setOnClickListener {
            val frag = KnowZonesFragment()
            val str = resources.getString(R.string.know_zones)

            fragmentManager!!.beginTransaction()
                    .replace(R.id.more_fragment_container, frag, str)
                    .addToBackStack(str)
                    .commitAllowingStateLoss()
        }

        val tipsBtn = view.findViewById<ImageButton>(R.id.ibTips)
        tipsBtn.setOnClickListener {
            val frag = TipsFragment()
            val str = resources.getString(R.string.tips)

            fragmentManager!!.beginTransaction()
                    .replace(R.id.more_fragment_container, frag, str)
                    .addToBackStack(str)
                    .commitAllowingStateLoss()
        }

        /* Set up excy link view  */
        val excyLinkTV = view.findViewById<View>(R.id.tvLink) as TextView
        excyLinkTV.movementMethod = LinkMovementMethod.getInstance()

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        val EXCY_LIVE_WEBSITE = "http://www.excy.live"

        fun newInstance(): MoreBaseFragment {
            val fragment = MoreBaseFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
