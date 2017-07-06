package de.gaddenzwerch.workout.presenter;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

import de.gaddenzwerch.workout.model.Exercise;
import de.gaddenzwerch.workout.model.de.gaddenzwerch.workout.model.data.exercise.ExerciseDataSource;
import de.gaddenzwerch.workout.model.de.gaddenzwerch.workout.model.data.exercise.ExerciseRepository;

public class ExerciseDetailPresenter implements ExerciseDetailContract.Presenter {

    private final ExerciseRepository mExerciseRepository;
    private final ExerciseDetailContract.View mExerciseDetailView;

    @Nullable
    private UUID mExerciseId;

    public ExerciseDetailPresenter(@Nullable String taskId,
                                   @Nullable ExerciseRepository exerciseRepository,
                                   @Nullable ExerciseDetailContract.View exerciseDetailView) {
        mExerciseId = UUID.fromString(taskId);
        mExerciseRepository = exerciseRepository;
        mExerciseDetailView = exerciseDetailView;

        mExerciseDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        openTask();
    }

    private void openTask() {
        if(mExerciseId == null) {
            mExerciseDetailView.showMissingExercise();
        }

        mExerciseDetailView.setLoadingIndicator(true);
        mExerciseRepository.getExercise(mExerciseId, new ExerciseDataSource.GetExerciseCallback() {
            @Override
            public void onExerciseLoaded(Exercise exercise) {
                // The view may not be able to handle UI updates anymore
                if(!mExerciseDetailView.isActive()){
                    return;
                }
                mExerciseDetailView.setLoadingIndicator(false);

                if(exercise == null) {
                    mExerciseDetailView.showMissingExercise();
                } else {
                    showExercise(exercise);
                }

            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if(!mExerciseDetailView.isActive()){
                    return;
                }
                mExerciseDetailView.showMissingExercise();
            }
        });
    }

    @Override
    public void editExercise() {
        if(mExerciseId==null) {
            mExerciseDetailView.showMissingExercise();
            return;
        }
        mExerciseDetailView.showEditExercise(mExerciseId.toString());
    }

    @Override
    public void deleteExercise() {
        if(mExerciseId==null) {
            mExerciseDetailView.showMissingExercise();
            return;
        }
        //TODO not implemented yet
        // mExerciseRepository.deleteExercise(mExerciseId);
        return;
    }

    private void showExercise(@NonNull Exercise exercise) {
        String title = exercise.getExerciseName();
        String description = exercise.getExerciseDescription();

        if(title==null || title == "") {
            mExerciseDetailView.hideTitle();
        } else {
            mExerciseDetailView.showTitle(title);
        }

        if(description==null || description == "") {
            mExerciseDetailView.hideDescription();
        } else {
            mExerciseDetailView.showDescription(description);
        }
    }
}
