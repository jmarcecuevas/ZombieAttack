package com.luckycode.zombieattack.presenter;

/**
 * Created by MarceloCuevas on 16/12/2016.
 */

public interface LoginPresenter {
    void validateCredentials(String userName,String password);
    void registerReceiver();
    void unregisterReceiver();
}
