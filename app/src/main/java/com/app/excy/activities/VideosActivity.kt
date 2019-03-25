package com.app.excy.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import com.app.excy.R
import com.app.excy.models.ExerciseInfo

/**
 * A simple [Fragment] subclass.
 */
class VideosActivity : BaseListActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPagerAdapter.setActivity(this::class.java.simpleName)
    }

    fun createXCS200VideoList(): ArrayList<ExerciseInfo> {
        val videos = ArrayList<ExerciseInfo>()
        // Workout
        videos.add(ExerciseInfo(getString(R.string.workout1), getString(R.string.armCandyYoutubeLink), 1,
                ExerciseInfo.ExerciseType.WORKOUT))
        videos.add(ExerciseInfo(getString(R.string.workout2), getString(R.string.superCycleYoutubeLink), 2,
                ExerciseInfo.ExerciseType.WORKOUT))
        videos.add(ExerciseInfo(getString(R.string.workout3), getString(R.string.cycleLegYoutubeLink), 3,
                ExerciseInfo.ExerciseType.WORKOUT))
        videos.add(ExerciseInfo(getString(R.string.workout4), getString(R.string.coreFloorYoutubeLink), 4,
                ExerciseInfo.ExerciseType.WORKOUT))
        videos.add(ExerciseInfo(getString(R.string.workout5), getString(R.string.armBlastYoutubeLink), 5,
                ExerciseInfo.ExerciseType.WORKOUT))
        videos.add(ExerciseInfo(getString(R.string.workout6), getString(R.string.ultimateArmLegYoutubeLink), 6,
                ExerciseInfo.ExerciseType.WORKOUT))

        // Arm Ergonomics
        videos.add(ExerciseInfo(getString(R.string.arms1), getString(R.string.link_arms1), 1,
                ExerciseInfo.ExerciseType.XCS200_ARMS))
        videos.add(ExerciseInfo(getString(R.string.arms2), getString(R.string.link_arms2), 2,
                ExerciseInfo.ExerciseType.XCS200_ARMS))
        videos.add(ExerciseInfo(getString(R.string.arms3), getString(R.string.link_arms3), 3,
                ExerciseInfo.ExerciseType.XCS200_ARMS))
        videos.add(ExerciseInfo(getString(R.string.arms4), getString(R.string.link_arms4), 4,
                ExerciseInfo.ExerciseType.XCS200_ARMS))
        videos.add(ExerciseInfo(getString(R.string.arms5), getString(R.string.link_arms5), 5,
                ExerciseInfo.ExerciseType.XCS200_ARMS))
        videos.add(ExerciseInfo(getString(R.string.arms6), getString(R.string.link_arms6), 6,
                ExerciseInfo.ExerciseType.XCS200_ARMS))
        videos.add(ExerciseInfo(getString(R.string.arms7), getString(R.string.link_arms7), 7,
                ExerciseInfo.ExerciseType.XCS200_ARMS))
        videos.add(ExerciseInfo(getString(R.string.arms8), getString(R.string.link_arms8), 8,
                ExerciseInfo.ExerciseType.XCS200_ARMS))
        videos.add(ExerciseInfo(getString(R.string.arms9), getString(R.string.link_arms9), 9,
                ExerciseInfo.ExerciseType.XCS200_ARMS))
        videos.add(ExerciseInfo(getString(R.string.arms10), getString(R.string.link_arms10), 10,
                ExerciseInfo.ExerciseType.XCS200_ARMS))
        videos.add(ExerciseInfo(getString(R.string.arms11), getString(R.string.link_arms11), 11,
                ExerciseInfo.ExerciseType.XCS200_ARMS))
        videos.add(ExerciseInfo(getString(R.string.arms12), getString(R.string.link_arms12), 12,
                ExerciseInfo.ExerciseType.XCS200_ARMS))
        videos.add(ExerciseInfo(getString(R.string.arms13), getString(R.string.link_arms13), 13,
                ExerciseInfo.ExerciseType.XCS200_ARMS))
        videos.add(ExerciseInfo(getString(R.string.arms14), getString(R.string.link_arms14), 14,
                ExerciseInfo.ExerciseType.XCS200_ARMS))

        // Leg Ergonomics
        videos.add(ExerciseInfo(getString(R.string.legs1), getString(R.string.link_legs1), 1,
                ExerciseInfo.ExerciseType.XCS200_LEGS))
        videos.add(ExerciseInfo(getString(R.string.legs2), getString(R.string.link_legs2), 2,
                ExerciseInfo.ExerciseType.XCS200_LEGS))
        videos.add(ExerciseInfo(getString(R.string.legs3), getString(R.string.link_legs3), 3,
                ExerciseInfo.ExerciseType.XCS200_LEGS))
        videos.add(ExerciseInfo(getString(R.string.legs4), getString(R.string.link_legs4), 4,
                ExerciseInfo.ExerciseType.XCS200_LEGS))
        videos.add(ExerciseInfo(getString(R.string.legs5), getString(R.string.link_legs5), 5,
                ExerciseInfo.ExerciseType.XCS200_LEGS))
        videos.add(ExerciseInfo(getString(R.string.legs6), getString(R.string.link_legs6), 6,
                ExerciseInfo.ExerciseType.XCS200_LEGS))
        videos.add(ExerciseInfo(getString(R.string.legs7), getString(R.string.link_legs7), 7,
                ExerciseInfo.ExerciseType.XCS200_LEGS))
        videos.add(ExerciseInfo(getString(R.string.legs8), getString(R.string.link_legs8), 8,
                ExerciseInfo.ExerciseType.XCS200_LEGS))
        videos.add(ExerciseInfo(getString(R.string.legs9), getString(R.string.link_legs9), 9,
                ExerciseInfo.ExerciseType.XCS200_LEGS))
        videos.add(ExerciseInfo(getString(R.string.legs10), getString(R.string.link_legs10), 10,
                ExerciseInfo.ExerciseType.XCS200_LEGS))
        videos.add(ExerciseInfo(getString(R.string.legs11), getString(R.string.link_legs11), 11,
                ExerciseInfo.ExerciseType.XCS200_LEGS))
        videos.add(ExerciseInfo(getString(R.string.legs12), getString(R.string.link_legs12), 12,
                ExerciseInfo.ExerciseType.XCS200_LEGS))
        videos.add(ExerciseInfo(getString(R.string.legs13), getString(R.string.link_legs13), 13,
                ExerciseInfo.ExerciseType.XCS200_LEGS))

        return videos
    }

    fun createXCR300VideoList(): ArrayList<ExerciseInfo> {
        val videos = ArrayList<ExerciseInfo>()

        // Arm Ergonomics
        videos.add(ExerciseInfo(getString(R.string.xcr300_video1_text), getString(R.string.xcr300_video1_yt), 1,
                ExerciseInfo.ExerciseType.XCR300_ARMS))
        videos.add(ExerciseInfo(getString(R.string.xcr300_video2_text), getString(R.string.xcr300_video2_yt), 2,
                ExerciseInfo.ExerciseType.XCR300_ARMS))
        videos.add(ExerciseInfo(getString(R.string.xcr300_video3_text), getString(R.string.xcr300_video3_yt), 3,
                ExerciseInfo.ExerciseType.XCR300_ARMS))
        videos.add(ExerciseInfo(getString(R.string.xcr300_video4_text), getString(R.string.xcr300_video4_yt), 4,
                ExerciseInfo.ExerciseType.XCR300_ARMS))
        videos.add(ExerciseInfo(getString(R.string.xcr300_video5_text), getString(R.string.xcr300_video5_yt), 5,
                ExerciseInfo.ExerciseType.XCR300_ARMS))
        videos.add(ExerciseInfo(getString(R.string.xcr300_video6_text), getString(R.string.xcr300_video6_yt), 6,
                ExerciseInfo.ExerciseType.XCR300_ARMS))

        // Leg Ergonomics
        videos.add(ExerciseInfo(getString(R.string.xcr300_video7_text), getString(R.string.xcr300_video7_yt), 1,
                ExerciseInfo.ExerciseType.XCR300_LEGS))
        videos.add(ExerciseInfo(getString(R.string.xcr300_video8_text), getString(R.string.xcr300_video8_yt), 2,
                ExerciseInfo.ExerciseType.XCR300_LEGS))

        return videos
    }
}