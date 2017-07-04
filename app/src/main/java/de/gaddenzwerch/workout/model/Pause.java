package de.gaddenzwerch.workout.model;

/**
 * Created by andreass on 04.07.2017.
 */

public class Pause {
    private int mDurationInSec;

    public Pause(int durationInSec){
        mDurationInSec = durationInSec;
    }

    public int getDurationInSec() {
        return mDurationInSec;
    }

    public void setDurationInSec(int durationInSec) {
        mDurationInSec = durationInSec;
    }
}
