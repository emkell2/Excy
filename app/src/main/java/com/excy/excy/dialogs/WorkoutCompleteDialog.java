package com.excy.excy.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.excy.excy.utilities.WorkoutUtilities;

import java.util.HashMap;

/**
 * Created by erin.kelley on 4/30/17.
 */

public class WorkoutCompleteDialog extends DialogFragment{
    public static final String WORKOUT_COMPLETE_DIALOG = "WORKOUT COMPLETE DIALOG";
    public static final String WORKOUT_COMPLETE_DIALOG_DONE = "WORKOUT COMPLETE DIALOG DONE";

    public static WorkoutCompleteDialog newInstance(HashMap<String, Object> workout,
                                                    boolean workoutComplete) {
        Bundle args = new Bundle();
        args.putSerializable(WorkoutUtilities.WORKOUT_DATA, workout);
        args.putBoolean(WORKOUT_COMPLETE_DIALOG_DONE, workoutComplete);

        WorkoutCompleteDialog fragment = new WorkoutCompleteDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final HashMap workout = (HashMap) getArguments().getSerializable(WorkoutUtilities.WORKOUT_DATA);
        boolean workoutComplete = getArguments().getBoolean(WORKOUT_COMPLETE_DIALOG_DONE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Workout Complete");
        if (workoutComplete) {
            final CharSequence[] items = {"Save", "Trash and exit"};
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Save")) {
                        TrackResultsDialog.newInstance(workout).show(getFragmentManager(),
                                TrackResultsDialog.TRACK_RESULTS_DIALOG);
                    } else if (items[item].equals("Trash and exit")) {
                        getActivity().finish();
                        return;
                    }
                }
            });
        } else {
            final CharSequence[] items = {"Save", "Trash and Exit", "Resume Workout"};
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Save")) {
                        TrackResultsDialog.newInstance(workout).show(getFragmentManager(),
                                TrackResultsDialog.TRACK_RESULTS_DIALOG);
                    } else if (items[item].equals("Trash and exit")) {
                        getActivity().finish();
                        return;
                    } else if (items[item].equals("Resume Workout")) {
                        dialog.dismiss();
                    }
                }
            });
        }


        return builder.create();
    }
}
