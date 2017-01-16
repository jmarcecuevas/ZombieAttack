package com.luckycode.zombieattack.utils.Mapbox;

import android.content.Context;
import android.location.LocationManager;

/**
 * Created by MarceloCuevas on 03/01/2017.
 */

public class GPSStatusManager {
    private static int ENABLED=1;
    private static int DISABLED=0;


    public static int getGPStatus(Context context){
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            return ENABLED;
        }
        return DISABLED;
    }

    public static boolean isGPSEnabled(Context context){
        int status=getGPStatus(context);
        if(status==ENABLED){
            return true;
        }else
            return false;
    }

}
