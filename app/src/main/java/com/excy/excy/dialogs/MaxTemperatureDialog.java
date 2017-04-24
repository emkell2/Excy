package com.excy.excy.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.excy.excy.R;
import com.excy.excy.utilities.WorkoutUtilities;

import java.util.HashMap;

/**
 * Created by erin.kelley on 4/14/17.
 */

public class MaxTemperatureDialog extends DialogFragment {
    public static final String MAX_TEMP_DIALOG = "MAX TEMP DIALOG";

    public static MaxTemperatureDialog newInstance(HashMap<String, Object> workout) {
        Bundle args = new Bundle();
        args.putSerializable(WorkoutUtilities.WORKOUT_DATA, workout);

        MaxTemperatureDialog fragment = new MaxTemperatureDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final HashMap workout = (HashMap) getArguments().getSerializable(WorkoutUtilities.WORKOUT_DATA);
        final EditText input = new EditText(getActivity());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        input.setLayoutParams(params);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setGravity(Gravity.CENTER);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.max_temp_title)
                .setMessage(R.string.max_temp_message)
                .setView(input)
                .setPositiveButton(R.string.enter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int maxTemp = Integer.valueOf(input.getText().toString());
                        workout.put("maxTemp", maxTemp);
                        dismiss();
                        TrackResultsDialog.newInstance(workout).show(getFragmentManager(),
                                TrackResultsDialog.TRACK_RESULTS_DIALOG);
                    }
                })
                .setNegativeButton(R.string.skip, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                        TrackResultsDialog.newInstance(workout).show(getFragmentManager(),
                                TrackResultsDialog.TRACK_RESULTS_DIALOG);
                    }
                });

        Dialog dialog = builder.create();

        // Allows soft keyboard to show without it being clunky and taking forever to show
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        return dialog;
    }
}
