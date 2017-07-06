package de.gaddenzwerch.workout.model.de.gaddenzwerch.workout.model.data.exercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.gaddenzwerch.workout.model.Exercise;


public class ExerciseRepository
        implements ExerciseDataSource {

    private static ExerciseRepository INSTANCE = null;
    private final ExerciseDataSource mExercisesDataSource;

    Map<UUID, Exercise> mCachedExercises;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;

    // Prevent direct instantiation
    private ExerciseRepository(@NonNull ExerciseDataSource exercisesLocalDataSource) {
        mExercisesDataSource = exercisesLocalDataSource;
    }

    /**
     * Returns the single instance of this class, creating it if necessary
     *
     * @param taskLocalDataSource the device storage data source
     * @return the {@link ExerciseRepository} instance
     */
    public static ExerciseRepository getInstance(ExerciseDataSource taskLocalDataSource) {
        if(INSTANCE == null) {
            INSTANCE = new ExerciseRepository(taskLocalDataSource);
        }

        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(ExerciseDataSource)} to create a new instance next time
     * it's called'
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getExercises(@NonNull final LoadExerciseCallback callback) {
        //TODO find substitute for checkNotNull(callback)

        // Respond immediately with cache if available and not dirty
        if(mCachedExercises != null && !mCacheIsDirty) {
            callback.onExerciseLoaded(new ArrayList<Exercise>(mCachedExercises.values()));
            return;
        }

        if(mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network
            // Not necessary as there currently is no data stored in network.
        } else {
            // Query the local storage if available. If not, query the network.
            mExercisesDataSource.getExercises(new LoadExerciseCallback() {
                @Override
                public void onExerciseLoaded(List<Exercise> exercises) {
                    refreshCache(exercises);
                    callback.onExerciseLoaded(new ArrayList<Exercise>(mCachedExercises.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    // Remote DataSource not needed at the moment as there is no remote connection.
                    callback.onDataNotAvailable();
                }
            });
        }
    }

    @Override
    public void saveExercise(@NonNull Exercise exercise){
        //TODO find substitute for checkNull(exercise)
        mExercisesDataSource.saveExercise(exercise);

        // Do in memory cache update to keep the app UI up to date
        if(mCachedExercises == null) {
            mCachedExercises = new LinkedHashMap<>();
        }
        mCachedExercises.put(exercise.getId(), exercise);
    }

    @Override
    public void getExercise(@NonNull final UUID exerciseId, @NonNull final GetExerciseCallback callback) {
        //TODO find substitute for checkNull(exerciseId)
        //TODO find substitute for checkNull(callback)

        Exercise cachedExercise = getExerciseWithId(exerciseId);

        //Respond immediately with cache if available
        if(cachedExercise != null) {
            callback.onExerciseLoaded(cachedExercise);
            return;
        }

        // Load from server/persisted if needed.

        // Is the task in the local data source? If not, query the network.
        mExercisesDataSource.getExercise(exerciseId, new GetExerciseCallback() {
            @Override
            public void onExerciseLoaded(Exercise exercise) {
                // Do in memory cache update to keep the app UI up to date
                if(mCachedExercises == null) {
                    mCachedExercises = new LinkedHashMap<>();
                }
                mCachedExercises.put(exerciseId, exercise);
                callback.onExerciseLoaded(exercise);
            }

            @Override
            public void onDataNotAvailable() {
                // Remote DataSource not needed at the moment as there is no remote connection.
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<Exercise> exercises) {
        if(mCachedExercises == null) {
            mCachedExercises = new LinkedHashMap<>();
        }
        mCachedExercises.clear();

        for(Exercise exercise: exercises){
            mCachedExercises.put(exercise.getId(), exercise);
        }

        mCacheIsDirty = false;
    }

    @Override
    public void refreshExercises() {
        mCacheIsDirty = true;
    }

    @Nullable
    private Exercise getExerciseWithId(@NonNull UUID id) {
        //TODO find substitute for checkNotNull(id)
        if(mCachedExercises == null ||
           mCachedExercises.isEmpty()) {
            return null;
        } else {
            return mCachedExercises.get(id);
        }
    }

    @Override
    public void updateExercise(@NonNull Exercise exercise) {
        //TODO find substitute for checkNotNull(exercise)
        mExercisesDataSource.updateExercise(exercise);

        Exercise currentExercise = new Exercise(exercise.getId(), exercise.getExerciseName(), exercise.getExerciseDescription(), exercise.getImage());

        // Do in memory cache update to keep the app UI up to date
        if(mCachedExercises == null) {
            mCachedExercises = new LinkedHashMap<>();
        }
        mCachedExercises.put(exercise.getId(), currentExercise);
    }
}
