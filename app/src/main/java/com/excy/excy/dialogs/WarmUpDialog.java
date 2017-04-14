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

public class WarmUpDialog extends DialogFragment {
    public static final String WARM_UP_DIALOG = "WARM UP DIALOG";
    public static final String WARM_UP_DIALOG_INTERVAL_ARG = "WARM UP DIALOG INTERVAL ARG";

    public static WarmUpDialog newInstance(boolean setCurrentInterval) {

        Bundle args = new Bundle();
        args.putBoolean(WARM_UP_DIALOG_INTERVAL_ARG, setCurrentInterval);

        WarmUpDialog fragment = new WarmUpDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final boolean setInterval = getArguments().getBoolean(WARM_UP_DIALOG_INTERVAL_ARG);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.warm_up_title)
                .setMessage(R.string.warm_up_message)
                .setPositiveButton(R.string.warm_up, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                        MinTemperatureDialog.newInstance(setInterval).show(getFragmentManager(),
                                MinTemperatureDialog.MIN_TEMP_DIALOG);
                    }
                })
                .setNegativeButton(R.string.skip, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                        startTimer(setInterval);
                    }
                });

        return builder.create();
    }

    private void startTimer(boolean setInterval) {
        Intent intent = new Intent(WorkoutUtilities.INTENT_START_TIMER);
        intent.putExtra(WorkoutUtilities.INTENT_SET_INTERVAL, setInterval);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }
}
