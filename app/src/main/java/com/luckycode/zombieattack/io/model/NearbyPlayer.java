package com.luckycode.zombieattack.io.model;

import com.mapbox.mapboxsdk.annotations.Marker;

/**
 * Created by MarceloCuevas on 29/12/2016.
 */

public class NearbyPlayer {

    private Marker marker;
    private String userName;
    private double lat;
    private double lng;
    private boolean inRange;
    private boolean updated;

    public NearbyPlayer(String userName, double lat, double lng){
        setUserName(userName);
        setLat(lat);
        setLng(lng);
        setInRange(true);
    }

    public Marker getMarker(){
        return marker;
    }

    public void setMarker(Marker marker){
        this.marker=marker;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isUpdated(){
        return updated;
    }

    public void setUpdated(boolean updated){
        this.updated=updated;
    }

    public boolean isInRange(){
        return inRange;
    }

    public void setInRange(boolean inRange){
        this.inRange=inRange;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
