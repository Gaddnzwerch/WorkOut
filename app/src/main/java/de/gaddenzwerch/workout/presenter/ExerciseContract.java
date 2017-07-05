package de.gaddenzwerch.workout.presenter;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.UUID;

import de.gaddenzwerch.workout.BasePresenter;
import de.gaddenzwerch.workout.BaseView;
import de.gaddenzwerch.workout.model.Exercise;

public interface ExerciseContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
        void showExercises(List<Exercise> exercises);
        void showAddExercise();
        void showExerciseDetailsUi(UUID exerciseId);
        void showNoExercises();
        boolean isActive();
        void showLoadingExercisesError();
    }

    interface Presenter extends BasePresenter {
        void result(int requestCode, int resultCode);
        void loadExercises(boolean forceUpdate);
        void addNewExercise();
        void openExerciseDetails(@NonNull Exercise requestedExercise);
    }
}
