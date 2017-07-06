package de.gaddenzwerch.workout.model.de.gaddenzwerch.workout.model.data.set;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.gaddenzwerch.workout.model.Set;

public class SetRepository implements SetDataSource {
    private static SetRepository INSTANCE = null;
    private final SetDataSource mSetDataSource;

    Map<UUID, Set> mCachedSets;
    boolean mCacheIsDirty = false;

    private SetRepository(@NonNull SetDataSource setsLocalDataSource) {
        mSetDataSource = setsLocalDataSource;
    }

    public static SetRepository getInstance(SetDataSource setLocalDataSource) {
        if(INSTANCE == null) {
            INSTANCE = new SetRepository(setLocalDataSource);
        }
        return INSTANCE;
    }

    public void destroyInstance() { INSTANCE = null; }

    @Override
    public void getSets(@NonNull final LoadSetCallback callback) {
        // Respond immediately with cache if available and not dirty
        if(mCachedSets != null && !mCacheIsDirty) {
            callback.onSetLoaded(new ArrayList<Set>(mCachedSets.values()));
            return;
        }

        if(mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network
            // Not necessary as there currently is no data stored in network.
        } else {
            // Query the local storage if available.
            mSetDataSource.getSets(new LoadSetCallback() {
                @Override
                public void onSetLoaded(List<Set> sets) {
                    refreshCache(sets);
                    callback.onSetLoaded(new ArrayList<Set>(mCachedSets.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
                }
            });
        }

    }

    private void refreshCache(List<Set> sets) {
        createCachedSetIfNecessary();
        mCachedSets.clear();

        for(Set set: sets) {
            mCachedSets.put(set.getId(), set);
        }

        mCacheIsDirty = false;
    }

    private void createCachedSetIfNecessary() {
        if(mCachedSets == null) {
            mCachedSets = new LinkedHashMap<>();
        }
    }

    @Override
    public void getSet(@NonNull UUID setId, @NonNull final GetSetCallback callback) {
        Set cachedSet = getSetWithId(setId);

        if(cachedSet != null) {
            callback.onSetLoaded(cachedSet);
            return;
        }

        mSetDataSource.getSet(setId, new GetSetCallback() {
            @Override
            public void onSetLoaded(Set set) {
                createCachedSetIfNecessary();
                mCachedSets.put(set.getId(), set);
                callback.onSetLoaded(set);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Nullable
    private Set getSetWithId(@NonNull UUID setId) {
        if(mCachedSets == null || mCachedSets.isEmpty()) {
            return null;
        } else {
            return mCachedSets.get(setId);
        }
    }

    @Override
    public void saveSet(@NonNull Set set) {
        mSetDataSource.saveSet(set);

        createCachedSetIfNecessary();
        mCachedSets.put(set.getId(), set);
    }

    @Override
    public void updateSet(@NonNull Set set) {
        mSetDataSource.updateSet(set);

        Set currentSet = new Set(set);

        createCachedSetIfNecessary();
        mCachedSets.put(set.getId(), currentSet);
    }

    @Override
    public void refreshSet() {
        mCacheIsDirty = true;
    }
}
