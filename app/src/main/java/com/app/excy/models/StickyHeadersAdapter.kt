package com.app.excy.models

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.app.excy.R
import com.app.excy.SectioningAdapter
import com.app.excy.interfaces.OnListFragmentInteractionListener
import com.squareup.picasso.Picasso
import java.util.*



/**
 * Created by erin.kelley on 10/15/17.
 */

class StickyHeadersAdapter(private var listener: OnListFragmentInteractionListener) : SectioningAdapter() {

    private var mValues: List<ExerciseInfo>? = null
    private val sections = ArrayList<Section>()

    val WORKOUTS = "Workouts"
    val ARM_ERGONOMICS = "Arm Ergonomics"
    val LEG_ERGONOMICS = "Leg Ergonomics"
    val LOWER_BODY = "Lower Body"
    val UPPER_BODY = "Upper Body"
    val TOTAL_BODY = "Total Body"

    val videoWidth = 1080
    val videoHeight = 540
    val learnWidth = 600
    val learnHeight = 600

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

        holder.mItem = section.info[itemIndex]
        holder.mTextView.text = holder.mItem!!.getDescription()
        holder.mTextView.gravity = when (section.text!!) {
            WORKOUTS, ARM_ERGONOMICS, LEG_ERGONOMICS -> Gravity.NO_GRAVITY
            LOWER_BODY, UPPER_BODY, TOTAL_BODY -> Gravity.CENTER
            else -> Gravity.NO_GRAVITY
        }

        holder.playButton.visibility = when (section.text!!) {
            WORKOUTS, LOWER_BODY, UPPER_BODY, TOTAL_BODY -> View.GONE
            else -> View.VISIBLE
        }

        if (section.info[itemIndex].id != -1) {
            setupImageView(holder.mImageView, holder.mItem)
            holder.mTextView.setTextColor(Color.parseColor("#6C6868"))
            holder.mTextView.textSize = 18.0f
            holder.mTextView.typeface = Typeface.DEFAULT_BOLD
            holder.mTextView.paintFlags = 0
        } else {
            holder.mImageView.setImageDrawable(null)
            holder.mTextView.setTextColor(Color.BLUE)
            holder.mTextView.textSize = 15.0f
            holder.mTextView.setTypeface(null, Typeface.ITALIC);
            holder.mTextView.paintFlags = holder.mTextView.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG
        }

