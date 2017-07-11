package de.gaddenzwerch.workout.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ProgressionLevel {

    private int mLevel;
    @NonNull
    private Exercise mExercise;
    private int mMinRepetitions;
    private int mMaxRepetitions;
    private int mNumberOfSets;
    private int mBreakTime;

    public ProgressionLevel(int level,
                            @NonNull Exercise exercise,
                            int minRepetitions,
                            int maxRepetitions,
                            int numberOfSets,
                            int breakTime) {
        mLevel = level;
        mExercise = exercise;
        mMinRepetitions = minRepetitions;
        mMaxRepetitions = maxRepetitions;
        mNumberOfSets = numberOfSets;
        mBreakTime = breakTime;
    }

    public int getLevel() {
        return mLevel;
    }

    @NonNull
    public Exercise getExercise() {
        return mExercise;
    }

    public int getMinRepetitions() {
        return mMinRepetitions;
    }

    public int getMaxRepetitions() {
        return mMaxRepetitions;
    }

    public int getMinNumberOfSets() {
        return mNumberOfSets;
    }

    public int getMaxNumberOfSets() {
        return mNumberOfSets;
    }

    public int getBreakTime() { return mBreakTime; }

    public Unit createUnit( int increment ) {

        if( increment > getMaxIncrement()) {
            throw new IndexOutOfBoundsException();
            //TODO create separate exception
        }

        List<Set> lSetList = new ArrayList<>();

        // create sets with min repetitions
        for( int setCount=0; setCount < mNumberOfSets; setCount++) {
            lSetList.add(new Set("", mExercise, mMinRepetitions));
        }

        // increase repetitions till increment is reached
        while( increment > 0) {
            for( Set set : lSetList) {
                set.setQuantity(set.getQuantity() + 1);
                increment--;

                if(increment == 0){
                    break;
                }
            }
        }

        return new Unit("", lSetList, mBreakTime);
    }

    private int getMaxIncrement() {
        return (mMaxRepetitions - mMinRepetitions) * mNumberOfSets;
    }
}
