package de.gaddenzwerch.workout.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

public class Set {
    @NonNull
    private UUID mId;
    @Nullable
    private String mName;
    @NonNull
    private Exercise mExercise;
    @NonNull
    private int mQuantity;
    @Nullable
    private Pause mPause;

    /**
     * Creates a new Set with the {@code name) where {@link Exercise} is repeated {@code quantity}
     * times. A {@code pauseInSec} can be provided.
     */
    public Set(@Nullable String name, @NonNull Exercise exerciseId, @NonNull int quantity, @Nullable int pauseInSec) {
        mId = UUID.randomUUID();
        mName = name;
        mExercise = exerciseId;
        mQuantity = quantity;
        mPause = new Pause(pauseInSec);
    }

    /**
     * This constructor creates an already existing task given the {@code id}.
     */
    public Set(@NonNull UUID id, @Nullable String name, @NonNull Exercise exerciseId, @NonNull int quantity, @Nullable int pauseInSec) {
        //TODO read values
        mId = id;
        mName = name;
        mExercise = exerciseId;
        mQuantity = quantity;
        mPause = new Pause(pauseInSec);
    }

    /**
     * This constructor provides a convenient way to clone a Set.
     * @param originalSet
     */
    public Set(@NonNull Set originalSet) {
        mId = originalSet.getId();
        mName = originalSet.getName();
        mExercise = originalSet.getExercise();
        mQuantity = originalSet.getQuantity();
        mPause = new Pause(originalSet.getPauseTimeInSec());
    }

    public UUID getId() { return mId; }

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

    public int getPauseTimeInSec() { return mPause.getDurationInSec(); }

}
