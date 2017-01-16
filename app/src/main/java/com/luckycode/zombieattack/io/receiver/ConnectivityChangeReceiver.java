package com.luckycode.zombieattack.io.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.luckycode.zombieattack.io.observer.ObservableObject;
import com.luckycode.zombieattack.utils.ConnectivityStatusManager;

/**
 * Created by MarceloCuevas on 16/12/2016.
 */

/** It's a component which allows me to register for
 *  internet connectivity events.
 */

public class ConnectivityChangeReceiver extends BroadcastReceiver {
    private static String TAG= ConnectivityChangeReceiver.class.getSimpleName();
    private Context context;
    private boolean availableInternet;

    public ConnectivityChangeReceiver(Context context){
        this.context=context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        availableInternet= ConnectivityStatusManager.isInternetAvailable(context);
        if(availableInternet){
            Log.i(TAG,"INTERNET AVAILABLE");
            ObservableObject.getInstance().updateValue("CONNECTED_INTERNET");
        }else{
            Log.i(TAG,"INTERNET NOT AVAILABLE");
            ObservableObject.getInstance().updateValue("DISCONNECTED_INTERNET");
        }
    }

}
