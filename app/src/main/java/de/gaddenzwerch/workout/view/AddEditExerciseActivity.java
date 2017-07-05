package de.gaddenzwerch.workout.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import de.gaddenzwerch.workout.R;
import de.gaddenzwerch.workout.presenter.AddEditExercisePresenter;

/**
 * Displays an add or edit exercise screen
 */
public class AddEditExerciseActivity extends AppCompatActivity {
    public static final int REQUEST_ADD_TASK = 1;
    public static final String SHOULD_LOAD_DATA_FROM_REPO_KEY = "SHOULD_LOAD_DATA_FROM_REPO_KEY";

    private AddEditExercisePresenter mAddEditExercisePresenter;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_exercise);

        // Set up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);

        AddEditExerciseFragment addEditExerciseFragment = (AddEditExerciseFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        String exerciseId = getIntent().getStringExtra(AddEditExerciseFragment.ARGUMENT_EDIT_EXERCISE_ID);

        setToolbarTitle(exerciseId);

        if(addEditExerciseFragment == null) {
            addEditExerciseFragment = addEditExerciseFragment.newInstance();

            if(getIntent().hasExtra(AddEditExerciseFragment.ARGUMENT_EDIT_EXERCISE_ID)) {
                Bundle bundle = new Bundle();
                bundle.putString(AddEditExerciseFragment.ARGUMENT_EDIT_EXERCISE_ID, exerciseId);
                addEditExerciseFragment.setArguments(bundle);
            }

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), addEditExerciseFragment, R.id.contentFrame);
        }

    }

    private void setToolbarTitle(String exerciseId) {
    }
}
