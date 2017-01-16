package com.luckycode.zombieattack.io.callback;

import android.location.Location;

/**
 * Created by MarceloCuevas on 22/12/2016.
 */

/** It's very important to register when user location changes and
 *  LocationApiManager is connected. This is the class
 *  in charge of managing it.
 */

public interface LocationCallback {
    void onLocationChanged(Location location);
    void onLocationApiManagerConnected();
}
