package de.gaddenzwerch.workout.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents one progression of a {@link Program}. Progressions are stored in a DB and will not
 * be user-generated at the moment.
 */
public class Progression {
    private UUID mId;
    private String mName;
    private ArrayList<ProgressionLevel> mProgessionLevels;
    private int mDefaultMinRepetitions;
    private int mDefaultMaxRepetitions;
    private int mDefaultNumberOfSets;

    /**
     * Generates a new progression with the given {@param name} and default values.
     * @param name
     */
    public Progression (String name) {
        new Progression(UUID.randomUUID(), name, 4, 8, 3);
    }

    public Progression (UUID id,
                        String name,
                        int defaultMinRepetitions,
                        int defaultMaxRepetitions,
                        int defaultNumberOfSets) {
        mId = id;
        mName = name;
        mDefaultMinRepetitions = defaultMinRepetitions;
        mDefaultMaxRepetitions = defaultMaxRepetitions;
        mDefaultNumberOfSets = defaultNumberOfSets;
        mProgessionLevels = new ArrayList<>();
    }

    /**
     * Takes {@param exercise}, converts it to a {@link ProgressionLevel} and adds it as last level.
     */
    public void addExercise(Exercise exercise) {
        int lLevel = mProgessionLevels.size();
        lLevel++;
        ProgressionLevel lProgressionLevel = new ProgressionLevel(lLevel, exercise, mDefaultMinRepetitions, mDefaultMaxRepetitions, mDefaultNumberOfSets);
        mProgessionLevels.add(lProgressionLevel);
    }



}
