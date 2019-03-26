package com.app.excy.activities

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.ViewTreeObserver
import com.app.excy.R
import com.app.excy.models.ExerciseInfo
import com.app.excy.models.ListPagerAdapter
import com.app.excy.utilities.AppUtilities
import com.app.excy.utilities.Constants
import kotlinx.android.synthetic.main.activity_base_list.*



inline fun View.afterMeasured(crossinline f: View.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}

open class BaseListActivity : AppCompatActivity() {
    lateinit var pager: ViewPager
    lateinit var viewPagerAdapter: ListPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_list)
        AppUtilities.setBottomNavBarIconActive(this, R.id.action_more)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        AppUtilities.removeShiftMode(bottomNav)

        bottomNav.setOnNavigationItemSelectedListener { item ->
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

        pager = findViewById(R.id.viewPager)
        val tabs = findViewById<TabLayout>(R.id.tabLayout)
        bottomNav.afterMeasured {
            val params = pager.layoutParams as ConstraintLayout.LayoutParams
            params.height = pager.height - bottomNav.height - tabs.height
            pager.layoutParams = params
            pager.requestLayout()
        }

        viewPagerAdapter = ListPagerAdapter(supportFragmentManager)
        pager.adapter = viewPagerAdapter

        tabLayout.setupWithViewPager(pager)
    }

    override fun onResume() {
        super.onResume()

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_more)
    }

    fun launchWebViewActivity(info: ExerciseInfo?) {
        if (info != null && !TextUtils.isEmpty(info.url)) {
            val webViewIntent = Intent(this, WebViewActivity::class.java)
            webViewIntent.putExtra(Constants.WEBVIEW_URL, info.url)

            startActivity(webViewIntent)
        }
    }
}
