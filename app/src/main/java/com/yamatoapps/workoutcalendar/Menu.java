package com.yamatoapps.workoutcalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button btnWorkout,btnWorkoutCalendar,btnMotivations;
        btnWorkout = findViewById(R.id.btnWorkout);
        btnWorkoutCalendar = findViewById(R.id.btnWorkoutCalendar);
        btnMotivations = findViewById(R.id.btnMotivations);
        btnWorkoutCalendar.setOnClickListener(view -> {
            startActivity(new Intent(Menu.this, WorkoutSchedule.class));
        });
    }
}