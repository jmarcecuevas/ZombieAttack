package com.luckycode.zombieattack.io.callback;

/**
 * Created by Marcelo Cuevas on 10/12/2016.
 */

/** Callback to register sign in results **/

public interface LoginCallback extends ServerCallback{
    void onSuccessLogin();
    void onFailedLogin();
}
