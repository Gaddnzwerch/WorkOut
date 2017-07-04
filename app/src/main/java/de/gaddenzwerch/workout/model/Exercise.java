package de.gaddenzwerch.workout.model;

import java.util.Objects;
import java.util.UUID;

public class Exercise {
    private UUID mId;
    private String mName;
    private String mDescription;
    private String mImage;

    /**
     * Creates a new Exercise providing a new Id
     * @param name of the exercise
     * @param description of the exercise
     * @param image graphical representation
     */
    public Exercise(String name, String description, String image){
        mId = UUID.randomUUID();
        mName = name;
        mDescription = description;
        mImage = image;
    }

    public Exercise(UUID id, String name, String description, String image) {
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

    public void setExerciseName(String name) {
        mName = name;
    }

    public String getExerciseDescription() {
        return mDescription;
    }

    public void setExerciseDescription(String description) {
        mDescription = description;
    }

    public void setImage(String image) {
        mImage = image;
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

}
