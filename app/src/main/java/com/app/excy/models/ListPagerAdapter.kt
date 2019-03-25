package com.app.excy.models

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.app.excy.activities.LearnExercisesActivity
import com.app.excy.activities.VideosActivity
import com.app.excy.fragments.LearnExercisesFragment
import com.app.excy.fragments.VideosFragment

class ListPagerAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {

    private lateinit var activity: String

    override fun getItem(position: Int): Fragment {
        lateinit var frag: Fragment
        if (activity == VideosActivity::class.java.simpleName) {
            frag = VideosFragment.newInstance(position)
        } else if (activity == LearnExercisesActivity::class.java.simpleName) {
            frag = LearnExercisesFragment.newInstance(position)
        } else {
            frag = VideosFragment.newInstance(position)
        }

        return frag
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "XCS 200"
            1 -> "XCR 300"
            else -> {
                return ""
            }
        }
    }

    fun setActivity(activity: String) {
        this.activity = activity
    }
}