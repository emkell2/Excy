package com.app.excy.models

import android.content.Context
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

    private var mValues: List<ExerciseTip>? = null
    private val sections = ArrayList<Section>()
    private var listener = listener

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
        holder.mTextView.text = holder.mItem!!.getTip()

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
        val section = sections[sectionIndex] ?: return 0

        return section.tips.size
    }

    override fun doesSectionHaveHeader(sectionIndex: Int): Boolean {
        return true
    }

    override fun doesSectionHaveFooter(sectionIndex: Int): Boolean {
        return false
    }

    fun setSections(tips: List<ExerciseTip>) {
        mValues = tips
        sections.clear()

        // Sort tips into buckets by exercise type
        var currentSection: Section? = null
        var prevType: ExerciseTip.ExerciseType? = null

        for (tip in tips) {
            val currentType = tip.exerciseType
            if (currentSection == null || (currentSection != null && prevType != null
                            && currentType.toString() != prevType.toString())) {
                if (currentSection != null) {
                    sections.add(currentSection)
                }

                currentSection = Section()
                if (currentType.toString() == ExerciseTip.ExerciseType.ARMS.toString()) {
                    currentSection.text = "Arm Ergonomics"
                } else {
                    currentSection.text = "Leg Ergonomics"
                }
            }

            currentSection?.tips?.add(tip)

            prevType = currentType
        }

        currentSection?.let { sections.add(it) }
        notifyAllSectionsDataSetChanged()
    }

    private fun setupImageView(imageView: ImageView, tip: ExerciseTip?) {
        if (tip != null) {
            val imageResId = getImageDrawableResId(tip)
            if (imageResId > 0) {
                Picasso.with(imageView.context)
                        .load(imageResId)
                        .resize(1080, 540)
                        .into(imageView)
            }
        }
    }

    private fun getImageDrawableResId(tip: ExerciseTip): Int {
        var resId = 0
        val type = tip.exerciseType.toString()
        if (type == ExerciseTip.ExerciseType.ARMS.toString()) {
            when (tip.getTipNum()) {
                1 -> resId = R.drawable.arms_1
                2 -> resId = R.drawable.arms_2
                3 -> resId = R.drawable.arms_3
                4 -> resId = R.drawable.arms_4
                5 -> resId = R.drawable.arms_5
                6 -> resId = R.drawable.arms_6
                7 -> resId = R.drawable.arms_7
                8 -> resId = R.drawable.arms_8
                9 -> resId = R.drawable.arms_9
                10 -> resId = R.drawable.arms_10
                11 -> resId = R.drawable.arms_11
                12 -> resId = R.drawable.arms_12
            }
        } else if (type == ExerciseTip.ExerciseType.LEGS.toString()) {
            when (tip.getTipNum()) {
                1 -> resId = R.drawable.legs_1
                2 -> resId = R.drawable.legs_2
                3 -> resId = R.drawable.legs_3
                4 -> resId = R.drawable.legs_4
                5 -> resId = R.drawable.legs_5
                6 -> resId = R.drawable.legs_6
                7 -> resId = R.drawable.legs_7
                8 -> resId = R.drawable.legs_8
                9 -> resId = R.drawable.legs_9
            }
        }
        return resId
    }

    inner class ItemViewHolder(var mView: View) : SectioningAdapter.ItemViewHolder(mView) {
        val mTextView: TextView
        val mImageView: ImageView
        var mItem: ExerciseTip? = null

        init {
            mTextView = mView.findViewById<View>(R.id.tvArmsLegs) as TextView
            mImageView = mView.findViewById<View>(R.id.ivArmsLegs) as ImageView
        }
    }

    inner class HeaderViewHolder(itemView: View) : SectioningAdapter.HeaderViewHolder(itemView) {
        var mHeaderView: TextView

        init {
            mHeaderView = itemView.findViewById<View>(R.id.listSectionHeaderTextView) as TextView
        }
    }

    private inner class Section {
        internal var text: String? = null
        internal var tips = ArrayList<ExerciseTip>()
    }
}
