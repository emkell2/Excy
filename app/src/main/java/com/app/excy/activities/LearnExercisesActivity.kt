package com.app.excy.activities

import android.os.Bundle
import com.app.excy.R
import com.app.excy.models.ExerciseInfo
import java.util.*

class LearnExercisesActivity : BaseVideoActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val exerciseList = createExerciseList()
        adapter.setSections(exerciseList)
    }

    private fun createExerciseList(): ArrayList<ExerciseInfo> {
        val exercises = ArrayList<ExerciseInfo>()
        // Lower Body
        exercises.add(ExerciseInfo(getString(R.string.lower_body1), getString(R.string.link_lower_body1), 1,
                ExerciseInfo.ExerciseType.LOWER_BODY))
        exercises.add(ExerciseInfo(getString(R.string.lower_body2), getString(R.string.link_lower_body2), 2,
                ExerciseInfo.ExerciseType.LOWER_BODY))
        exercises.add(ExerciseInfo(getString(R.string.lower_body3), getString(R.string.link_lower_body3), 3,
                ExerciseInfo.ExerciseType.LOWER_BODY))
        exercises.add(ExerciseInfo(getString(R.string.lower_body4), getString(R.string.link_lower_body4), 4,
                ExerciseInfo.ExerciseType.LOWER_BODY))
        exercises.add(ExerciseInfo(getString(R.string.lower_body5), getString(R.string.link_lower_body5), 5,
                ExerciseInfo.ExerciseType.LOWER_BODY))
        exercises.add(ExerciseInfo(getString(R.string.lower_body6), getString(R.string.link_lower_body6), 6,
                ExerciseInfo.ExerciseType.LOWER_BODY))

        // Upper Body
        exercises.add(ExerciseInfo(getString(R.string.upper_body1), getString(R.string.link_upper_body1), 1,
                ExerciseInfo.ExerciseType.UPPER_BODY))
        exercises.add(ExerciseInfo(getString(R.string.upper_body2), getString(R.string.link_upper_body2), 2,
                ExerciseInfo.ExerciseType.UPPER_BODY))
        exercises.add(ExerciseInfo(getString(R.string.upper_body3), getString(R.string.link_upper_body3), 3,
                ExerciseInfo.ExerciseType.UPPER_BODY))
        exercises.add(ExerciseInfo(getString(R.string.upper_body4), getString(R.string.link_upper_body4), 4,
                ExerciseInfo.ExerciseType.UPPER_BODY))
        exercises.add(ExerciseInfo(getString(R.string.upper_body5), getString(R.string.link_upper_body5), 5,
                ExerciseInfo.ExerciseType.UPPER_BODY))
        exercises.add(ExerciseInfo(getString(R.string.upper_body6), getString(R.string.link_upper_body6), 6,
                ExerciseInfo.ExerciseType.UPPER_BODY))
        exercises.add(ExerciseInfo(getString(R.string.upper_body7), getString(R.string.link_upper_body7), 7,
                ExerciseInfo.ExerciseType.UPPER_BODY))
        exercises.add(ExerciseInfo(getString(R.string.upper_body8), getString(R.string.link_upper_body8), 8,
                ExerciseInfo.ExerciseType.UPPER_BODY))

        // Total Body
        exercises.add(ExerciseInfo(getString(R.string.total_body1), getString(R.string.link_total_body1), 1,
                ExerciseInfo.ExerciseType.TOTAL_BODY))
        exercises.add(ExerciseInfo(getString(R.string.total_body2), getString(R.string.link_total_body2), 2,
                ExerciseInfo.ExerciseType.TOTAL_BODY))
        exercises.add(ExerciseInfo(getString(R.string.total_body3), getString(R.string.link_total_body3), 3,
                ExerciseInfo.ExerciseType.TOTAL_BODY))
        exercises.add(ExerciseInfo(getString(R.string.total_body4), getString(R.string.link_total_body4), 4,
                ExerciseInfo.ExerciseType.TOTAL_BODY))
        exercises.add(ExerciseInfo(getString(R.string.total_body5), getString(R.string.link_total_body5), 5,
                ExerciseInfo.ExerciseType.TOTAL_BODY))
        exercises.add(ExerciseInfo(getString(R.string.total_body6), getString(R.string.link_total_body6), 6,
                ExerciseInfo.ExerciseType.TOTAL_BODY))
        exercises.add(ExerciseInfo(getString(R.string.total_body7), getString(R.string.link_total_body7), 7,
                ExerciseInfo.ExerciseType.TOTAL_BODY))
        exercises.add(ExerciseInfo(getString(R.string.total_body8), getString(R.string.link_total_body8), 8,
                ExerciseInfo.ExerciseType.TOTAL_BODY))

        return exercises
    }
}
