package de.gaddenzwerch.workout.model;

import android.support.annotation.Nullable;

import java.util.List;

import de.gaddenzwerch.workout.model.Set;

public class Unit {
    private String mName;
    private List<Set> mSets;
    private int mPauseTimer;

    public Unit(@Nullable String name, List<Set> sets, int breakTime) {
        mName = name;
        mSets = sets;
        mPauseTimer = breakTime;
    }

    @Override
    public String toString() {
        String lReturn = mName;

        for(Set set: mSets) {
            lReturn += "\n" + set.toString();
        }

        return lReturn;
    }
}
