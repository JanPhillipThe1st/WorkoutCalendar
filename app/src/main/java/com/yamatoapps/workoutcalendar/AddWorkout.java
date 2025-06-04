package com.yamatoapps.workoutcalendar;

import androidx.annotation.IntRange;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddWorkout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);
        Button btnSelectDate = findViewById(R.id.btnSelectDate);
        TextView tvDayCount = findViewById(R.id.tvDayCount);
        TextView tvWorkoutName = findViewById(R.id.tvWorkoutName);
        TextView tvTargetMuscle = findViewById(R.id.tvTargetMuscle);
        Button btnSaveWorkout = findViewById(R.id.btnSaveWorkout);
        final Calendar c = Calendar.getInstance();
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.

                // on below line we are getting
                // our day, month and year.
                int month1,date1,year1;
                month1 = c.get(Calendar.MONTH);
                date1 = c.get(Calendar.DAY_OF_MONTH);
                year1 = c.get(Calendar.YEAR);
                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        AddWorkout.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                c.set(Calendar.DATE,dayOfMonth);
                                c.set(Calendar.MONTH,monthOfYear);
                                c.set(Calendar.YEAR,year);
                                TimePickerDialog timePickerDialog = new TimePickerDialog(
                                        // on below line we are passing context.
                                        AddWorkout.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                                c.set(Calendar.HOUR_OF_DAY,i);
                                                c.set(Calendar.MINUTE,i1);
                                                c.set(Calendar.SECOND,0);
                                                c.set(Calendar.MILLISECOND,0);
                                                btnSelectDate.setText(c.getTime().toLocaleString());
                                            }
                                        },0,0,true);
                                // at last we are calling show to
                                // display our date picker dialog.
                                timePickerDialog.show();
                            }
                        },year1,month1,date1);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });
        btnSaveWorkout.setOnClickListener(view ->{
            ProgressDialog progressDialog = new ProgressDialog(AddWorkout.this);
            progressDialog.setTitle("Saving workout");
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, Object> map = new HashMap<>();
            map.put("date_of_workout",c.getTime());
            map.put("day_count",Integer.parseInt(tvDayCount.getText().toString()));
            map.put("workout_name",tvWorkoutName.getText().toString());
            map.put("target_muscle",tvTargetMuscle.getText().toString());
            db.collection("scheduled_workouts").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    progressDialog.dismiss();
                    Toast.makeText(AddWorkout.this,"Workout successfully added!",Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        });
    }
}