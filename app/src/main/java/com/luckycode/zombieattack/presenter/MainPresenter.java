package com.luckycode.zombieattack.presenter;

/**
 * Created by MarceloCuevas on 20/12/2016.
 */

public interface MainPresenter {
    void connectToLocationService();
    void disconnectFromLocationService();
    //void fetchCompanyLocations();
    void onMapReady();
    void registerGPSReceiver();
    void unRegisterGPSReceiver();
    void registerNetworkReceiver();
    void unRegisterNetworkReceiver();
}
