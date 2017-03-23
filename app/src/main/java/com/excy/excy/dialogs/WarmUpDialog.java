package com.excy.excy.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.excy.excy.R;

/**
 * Created by erin.kelley on 3/14/17.
 */

public class WarmUpDialog extends DialogFragment {
    public static final String WARM_UP_DIALOG = "WARM UP DIALOG";

    public static WarmUpDialog newInstance() {

        Bundle args = new Bundle();

        WarmUpDialog fragment = new WarmUpDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.warm_up_title)
                .setMessage(R.string.warm_up_message)
                .setPositiveButton(R.string.warm_up, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                        new TemperatureDialog().show(getFragmentManager(),
                                TemperatureDialog.TEMPERATURE_DIALOG);
                    }
                })
                .setNegativeButton(R.string.skip, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });

        return builder.create();
    }
}
