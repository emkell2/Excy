package com.app.excy.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.app.excy.R;
import com.app.excy.activities.QuickStatsActivity;
import com.app.excy.activities.SurveyActivity;
import com.app.excy.utilities.AppUtilities;
import com.app.excy.utilities.WorkoutUtilities;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.app.excy.activities.SurveyActivity.SURVEY_TAG;

/**
 * Created by erin.kelley on 4/11/17.
 */

public class TrackResultsDialog extends DialogFragment {
    public static final String TRACK_RESULTS_DIALOG = "TRACK RESULTS DIALOG";

    public static TrackResultsDialog newInstance(HashMap<String, Object> workout) {
        Bundle args = new Bundle();
        args.putSerializable(WorkoutUtilities.WORKOUT_DATA, workout);

        TrackResultsDialog fragment = new TrackResultsDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final HashMap workout = (HashMap) getArguments().getSerializable(WorkoutUtilities.WORKOUT_DATA);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.track_results_title)
                .setMessage(R.string.track_results_message)
                .setPositiveButton(R.string.track, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                        getActivity().finish();

                        Intent intent = new Intent(getActivity(), SurveyActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        intent.putExtra(WorkoutUtilities.WORKOUT_DATA, workout);
                        getActivity().finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sendData(workout);
                        dismiss();
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(), QuickStatsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        return;
                    }
                });

        return builder.create();
    }

    private void sendData(HashMap<String, Object> workout) {
        String userId = workout.get("uid").toString();
        String enjoyment = "good";
        String location = "at home";
        workout.put("enjoyment", enjoyment);
        workout.put("location", location);
        DatabaseReference mDataBaseReference = FirebaseDatabase.getInstance().getReference();
        mDataBaseReference.child(AppUtilities.TABLE_NAME_WORKOUTS).child(userId).push()
                .setValue(workout, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Log.d(SURVEY_TAG, "Database Error message: " + databaseError.getMessage());
                            Log.d(SURVEY_TAG, "Database Error details : " + databaseError.getDetails());
                        }
                    }
                });
    }
}
