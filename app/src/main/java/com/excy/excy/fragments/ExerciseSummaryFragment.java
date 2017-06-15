package com.excy.excy.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.excy.excy.R;
import com.excy.excy.activities.WorkoutActivity;
import com.excy.excy.utilities.WorkoutUtilities;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExerciseSummaryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExerciseSummaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseSummaryFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RES_ID = "resId";

    private int mResId;
    private int audioResId;
    private long timeInMillis;

    private OnFragmentInteractionListener mListener;

    public ExerciseSummaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param resId integer layout resource id.
     * @return A new instance of fragment ExerciseSummaryFragment.
     */
    public static ExerciseSummaryFragment newInstance(int resId) {
        ExerciseSummaryFragment fragment = new ExerciseSummaryFragment();
        Bundle args = new Bundle();
        args.putInt(RES_ID, resId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mResId = getArguments().getInt(RES_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_summary, container, false);

        if (mResId > 0) {
            ImageView workoutImage = (ImageView) view.findViewById(R.id.ivWorkoutImage);
            ImageView workoutButtons = (ImageView) view.findViewById(R.id.ivWorkoutButtons);
            ImageView workoutSummary = (ImageView) view.findViewById(R.id.ivWorkoutSummary);
            ImageView workoutGraph = (ImageView) view.findViewById(R.id.ivWorkoutGraph);

            switch (mResId) {
                case R.id.ibArmCandy:
                    workoutImage.setImageResource(R.drawable.wb_arm_candy);
                    workoutButtons.setImageResource(R.drawable.arm_candy_description_btns);
                    workoutSummary.setImageResource(R.drawable.arm_candy_description_summary);
                    workoutGraph.setImageResource(R.drawable.pz_arm_candy_graph);

                    timeInMillis = WorkoutUtilities.armCandyTimeMS;
                    audioResId = R.raw.arm_candy;
                    break;
                case R.id.ibSuperCycleCardio:
                    workoutImage.setImageResource(R.drawable.wb_super_cycle_cardio);
                    workoutButtons.setImageResource(R.drawable.super_cardio_description_btns);
                    workoutSummary.setImageResource(R.drawable.super_cardio_description_summary);
                    workoutGraph.setImageResource(R.drawable.pz_super_cycle_graph);

                    timeInMillis = WorkoutUtilities.superCycleCardioTimeMS;
                    audioResId = R.raw.super_cycle;
                    break;
                case R.id.ibCycleLegBlast:
                    workoutImage.setImageResource(R.drawable.wb_cycle_leg_blast);
                    workoutButtons.setImageResource(R.drawable.cycle_blast_description_btns);
                    workoutSummary.setImageResource(R.drawable.cycle_blast_description_summary);
                    workoutGraph.setImageResource(R.drawable.pz_cycle_leg_blast_graph);

                    timeInMillis = WorkoutUtilities.cycleLegBlastTimeMS;
                    audioResId = R.raw.leg_blast;
                    break;
                case R.id.ibCoreFloorExplosion:
                    workoutImage.setImageResource(R.drawable.wb_core_floor_explosion);
                    workoutButtons.setImageResource(R.drawable.core_floor_description_btns);
                    workoutSummary.setImageResource(R.drawable.core_floor_description_summary);
                    workoutGraph.setImageResource(R.drawable.pz_core_floor_explosion_graph);

                    timeInMillis = WorkoutUtilities.coreFloorExplosionTimeMS;
                    audioResId = R.raw.core_floor;
                    break;
                case R.id.ibArmBlast:
                    workoutImage.setImageResource(R.drawable.wb_arm_blast);
                    workoutButtons.setImageResource(R.drawable.arm_blast_description_btns);
                    workoutSummary.setImageResource(R.drawable.arm_blast_description_summary);
                    workoutGraph.setImageResource(R.drawable.pz_arm_blast_graph);

                    timeInMillis = WorkoutUtilities.armBlastTimeMS;
                    audioResId = R.raw.arm_blast;
                    break;
                case R.id.ibUltimateArmAndLegToning:
                    workoutImage.setImageResource(R.drawable.wb_ultimate_arm_leg_tone);
                    workoutButtons.setImageResource(R.drawable.arm_and_leg_description_btns);
                    workoutSummary.setImageResource(R.drawable.arm_leg_description_summary);
                    workoutGraph.setImageResource(R.drawable.pz_ultimate_arm_and_leg_toning_graph);

                    timeInMillis = WorkoutUtilities.ultimateArmAndLegTimeMS;
                    audioResId = R.raw.ultimate_arm_leg;
                    break;
            }
        }

        Button startBtn = (Button) view.findViewById(R.id.btnStart);
        startBtn.getBackground().setColorFilter(getResources().getColor(R.color.colorExcyGreen),
                PorterDuff.Mode.MULTIPLY);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WorkoutActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                intent.putExtra(WorkoutUtilities.WORKOUT_DATA_RES_ID, mResId);
                intent.putExtra(WorkoutUtilities.WORKOUT_DATA_TIME_MILLIS, timeInMillis);
                intent.putExtra(WorkoutUtilities.WORKOUT_DATA_AUDIO_RES_ID, audioResId);
                startActivity(intent);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onSummaryRead(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSummaryRead(Uri uri);
    }
}
