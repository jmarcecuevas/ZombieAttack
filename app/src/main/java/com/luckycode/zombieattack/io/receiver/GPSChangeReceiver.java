package com.luckycode.zombieattack.io.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.luckycode.zombieattack.io.observer.GPSObservable;
import com.luckycode.zombieattack.utils.Mapbox.GPSStatusManager;

/**
 * Created by MarceloCuevas on 03/01/2017.
 */

/** It's a component which allows me to register for
 *  GPS signal events.
 */

public class GPSChangeReceiver extends BroadcastReceiver {
    private static String TAG= GPSChangeReceiver.class.getSimpleName();
    private Context context;
    private boolean GPSEnabled;

    public GPSChangeReceiver(Context context){
        this.context=context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
            GPSEnabled= GPSStatusManager.isGPSEnabled(context);
            if(GPSEnabled){
                Log.i(TAG,"GPS ENABLED");
                GPSObservable.getInstance().updateValue(true);
            }else{
                Log.i(TAG,"GPS DISABLED");
                GPSObservable.getInstance().updateValue(false);
            }
        }

    }
}
