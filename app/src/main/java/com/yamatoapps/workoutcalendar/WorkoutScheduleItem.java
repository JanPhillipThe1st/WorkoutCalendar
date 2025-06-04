package com.yamatoapps.workoutcalendar;

import java.util.Date;

public class WorkoutScheduleItem {
    public Date date_of_workout;
    public int day_count;
    public String workout_name;
    public String target_muscle;
    public String document_id = "";


    public WorkoutScheduleItem(Date date_of_workout, int day_count, String workout_name, String target_muscle, String document_id) {
        this.date_of_workout = date_of_workout;
        this.day_count = day_count;
        this.workout_name = workout_name;
        this.target_muscle = target_muscle;
        this.document_id = document_id;
    }
}
