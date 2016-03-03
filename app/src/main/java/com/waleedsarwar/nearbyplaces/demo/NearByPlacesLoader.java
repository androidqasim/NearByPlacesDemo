package com.waleedsarwar.nearbyplaces.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Waleed Sarwar
 * @since February 23, 2016 03:47 PM
 */
public class NearByPlacesLoader extends Loader<List<NearbyPlace>> implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<PlaceLikelihoodBuffer> {
    private static final String TAG = "NearByPlacesLoader";

    private GoogleApiClient mGoogleApiClient;
    private List<NearbyPlace> mNearByPlaces;

    public NearByPlacesLoader(Context context, Activity activity) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        Log.d(TAG, "onStartLoading() called");
        if (mNearByPlaces != null) {
            deliverResult(mNearByPlaces);
        }
        if (mGoogleApiClient == null) {
            mGoogleApiClient =
                    new GoogleApiClient.Builder(getContext(), this, this)
                            .addApi(Places.PLACE_DETECTION_API)
                            .build();
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onForceLoad() {
        if (mNearByPlaces != null) {
            deliverResult(mNearByPlaces);
        }
        // Try to reconnect if we aren’t connected
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void deliverResult(List<NearbyPlace> nearbyPlaces) {
        mNearByPlaces = nearbyPlaces;
        super.deliverResult(mNearByPlaces);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected() called");
        callPlaceDetectionApi();
    }

    private void callPlaceDetectionApi() {
        Log.d(TAG, "callPlaceDetectionApi() called");
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                .getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended() called");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed() called with");

    }

    @Override
    public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
        Log.d(TAG, "onResult() called with: " + "likelyPlaces = [" + likelyPlaces.toString() + "]");
        List<NearbyPlace> nearbyPlaces = new ArrayList<>();
        for (PlaceLikelihood placeLikelihood : likelyPlaces) {
            NearbyPlace nearbyPlace = new NearbyPlace();
            Log.i(TAG, String.format("Place '%s' with " +
                            "likelihood: %g",
                    placeLikelihood.getPlace().getName(),
                    placeLikelihood.getLikelihood()));
            nearbyPlace.setName(placeLikelihood.getPlace().getName().toString());
            nearbyPlace.setLikelihood(placeLikelihood.getLikelihood());
            nearbyPlace.setAddress(placeLikelihood.getPlace().getAddress().toString());
            nearbyPlaces.add(nearbyPlace);


        }
        likelyPlaces.release();
        if (nearbyPlaces.size() > 0) {
            deliverResult(nearbyPlaces);
        }

    }
}
