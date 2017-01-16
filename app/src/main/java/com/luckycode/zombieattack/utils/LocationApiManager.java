package com.luckycode.zombieattack.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.luckycode.zombieattack.io.callback.LocationCallback;
import com.luckycode.zombieattack.utils.Mapbox.GPSStatusManager;

/**
 * Created by MarceloCuevas on 22/12/2016.
 */

public class LocationApiManager implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<LocationSettingsResult>,LocationListener{

    private static final String TAG= LocationApiManager.class.getSimpleName();
    private static final int LOCATION_REQUEST_INTERVAL=10000;
    private static final int LOCATION_FASTEST_REQUEST_INTERVAL=4000;
    private static final int LOCATION_REQUEST_PRIORITY= LocationRequest.PRIORITY_HIGH_ACCURACY;

    private GoogleApiClient mGoogleApiClient;
    private LocationSettingsRequest.Builder mLocationSettingsRequestBuilder;
    private Location mLastKnownLocation;
    private LocationRequest mLocationRequest;
    private PendingResult<LocationSettingsResult> pendingResult;
    private Context mContext;
    private LocationCallback mLocationCallback;

    public LocationApiManager(Context context,GoogleApiClient googleApiClient,LocationRequest locationRequest,
                              LocationSettingsRequest.Builder locationSettingsRequestBuilder){
        mContext=context;
        mGoogleApiClient=googleApiClient;
        mGoogleApiClient.registerConnectionCallbacks(this);
        mGoogleApiClient.registerConnectionFailedListener(this);
        mLocationRequest=locationRequest;
        mLocationRequest.setInterval(LOCATION_REQUEST_INTERVAL);
        mLocationRequest.setFastestInterval(LOCATION_FASTEST_REQUEST_INTERVAL);
        mLocationRequest.setPriority(LOCATION_REQUEST_PRIORITY);
        mLocationSettingsRequestBuilder= locationSettingsRequestBuilder;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected: hit");

        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onConnected: permissions not granted");
            return;
        }

        pendingResult = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                mLocationSettingsRequestBuilder.build());
//        pendingResult.setResultCallback(this);

        mLastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(GPSStatusManager.isGPSEnabled(mContext))
            mLocationCallback.onLocationApiManagerConnected();

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

        if (mLastKnownLocation != null) {
            Log.i(TAG, "onConnected: lat " + mLastKnownLocation.getLatitude());
            Log.i(TAG, "onConnected: long " + mLastKnownLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended: hit");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: hit");
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        Log.d(TAG, "onResult: hit");
        final Status status = locationSettingsResult.getStatus();

        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.i(TAG, "onResult: status - SUCCESS");
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.i(TAG, "onResult: status - RESOLUTION_REQUIRED");
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.i(TAG, "onResult: status - SETTINGS_CHANGE_UNAVAILABLE");
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged: hit");
        mLastKnownLocation = location;

        if(mLocationCallback != null)
            mLocationCallback.onLocationChanged(location);
    }

    /**
     * Connect to {@link LocationServices}
     */
    public void connect() {
        Log.d(TAG, "connect: hit");
        mGoogleApiClient.connect();
    }


    /**
     * Disconnect from {@link LocationServices}
     */
    public void disconnect() {
        Log.d(TAG, "disconnect: hit");
            mGoogleApiClient.disconnect();
    }

    public void removeLocationUpdates(){
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
    }


    public GoogleApiClient getmGoogleApiClient() {
        return mGoogleApiClient;
    }

    public boolean isConnectionEstanblished() {
        return mGoogleApiClient.isConnected();
    }

    public void setLocationCallback(LocationCallback locationCallback) {
        this.mLocationCallback = locationCallback;
    }

    public Location getmLastKnownLocation(){
        return mLastKnownLocation;
    }
}
