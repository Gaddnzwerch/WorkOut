package de.gaddenzwerch.workout.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.gaddenzwerch.workout.R;
import de.gaddenzwerch.workout.presenter.ExercisePresenter;

public class ExerciseActivity extends AppCompatActivity  {

    private ExercisePresenter mExercisePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
    }

}
