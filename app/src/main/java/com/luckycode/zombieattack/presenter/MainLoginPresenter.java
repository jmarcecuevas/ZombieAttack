package com.luckycode.zombieattack.presenter;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;

/**
 * Created by MarceloCuevas on 08/01/2017.
 */

public interface MainLoginPresenter {
    void registerReceiver();
    void unregisterReceiver();
    void handleSignInWithGoogleResult(GoogleSignInResult result);
    void register(String name,String nickName,String email);
}
