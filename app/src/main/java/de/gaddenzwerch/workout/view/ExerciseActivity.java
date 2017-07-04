package de.gaddenzwerch.workout.view;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import de.gaddenzwerch.workout.R;
import de.gaddenzwerch.workout.presenter.ExercisePresenter;

public class ExerciseActivity extends AppCompatActivity  {

    private DrawerLayout mDrawerLayout;
    private ExercisePresenter mExercisePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        // Set up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        

    }

}
