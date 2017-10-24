package com.excy.excy.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.excy.excy.R;
import com.excy.excy.activities.ErgonomicsActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoreBaseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoreBaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoreBaseFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    public static final String EXCY_LIVE_WEBSITE = "http://www.excy.live";

    public MoreBaseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 String.
     * @return A new instance of fragment MoreBaseFragment.
     */
    public static MoreBaseFragment newInstance(String param1) {
        MoreBaseFragment fragment = new MoreBaseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_base, container, false);

        final FragmentManager fragmentManager = getFragmentManager();

        ImageButton liveBtn = (ImageButton) view.findViewById(R.id.ibLive);
        liveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse(EXCY_LIVE_WEBSITE);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

        ImageButton learnExercisesBtn = (ImageButton) view.findViewById(R.id.ibLearnExercises);
        learnExercisesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Fragment frag = new ErgonomicsActivity();
//                String str = getResources().getString(R.string.learn_exercises);
//
//                fragmentManager.beginTransaction()
//                        .replace(R.id.more_fragment_container, frag, str)
//                        .addToBackStack(str)
//                        .commitAllowingStateLoss();
                Intent intent = new Intent(getContext(), ErgonomicsActivity.class);
                startActivity(intent);
            }
        });

        ImageButton watchWorkoutsBtn = (ImageButton) view.findViewById(R.id.ibVideos);
        watchWorkoutsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag = new WatchWorkoutsFragment();
                String str = getResources().getString(R.string.watch_workouts);

                fragmentManager.beginTransaction()
                        .replace(R.id.more_fragment_container, frag, str)
                        .addToBackStack(str)
                        .commitAllowingStateLoss();
            }
        });

        ImageButton knowZonesBtn = (ImageButton) view.findViewById(R.id.ibKnowZones);
        knowZonesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag = new KnowZonesFragment();
                String str = getResources().getString(R.string.know_zones);

                fragmentManager.beginTransaction()
                        .replace(R.id.more_fragment_container, frag, str)
                        .addToBackStack(str)
                        .commitAllowingStateLoss();
            }
        });

        ImageButton tipsBtn = (ImageButton) view.findViewById(R.id.ibTips);
        tipsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag = new TipsFragment();
                String str = getResources().getString(R.string.tips);

                fragmentManager.beginTransaction()
                        .replace(R.id.more_fragment_container, frag, str)
                        .addToBackStack(str)
                        .commitAllowingStateLoss();
            }
        });

        /* Set up excy link view  */
        TextView excyLinkTV = (TextView) view.findViewById(R.id.tvLink);
        excyLinkTV.setMovementMethod(LinkMovementMethod.getInstance());

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onBaseFragCreated(uri);
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
        void onBaseFragCreated(Uri uri);
    }
}
