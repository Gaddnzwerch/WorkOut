package de.gaddenzwerch.workout.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.gaddenzwerch.workout.R;
import de.gaddenzwerch.workout.model.Exercise;
import de.gaddenzwerch.workout.presenter.ExerciseContract;

/**
 * Display a grid of {@link de.gaddenzwerch.workout.model.Exercise}s.
 */
public class ExerciseFragment extends Fragment implements ExerciseContract.View {

    private ExerciseContract.Presenter mPresenter;
    private ExercisesAdapter mListAdapter;
    private View mNoExerciseView;
    private ImageView mNoExerciseIcon;
    private TextView mNoExerciseMainView;
    private TextView mNoExerciseAddView;
    private LinearLayout mExerciseView;

    public ExerciseFragment() {
        // Required empty public constructor
    }

    public static ExerciseFragment newInstance() {
        return new ExerciseFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new ExercisesAdapter(new ArrayList<Exercise>(0), mItemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull ExerciseContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_exercise, container, false);

        // Set up tasks view
        ListView listView = (ListView) root.findViewById(R.id.exercise_list);
        listView.setAdapter(mListAdapter);
        mExerciseView = (LinearLayout) root.findViewById(R.id.exercisesLL);

        // Set up no tasks view
        mNoExerciseView = root.findViewById(R.id.noExercises);
        mNoExerciseIcon = (ImageView) root.findViewById(R.id.noExercisesIcon);
        mNoExerciseMainView = (TextView) root.findViewById(R.id.noExercisesMain);
        mNoExerciseAddView = (TextView) root.findViewById(R.id.noExercisesAdd);
        mNoExerciseAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddExercise();
            }
        });

        //TODO Set up floating action button
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_exercise);

        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addNewExercise();
            }
        });

        // Set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                (ScrollChildSwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(listView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadExercises(false);
            }
        });

        setHasOptionsMenu(true);

        return root;
    }

    /**
     * Listener for clicks on tasks in the ListView
     */
    ExerciseItemListener mItemListener = new ExerciseItemListener() {
        @Override
        public void onExerciseClick(Exercise clickedExercise) {
            mPresenter.openExerciseDetails(clickedExercise);
        }
    };

    @Override
    public void setLoadingIndicator(final boolean active) {
        if(getView() == null) {
            return;
        }

        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showExercises(List<Exercise> exercises) {
        mListAdapter.replaceData(exercises);

        mExerciseView.setVisibility(View.VISIBLE);
        mNoExerciseView.setVisibility(View.GONE);
    }

    @Override
    public void showAddExercise() {
        Intent intent = new Intent(getContext(), AddEditExerciseActivity.class);
        startActivityForResult(intent, AddEditExerciseActivity.REQUEST_ADD_TASK);
    }

    @Override
    public void showExerciseDetailsUi(String exerciseId) {
        Intent intent = new Intent(getContext(), ExerciseDetailActivity.class);
        intent.putExtra(ExerciseDetailActivity.EXTRA_EXERCISE_ID, exerciseId);
        startActivity(intent);

    }

    @Override
    public void showNoExercises() {
        showNoExercisesViews(
                getResources().getString(R.string.no_exercises_all),
                R.drawable.ic_verified_user_24dp,
                false
        );
    }

    private void showNoExercisesViews(String mainText, int iconRes, boolean showAddView) {
        mExerciseView.setVisibility(View.GONE);
        mNoExerciseView.setVisibility(View.VISIBLE);

        mNoExerciseMainView.setText(mainText);
        mNoExerciseIcon.setImageDrawable(getResources().getDrawable(iconRes));
        mNoExerciseAddView.setVisibility(showAddView ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showLoadingExercisesError() {
        showMessage(getString(R.string.loading_exercises_error));
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


    private static class ExercisesAdapter extends BaseAdapter {
        private List<Exercise> mExercises;
        private ExerciseItemListener mExerciseItemListener;

        public ExercisesAdapter(List<Exercise> exercises, ExerciseItemListener itemListener) {
            setList(exercises);
            mExerciseItemListener = itemListener;
        }

        public void replaceData(List<Exercise> exercises) {
            setList(exercises);
            notifyDataSetChanged();
        }

        private void setList(List<Exercise> exercises) {
            mExercises = exercises;
        }

        @Override
        public int getCount() {
            return mExercises.size();
        }

        @Override
        public Exercise getItem(int i) {
            return mExercises.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if(rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.exercise_item, viewGroup, false);
            }

            final Exercise exercise = getItem(i);

            TextView titleTV = (TextView) rowView.findViewById(R.id.title);
            titleTV.setText(exercise.getExerciseName());

            // No listeners for actions needed at the moment
            rowView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    mExerciseItemListener.onExerciseClick(exercise);
                }
            });

            return rowView;
        }

    }

    public interface ExerciseItemListener {
        void onExerciseClick(Exercise clickedExercise);
    }
}
