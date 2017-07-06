package de.gaddenzwerch.workout.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Objects;
import java.util.UUID;

public class Exercise {
    @NonNull
    private UUID mId;
    @Nullable
    private String mName;
    @Nullable
    private String mDescription;
    @Nullable
    private String mImage;

    /**
     * Creates a new Exercise providing a new Id
     * @param name of the exercise
     * @param description of the exercise
     * @param image graphical representation
     */
    public Exercise(@Nullable String name, @Nullable String description, @Nullable String image){
        mId = UUID.randomUUID();
        mName = name;
        mDescription = description;
        mImage = image;
    }

    public Exercise(@NonNull UUID id, @Nullable String name, @Nullable String description, @Nullable String image) {
        mId = id;
        mName = name;
        mDescription = description;
        mImage = image;
    }

    public UUID getId() {
        return mId;
    }

    public String getExerciseName() {
        return mName;
    }

    public String getExerciseDescription() {
        return mDescription;
    }

    public String getImage() {
        return mImage;
    }

    @Override
    public boolean equals(Object object) {
        if (this==object) {
            return true;
        }

        if (object == null ||
            getClass() != object.getClass() ) {
            return false;
        }

        Exercise exercise = (Exercise) object;

        return Objects.equals(mId, exercise.getId()) &&
               Objects.equals(mName, exercise.getExerciseName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mName);
    }

    public boolean isEmpty() {
        //TODO implement
        return false;
    }
}
