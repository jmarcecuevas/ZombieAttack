package com.luckycode.zombieattack.ui.viewmodel;

/**
 * Created by t3ch on 03/12/2016.
 */

public interface LoginView {
    void setAvailableInternet(boolean availableInternet);
    boolean isInternetAvailable();
    void showProgress();
    void hideProgress();
    void cancelProgress();
    void setInternetConnectionError();
    void dismissInternetConnectionError();
    void setUserNameError();
    void setPasswordError();
    void setInvalidCredentialsError();
    void setSuccessLogin();
    void goToRegister();
    void backToMainLogin();
}
