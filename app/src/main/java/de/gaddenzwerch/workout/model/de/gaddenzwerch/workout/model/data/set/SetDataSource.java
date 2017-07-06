package de.gaddenzwerch.workout.model.de.gaddenzwerch.workout.model.data.set;


import android.support.annotation.NonNull;

import java.util.List;
import java.util.UUID;

import de.gaddenzwerch.workout.model.Set;

public interface SetDataSource {

    interface LoadSetCallback {
        void onSetLoaded(List<Set> set);
        void onDataNotAvailable();
    }

    interface GetSetCallback {
        void onSetLoaded(Set set);
        void onDataNotAvailable();
    }

    void getSets(@NonNull LoadSetCallback callback);
    void getSet(@NonNull UUID setId, @NonNull GetSetCallback callback);
    void saveSet(@NonNull Set set);
    void updateSet(@NonNull Set set);
    void refreshSet();
}
