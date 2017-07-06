package de.gaddenzwerch.workout;

import android.content.Context;
import android.support.annotation.NonNull;

import de.gaddenzwerch.workout.model.de.gaddenzwerch.workout.model.data.ExerciseLocalDataSource;
import de.gaddenzwerch.workout.model.de.gaddenzwerch.workout.model.data.ExerciseRepository;
import de.gaddenzwerch.workout.model.de.gaddenzwerch.workout.model.data.FakeExercisesRemoteDataSource;

/**
 * Enables injection of mock implementations for
 * {@link de.gaddenzwerch.workout.model.de.gaddenzwerch.workout.model.data.ExerciseDataSource} at compile time.
 * This is useful for testing as it allows us to use a fake instance of the class to isolate the
 * dependencies and run a test hermetically.
 *
 * Currently not needed as there is no remote data source supported.
 */
public class Injection {

    public static ExerciseRepository provideExercisesRepository(@NonNull Context context) {
        return ExerciseRepository.getInstance(ExerciseLocalDataSource.getInstance(context));
    }
}
