package de.gaddenzwerch.workout.model.de.gaddenzwerch.workout.model.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import de.gaddenzwerch.workout.model.Exercise;

/**
 * Created by andreass on 06.07.2017.
 */
public class FakeExercisesRemoteDataSource implements ExerciseDataSource {

    private static FakeExercisesRemoteDataSource INSTANCE;

    private static final Map<UUID, Exercise> EXERCISES_SERVICE_DATA = new LinkedHashMap<>();

    // Prevent direct instantiation.
    private FakeExercisesRemoteDataSource() {}

    public static FakeExercisesRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeExercisesRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getExercises(@NonNull LoadExerciseCallback callback) {
        callback.onExerciseLoaded(new ArrayList<>(EXERCISES_SERVICE_DATA.values()));
    }

    @Override
    public void getExercise(@NonNull UUID exerciseId, @NonNull GetExerciseCallback callback) {
        Exercise exercise = EXERCISES_SERVICE_DATA.get(exerciseId);
        callback.onExerciseLoaded(exercise);
    }

    @Override
    public void saveExercise(@NonNull Exercise exercise) {
        EXERCISES_SERVICE_DATA.put(exercise.getId(), exercise);
    }

    @Override
    public void updateExercise(@NonNull Exercise exercise) {
        Exercise activeExercise = new Exercise(exercise.getId(), exercise.getExerciseName(),
                exercise.getExerciseDescription(), exercise.getImage());
        EXERCISES_SERVICE_DATA.put(exercise.getId(), activeExercise);
    }

    @Override
    public void refreshExercises() {
        // Not needed for fake calls
    }



}
