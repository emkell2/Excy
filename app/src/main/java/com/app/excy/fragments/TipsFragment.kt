package com.app.excy.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.app.excy.R

class TipsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tips, container, false)

        val tip01 = view.findViewById<ImageView>(R.id.ivTip01)
        tip01.setOnClickListener { onImagePressed(Uri.parse(getString(R.string.tip01Link))) }

        val tip02 = view.findViewById<ImageView>(R.id.ivTip02)
        tip02.setOnClickListener { onImagePressed(Uri.parse(getString(R.string.tip02Link))) }

        val tip03 = view.findViewById<ImageView>(R.id.ivTip03)
        tip03.setOnClickListener { onImagePressed(Uri.parse(getString(R.string.tip03Link))) }

        val tip04 = view.findViewById<ImageView>(R.id.ivTip04)
        tip04.setOnClickListener { onImagePressed(Uri.parse(getString(R.string.tip04Link))) }

        val tip05 = view.findViewById<ImageView>(R.id.ivTip05)
        tip05.setOnClickListener { onImagePressed(Uri.parse(getString(R.string.tip05Link))) }

        val tip06 = view.findViewById<ImageView>(R.id.ivTip06)
        tip06.setOnClickListener { onImagePressed(Uri.parse(getString(R.string.tip06Link))) }

        val tip07 = view.findViewById<ImageView>(R.id.ivTip07)
        tip07.setOnClickListener { onImagePressed(Uri.parse(getString(R.string.tip07Link))) }

        val tip08 = view.findViewById<ImageView>(R.id.ivTip08)
        tip08.setOnClickListener { onImagePressed(Uri.parse(getString(R.string.tip08Link))) }

        val tip09 = view.findViewById<ImageView>(R.id.ivTip09)
        tip09.setOnClickListener { onImagePressed(Uri.parse(getString(R.string.tip09Link))) }

        val tip10 = view.findViewById<ImageView>(R.id.ivTip10)
        tip10.setOnClickListener { onImagePressed(Uri.parse(getString(R.string.tip10Link))) }

        val tip11 = view.findViewById<ImageView>(R.id.ivTip11)
        tip11.setOnClickListener { onImagePressed(Uri.parse(getString(R.string.tip11Link))) }

        val tip12 = view.findViewById<ImageView>(R.id.ivTip12)
        tip12.setOnClickListener { onImagePressed(Uri.parse(getString(R.string.tip12Link))) }

        val tip13 = view.findViewById<ImageView>(R.id.ivTip13)
        tip13.setOnClickListener { onImagePressed(Uri.parse(getString(R.string.tip13Link))) }

        val tip14 = view.findViewById<ImageView>(R.id.ivTip14)
        tip14.setOnClickListener { onImagePressed(Uri.parse(getString(R.string.tip14Link))) }

        val tip15 = view.findViewById<ImageView>(R.id.ivTip15)
        tip15.setOnClickListener { onImagePressed(Uri.parse(getString(R.string.tip15Link))) }

        val tip16 = view.findViewById<ImageView>(R.id.ivTip16)
        tip16.setOnClickListener { onImagePressed(Uri.parse(getString(R.string.tip16Link))) }

        val tip17 = view.findViewById<ImageView>(R.id.ivTip17)
        tip17.setOnClickListener { onImagePressed(Uri.parse(getString(R.string.tip17Link))) }

        return view
    }

    fun onImagePressed(uri: Uri) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.data = uri
        startActivity(intent)
    }

    companion object {

        fun newInstance(): TipsFragment {
            val fragment = TipsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
