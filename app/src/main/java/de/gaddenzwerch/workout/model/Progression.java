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
    private ArrayList<ProgressionLevel> mProgressionLevels;
    private int mDefaultMinRepetitions;
    private int mDefaultMaxRepetitions;
    private int mDefaultNumberOfSets;
    private int mDefaultBreakTime;

    /**
     * Generates a new progression with the given {@param name} and default values.
     * @param name
     */
    public Progression (String name) {
        this(UUID.randomUUID(), name, 4, 8, 3, 90);
    }

    public Progression (UUID id,
                        String name,
                        int defaultMinRepetitions,
                        int defaultMaxRepetitions,
                        int defaultNumberOfSets,
                        int defaultBreakTime) {
        mId = id;
        mName = name;
        mDefaultMinRepetitions = defaultMinRepetitions;
        mDefaultMaxRepetitions = defaultMaxRepetitions;
        mDefaultNumberOfSets = defaultNumberOfSets;
        mDefaultBreakTime = defaultBreakTime;
        mProgressionLevels = new ArrayList<>();
    }

    /**
     * Takes {@param exercise}, converts it to a {@link ProgressionLevel} and adds it as last level.
     */
    public void addExercise(Exercise exercise) {
        if (mProgressionLevels == null) {
            mProgressionLevels = new ArrayList<>();
        }
        int lLevel = mProgressionLevels.size();
        addExercise(exercise, lLevel, mDefaultMinRepetitions, mDefaultMaxRepetitions, mDefaultNumberOfSets, mDefaultBreakTime);
    }

    public void addExercise(Exercise exercise, int level, int minRepetitions, int maxRepetitions, int numberOfSets, int breakTime) {
        mProgressionLevels.ensureCapacity(level + 1);
        ProgressionLevel progressionLevel = new ProgressionLevel(level + 1, exercise, minRepetitions, maxRepetitions, numberOfSets, breakTime);
        mProgressionLevels.add(level, progressionLevel);
    }

    public int getNumberOfProgressions() {
        return mProgressionLevels.size();
    }

    protected ProgressionLevel getLevel(int level) {
        if (mProgressionLevels == null) {
            mProgressionLevels = new ArrayList<>();
        }
        if(level <= mProgressionLevels.size()) {
            return mProgressionLevels.get(level - 1);
        }
        return null;
    }

    protected ProgressionLevel getNextProgressionLevel(ProgressionLevel progressionLevel) {
        // check if progressionLevel is in list
        if(mProgressionLevels.contains(progressionLevel)) {
            int lCurrentLevel = progressionLevel.getLevel();

            if(lCurrentLevel != getNumberOfProgressions()) {
                return getLevel(lCurrentLevel + 1);
            }
        }
        return null;
    }

    public Unit createUnit(int level, int increment) {
        ProgressionLevel progressionLevel = getLevel(level);
        return progressionLevel.createUnit(increment);
    }
}
