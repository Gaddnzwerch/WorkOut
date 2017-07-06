package de.gaddenzwerch.workout.model.de.gaddenzwerch.workout.model.data.exercise;

import android.provider.BaseColumns;

public final class ExercisePersistenceContract {
    private ExercisePersistenceContract() {}

    public static abstract class ExerciseEntry implements BaseColumns {
        public static final String TABLE_NAME = "exercises";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_IMAGE = "image";
    }
}
