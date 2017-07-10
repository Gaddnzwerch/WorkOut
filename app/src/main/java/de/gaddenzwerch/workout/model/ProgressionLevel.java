package de.gaddenzwerch.workout.model;

import android.support.annotation.NonNull;

public class ProgressionLevel {

    private int mLevel;
    @NonNull
    private Exercise mExercise;
    private int mMinRepetitions;
    private int mMaxRepetitions;
    private int mNumberOfSets;

    public ProgressionLevel(int level,
                            @NonNull Exercise exercise,
                            int minRepetitions,
                            int maxRepetitions,
                            int numberOfSets) {
        mLevel = level;
        mExercise = exercise;
        mMinRepetitions = minRepetitions;
        mMaxRepetitions = maxRepetitions;
        mNumberOfSets = numberOfSets;
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
}
