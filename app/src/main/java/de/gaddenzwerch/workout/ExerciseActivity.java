package de.gaddenzwerch.workout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ExerciseActivity extends AppCompatActivity {

    private TextView mTitle;
    protected static final int EXERCISE_SUCCESSFUL = 1;
    protected static final int EXERCISE_FAILED = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Intent intent = getIntent();
        String lWorkoutTitle = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        mTitle = (TextView) findViewById(R.id.tvExerciseTitle);
        mTitle.setText(lWorkoutTitle);

        setResult(EXERCISE_FAILED);
    }

    public void finishedExcerciseSuccessfull(View view){
        setResult(EXERCISE_SUCCESSFUL);
        finish();
    }

    public void finishedExcerciseNotSuccessfull(View view){
        setResult(EXERCISE_FAILED);
        finish();
    }
}
