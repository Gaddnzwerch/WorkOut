package de.gaddenzwerch.workout;

import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.os.CountDownTimer;

public class CountdownTimer extends AppCompatActivity {

    private Integer mTimeToCount;
    private TextView mTvTime;
    private CountDownTimer mCountDownTimer;
    private Vibrator mVibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_timer);

        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        Intent intent = getIntent();
        mTimeToCount = intent.getIntExtra(MainActivity.EXTRA_TIME, 0);

        mTvTime = (TextView) findViewById(R.id.tvTime);
        mTvTime.setText(mTimeToCount.toString());

        mCountDownTimer = new CountDownTimer(this.mTimeToCount * 1000, 1000) {

            public void onTick(long pMillisecondsUntilFinished) {
                Long lSecondsUntilFinished = pMillisecondsUntilFinished / 1000;
                mTvTime.setText(lSecondsUntilFinished.toString());

                if (lSecondsUntilFinished == 2) {
                    notifyCountDown();
                }
            }

            public void onFinish() {
                mTvTime.setText("");
                finish();
            }
        };
    }

    @Override
    protected void onStart(){
        super.onStart();
        this.startTimer();
    }

    private void startTimer() {
        mCountDownTimer.start();
    }

    private void notifyCountDown() {
        mVibrator.vibrate((long) 100);
    }
}
