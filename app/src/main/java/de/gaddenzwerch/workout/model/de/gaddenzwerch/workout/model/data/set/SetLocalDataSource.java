package de.gaddenzwerch.workout.model.de.gaddenzwerch.workout.model.data.set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.gaddenzwerch.workout.model.Set;
import de.gaddenzwerch.workout.model.de.gaddenzwerch.workout.model.data.WorkOutDbHelper;

public class SetLocalDataSource implements SetDataSource {

    private static SetLocalDataSource INSTANCE;
    private WorkOutDbHelper mDbHelper;

    public static final String[] PROJECTION = new String[]{
            SetPersistenceContract.SetEntry.COLUMN_NAME_ID,
            SetPersistenceContract.SetEntry.COLUMN_NAME_NAME,
            SetPersistenceContract.SetEntry.COLUMN_NAME_EXERCISE_ID,
            SetPersistenceContract.SetEntry.COLUMN_NAME_QUANTITY,
            SetPersistenceContract.SetEntry.COLUMN_NAME_PAUSE_DURATION
    };

    // Prevent direct instantiation
    private SetLocalDataSource(@NonNull Context context) {
        mDbHelper = new WorkOutDbHelper(context);
    }

    public static SetLocalDataSource getInstance(@NonNull Context context) {
        if(INSTANCE == null) {
            INSTANCE = new SetLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getSets(@NonNull LoadSetCallback callback) {
        List<Set> sets = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor c = db.query(SetPersistenceContract.SetEntry.TABLE_NAME, PROJECTION, null, null, null, null, null);

        if(c != null) {
            while (c.moveToNext()) {
                Set set = getSetFromCursor(c);
                sets.add(set);
            }
        }

        if(c != null) {
            c.close();
        }

        db.close();

        if(sets.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onSetLoaded(sets);
        }
    }


    @Override
    public void getSet(@NonNull UUID setId, @NonNull GetSetCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String selection = SetPersistenceContract.SetEntry.COLUMN_NAME_ID + " LIKE ?";
        String [] selectionArgs = {setId.toString()};

        Cursor c = db.query(SetPersistenceContract.SetEntry.TABLE_NAME, PROJECTION, selection, selectionArgs, null, null, null);

        Set set = null;

        if(c != null && c.getCount() > 0) {
            c.moveToFirst();
            set = getSetFromCursor(c);
        }

        if(c != null) {
            c.close();
        }

        db.close();

        if(set != null) {
            callback.onSetLoaded(set);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void saveSet(@NonNull Set set) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(SetPersistenceContract.SetEntry.COLUMN_NAME_ID, set.getId().toString());
        values.put(SetPersistenceContract.SetEntry.COLUMN_NAME_NAME, set.getName());
        values.put(SetPersistenceContract.SetEntry.COLUMN_NAME_EXERCISE_ID, set.getExerciseId().toString());
        values.put(SetPersistenceContract.SetEntry.COLUMN_NAME_QUANTITY, set.getQuantity());
        values.put(SetPersistenceContract.SetEntry.COLUMN_NAME_PAUSE_DURATION, set.getPauseTimeInSec());

        db.insert(SetPersistenceContract.SetEntry.TABLE_NAME, null, values);
    }

    @Override
    public void updateSet(@NonNull Set set) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(SetPersistenceContract.SetEntry.COLUMN_NAME_NAME, set.getName());
        values.put(SetPersistenceContract.SetEntry.COLUMN_NAME_EXERCISE_ID, set.getExerciseId().toString());
        values.put(SetPersistenceContract.SetEntry.COLUMN_NAME_QUANTITY, set.getQuantity());
        values.put(SetPersistenceContract.SetEntry.COLUMN_NAME_PAUSE_DURATION, set.getPauseTimeInSec());

        String selection = SetPersistenceContract.SetEntry.COLUMN_NAME_ID + " LIKE ? ";
        String [] selectionArgs = {set.getId().toString()};

        db.update(SetPersistenceContract.SetEntry.TABLE_NAME, values, selection, selectionArgs);

        db.close();
    }

    @Override
    public void refreshSet() {
        // Not required because the {@link SetRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }


    @NonNull
    private Set getSetFromCursor(Cursor c) {
        UUID id = UUID.fromString(c.getString(c.getColumnIndexOrThrow(SetPersistenceContract.SetEntry.COLUMN_NAME_ID)));
        String name = c.getString(c.getColumnIndexOrThrow(SetPersistenceContract.SetEntry.COLUMN_NAME_NAME));
        UUID exerciseId = UUID.fromString(c.getString(c.getColumnIndexOrThrow(SetPersistenceContract.SetEntry.COLUMN_NAME_EXERCISE_ID)));
        int quantity = c.getInt(c.getColumnIndexOrThrow(SetPersistenceContract.SetEntry.COLUMN_NAME_QUANTITY));
        int pause = c.getInt((c.getColumnIndexOrThrow(SetPersistenceContract.SetEntry.COLUMN_NAME_PAUSE_DURATION)));

        return new Set(id, name, exerciseId, quantity, pause);
    }
}
