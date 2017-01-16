package com.luckycode.zombieattack.io.callback;

import com.luckycode.zombieattack.io.model.NearbyPlayer;

import java.util.ArrayList;

/**
 * Created by MarceloCuevas on 29/12/2016.
 */

public interface NearbyUsersCallback {
    void onEmptyNearbyUsersSearch();
    void onNearbyUsersFound(ArrayList<NearbyPlayer> nearbyUsers);

}
