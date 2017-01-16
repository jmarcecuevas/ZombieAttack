package com.luckycode.zombieattack.ui.viewmodel;

/**
 * Created by MarceloCuevas on 19/12/2016.
 */

public interface RegisterView {
    void setAvailableInternet(boolean availableInternet);
    boolean isInternetAvailable();
    void showProgress();
    void hideProgress();
    void cancelProgress();
    void setInternetConnectionError();
    void dismissInternetConnectionError();
    void setEmptyNameError();
    void setEmptyUserNameError();
    void setEmptyPasswordError();
    void setEmptyEmailError();
    void setValidUserName(boolean validUserName);
    void setValidEmail(boolean validEmail);
    void setSuccessRegister();
    boolean isUserNameValid();
    boolean isEmailValid();
    void setInvalidUserNameError();
    void setInvalidPasswordError();
    void setInvalidEmailError();
    void setUserAlreadyExistsError();
    void backToLogin();
    void setUnknownError();
    void setValidPassword(boolean validPassword);
    boolean isPasswordValid();



}
