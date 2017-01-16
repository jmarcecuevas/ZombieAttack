package com.luckycode.zombieattack.ui.viewmodel;

import android.location.Location;

import com.luckycode.zombieattack.io.model.NearbyPlayer;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.geometry.LatLng;

/**
 * Created by MarceloCuevas on 20/12/2016.
 */

public interface MainView {
    void vibrate();
    void setNickname(String nickname);
    void updateLocationOnMap(String userName,Location location);
    void showLocationOnMap(String userName,Location location);
    void showNearbyPlayerOnMap(NearbyPlayer nearbyPlayer);
    void updateNearbyPlayerLocation(Marker marker, LatLng location);
    void removeNearbyPlayerOnMap(NearbyPlayer nearbyPlayer);
    void setInternetConnectionError();
    void dismissInternetConnectionError();
    void setAvailableInternet(boolean availableInternet);
    boolean isInternetAvailable();
    void setGPSConnectionError();
    void dismissGPSConnectionError();
    void setAvailableGPS(boolean availableGPS);
    boolean isGPSAvailable();
}
