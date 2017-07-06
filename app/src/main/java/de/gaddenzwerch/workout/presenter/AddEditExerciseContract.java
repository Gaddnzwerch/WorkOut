package de.gaddenzwerch.workout.presenter;

import de.gaddenzwerch.workout.BasePresenter;
import de.gaddenzwerch.workout.BaseView;

public interface AddEditExerciseContract {

    interface View extends BaseView<Presenter> {
        void showEmptyExerciseError();
        void showExerciseList();
        void setTitle(String title);
        void setDescription(String description);
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void saveExercise(String title, String description);
        void populateExercise();
        boolean isDataMissing();
    }

}
