package com.waleedsarwar.nearbyplaces.demo;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Waleed Sarwar
 * @since February 23, 2016 03:34 PM
 */
public class NearByPlacesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NearbyPlace>> {
    private static final String TAG = "NearByPlacesActivity";
    private static final int PLACES_DETECTION_LOADER = 0;
    private RecyclerView mNearByPlacesRecyclerView;
    private NearByPlacesAdapter mNearByPlacesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_nearby_places);

        mNearByPlacesRecyclerView = (RecyclerView) findViewById(R.id.places_detection_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mNearByPlacesRecyclerView.setLayoutManager(mLayoutManager);
        mNearByPlacesAdapter = new NearByPlacesAdapter(this, new ArrayList<NearbyPlace>());
        mNearByPlacesRecyclerView.setAdapter(mNearByPlacesAdapter);
        mNearByPlacesRecyclerView.setItemAnimator(new DefaultItemAnimator());


        getSupportLoaderManager().initLoader(PLACES_DETECTION_LOADER, null, this);

    }


    @Override
    public Loader<List<NearbyPlace>> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader() called");
        return new NearByPlacesLoader(this, this);
    }

    @Override
    public void onLoadFinished(Loader<List<NearbyPlace>> loader, List<NearbyPlace> nearbyPlaces) {
        Log.d(TAG, "onLoadFinished() called");
        mNearByPlacesAdapter.addPlaces(nearbyPlaces);
    }

    @Override
    public void onLoaderReset(Loader<List<NearbyPlace>> loader) {
        Log.d(TAG, "onLoaderReset() called");
        mNearByPlacesAdapter.removeNearByPlaces();
    }
}
