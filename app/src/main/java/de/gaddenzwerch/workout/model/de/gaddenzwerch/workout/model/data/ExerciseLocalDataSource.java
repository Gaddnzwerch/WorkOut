package de.gaddenzwerch.workout.model.de.gaddenzwerch.workout.model.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.gaddenzwerch.workout.model.Exercise;


/**
 * Concrete implementation of a data source as a db.
 */
public class ExerciseLocalDataSource
        implements ExerciseDataSource {

    private static ExerciseLocalDataSource INSTANCE;
    private ExerciseDbHelper mDbHelper;

    // Prevent direct instantiation
    private ExerciseLocalDataSource(@NonNull Context context) {
        //TODO find substitute for checkNotNull(context);
        mDbHelper = new ExerciseDbHelper(context);
    }

    public static ExerciseLocalDataSource getInstance(@NonNull Context context) {
        if(INSTANCE == null) {
            INSTANCE = new ExerciseLocalDataSource(context);
        }
        return INSTANCE;
    }


    /**
     * Note: {@link LoadExerciseCallback#onDataNotAvailable()} is fired if the db doesn't exist or
     * the table is empty.
     */
    @Override
    public void getExercises(@NonNull LoadExerciseCallback callback) {
        List<Exercise> exercises = new ArrayList<Exercise>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_ID,
                ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_NAME,
                ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_DESCRIPTION,
                ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_IMAGE
        };

        Cursor c = db.query(ExercisePersistenceContract.ExerciseEntry.TABLE_NAME, projection, null, null, null, null,null);

        if(c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                UUID id = UUID.fromString(c.getString(c.getColumnIndexOrThrow( ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_ID)));
                String name = c.getString(c.getColumnIndexOrThrow( ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_NAME));
                String description = c.getString(c.getColumnIndexOrThrow( ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_DESCRIPTION));
                String image = c.getString(c.getColumnIndexOrThrow( ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_IMAGE));

                Exercise exercise = new Exercise(id, name, description, image);
                exercises.add(exercise);
            }
        }

        if(c != null){
            c.close();
        }

        db.close();

        if(exercises.isEmpty()){
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onExerciseLoaded(exercises);
        }
    }

    /**
     * Note: {@link GetExerciseCallback#onDataNotAvailable()} is fired if the {@link Exercise} isn't
     * found.
     */
    @Override
    public void getExercise(@NonNull UUID exerciseId, @NonNull GetExerciseCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_ID,
                ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_NAME,
                ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_DESCRIPTION,
                ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_IMAGE
        };

        String selection = ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_ID + " LIKE ?";
        String [] selectionArgs = {exerciseId.toString()};

        Cursor c = db.query(
                ExercisePersistenceContract.ExerciseEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        Exercise exercise = null;

        if(c != null && c.getCount() > 0) {
            c.moveToFirst();
            UUID id = UUID.fromString(c.getString(c.getColumnIndexOrThrow( ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_ID)));
            String name = c.getString(c.getColumnIndexOrThrow( ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_NAME));
            String description = c.getString(c.getColumnIndexOrThrow( ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_DESCRIPTION));
            String image = c.getString(c.getColumnIndexOrThrow( ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_IMAGE));

            exercise = new Exercise(id, name, description, image);
        }

        if(c != null){
            c.close();
        }

        db.close();

        if(exercise != null) {
            callback.onExerciseLoaded(exercise);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void saveExercise(@NonNull Exercise exercise) {
        //TODO substitute checkNotNull(exercise)
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_ID, exercise.getId().toString());
        values.put(ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_NAME, exercise.getExerciseName());
        values.put(ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_DESCRIPTION, exercise.getExerciseDescription());
        values.put(ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_IMAGE, exercise.getImage());

        db.insert(ExercisePersistenceContract.ExerciseEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void updateExercise(@NonNull Exercise exercise) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_NAME, exercise.getExerciseName());
        values.put(ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_DESCRIPTION, exercise.getExerciseDescription());
        values.put(ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_IMAGE, exercise.getImage());

        String selection = ExercisePersistenceContract.ExerciseEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = {exercise.getId().toString()} ;

        db.update(ExercisePersistenceContract.ExerciseEntry.TABLE_NAME, values, selection, selectionArgs);

        db.close();
    }

    @Override
    public void refreshExercises() {
        // Not required because the {@link ExerciseRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }
}
