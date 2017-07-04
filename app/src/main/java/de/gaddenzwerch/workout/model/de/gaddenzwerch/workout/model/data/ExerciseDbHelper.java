package de.gaddenzwerch.workout.model.de.gaddenzwerch.workout.model.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExerciseDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WorkOut.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String BOOLEAN_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ExercisePersistenceContract.ExerciseEntry.TABLE_NAME + " (" +
                ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_ID + TEXT_TYPE + " PRIMARY KEY," +
                ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_IMAGE + TEXT_TYPE +
            " )";

    public ExerciseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // not required, still version 1
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // not required, still version 1
    }

}
