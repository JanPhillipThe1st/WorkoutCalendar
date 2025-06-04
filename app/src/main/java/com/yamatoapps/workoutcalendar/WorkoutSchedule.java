package com.yamatoapps.workoutcalendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

public class WorkoutSchedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_schedule);
        Button btnAddWorkoutSchedule = findViewById(R.id.btnAddWorkoutSchedule);
        ArrayList<WorkoutScheduleItem> items = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        WorkoutScheduleAdapter adapter = new WorkoutScheduleAdapter(this, items);

       db.collection("scheduled_workouts").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent( QuerySnapshot value,  FirebaseFirestoreException error) {
                for (DocumentSnapshot document :
                        value.getDocuments()) {
                    adapter.add(new WorkoutScheduleItem(
                            document.getDate("date_of_workout", DocumentSnapshot.ServerTimestampBehavior.ESTIMATE),
                            Integer.parseInt (document.get("day_count").toString()),
                            document.getString("workout_name"),document.getString("target_muscle"),
                            document.getId()
                    ));
                }
            }
        });
        btnAddWorkoutSchedule.setOnClickListener(view -> {
            startActivity(new Intent(WorkoutSchedule.this, AddWorkout.class));
        });
        ListView lvWorkouts = findViewById(R.id.lvWorkouts);
        lvWorkouts.setAdapter(adapter);
    }
}