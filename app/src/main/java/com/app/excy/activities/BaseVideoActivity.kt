package com.app.excy.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.app.excy.R
import com.app.excy.StickyHeaderLayoutManager
import com.app.excy.interfaces.OnListFragmentInteractionListener
import com.app.excy.models.ExerciseInfo
import com.app.excy.models.StickyHeadersAdapter
import com.app.excy.utilities.AppUtilities
import com.app.excy.utilities.Constants
import kotlinx.android.synthetic.main.activity_base_video.*

open class BaseVideoActivity : AppCompatActivity() {
    val adapter = StickyHeadersAdapter(object: OnListFragmentInteractionListener {
        override fun onListFragmentInteraction(info: ExerciseInfo?) {
            launchWebViewActivity(info)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_video)
        AppUtilities.setBottomNavBarIconActive(this, R.id.action_more)

        val bottomNavigationView = findViewById(R.id.bottomNavigationView) as BottomNavigationView

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

        stickyHeaderRecyclerView.layoutManager = StickyHeaderLayoutManager()
        stickyHeaderRecyclerView.adapter = adapter
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
