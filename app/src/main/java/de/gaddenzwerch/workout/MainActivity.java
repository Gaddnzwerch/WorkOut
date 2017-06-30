package de.gaddenzwerch.workout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "de.gaddenzwerch.workout.MESSAGE";
    public static final String EXTRA_TIME = "de.gaddenzwerch.workout.TIME";
    public static final String EXTRA_TIMER_NAME = "de.gaddenzwerch.workout.TIMER_NAME";
    private static final int RESULT_TIMER = 1;
    private static final int RESULT_EXERCISE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case RESULT_TIMER:
                if(resultCode == CountdownTimer.TIMER_FINISHED){
                    EditText editText = (EditText) findViewById(R.id.editText);
                    String message = "Abgelaufen";
                    editText.setText(message);
                }
                break;
            case RESULT_EXERCISE:
                break;
        }


    }
}
