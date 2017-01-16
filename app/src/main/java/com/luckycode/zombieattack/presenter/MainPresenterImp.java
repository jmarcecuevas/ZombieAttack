package com.luckycode.zombieattack.presenter;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;

import com.luckycode.zombieattack.common.BasePresenter;
import com.luckycode.zombieattack.interactor.MainInteractor;
import com.luckycode.zombieattack.io.callback.LocationCallback;
import com.luckycode.zombieattack.io.callback.NearbyUsersCallback;
import com.luckycode.zombieattack.io.model.NearbyPlayer;
import com.luckycode.zombieattack.io.observer.GPSObservable;
import com.luckycode.zombieattack.io.observer.ObservableObject;
import com.luckycode.zombieattack.io.receiver.ConnectivityChangeReceiver;
import com.luckycode.zombieattack.io.receiver.GPSChangeReceiver;
import com.luckycode.zombieattack.ui.viewmodel.MainView;
import com.luckycode.zombieattack.utils.LocationApiManager;
import com.luckycode.zombieattack.utils.Mapbox.MarkersManager;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by MarceloCuevas on 20/12/2016.
 */

public class MainPresenterImp extends BasePresenter implements MainPresenter,LocationCallback,NearbyUsersCallback{

    private static final String TAG = MainPresenterImp.class.getSimpleName();
    private SharedPreferences sharedPreferences;
    private String userName;
    private Context context;
    private MainView mainView;
    private MainInteractor interactor;
    private LocationApiManager locationApiManager;
    private MarkersManager markersManager;
    private ArrayList<NearbyPlayer> nearbyPlayers;
    private GPSChangeReceiver gpsChangeReceiver;
    private ConnectivityChangeReceiver connectivityChangeReceiver;

    public MainPresenterImp(Context context,GPSChangeReceiver gpsChangeReceiver, ConnectivityChangeReceiver connectivityChangeReceiver,
                            MainView mainView, MarkersManager markersManager,MainInteractor interactor, LocationApiManager locationApiManager){
        this.context=context;
        this.gpsChangeReceiver=gpsChangeReceiver;
        this.connectivityChangeReceiver=connectivityChangeReceiver;
        this.mainView=mainView;
        this.interactor=interactor;
        this.locationApiManager=locationApiManager;
        this.locationApiManager.setLocationCallback(this);
        this.markersManager=markersManager;
        setUserName();
        mainView.setNickname(userName);
    }

    private void setUserName(){
        sharedPreferences= context.getSharedPreferences("MyPref",context.MODE_PRIVATE);
        userName=sharedPreferences.getString("userName","NULL");
        sharedPreferences=context.getSharedPreferences(userName,context.MODE_PRIVATE);
    }

    @Override
    public void onStart() {}

    @Override
    public void onStop() {
        disconnectFromLocationService();
    }

    @Override
    public void onPause() {
        unRegisterGPSReceiver();
        unRegisterNetworkReceiver();
        if(locationApiManager.isConnectionEstanblished())
            locationApiManager.removeLocationUpdates();
        GPSObservable.getInstance().deleteObserver(this);
        ObservableObject.getInstance().deleteObserver(this);
    }

    @Override
    public void onResume() {
        connectToLocationService();
        registerGPSReceiver();
        registerNetworkReceiver();
    }

    @Override
    public void onDestroy() {
        mainView=null;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(mainView!=null){
            if(!GPSObservable.getInstance().getValue()){
                mainView.setGPSConnectionError();
                mainView.setAvailableGPS(false);
            }else if(GPSObservable.getInstance().getValue()){
                mainView.dismissGPSConnectionError();
                mainView.setAvailableGPS(true);
            }
            if(ObservableObject.getInstance().getValue().equals("DISCONNECTED_INTERNET")){
                mainView.setInternetConnectionError();
                mainView.setAvailableInternet(false);
            }else if(ObservableObject.getInstance().getValue().equals("CONNECTED_INTERNET")){
                mainView.dismissInternetConnectionError();
                mainView.setAvailableInternet(true);
            }
        }
    }

    @Override
    public void connectToLocationService() {
        Log.d(TAG, "connectToLocationService: hit");
        locationApiManager.connect();
    }

    @Override
    public void disconnectFromLocationService() {
        Log.d(TAG, "disconnectFromLocationService: hit");
        locationApiManager.disconnect();
    }

    @Override
    public void onMapReady() {
        Log.e("MAP SUUUUPER","READY");
        connectToLocationService();
    }

    /** Register a BroadCastReceiver for
     * GPS status changes events **/
    @Override
    public void registerGPSReceiver() {
        GPSObservable.getInstance().addObserver(this);
        ObservableObject.getInstance().addObserver(this);
        IntentFilter intentFilterGPS= new IntentFilter();
        intentFilterGPS.addAction("android.location.PROVIDERS_CHANGED");
        context.getApplicationContext().registerReceiver(gpsChangeReceiver,intentFilterGPS);
    }

    @Override
    public void unRegisterGPSReceiver() {
        context.getApplicationContext().unregisterReceiver(gpsChangeReceiver);
    }

    @Override
    public void registerNetworkReceiver() {
        IntentFilter intentFilterNetwork= new IntentFilter();
        intentFilterNetwork.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(connectivityChangeReceiver,intentFilterNetwork);
    }

    @Override
    public void unRegisterNetworkReceiver() {
        context.unregisterReceiver(connectivityChangeReceiver);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null){
            mainView.updateLocationOnMap(userName,location);
            interactor.updateLocation(userName,location.getLatitude(),location.getLongitude(),
                    sharedPreferences.getInt("searchRadius",70),this);
        }
    }

    @Override
    public void onLocationApiManagerConnected() {
        mainView.showLocationOnMap(userName,locationApiManager.getmLastKnownLocation());
    }

    @Override
    public void onEmptyNearbyUsersSearch() {
        Log.i(TAG,"NO USERS FOUND");
    }

    /** It handles nearby users found. Updates their positions,
     * draws new users and deletes them when It's necessary **/
    @Override
    public void onNearbyUsersFound(ArrayList<NearbyPlayer> nearbyUsers) {
        nearbyPlayers=markersManager.validateArray(nearbyUsers);

        for(int i=0;i<nearbyPlayers.size();i++){
            if(nearbyPlayers.get(i).isInRange()){
                if(nearbyPlayers.get(i).isUpdated()){
                    mainView.updateNearbyPlayerLocation(nearbyPlayers.get(i).getMarker(),new LatLng(nearbyPlayers.get(i).getLat(),
                            nearbyPlayers.get(i).getLng()));
                }else{
                    mainView.showNearbyPlayerOnMap(nearbyPlayers.get(i));
                    mainView.vibrate();
                }
            }else{
                mainView.removeNearbyPlayerOnMap(nearbyPlayers.get(i));
                markersManager.delete(i);
            }
        }
        markersManager.setOutOfRange();
    }
}
