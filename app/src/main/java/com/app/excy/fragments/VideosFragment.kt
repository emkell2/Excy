package com.app.excy.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.excy.R
import com.app.excy.StickyHeaderLayoutManager
import com.app.excy.activities.VideosActivity
import com.app.excy.interfaces.OnListFragmentInteractionListener
import com.app.excy.models.ExerciseInfo
import com.app.excy.models.StickyHeadersAdapter
import kotlinx.android.synthetic.main.fragment_videos.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [VideosFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */

private const val POSITION = "position"

class VideosFragment : Fragment() {
    private var pos: Int? = null
    private var videoList: ArrayList<ExerciseInfo>? = null
    val adapter = StickyHeadersAdapter(object: OnListFragmentInteractionListener {
        override fun onListFragmentInteraction(info: ExerciseInfo?) {
            (activity as VideosActivity).launchWebViewActivity(info)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pos = it.getInt(POSITION)
        }

        videoList = when (pos) {
            0 -> (activity as VideosActivity).createXCS200VideoList()
            1 -> (activity as VideosActivity).createXCR300VideoList()
            else -> null
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_videos, container, false)

        v.stickyHeaderRecyclerView.layoutManager = StickyHeaderLayoutManager()
        v.stickyHeaderRecyclerView.adapter = adapter

        videoList?.let {
            adapter.setSections(it)
        }

        return v
    }

    companion object {
        @JvmStatic
        fun newInstance(pos: Int) =
                VideosFragment().apply {
                    arguments = Bundle().apply {
                        putInt(POSITION, pos)
                    }
                }
    }
}
