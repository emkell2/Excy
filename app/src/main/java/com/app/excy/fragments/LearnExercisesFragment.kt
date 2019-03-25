package com.app.excy.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.excy.R
import com.app.excy.StickyHeaderLayoutManager
import com.app.excy.activities.LearnExercisesActivity
import com.app.excy.interfaces.OnListFragmentInteractionListener
import com.app.excy.models.ExerciseInfo
import com.app.excy.models.StickyHeadersAdapter
import kotlinx.android.synthetic.main.fragment_videos.view.*

private const val POSITION = "position"

/**
 * A simple [Fragment] subclass.
 * Use the [LearnExercisesFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class LearnExercisesFragment : Fragment() {
    private var pos: Int? = null
    private var exerciseList: ArrayList<ExerciseInfo>? = null
    val adapter = StickyHeadersAdapter(object: OnListFragmentInteractionListener {
        override fun onListFragmentInteraction(info: ExerciseInfo?) {
            (activity as LearnExercisesActivity).launchWebViewActivity(info)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pos = it.getInt(POSITION)
        }

        exerciseList = when (pos) {
            0 -> (activity as LearnExercisesActivity).createXCS200ExerciseList()
            1 -> (activity as LearnExercisesActivity).createXCR300ExerciseList()
            else -> null
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_videos, container, false)

        v.stickyHeaderRecyclerView.layoutManager = StickyHeaderLayoutManager()
        v.stickyHeaderRecyclerView.adapter = adapter

        exerciseList?.let {
            adapter.setSections(it)
        }

        return v
    }

    companion object {
        @JvmStatic
        fun newInstance(pos: Int) =
                LearnExercisesFragment().apply {
                    arguments = Bundle().apply {
                        putInt(POSITION, pos)
                    }
                }
    }
}
