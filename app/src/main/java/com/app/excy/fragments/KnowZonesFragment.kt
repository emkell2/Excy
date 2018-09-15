package com.app.excy.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.app.excy.R

class KnowZonesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_know_zones, container, false)
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        fun newInstance(): KnowZonesFragment {
            val fragment = KnowZonesFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
