package com.app.excy.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.app.excy.R;
import com.app.excy.utilities.WorkoutUtilities;

import java.util.HashMap;

/**
 * Created by erin.kelley on 3/14/17.
 */

public class WarmUpDialog extends DialogFragment {
    public static final String WARM_UP_DIALOG = "WARM UP DIALOG";
    public static final String WARM_UP_DIALOG_INTERVAL_ARG = "WARM UP DIALOG INTERVAL ARG";
    public static final String WARM_UP_DIALOG_INTENT_STRING = "WARM UP DIALOG INTENT STRING";

    public static WarmUpDialog newInstance(boolean setCurrentInterval, String intentString,
                                           HashMap workout) {

        Bundle args = new Bundle();
        args.putBoolean(WARM_UP_DIALOG_INTERVAL_ARG, setCurrentInterval);
        args.putString(WARM_UP_DIALOG_INTENT_STRING, intentString);
        args.putSerializable(WorkoutUtilities.WORKOUT_DATA, workout);

        WarmUpDialog fragment = new WarmUpDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final boolean setInterval = getArguments().getBoolean(WARM_UP_DIALOG_INTERVAL_ARG);
        final String intentString = getArguments().getString(WARM_UP_DIALOG_INTENT_STRING);
        final HashMap map = (HashMap) getArguments().getSerializable(WorkoutUtilities.WORKOUT_DATA);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.warm_up_title)
                .setMessage(R.string.warm_up_message)
                .setPositiveButton(R.string.warm_up, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                        MinTemperatureDialog.newInstance(setInterval, intentString, true, map)
                                .show(getFragmentManager(), MinTemperatureDialog.MIN_TEMP_DIALOG);
                    }
                })
                .setNegativeButton(R.string.skip, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                        MinTemperatureDialog.newInstance(setInterval, intentString, false, map)
                                .show(getFragmentManager(), MinTemperatureDialog.MIN_TEMP_DIALOG);
                    }
                });

        return builder.create();
    }
}
