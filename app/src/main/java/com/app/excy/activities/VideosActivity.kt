package com.app.excy.activities


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils

import com.app.excy.R
import com.app.excy.StickyHeaderLayoutManager
import com.app.excy.interfaces.OnListFragmentInteractionListener
import com.app.excy.models.ExerciseVideo
import com.app.excy.models.StickyHeadersAdapter
import com.app.excy.utilities.AppUtilities
import com.app.excy.utilities.Constants
import kotlinx.android.synthetic.main.activity_videos.*

import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class VideosActivity : AppCompatActivity() {
    var videoList = ArrayList<ExerciseVideo>()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout for this fragment
        setContentView(R.layout.activity_videos)

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

        videoList = createVideoList()
        var adapter = StickyHeadersAdapter(object: OnListFragmentInteractionListener {
            override fun onListFragmentInteraction(video: ExerciseVideo?) {
                launchWebViewActivity(video)
            }
        })
        videos.layoutManager = StickyHeaderLayoutManager()
        videos.adapter = adapter
        adapter.setSections(videoList)
    }

    private fun createVideoList(): ArrayList<ExerciseVideo> {
        val videos = ArrayList<ExerciseVideo>()
        // Workout
        videos.add(ExerciseVideo(getString(R.string.workout1), getString(R.string.armCandyYoutubeLink), 1,
                ExerciseVideo.ExerciseType.WORKOUT))
        videos.add(ExerciseVideo(getString(R.string.workout2), getString(R.string.superCycleYoutubeLink), 2,
                ExerciseVideo.ExerciseType.WORKOUT))
        videos.add(ExerciseVideo(getString(R.string.workout3), getString(R.string.cycleLegYoutubeLink), 3,
                ExerciseVideo.ExerciseType.WORKOUT))
        videos.add(ExerciseVideo(getString(R.string.workout4), getString(R.string.coreFloorYoutubeLink), 4,
                ExerciseVideo.ExerciseType.WORKOUT))
        videos.add(ExerciseVideo(getString(R.string.workout5), getString(R.string.armBlastYoutubeLink), 5,
                ExerciseVideo.ExerciseType.WORKOUT))
        videos.add(ExerciseVideo(getString(R.string.workout6), getString(R.string.ultimateArmLegYoutubeLink), 6,
                ExerciseVideo.ExerciseType.WORKOUT))

        // Arm Ergonomics
        videos.add(ExerciseVideo(getString(R.string.arms1), getString(R.string.link_arms1), 1,
                ExerciseVideo.ExerciseType.ARMS))
        videos.add(ExerciseVideo(getString(R.string.arms2), getString(R.string.link_arms2), 2,
                ExerciseVideo.ExerciseType.ARMS))
        videos.add(ExerciseVideo(getString(R.string.arms3), getString(R.string.link_arms3), 3,
                ExerciseVideo.ExerciseType.ARMS))
        videos.add(ExerciseVideo(getString(R.string.arms4), getString(R.string.link_arms4), 4,
                ExerciseVideo.ExerciseType.ARMS))
        videos.add(ExerciseVideo(getString(R.string.arms5), getString(R.string.link_arms5), 5,
                ExerciseVideo.ExerciseType.ARMS))
        videos.add(ExerciseVideo(getString(R.string.arms6), getString(R.string.link_arms6), 6,
                ExerciseVideo.ExerciseType.ARMS))
        videos.add(ExerciseVideo(getString(R.string.arms7), getString(R.string.link_arms7), 7,
                ExerciseVideo.ExerciseType.ARMS))
        videos.add(ExerciseVideo(getString(R.string.arms8), getString(R.string.link_arms8), 8,
                ExerciseVideo.ExerciseType.ARMS))
        videos.add(ExerciseVideo(getString(R.string.arms9), getString(R.string.link_arms9), 9,
                ExerciseVideo.ExerciseType.ARMS))
        videos.add(ExerciseVideo(getString(R.string.arms10), getString(R.string.link_arms10), 10,
                ExerciseVideo.ExerciseType.ARMS))
        videos.add(ExerciseVideo(getString(R.string.arms11), getString(R.string.link_arms11), 11,
                ExerciseVideo.ExerciseType.ARMS))
        videos.add(ExerciseVideo(getString(R.string.arms12), getString(R.string.link_arms12), 12,
                ExerciseVideo.ExerciseType.ARMS))
        videos.add(ExerciseVideo(getString(R.string.arms13), getString(R.string.link_arms13), 13,
                ExerciseVideo.ExerciseType.ARMS))
        videos.add(ExerciseVideo(getString(R.string.arms14), getString(R.string.link_arms14), 14,
                ExerciseVideo.ExerciseType.ARMS))

        // Leg Ergonomics
        videos.add(ExerciseVideo(getString(R.string.legs1), getString(R.string.link_legs1), 1,
                ExerciseVideo.ExerciseType.LEGS))
        videos.add(ExerciseVideo(getString(R.string.legs2), getString(R.string.link_legs2), 2,
                ExerciseVideo.ExerciseType.LEGS))
        videos.add(ExerciseVideo(getString(R.string.legs3), getString(R.string.link_legs3), 3,
                ExerciseVideo.ExerciseType.LEGS))
        videos.add(ExerciseVideo(getString(R.string.legs4), getString(R.string.link_legs4), 4,
                ExerciseVideo.ExerciseType.LEGS))
        videos.add(ExerciseVideo(getString(R.string.legs5), getString(R.string.link_legs5), 5,
                ExerciseVideo.ExerciseType.LEGS))
        videos.add(ExerciseVideo(getString(R.string.legs6), getString(R.string.link_legs6), 6,
                ExerciseVideo.ExerciseType.LEGS))
        videos.add(ExerciseVideo(getString(R.string.legs7), getString(R.string.link_legs7), 7,
                ExerciseVideo.ExerciseType.LEGS))
        videos.add(ExerciseVideo(getString(R.string.legs8), getString(R.string.link_legs8), 8,
                ExerciseVideo.ExerciseType.LEGS))
        videos.add(ExerciseVideo(getString(R.string.legs9), getString(R.string.link_legs9), 9,
                ExerciseVideo.ExerciseType.LEGS))
        videos.add(ExerciseVideo(getString(R.string.legs10), getString(R.string.link_legs10), 10,
                ExerciseVideo.ExerciseType.LEGS))
        videos.add(ExerciseVideo(getString(R.string.legs11), getString(R.string.link_legs11), 11,
                ExerciseVideo.ExerciseType.LEGS))
        videos.add(ExerciseVideo(getString(R.string.legs12), getString(R.string.link_legs12), 12,
                ExerciseVideo.ExerciseType.LEGS))
        videos.add(ExerciseVideo(getString(R.string.legs13), getString(R.string.link_legs13), 13,
                ExerciseVideo.ExerciseType.LEGS))

        return videos
    }

    override fun onResume() {
        super.onResume()

        AppUtilities.setBottomNavBarIconActive(this, R.id.action_more)
    }

    fun launchWebViewActivity(video: ExerciseVideo?) {
        if (video != null && !TextUtils.isEmpty(video.url)) {
            val webViewIntent = Intent(this, WebViewActivity::class.java)
            webViewIntent.putExtra(Constants.WEBVIEW_URL, video.url)

            startActivity(webViewIntent)
        }
    }
}