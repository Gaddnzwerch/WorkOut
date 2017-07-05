package de.gaddenzwerch.workout.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.gaddenzwerch.workout.R;

public class ExerciseDetailActivity extends AppCompatActivity {

    public final static String EXTRA_EXERCISE_ID = "TASK_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);
    }
}
