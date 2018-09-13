package com.app.excy.fragments

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

import com.app.excy.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [WatchWorkoutsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [WatchWorkoutsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WatchWorkoutsFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_watch_workouts, container, false)

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

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onWorkoutSelected(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            //            throw new RuntimeException(context.toString()
            //                    + " must implement OnFragmentInteractionListener");
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onWorkoutSelected(uri: Uri)
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
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WatchWorkoutsFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): WatchWorkoutsFragment {
            val fragment = WatchWorkoutsFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
