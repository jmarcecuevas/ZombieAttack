package com.luckycode.zombieattack.ui.viewmodel;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by MarceloCuevas on 07/01/2017.
 */

public interface MainLoginView {
    void setAvailableInternet(boolean availableInternet);
    boolean isInternetAvailable();
    void showProgress();
    void hideProgress();
    void setSuccessLogin(GoogleSignInAccount acc,String userName);
    void setSuccessRegister();
    void setEmptyNicknameError();
    void setShortNicknameError();
    void setInvalidNicknameError();
    void setInternetConnectionError();
    void dismissInternetConnectionError();
    void signInWithLuckyCode();
    void signInWithGoogle();
    void enterNickname(GoogleSignInAccount acc);
}
