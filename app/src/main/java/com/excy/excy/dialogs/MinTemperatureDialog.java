package com.excy.excy.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.excy.excy.R;
import com.excy.excy.utilities.WorkoutUtilities;

/**
 * Created by erin.kelley on 3/14/17.
 */

public class MinTemperatureDialog extends DialogFragment {
    public static final String MIN_TEMP_DIALOG = "MIN TEMP DIALOG";
    public static final String MIN_TEMP_DIALOG_INTERVAL_ARG = "MIN TEMP DIALOG INTERVAL ARG";
    public static final String MIN_TEMP_DIALOG_INTENT_STRING = "MIN TEMP DIALOG INTENT STRING";

    public static MinTemperatureDialog newInstance(boolean setInveral, String intentString) {
        Bundle args = new Bundle();
        args.putBoolean(MIN_TEMP_DIALOG_INTERVAL_ARG, setInveral);
        args.putString(MIN_TEMP_DIALOG_INTENT_STRING, intentString);

        MinTemperatureDialog fragment = new MinTemperatureDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final boolean setInterval = getArguments().getBoolean(MIN_TEMP_DIALOG_INTERVAL_ARG);
        final String intentString = getArguments().getString(MIN_TEMP_DIALOG_INTENT_STRING);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.min_temp_title)
                .setMessage(R.string.min_temp_message)
                .setPositiveButton(R.string.enter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                        startTimer(setInterval, intentString);
                    }
                })
                .setNegativeButton(R.string.skip, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                        startTimer(setInterval, intentString);
                    }
                });

        return builder.create();
    }

    private void startTimer(boolean setInterval, String intentString) {
        Intent intent = new Intent(intentString);
        intent.putExtra(WorkoutUtilities.INTENT_SET_INTERVAL, setInterval);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }
}
