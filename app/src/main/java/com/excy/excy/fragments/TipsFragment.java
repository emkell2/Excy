package com.excy.excy.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.excy.excy.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TipsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TipsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TipsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TipsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TipsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TipsFragment newInstance(String param1, String param2) {
        TipsFragment fragment = new TipsFragment();
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
        View view = inflater.inflate(R.layout.fragment_tips, container, false);

        ImageView tip01 = (ImageView) view.findViewById(R.id.ivTip01);
        tip01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImagePressed(Uri.parse(getString(R.string.tip01Link)));
            }
        });

        ImageView tip02 = (ImageView) view.findViewById(R.id.ivTip02);
        tip02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImagePressed(Uri.parse(getString(R.string.tip02Link)));
            }
        });

        ImageView tip03 = (ImageView) view.findViewById(R.id.ivTip03);
        tip03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImagePressed(Uri.parse(getString(R.string.tip03Link)));
            }
        });

        ImageView tip04 = (ImageView) view.findViewById(R.id.ivTip04);
        tip04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImagePressed(Uri.parse(getString(R.string.tip04Link)));
            }
        });

        ImageView tip05 = (ImageView) view.findViewById(R.id.ivTip05);
        tip05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImagePressed(Uri.parse(getString(R.string.tip05Link)));
            }
        });

        ImageView tip06 = (ImageView) view.findViewById(R.id.ivTip06);
        tip06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImagePressed(Uri.parse(getString(R.string.tip06Link)));
            }
        });

        ImageView tip07 = (ImageView) view.findViewById(R.id.ivTip07);
        tip07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImagePressed(Uri.parse(getString(R.string.tip07Link)));
            }
        });

        ImageView tip08 = (ImageView) view.findViewById(R.id.ivTip08);
        tip08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImagePressed(Uri.parse(getString(R.string.tip08Link)));
            }
        });

        ImageView tip09 = (ImageView) view.findViewById(R.id.ivTip09);
        tip09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImagePressed(Uri.parse(getString(R.string.tip09Link)));
            }
        });

        ImageView tip10 = (ImageView) view.findViewById(R.id.ivTip10);
        tip10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImagePressed(Uri.parse(getString(R.string.tip10Link)));
            }
        });

        ImageView tip11 = (ImageView) view.findViewById(R.id.ivTip11);
        tip11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImagePressed(Uri.parse(getString(R.string.tip11Link)));
            }
        });

        ImageView tip12 = (ImageView) view.findViewById(R.id.ivTip12);
        tip12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImagePressed(Uri.parse(getString(R.string.tip12Link)));
            }
        });

        ImageView tip13 = (ImageView) view.findViewById(R.id.ivTip13);
        tip13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImagePressed(Uri.parse(getString(R.string.tip13Link)));
            }
        });

        ImageView tip14 = (ImageView) view.findViewById(R.id.ivTip14);
        tip14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImagePressed(Uri.parse(getString(R.string.tip14Link)));
            }
        });

        ImageView tip15 = (ImageView) view.findViewById(R.id.ivTip15);
        tip15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImagePressed(Uri.parse(getString(R.string.tip15Link)));
            }
        });

        ImageView tip16 = (ImageView) view.findViewById(R.id.ivTip16);
        tip16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImagePressed(Uri.parse(getString(R.string.tip16Link)));
            }
        });

        ImageView tip17 = (ImageView) view.findViewById(R.id.ivTip17);
        tip17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImagePressed(Uri.parse(getString(R.string.tip17Link)));
            }
        });

        return view;
    }

    public void onImagePressed(Uri uri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(uri);
        startActivity(intent);
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
        void onTipSelected(Uri uri);
    }
}
