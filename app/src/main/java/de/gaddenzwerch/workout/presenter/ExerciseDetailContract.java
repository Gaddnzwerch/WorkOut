package de.gaddenzwerch.workout.presenter;

import de.gaddenzwerch.workout.BasePresenter;
import de.gaddenzwerch.workout.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface ExerciseDetailContract {

    interface View extends BaseView<ExerciseDetailContract.Presenter> {
        void setLoadingIndicator(boolean active);
        void showMissingExercise();
        void hideTitle();
        void showTitle(String title);
        void hideDescription();
        void showDescription(String description);
        void showEditExercise(String exerciseId);
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void editExercise();
        void deleteExercise();
    }
}
