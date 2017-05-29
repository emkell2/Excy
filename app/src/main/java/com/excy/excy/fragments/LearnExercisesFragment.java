package com.excy.excy.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.excy.excy.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LearnExercisesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LearnExercisesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String STR_ARM_CANDY = "Arm Candy";
    public static final String STR_SUPER_CYCLE_CARDIO = "Super Cycle Cardio";
    public static final String STR_CYCLE_LEG_BLAST = "Cycle Leg Blast";
    public static final String STR_CORE_FLOOR_EXPLOSION = "Core Floor Explosion";
    public static final String STR_ARM_BLAST = "Arm Blast";
    public static final String STR_ULTIMATE_ARM_LEG_TONING = "Ultimate Arm and Leg Toning";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LearnExercisesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LearnExercisesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LearnExercisesFragment newInstance(String param1, String param2) {
        LearnExercisesFragment fragment = new LearnExercisesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learn_exercises, container, false);

        final FragmentManager fragmentManager = getFragmentManager();

        ImageButton armCandyBtn = (ImageButton) view.findViewById(R.id.ibArmCandy);
        armCandyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag = ExerciseSummaryFragment.newInstance(R.id.ibArmCandy);

                fragmentManager.beginTransaction()
                        .replace(R.id.more_fragment_container, frag, STR_ARM_CANDY)
                        .addToBackStack(STR_ARM_CANDY)
                        .commitAllowingStateLoss();
            }
        });

        ImageButton superCycleCardioBtn = (ImageButton) view.findViewById(R.id.ibSuperCycleCardio);
        superCycleCardioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag = ExerciseSummaryFragment.newInstance(R.id.ibSuperCycleCardio);

                fragmentManager.beginTransaction()
                        .replace(R.id.more_fragment_container, frag, STR_SUPER_CYCLE_CARDIO)
                        .addToBackStack(STR_SUPER_CYCLE_CARDIO)
                        .commitAllowingStateLoss();
            }
        });

        ImageButton cycleLegBlastBtn = (ImageButton) view.findViewById(R.id.ibCycleLegBlast);
        cycleLegBlastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag = ExerciseSummaryFragment.newInstance(R.id.ibCycleLegBlast);

                fragmentManager.beginTransaction()
                        .replace(R.id.more_fragment_container, frag, STR_CYCLE_LEG_BLAST)
                        .addToBackStack(STR_CYCLE_LEG_BLAST)
                        .commitAllowingStateLoss();
            }
        });

        ImageButton coreFloorExplosionBtn = (ImageButton) view.findViewById(R.id.ibCoreFloorExplosion);
        coreFloorExplosionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag = ExerciseSummaryFragment.newInstance(R.id.ibCoreFloorExplosion);

                fragmentManager.beginTransaction()
                        .replace(R.id.more_fragment_container, frag, STR_CORE_FLOOR_EXPLOSION)
                        .addToBackStack(STR_CORE_FLOOR_EXPLOSION)
                        .commitAllowingStateLoss();
            }
        });

        ImageButton armBlastBtn = (ImageButton) view.findViewById(R.id.ibArmBlast);
        armBlastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag = ExerciseSummaryFragment.newInstance(R.id.ibArmBlast);

                fragmentManager.beginTransaction()
                        .replace(R.id.more_fragment_container, frag, STR_ARM_BLAST)
                        .addToBackStack(STR_ARM_BLAST)
                        .commitAllowingStateLoss();
            }
        });

        ImageButton ultimateArmLegToningBtn = (ImageButton) view.findViewById(R.id.ibUltimateArmAndLegToning);
        ultimateArmLegToningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag = ExerciseSummaryFragment.newInstance(R.id.ibUltimateArmAndLegToning);

                fragmentManager.beginTransaction()
                        .replace(R.id.more_fragment_container, frag, STR_ULTIMATE_ARM_LEG_TONING)
                        .addToBackStack(STR_ULTIMATE_ARM_LEG_TONING)
                        .commitAllowingStateLoss();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onExerciseSelected(uri);
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
        void onExerciseSelected(Uri uri);
    }
}
