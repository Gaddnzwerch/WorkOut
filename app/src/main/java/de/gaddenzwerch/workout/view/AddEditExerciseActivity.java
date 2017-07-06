package de.gaddenzwerch.workout.view;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.UUID;

import de.gaddenzwerch.workout.Injection;
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

        boolean shouldLoadDataFromRepo = true;

        // Prevent the presenter from loading data from the repository if this is a config change.
        if(savedInstanceState != null) {
            // Data might not have loaded when the config change happen, so we saved the state.
            shouldLoadDataFromRepo = savedInstanceState.getBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY);
        }

        // Create the presenter
        mAddEditExercisePresenter = new AddEditExercisePresenter(
                exerciseId,
                Injection.provideExercisesRepository(getApplicationContext()),
                addEditExerciseFragment,
                shouldLoadDataFromRepo);
    }

    private void setToolbarTitle(@Nullable String exerciseId) {
        if(exerciseId == null) {
            mActionBar.setTitle(R.string.add_exercise);
        } else {
            mActionBar.setTitle(R.string.edit_exercise);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the state so that next time we know if we need to refresh the data.
        outState.putBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY, mAddEditExercisePresenter.isDataMissing());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /*
    TODO add espresso support
    */

}
