package de.gaddenzwerch.workout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.os.CountDownTimer;

public class CountdownTimer extends AppCompatActivity {

    private Integer mTimeToCount;
    private TextView mTvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_timer);

        Intent intent = getIntent();
        mTimeToCount = intent.getIntExtra(MainActivity.EXTRA_TIME, 0);

        mTvTime = (TextView) findViewById(R.id.tvTime);
        mTvTime.setText(mTimeToCount.toString());
    }

    @Override
    protected void onStart(){
        super.onStart();
        this.startTimer();
    }

    private void startTimer() {
        new CountDownTimer(this.mTimeToCount * 1000, 1000){

            public void onTick(long pMillisecondsUntilFinished){
                Long lSecondsLeft = (pMillisecondsUntilFinished + 1000) / 1000;
                mTvTime.setText(lSecondsLeft.toString());
            }

            public void onFinish(){
                mTvTime.setText("");
                finish();
            }

        }.start();
    }
}
