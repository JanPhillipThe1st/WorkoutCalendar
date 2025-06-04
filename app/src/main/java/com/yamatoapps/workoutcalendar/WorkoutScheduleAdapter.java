package com.yamatoapps.workoutcalendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;

public class WorkoutScheduleAdapter extends ArrayAdapter<WorkoutScheduleItem> {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public WorkoutScheduleAdapter(@NonNull Context context,  ArrayList<WorkoutScheduleItem> objects) {
        super(context, R.layout.workout_schedule_item, objects);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        WorkoutScheduleItem item = getItem(position);
       if (convertView == null){
           convertView  = LayoutInflater.from(getContext()).inflate(R.layout.workout_schedule_item, parent, false);
       }
       //Bind views here...
        TextView tvDayOfWeek,tvDayCount,tvWorkoutName,tvTargetMuscle;
       Button btnDelete,btnEdit;
       btnDelete = convertView.findViewById(R.id.btnDelete);
       btnEdit = convertView.findViewById(R.id.btnEdit);
       btnEdit.setOnClickListener(view ->{

       });
        btnDelete.setOnClickListener(view -> {
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(parent.getContext());
            alertDialogBuilder.setTitle("Delete Workout");
            alertDialogBuilder.setMessage("Are you sure you want to delete this workout?");
            alertDialogBuilder.setPositiveButton("NO", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            alertDialogBuilder.setNegativeButton("YES", (dialogInterface, i) -> {

                MaterialAlertDialogBuilder deleteDialogBuilder = new MaterialAlertDialogBuilder(parent.getContext());
                deleteDialogBuilder.setTitle("Delete success");
                deleteDialogBuilder.setMessage("Workout deleted successfully!");
                deleteDialogBuilder.setPositiveButton("OK", (deleteDialogBuilderDialogInterface,j)->{
                    deleteDialogBuilderDialogInterface.dismiss();
                    Activity context = (Activity) parent.getContext();
                });
                db.collection("scheduled_workouts").document(item.document_id).delete().addOnSuccessListener(unused -> {
                    deleteDialogBuilder.create().show();
                    dialogInterface.dismiss();
                });
            });
            alertDialogBuilder.create().show();
        });

        btnEdit.setOnClickListener(view -> {
            Intent intent = new Intent(parent.getContext(), EditWorkout.class);
            intent.putExtra("document_id",item.document_id);
            parent.getContext().startActivity(intent);
        });
       tvDayOfWeek = convertView.findViewById(R.id.tvDayOfWeek);
       tvDayCount = convertView.findViewById(R.id.tvDayCount);
       tvWorkoutName = convertView.findViewById(R.id.tvWorkoutName);
       tvTargetMuscle = convertView.findViewById(R.id.tvTargetMuscle);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(item.date_of_workout);
       tvDayOfWeek.setText(String.valueOf(item.date_of_workout.toLocaleString()));
       tvDayCount.setText("Day "+String.valueOf(item.day_count));
       tvWorkoutName.setText(String.valueOf(item.workout_name));
       tvTargetMuscle.setText(String.valueOf(item.target_muscle));
        return convertView;

    }
}
