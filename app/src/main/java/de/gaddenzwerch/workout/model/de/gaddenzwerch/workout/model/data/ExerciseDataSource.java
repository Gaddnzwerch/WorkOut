package de.gaddenzwerch.workout.model.de.gaddenzwerch.workout.model.data;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.UUID;

import de.gaddenzwerch.workout.model.Exercise;

public interface ExerciseDataSource {

    interface LoadExerciseCallback {
        void onExerciseLoaded(List<Exercise> exercises);
        void onDataNotAvailable();
    }

    interface GetExerciseCallback {
        void onExerciseLoaded(Exercise exercise);
        void onDataNotAvailable();
    }

    void getExercises(@NonNull LoadExerciseCallback callback);
    void getExercise(@NonNull UUID exerciseId, @NonNull GetExerciseCallback callback);
    void saveExercise(@NonNull Exercise exercise);
    void updateExercise(@NonNull Exercise exercise);
    void refreshExercises();
}
