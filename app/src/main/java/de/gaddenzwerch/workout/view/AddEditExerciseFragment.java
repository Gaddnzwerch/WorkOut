package de.gaddenzwerch.workout.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.gaddenzwerch.workout.R;
import de.gaddenzwerch.workout.presenter.AddEditExerciseContract;


public class AddEditExerciseFragment extends Fragment implements AddEditExerciseContract.View {

    public static final String ARGUMENT_EDIT_EXERCISE_ID = "EDIT_EXERCISE_ID";

    private AddEditExerciseContract.Presenter mPresenter;
    private TextView mTitle;
    private TextView mDescription;

    public static AddEditExerciseFragment newInstance() {
        return new AddEditExerciseFragment();
    }

    public AddEditExerciseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull AddEditExerciseContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_task_done);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.saveExercise(mTitle.getText().toString(), mDescription.getText().toString());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_exercise, container, false);
        mTitle = (TextView) root.findViewById(R.id.add_exercise_title);
        mDescription = (TextView) root.findViewById(R.id.add_exercise_description);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void showEmptyExerciseError() {
        Snackbar.make(mTitle, getString(R.string.empty_exercise_message),Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showExerciseList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setDescription(String description) {
        mDescription.setText(description);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }



}
