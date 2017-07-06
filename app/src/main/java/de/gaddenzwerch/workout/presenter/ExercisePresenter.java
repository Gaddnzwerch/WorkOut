package de.gaddenzwerch.workout.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import de.gaddenzwerch.workout.model.Exercise;
import de.gaddenzwerch.workout.model.de.gaddenzwerch.workout.model.data.exercise.ExerciseDataSource;
import de.gaddenzwerch.workout.model.de.gaddenzwerch.workout.model.data.exercise.ExerciseRepository;

/**
 * Listens to user actions from the UI , retrieves the data and updates the UI as required.
 * https://github.com/googlesamples/android-architecture/blob/todo-mvp/todoapp/app/src/main/java/com/example/android/architecture/blueprints/todoapp/tasks/TasksPresenter.java
 */
public class ExercisePresenter implements ExerciseContract.Presenter {

    private final ExerciseRepository mExerciseRepository;
    private final ExerciseContract.View mExerciseView;

    private boolean mFirstLoad = true;

    public ExercisePresenter(@NonNull ExerciseRepository exerciseRepository, @NonNull ExerciseContract.View exerciseView) {
        //TODO find substitute for checkNotNull(exerciseRepository)
        //TODO find substitute for checkNotNull(exerciseView)
        mExerciseRepository = exerciseRepository;
        mExerciseView = exerciseView;

        mExerciseView.setPresenter(this);
    }

    @Override
    public void start() {
        loadExercises(false);
    }

    @Override
    public void result(int requestCode, int resultCode) {
        // If a task was successfully added, show shackbar
        //TODO
    }

    @Override
    public void loadExercises(boolean forceUpdate) {
        loadExercises(forceUpdate, true);
    }

    private void loadExercises(boolean forceUpdate, final boolean showLoadingUI) {
        if(showLoadingUI) {
            mExerciseView.setLoadingIndicator(true);
        }
        if(forceUpdate) {
            mExerciseRepository.refreshExercises();
        }

        mExerciseRepository.getExercises(new ExerciseDataSource.LoadExerciseCallback() {
            @Override
            public void onExerciseLoaded(List<Exercise> exercises) {

                // The view may not be able to handle UI updates anymore
                if(!mExerciseView.isActive()){
                    return;
                }

                if(showLoadingUI){
                    mExerciseView.setLoadingIndicator(false);
                }

                processExercises(exercises);
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if(!mExerciseView.isActive()){
                    return;
                }
                mExerciseView.showLoadingExercisesError();
            }
        });
    }

    private void processExercises(List<Exercise> exercises) {
        if(exercises.isEmpty()) {
            // Show a message indicating there are no exercises for that filter type.
            processEmptyExercises();
        } else {
            // Show the list of exercises
            mExerciseView.showExercises(exercises);
        }
    }

    private void processEmptyExercises() {
        mExerciseView.showNoExercises();
    }

    @Override
    public void addNewExercise() {
        mExerciseView.showAddExercise();
    }

    @Override
    public void openExerciseDetails(@NonNull Exercise requestedExercise) {
        mExerciseView.showExerciseDetailsUi(requestedExercise.getId().toString());
    }
}
