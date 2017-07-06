package de.gaddenzwerch.workout.view;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.gaddenzwerch.workout.R;
import de.gaddenzwerch.workout.presenter.ExerciseDetailContract;

/**
 * Main UI for the detail screen
 */
public class ExerciseDetailFragment extends Fragment
        implements ExerciseDetailContract.View
{
    @NonNull
    private static final String ARGUMENT_EXERCISE_ID = "EXERCISE_ID";

    @NonNull
    private static final int REQUEST_EDIT_EXERCISE = 1;

    private ExerciseDetailContract.Presenter mPresenter;

    private TextView mDetailTitle;
    private TextView mDetailDescription;

    public static ExerciseDetailFragment newInstance(@Nullable String exerciseId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_EXERCISE_ID, exerciseId);
        ExerciseDetailFragment fragment = new ExerciseDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    public ExerciseDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_exercise_detail, container, false);
        setHasOptionsMenu(true);
        mDetailTitle = (TextView) root.findViewById(R.id.exercise_detail_title);
        mDetailDescription = (TextView) root.findViewById(R.id.exercise_detail_description);

        // Set up floating action button
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_exercise);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.editExercise();
            }
        });

        return root;
    }

    @Override
    public void setPresenter(@NonNull ExerciseDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if(active) {
            mDetailTitle.setText("");
            mDetailDescription.setText(getString(R.string.loading));
        }
    }

    @Override
    public void showMissingExercise() {
        mDetailTitle.setText("");
        mDetailDescription.setText(getString(R.string.no_data));
    }

    @Override
    public void hideTitle() {
        mDetailTitle.setVisibility(View.GONE);
    }

    @Override
    public void showTitle(String title) {
        mDetailTitle.setVisibility(View.VISIBLE);
        mDetailTitle.setText(title);
    }

    @Override
    public void hideDescription() {
        mDetailDescription.setVisibility(View.GONE);
    }

    @Override
    public void showDescription(String description) {
        mDetailDescription.setVisibility(View.VISIBLE);
        mDetailDescription.setText(description);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_EDIT_EXERCISE) {
            // If the task was edited successfully, go back to the title list.
            if(resultCode == Activity.RESULT_OK) {
                getActivity().finish();
            }
        }
    }

    @Override
    public void showEditExercise(String exerciseId) {
        Intent intent = new Intent(getContext(), AddEditExerciseActivity.class);
        intent.putExtra(AddEditExerciseFragment.ARGUMENT_EDIT_EXERCISE_ID, exerciseId);
        startActivityForResult(intent, REQUEST_EDIT_EXERCISE);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
