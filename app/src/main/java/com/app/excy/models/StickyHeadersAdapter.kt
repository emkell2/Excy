package com.app.excy.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.app.excy.R
import com.app.excy.SectioningAdapter
import com.app.excy.interfaces.OnListFragmentInteractionListener
import com.squareup.picasso.Picasso

import java.util.ArrayList

/**
 * Created by erin.kelley on 10/15/17.
 */

class StickyHeadersAdapter(listener: OnListFragmentInteractionListener) : SectioningAdapter() {

    private var mValues: List<ExerciseVideo>? = null
    private val sections = ArrayList<Section>()
    private var listener = listener

    val WORKOUTS = "Workouts"
    val ARM_ERGONOMICS = "Arm Ergonomics"
    val LEG_ERGONOMICS = "Leg Ergonomics"

    override fun onCreateItemViewHolder(parent: ViewGroup, itemUserType: Int): ItemViewHolder? {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_list_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup, headerUserType: Int): HeaderViewHolder? {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_list_header, parent, false)
        return HeaderViewHolder(view)
    }

    override fun onCreateGhostHeaderViewHolder(parent: ViewGroup): SectioningAdapter.GhostHeaderViewHolder {
        val ghostView = View(parent.context)
        ghostView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        return SectioningAdapter.GhostHeaderViewHolder(ghostView)
    }

    override fun onBindItemViewHolder(viewHolder: SectioningAdapter.ItemViewHolder, sectionIndex: Int,
                                      itemIndex: Int, itemUserType: Int) {
        val section = sections[sectionIndex]
        val holder = viewHolder as ItemViewHolder

        holder.mItem = section.tips[itemIndex]
        holder.mTextView.text = holder.mItem!!.getDescription()

        if (section.text!! == WORKOUTS) {
            holder.playButton.visibility = View.GONE
        }

        holder.playButton.visibility = if (section.text!! == WORKOUTS) View.GONE else View.VISIBLE

        setupImageView(holder.mImageView, holder.mItem)

        holder.mView.setOnClickListener {
            listener?.onListFragmentInteraction(holder.mItem)
        }
    }

    override fun onBindHeaderViewHolder(viewHolder: SectioningAdapter.HeaderViewHolder,
                                        sectionIndex: Int, headerUserType: Int) {
        val headerViewHolder = viewHolder as HeaderViewHolder
        headerViewHolder.mHeaderView.text = sections[sectionIndex].text
    }

    override fun getNumberOfSections(): Int {
        return sections.size
    }

    override fun getNumberOfItemsInSection(sectionIndex: Int): Int {
        return sections[sectionIndex].tips.size
    }

    override fun doesSectionHaveHeader(sectionIndex: Int): Boolean {
        return true
    }

    override fun doesSectionHaveFooter(sectionIndex: Int): Boolean {
        return false
    }

    fun setSections(videos: List<ExerciseVideo>) {
        mValues = videos
        sections.clear()

        // Sort videoList into buckets by exercise type
        var currentSection: Section? = null
        var prevType: ExerciseVideo.ExerciseType? = null

        for (tip in videos) {
            val currentType = tip.exerciseType
            if (currentSection == null || (currentSection != null && prevType != null
                            && currentType.toString() != prevType.toString())) {
                if (currentSection != null) {
                    sections.add(currentSection)
                }

                currentSection = Section()
                currentSection.text = when (currentType.toString()) {
                    ExerciseVideo.ExerciseType.WORKOUT.toString() -> WORKOUTS
                    ExerciseVideo.ExerciseType.ARMS.toString() -> ARM_ERGONOMICS
                    ExerciseVideo.ExerciseType.LEGS.toString() -> LEG_ERGONOMICS
                    else -> ""
                }
            }

            currentSection?.tips?.add(tip)

            prevType = currentType
        }

        currentSection?.let { sections.add(it) }
        notifyAllSectionsDataSetChanged()
    }

    private fun setupImageView(imageView: ImageView, video: ExerciseVideo?) {
        if (video != null) {
            val imageResId = getImageDrawableResId(video)
            if (imageResId > 0) {
                Picasso.with(imageView.context)
                        .load(imageResId)
                        .resize(1080, 540)
                        .into(imageView)
            }
        }
    }

    private fun getImageDrawableResId(video: ExerciseVideo): Int {
        var resId = 0
        val type = video.exerciseType.toString()
        when (type) {
            ExerciseVideo.ExerciseType.ARMS.toString() ->
                resId = when (video.getId()) {
                    1 -> R.drawable.arms_1
                    2 -> R.drawable.arms_2
                    3 -> R.drawable.arms_3
                    4 -> R.drawable.arms_4
                    5 -> R.drawable.arms_5
                    6 -> R.drawable.arms_6
                    7 -> R.drawable.arms_7
                    8 -> R.drawable.arms_8
                    9 -> R.drawable.arms_9
                    10 -> R.drawable.arms_10
                    11 -> R.drawable.arms_11
                    12 -> R.drawable.arms_12
                    13 -> R.drawable.arms_13
                    14 -> R.drawable.arms_14
                    else -> R.drawable.arms_1
                }
            ExerciseVideo.ExerciseType.LEGS.toString() ->
                resId = when (video.getId()) {
                    1 -> R.drawable.legs_1
                    2 -> R.drawable.legs_2
                    3 -> R.drawable.legs_3
                    4 -> R.drawable.legs_4
                    5 -> R.drawable.legs_5
                    6 -> R.drawable.legs_6
                    7 -> R.drawable.legs_7
                    8 -> R.drawable.legs_8
                    9 -> R.drawable.legs_9
                    10 -> R.drawable.legs_10
                    11 -> R.drawable.legs_11
                    12 -> R.drawable.legs_12
                    13 -> R.drawable.legs_13
                    else -> R.drawable.legs_1
                }
            ExerciseVideo.ExerciseType.WORKOUT.toString() ->
                resId = when (video.getId()) {
                    1 -> R.drawable.watch_arm_candy
                    2 -> R.drawable.watch_super_cycle
                    3 -> R.drawable.watch_cycle_leg
                    4 -> R.drawable.watch_core_floor
                    5 -> R.drawable.watch_arm_blast
                    6 -> R.drawable.watch_ultimate_arm_leg
                    else -> R.drawable.watch_arm_candy
                }
        }
        return resId
    }

    inner class ItemViewHolder(var mView: View) : SectioningAdapter.ItemViewHolder(mView) {
        val mTextView: TextView
        val mImageView: ImageView
        val playButton: ImageView
        var mItem: ExerciseVideo? = null

        init {
            mTextView = mView.findViewById(R.id.tvArmsLegs)
            mImageView = mView.findViewById(R.id.ivArmsLegs)
            playButton = mView.findViewById(R.id.ivPlayVideo)
        }
    }

    inner class HeaderViewHolder(itemView: View) : SectioningAdapter.HeaderViewHolder(itemView) {
        var mHeaderView: TextView

        init {
            mHeaderView = itemView.findViewById(R.id.listSectionHeaderTextView)
        }
    }

    private inner class Section {
        internal var text: String? = null
        internal var tips = ArrayList<ExerciseVideo>()
    }
}