        holder.divider.visibility = if (itemIndex != (section.info.size - 1)) View.VISIBLE else View.GONE

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
        return sections[sectionIndex].info.size
    }

    override fun doesSectionHaveHeader(sectionIndex: Int): Boolean {
        return true
    }

    override fun doesSectionHaveFooter(sectionIndex: Int): Boolean {
        return false
    }

    fun setSections(infos: List<ExerciseInfo>) {
        mValues = infos
        sections.clear()

        // Sort videoList into buckets by exercise type
        var currentSection: Section? = null
        var prevType: ExerciseInfo.ExerciseType? = null

        for (info in infos) {
            val currentType = info.exerciseType
            if (currentSection == null || (currentSection != null && prevType != null
                            && currentType.toString() != prevType.toString())) {
                if (currentSection != null) {
                    sections.add(currentSection)
                }

                currentSection = Section()
                currentSection.text = when (currentType.toString()) {
                    ExerciseInfo.ExerciseType.WORKOUT.toString() -> WORKOUTS
                    ExerciseInfo.ExerciseType.ARMS.toString() -> ARM_ERGONOMICS
                    ExerciseInfo.ExerciseType.LEGS.toString() -> LEG_ERGONOMICS
                    ExerciseInfo.ExerciseType.LOWER_BODY.toString() -> LOWER_BODY
                    ExerciseInfo.ExerciseType.UPPER_BODY.toString() -> UPPER_BODY
                    ExerciseInfo.ExerciseType.TOTAL_BODY.toString() -> TOTAL_BODY
                    else -> ""
                }
            }

            currentSection.info.add(info)

            prevType = currentType
        }

        currentSection?.let { sections.add(it) }
        notifyAllSectionsDataSetChanged()
    }

    private fun setupImageView(imageView: ImageView, info: ExerciseInfo?) {
        if (info != null) {
            val imageResId = getImageDrawableResId(info)
            val width = when (info.exerciseType) {
                ExerciseInfo.ExerciseType.WORKOUT, ExerciseInfo.ExerciseType.ARMS, ExerciseInfo.ExerciseType.LEGS -> videoWidth
                ExerciseInfo.ExerciseType.UPPER_BODY, ExerciseInfo.ExerciseType.LOWER_BODY, ExerciseInfo.ExerciseType.TOTAL_BODY -> learnWidth
            }
            val height = when (info.exerciseType) {
                ExerciseInfo.ExerciseType.WORKOUT, ExerciseInfo.ExerciseType.ARMS, ExerciseInfo.ExerciseType.LEGS -> videoHeight
                ExerciseInfo.ExerciseType.UPPER_BODY, ExerciseInfo.ExerciseType.LOWER_BODY, ExerciseInfo.ExerciseType.TOTAL_BODY -> learnHeight
            }
            if (imageResId > 0) {
                Picasso.with(imageView.context)
                        .load(imageResId)
                        .resize(width, height)
                        .into(imageView)
            }
        }
    }

    private fun getImageDrawableResId(info: ExerciseInfo): Int {
        var resId = 0
        val type = info.exerciseType.toString()
        when (type) {
            ExerciseInfo.ExerciseType.ARMS.toString() ->
                resId = when (info.getId()) {
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
            ExerciseInfo.ExerciseType.LEGS.toString() ->
                resId = when (info.getId()) {
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
            ExerciseInfo.ExerciseType.WORKOUT.toString() ->
                resId = when (info.getId()) {
                    1 -> R.drawable.watch_arm_candy
                    2 -> R.drawable.watch_super_cycle
                    3 -> R.drawable.watch_cycle_leg
                    4 -> R.drawable.watch_core_floor
                    5 -> R.drawable.watch_arm_blast
                    6 -> R.drawable.watch_ultimate_arm_leg
                    else -> R.drawable.watch_arm_candy
                }
            ExerciseInfo.ExerciseType.LOWER_BODY.toString() ->
                resId = when (info.getId()) {
                    1 -> R.drawable.lower_body1
                    2 -> R.drawable.lower_body2
                    3 -> R.drawable.lower_body3
                    4 -> R.drawable.lower_body4
                    5 -> R.drawable.lower_body5
                    6 -> R.drawable.lower_body6
                    else -> R.drawable.lower_body1
                }
            ExerciseInfo.ExerciseType.UPPER_BODY.toString() ->
                resId = when (info.getId()) {
                    1 -> R.drawable.upper_body1
                    2 -> R.drawable.upper_body2
                    3 -> R.drawable.upper_body3
                    4 -> R.drawable.upper_body4
                    5 -> R.drawable.upper_body5
                    6 -> R.drawable.upper_body6
                    7 -> R.drawable.upper_body7
                    8 -> R.drawable.upper_body8
                    else -> R.drawable.upper_body1
                }
            ExerciseInfo.ExerciseType.TOTAL_BODY.toString() ->
                resId = when (info.getId()) {
                    1 -> R.drawable.total_body1
                    2 -> R.drawable.total_body2
                    3 -> R.drawable.total_body3
                    4 -> R.drawable.total_body4
                    5 -> R.drawable.total_body5
                    6 -> R.drawable.total_body6
                    7 -> R.drawable.total_body7
                    8 -> R.drawable.total_body8
                    else -> R.drawable.total_body1
                }
        }
        return resId
    }

    inner class ItemViewHolder(var mView: View) : SectioningAdapter.ItemViewHolder(mView) {
        val mTextView: TextView
        val mImageView: ImageView
        val playButton: ImageView
        val divider: View
        var mItem: ExerciseInfo? = null

        init {
            mTextView = mView.findViewById(R.id.description)
            mImageView = mView.findViewById(R.id.recyclerImage)
            playButton = mView.findViewById(R.id.ivPlayVideo)
            divider = mView.findViewById(R.id.divider)
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
        internal var info = ArrayList<ExerciseInfo>()
    }
}
