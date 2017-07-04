package de.gaddenzwerch.workout.model;

import java.util.UUID;

public class Set {
    private UUID mId;
    private String mName;
    private Exercise mExercise;
    private int mQuantity;
    private Pause mPause;

    /**
     * Creates a new Set generating a UUID
     * @param name
     * @param exercise
     * @param quantity
     * @param pause
     */
    public Set(String name, Exercise exercise, int quantity, Pause pause) {
        mId = UUID.randomUUID();
        mName = name;
        mExercise = exercise;
        mQuantity = quantity;
        mPause = pause;
    }

    public Set(String name, UUID exerciseId, int quantity, int pauseInSec) {
        //TODO read values
        mId = UUID.randomUUID();
        mName = name;
        mQuantity = quantity;
        mPause = new Pause(pauseInSec);
    }

    public String getName() {
        return mName;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public Exercise getExercise() {
        return mExercise;
    }

    public Pause getPause() {
        return mPause;
    }

}
