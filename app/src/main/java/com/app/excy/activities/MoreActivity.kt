package com.app.excy.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

import com.app.excy.R
import com.app.excy.fragments.MoreBaseFragment
import com.app.excy.utilities.AppUtilities

class MoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_more)

        val fragmentManager = supportFragmentManager

        val moreBaseFrag = MoreBaseFragment()
        fragmentManager.beginTransaction().replace(R.id.more_fragment_container, moreBaseFrag,
                resources.getString(R.string.more_base_frag)).commitAllowingStateLoss()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        AppUtilities.removeShiftMode(bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val intent: Intent
            when (item.itemId) {
                R.id.action_play -> {
                    intent = Intent(baseContext, PlayActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    startActivity(intent)
                }
                R.id.action_workouts -> {
                    intent = if (com.app.excy.activities.WorkoutActivity.sisActive)
                        Intent(baseContext, WorkoutActivity::class.java)
                    else
                        Intent(baseContext, WorkoutListActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    startActivity(intent)
                }
                R.id.action_me -> {
                    intent = Intent(baseContext, MeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    startActivity(intent)
                }
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_more)
    }
}
