package de.gaddenzwerch.workout.model.de.gaddenzwerch.workout.model.data.set;

import android.provider.BaseColumns;

public final class SetPersistenceContract  {
    private SetPersistenceContract() {};

    public static abstract class SetEntry implements BaseColumns {
        public static final String TABLE_NAME = "sets";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_EXERCISE_ID = "exercise_id";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_PAUSE_DURATION = "pause_duration";
    }
}
