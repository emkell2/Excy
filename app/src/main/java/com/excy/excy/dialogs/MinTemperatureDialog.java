package com.excy.excy.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.InputType;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.excy.excy.R;
import com.excy.excy.utilities.WorkoutUtilities;

import java.util.HashMap;

/**
 * Created by erin.kelley on 3/14/17.
 */

public class MinTemperatureDialog extends DialogFragment {
    public static final String MIN_TEMP_DIALOG = "MIN TEMP DIALOG";
    public static final String MIN_TEMP_DIALOG_INTERVAL_ARG = "MIN TEMP DIALOG INTERVAL ARG";
    public static final String MIN_TEMP_DIALOG_INTENT_STRING = "MIN TEMP DIALOG INTENT STRING";

    public static MinTemperatureDialog newInstance(boolean setInveral, String intentString,
                                                   HashMap<String, Object> workout) {
        Bundle args = new Bundle();
        args.putBoolean(MIN_TEMP_DIALOG_INTERVAL_ARG, setInveral);
        args.putString(MIN_TEMP_DIALOG_INTENT_STRING, intentString);
        args.putSerializable(WorkoutUtilities.WORKOUT_DATA, workout);

        MinTemperatureDialog fragment = new MinTemperatureDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final boolean setInterval = getArguments().getBoolean(MIN_TEMP_DIALOG_INTERVAL_ARG);
        final String intentString = getArguments().getString(MIN_TEMP_DIALOG_INTENT_STRING);
        final HashMap map = (HashMap) getArguments().getSerializable(WorkoutUtilities.WORKOUT_DATA);

        final EditText input = new EditText(getActivity());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        input.setLayoutParams(params);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setGravity(Gravity.CENTER);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.min_temp_title)
                .setMessage(R.string.min_temp_message)
                .setView(input)
                .setPositiveButton(R.string.enter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String minTemp = input.getText().toString();
                        map.put("minTemp", minTemp);
                        dismiss();
                        startTimer(setInterval, intentString, map);
                    }
                })
                .setNegativeButton(R.string.skip, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                        startTimer(setInterval, intentString, map);
                    }
                });

        Dialog dialog = builder.create();

        // Allows soft keyboard to show without it being clunky and taking forever to show
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        return dialog;
    }

    private void startTimer(boolean setInterval, String intentString, HashMap<String, Object> workout) {
        Intent intent = new Intent(intentString);
        intent.putExtra(WorkoutUtilities.INTENT_SET_INTERVAL, setInterval);
        intent.putExtra(WorkoutUtilities.WORKOUT_DATA, workout);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }
}
