package de.gaddenzwerch.workout.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Representing a combination of an {@link Exercise} an a quantity
 */
public class Set {
    @Nullable
    private String mName;
    @NonNull
    private Exercise mExercise;
    @NonNull
    private int mQuantity;

    /**
     * Creates a new Set with the {@code name) where {@link Exercise} is repeated {@code quantity}
     * times. A {@code pauseInSec} can be provided.
     */
    public Set(@Nullable String name, @NonNull Exercise exerciseId, @NonNull int quantity) {
        mName = name;
        mExercise = exerciseId;
        mQuantity = quantity;
    }

    /**
     * This constructor provides a convenient way to clone a Set.
     * @param originalSet
     */
    public Set(@NonNull Set originalSet) {
        mName = originalSet.getName();
        mExercise = originalSet.getExercise();
        mQuantity = originalSet.getQuantity();
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


}
