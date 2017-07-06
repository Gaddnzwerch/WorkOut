package de.gaddenzwerch.workout.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import de.gaddenzwerch.workout.Injection;
import de.gaddenzwerch.workout.R;
import de.gaddenzwerch.workout.presenter.ExerciseDetailPresenter;

public class ExerciseDetailActivity extends AppCompatActivity {

    public final static String EXTRA_EXERCISE_ID = "TASK_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        // Get the requested exercise id
        String exerciseId = getIntent().getStringExtra(EXTRA_EXERCISE_ID);

        ExerciseDetailFragment exerciseDetailFragment = (ExerciseDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if(exerciseDetailFragment == null) {
            exerciseDetailFragment = exerciseDetailFragment.newInstance(exerciseId);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    exerciseDetailFragment,
                    R.id.contentFrame);
        }

        // Create the presenter
        new ExerciseDetailPresenter(
                exerciseId,
                Injection.provideExercisesRepository(getApplicationContext()),
                exerciseDetailFragment);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
