package com.excy.excy.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.excy.excy.R;
import com.excy.excy.interfaces.OnListFragmentInteractionListener;
import com.squareup.picasso.Picasso;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erin.kelley on 10/15/17.
 */

public class StickyHeadersAdapter extends SectioningAdapter {

    private List<ExerciseTip> mValues;
    private ArrayList<Section> sections = new ArrayList<>();
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;

    public StickyHeadersAdapter(Context context, OnListFragmentInteractionListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemUserType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerUserType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_header, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex,
                                     int itemIndex, int itemUserType) {
        Section section = sections.get(sectionIndex);
        final ItemViewHolder holder = (ItemViewHolder) viewHolder;

        holder.mItem = section.tips.get(itemIndex);
        holder.mTextView.setText(holder.mItem.getTip());

        setupImageView(holder.mImageView, holder.mItem);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder,
                                       int sectionIndex, int headerUserType) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
        headerViewHolder.mHeaderView.setText(sections.get(sectionIndex).text);
    }

    @Override
    public int getNumberOfSections() {
        return sections.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        Section section = sections.get(sectionIndex);

        if (section == null) {
            return 0;
        }

        return section.tips.size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return true;
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return false;
    }

    public void setSections(List<ExerciseTip> tips) {
        mValues = tips;
        sections.clear();

        // Sort tips into buckets by exercise type
        Section currentSection = null;
        ExerciseTip.ExerciseType prevType = null;

        for (ExerciseTip tip : tips) {
            ExerciseTip.ExerciseType currentType = tip.exerciseType;
            if (currentSection == null || (currentSection != null && prevType != null
                    && !currentType.toString().equals(prevType.toString()))) {
                if (currentSection != null) {
                    sections.add(currentSection);
                }

                currentSection = new Section();
                if (currentType.toString().equals(ExerciseTip.ExerciseType.ARMS.toString())) {
                    currentSection.text = "Arm Ergonomics";
                } else {
                    currentSection.text = "Leg Ergonomics";
                }
            }

            if (currentSection != null) {
                currentSection.tips.add(tip);
            }

            prevType = currentType;
        }

        sections.add(currentSection);
        notifyAllSectionsDataSetChanged();
    }

    private void setupImageView(final ImageView imageView, ExerciseTip tip) {
        if (tip != null) {
            final int imageResId = getImageDrawableResId(tip);
            if (imageResId > 0) {
                    Picasso.with(imageView.getContext())
                            .load(imageResId)
                            .resize(1080, 540)
                            .into(imageView);
            }
        }
    }

    private int getImageDrawableResId(ExerciseTip tip) {
        int resId = 0;
        String type = tip.exerciseType.toString();
        if (type.equals(ExerciseTip.ExerciseType.ARMS.toString())) {
            switch (tip.getTipNum()) {
                case 1:
                    resId = R.drawable.arms_1;
                    break;
                case 2:
                    resId = R.drawable.arms_2;
                    break;
                case 3:
                    resId = R.drawable.arms_3;
                    break;
                case 4:
                    resId = R.drawable.arms_4;
                    break;
                case 5:
                    resId = R.drawable.arms_5;
                    break;
                case 6:
                    resId = R.drawable.arms_6;
                    break;
                case 7:
                    resId = R.drawable.arms_7;
                    break;
                case 8:
                    resId = R.drawable.arms_8;
                    break;
                case 9:
                    resId = R.drawable.arms_9;
                    break;
                case 10:
                    resId = R.drawable.arms_10;
                    break;
                case 11:
                    resId = R.drawable.arms_11;
                    break;
                case 12:
                    resId = R.drawable.arms_12;
                    break;
            }
        } else if (type.equals(ExerciseTip.ExerciseType.LEGS.toString())) {
            switch (tip.getTipNum()) {
                case 1:
                    resId = R.drawable.legs_1;
                    break;
                case 2:
                    resId = R.drawable.legs_2;
                    break;
                case 3:
                    resId = R.drawable.legs_3;
                    break;
                case 4:
                    resId = R.drawable.legs_4;
                    break;
                case 5:
                    resId = R.drawable.legs_5;
                    break;
                case 6:
                    resId = R.drawable.legs_6;
                    break;
                case 7:
                    resId = R.drawable.legs_7;
                    break;
                case 8:
                    resId = R.drawable.legs_8;
                    break;
                case 9:
                    resId = R.drawable.legs_9;
                    break;
            }
        }
        return resId;
    }

    class ItemViewHolder extends SectioningAdapter.ItemViewHolder {
        View mView;
        final TextView mTextView;
        final ImageView mImageView;
        ExerciseTip mItem;

        ItemViewHolder(View view) {
            super(view);
            mView = view;
            mTextView = (TextView) view.findViewById(R.id.tvArmsLegs);
            mImageView = (ImageView) view.findViewById(R.id.ivArmsLegs);
        }
    }

    class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder {
        TextView mHeaderView;

        HeaderViewHolder(View itemView) {
            super(itemView);
            mHeaderView = (TextView) itemView.findViewById(R.id.listSectionHeaderTextView);
        }
    }

    private class Section {
        String text;
        ArrayList<ExerciseTip> tips = new ArrayList<>();
    }
}
