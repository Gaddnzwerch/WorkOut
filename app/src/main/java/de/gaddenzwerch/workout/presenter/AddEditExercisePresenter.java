package de.gaddenzwerch.workout.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

import de.gaddenzwerch.workout.model.Exercise;
import de.gaddenzwerch.workout.model.de.gaddenzwerch.workout.model.data.ExerciseDataSource;

/**
 * Listens to user actions from the UI, retrieves the data and updates
 * the UI as required.
 */
public class AddEditExercisePresenter implements AddEditExerciseContract.Presenter,
        ExerciseDataSource.GetExerciseCallback{

    @NonNull
    private final ExerciseDataSource mExerciseRepository;

    @NonNull
    private final AddEditExerciseContract.View mAddExerciseView;

    @Nullable
    private UUID mExerciseId;

    private boolean mIsDataMissing;

    /**
     * Creates a presenter for the add/edit view.
     *
     * @param exerciseId ID of the exercise to edit on null for a new exercise
     * @param exerciseRepository a repository of data for tasks
     * @param addExerciseView the add/edit view
     * @param shouldLoadDataFromRepo whether data needs to be loaded or not (for config changes)
     */
    public AddEditExercisePresenter(@Nullable UUID exerciseId, @NonNull ExerciseDataSource exerciseRepository,
                                    @NonNull AddEditExerciseContract.View addExerciseView, boolean shouldLoadDataFromRepo) {
        mExerciseId = exerciseId;
        mExerciseRepository = exerciseRepository;
        mAddExerciseView = addExerciseView;
        mIsDataMissing = shouldLoadDataFromRepo;

        addExerciseView.setPresenter(this);
    }

    @Override
    public void start() {
        if(isNewExercise()) {
            populateExercise();
        }

    }

    @Override
    public void saveExercise(String title, String description) {
        if(isNewExercise()) {
            createExercise(title, description);
        } else {
            updateExercise(title, description);
        }
    }

    @Override
    public void populateExercise() {
        if(isNewExercise()) {
            throw new RuntimeException("populateExercise() was called but exercise is new.");
        }
        mExerciseRepository.getExercise(mExerciseId, this);
    }

    @Override
    public void onExerciseLoaded(Exercise exercise) {
        // The view may not be able to handle UI updates anymore
        if(mAddExerciseView.isActive()) {
            mAddExerciseView.setTitle(exercise.getExerciseName());
            mAddExerciseView.setDescription(exercise.getExerciseDescription());
        }
        mIsDataMissing = false;
    }

    @Override
    public void onDataNotAvailable() {
        // The view may not be able to handle UI updates anymore
        if (mAddExerciseView.isActive()){
            mAddExerciseView.showEmptyExerciseError();
        }
    }

    @Override
    public boolean isDataMissing() {
        return mIsDataMissing;
    }

    private boolean isNewExercise() {
        return mExerciseId == null;
    }

    private void createExercise(String title, String description) {
        Exercise newExercise = new Exercise(title, description, null);

        if (newExercise.isEmpty()) {
            mAddExerciseView.showEmptyExerciseError();
        } else {
            mExerciseRepository.saveExercise(newExercise);
            mAddExerciseView.showExerciseList();
        }
    }

    private void updateExercise(String title, String description) {
        if (isNewExercise()) {
            throw new RuntimeException("updateExercise() was called but the exercise is new.");
        }
        mExerciseRepository.saveExercise(new Exercise(mExerciseId, title, description, null));
        mAddExerciseView.showExerciseList();
    }

}
