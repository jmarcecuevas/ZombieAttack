package com.luckycode.zombieattack.presenter;

import android.widget.EditText;

/**
 * Created by MarceloCuevas on 19/12/2016.
 */

public interface RegisterPresenter {
    void validateUserName(EditText userName);
    void validatePassword(EditText password);
    void register(String name, String userName,String email,String password);
    void registerReceiver();
    void unRegisterReceiver();
}
