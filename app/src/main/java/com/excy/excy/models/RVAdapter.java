package com.excy.excy.models;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.excy.excy.R;
import com.excy.excy.utilities.WorkoutUtilities;

import java.util.ArrayList;

import static com.excy.excy.activities.PlayActivity.getContext;

/**
 * Created by erin.kelley on 4/24/17.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    ArrayList<Workout> workouts;

    public RVAdapter(ArrayList<Workout> workoutList){
        this.workouts = workoutList;
    }

    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_card, parent, false);

        RVAdapter.ViewHolder vh = new RVAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RVAdapter.ViewHolder holder, int position) {
        holder.workoutTitle.setText(workouts.get(position).getWorkoutTitle());
        holder.workoutDate.setText(workouts.get(position).getDateCompleted());
        holder.totalTime.setText(workouts.get(position).getTotalTime());
        holder.minTemp.setText("min: " + String.valueOf(workouts.get(position).getMinTemp()));
        holder.maxTemp.setText("max: " + String.valueOf(workouts.get(position).getMaxTemp()));
        holder.caloriesBurned.setText(String.valueOf(workouts.get(position).getCaloriesBurned()));

        // Set enjoyment data
        String enjoyment = workouts.get(position).getEnjoyment();
        if (!TextUtils.isEmpty(enjoyment)) {
            holder.enjoyment.setText(enjoyment);

            switch (enjoyment) {
                case WorkoutUtilities.AMAZING:
                case WorkoutUtilities.GREAT:
                    holder.faceImage.setImageDrawable(getContext().getResources()
                            .getDrawable(R.drawable.ic_smilie_happy));
                    break;
                case WorkoutUtilities.GOOD:
                    holder.faceImage.setImageDrawable(getContext().getResources()
                            .getDrawable(R.drawable.ic_smilie_satisfied));
                    break;
                case WorkoutUtilities.BAD:
                case WorkoutUtilities.AWFUL:
                    holder.faceImage.setImageDrawable(getContext().getResources()
                            .getDrawable(R.drawable.ic_smilie_sad));
                    break;
                default:
                    holder.faceImage.setImageDrawable(getContext().getResources()
                            .getDrawable(R.drawable.ic_smilie_happy));
                    break;
            }
        }

        // Set location data
        String location = workouts.get(position).getLocation();
        if (!TextUtils.isEmpty(location)) {
            holder.location.setText(location);

            switch (location) {
                case WorkoutUtilities.AT_HOME:
                    holder.locationImage.setImageDrawable(getContext().getResources()
                            .getDrawable(R.drawable.account_home));
                    break;
                case WorkoutUtilities.AT_WORK:
                    holder.locationImage.setImageDrawable(getContext().getResources()
                            .getDrawable(R.drawable.account_work));
                    break;
                case WorkoutUtilities.TRAVELING:
                    holder.locationImage.setImageDrawable(getContext().getResources()
                            .getDrawable(R.drawable.account_traveling));
                    break;
                case WorkoutUtilities.ON_THE_GO:
                    holder.locationImage.setImageDrawable(getContext().getResources()
                            .getDrawable(R.drawable.account_on_the_go));
                    break;
                default:
                    holder.locationImage.setImageDrawable(getContext().getResources()
                            .getDrawable(R.drawable.account_home));
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView workoutTitle;
        TextView workoutDate;
        TextView totalTime;
        TextView minTemp;
        TextView maxTemp;
        TextView caloriesBurned;
        TextView enjoyment;
        TextView location;
        ImageView faceImage;
        ImageView locationImage;

        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cvWorkout);
            workoutTitle = (TextView) itemView.findViewById(R.id.tvCardWorkoutName);
            workoutDate = (TextView) itemView.findViewById(R.id.tvCardWorkoutDate);
            totalTime = (TextView) itemView.findViewById(R.id.tvWorkoutTime);
            minTemp = (TextView) itemView.findViewById(R.id.tvMin);
            maxTemp = (TextView) itemView.findViewById(R.id.tvMax);
            caloriesBurned = (TextView) itemView.findViewById(R.id.tvBurn);
            enjoyment = (TextView) itemView.findViewById(R.id.tvFace);
            location = (TextView) itemView.findViewById(R.id.tvWorkoutLocation);
            faceImage = (ImageView) itemView.findViewById(R.id.ivFace);
            locationImage = (ImageView) itemView.findViewById(R.id.ivLocation);
        }
    }
}
