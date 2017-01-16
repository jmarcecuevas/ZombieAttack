package com.luckycode.zombieattack.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by MarceloCuevas on 16/12/2016.
 */

/** This class returns internet connectivity status **/

public class ConnectivityStatusManager {
    private static int NOT_CONNECTED=0;
    private static int CONNECTED=1;

    public static int getConnectivityStatus(Context context){
        ConnectivityManager mConnectivityManager= (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = mConnectivityManager.getActiveNetworkInfo();

        if(activeNetwork!=null && activeNetwork.isAvailable() && activeNetwork.isConnected()){
            return CONNECTED;
        }else
            return NOT_CONNECTED;
    }

    public static boolean isInternetAvailable(Context context){
        int status= getConnectivityStatus(context);
        if(status== CONNECTED)
            return true;
        return false;
    }
}
