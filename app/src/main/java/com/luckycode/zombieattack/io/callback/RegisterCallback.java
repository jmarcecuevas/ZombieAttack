package com.luckycode.zombieattack.io.callback;

/**
 * Created by MarceloCuevas on 19/12/2016.
 */

public interface RegisterCallback {
    void onSuccessRegister();
    void onUserAlreadyExists();
    void onUnknownError();
}
