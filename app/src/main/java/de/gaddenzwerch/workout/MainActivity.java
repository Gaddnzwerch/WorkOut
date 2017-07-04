package de.gaddenzwerch.workout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import de.gaddenzwerch.workout.view.ExerciseActivity;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "de.gaddenzwerch.workout.MESSAGE";
    public static final String EXTRA_TIME = "de.gaddenzwerch.workout.TIME";
    public static final String EXTRA_TIMER_NAME = "de.gaddenzwerch.workout.TIMER_NAME";
    private static final int RESULT_TIMER = 1;
    private static final int RESULT_EXERCISE = 2;
//    private Vector<AppCompatActivity> mActivities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        startWorkout(view);
    }

    /** Called to start a Countdown timer */
    public void startCountdown(View view) {
        Intent intent = new Intent(this, CountdownTimer.class);
        EditText editText = (EditText) findViewById(R.id.editTime);
        Integer timeToCount = Integer.parseInt(editText.getText().toString());
        intent.putExtra(EXTRA_TIME, timeToCount);
        intent.putExtra(EXTRA_TIMER_NAME, "Pause");
        startActivityForResult(intent, RESULT_TIMER);
    }

    public void startWorkout(View view) {
        loadWorkout();

        Intent intent = new Intent(this, ExerciseActivity.class);
        String message = "Übung Name";

        intent.putExtra(EXTRA_MESSAGE, message);
        startActivityForResult(intent, RESULT_EXERCISE);

        intent = new Intent(this, CountdownTimer.class);
        Integer timeToCount = 5;
        intent.putExtra(EXTRA_TIME, timeToCount);
        intent.putExtra(EXTRA_TIMER_NAME, "Pause");
        startActivityForResult(intent, RESULT_TIMER);

/*        for (AppCompatActivity lActivity:mActivities){

        }*/
    }

    private void startNextActivity() {

    }

    private void loadWorkout() {
        //TODO load workout
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case RESULT_TIMER:
                //TODO actions to perform if the timer finished as planned
                break;
            case RESULT_EXERCISE:
                EditText editText = (EditText) findViewById(R.id.editText);
                break;
        }
    }
}
