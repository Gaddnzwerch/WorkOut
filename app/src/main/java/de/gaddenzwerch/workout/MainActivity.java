package de.gaddenzwerch.workout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "de.gaddenzwerch.workout.MESSAGE";
    public static final String EXTRA_TIME = "de.gaddenzwerch.workout.TIME";

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
        startActivity(intent);
    }
}
