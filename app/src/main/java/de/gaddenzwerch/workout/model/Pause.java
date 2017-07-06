package de.gaddenzwerch.workout.model;

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
