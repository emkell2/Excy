package com.excy.excy.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.excy.excy.R;
import com.excy.excy.activities.SurveyActivity;
import com.excy.excy.utilities.WorkoutUtilities;

/**
 * Created by erin.kelley on 4/11/17.
 */

public class TrackResultsDialog extends DialogFragment {
    public static final String TRACK_RESULTS_DIALOG = "TRACK RESULTS DIALOG";
    public static final String TRACK_RESULTS_TIME_REMAINING = "TRACK RESULTS TIME REMAINING";

    public static TrackResultsDialog newInstance() {

        Bundle args = new Bundle();

        TrackResultsDialog fragment = new TrackResultsDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.track_results_title)
                .setMessage(R.string.track_results_message)
                .setPositiveButton(R.string.track, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String timeRemaining = getArguments().getString(TRACK_RESULTS_TIME_REMAINING);
                        dismiss();
                        getActivity().finish();

                        Intent intent = new Intent(getActivity(), SurveyActivity.class);
                        intent.putExtra(WorkoutUtilities.INTENT_TIME_REMAINING, timeRemaining);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });

        return builder.create();
    }
}
