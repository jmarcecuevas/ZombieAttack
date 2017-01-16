package com.luckycode.zombieattack.presenter;

import android.content.Context;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.luckycode.zombieattack.common.BasePresenter;
import com.luckycode.zombieattack.interactor.LoginInteractor;
import com.luckycode.zombieattack.io.callback.LoginCallback;
import com.luckycode.zombieattack.io.observer.ObservableObject;
import com.luckycode.zombieattack.io.receiver.ConnectivityChangeReceiver;
import com.luckycode.zombieattack.ui.viewmodel.LoginView;

import java.util.Observable;

/**
 * Created by Marcelo Cuevas on 10/12/2016.
 */

public class LoginPresenterImp extends BasePresenter implements LoginPresenter,LoginCallback {

    private LoginView loginView;
    private LoginInteractor loginInteractor;
    private ConnectivityChangeReceiver connectivityChangeReceiver;
    private Context context;

    public LoginPresenterImp(Context context,LoginView view, LoginInteractor interactor, ConnectivityChangeReceiver connectivityChangeReceiver) {
        this.context=context;
        loginView = view;
        loginInteractor = interactor;
        this.connectivityChangeReceiver=connectivityChangeReceiver;
    }

    @Override
    public void onStart() {}

    @Override
    public void onStop() {
        loginView.cancelProgress();
    }

    @Override
    public void onPause() {
        unregisterReceiver();
    }

    @Override
    public void onResume() {
        registerReceiver();
    }

    @Override
    public void onDestroy() {
        loginView=null;
    }

    @Override
    public void onNetworkError(TextView textView) {
        Log.e("NETWORK ERROR","WHILE HTTP REQUEST");
    }

    @Override
    public void onServerError() {
        Log.e("SERVER ERROR","WHILE HTTP REQUEST");
    }

    @Override
    public void onSuccessLogin() {
        loginView.hideProgress();
        loginView.setSuccessLogin();
    }

    @Override
    public void onFailedLogin() {
        loginView.hideProgress();
        loginView.setInvalidCredentialsError();
    }

    /** Validate credentials to sign in **/
    @Override
    public void validateCredentials(String userName, String password) {
        if(TextUtils.isEmpty(userName)|| TextUtils.isEmpty(password)){
            if(TextUtils.isEmpty(userName))
                loginView.setUserNameError();
            if(TextUtils.isEmpty(password))
                loginView.setPasswordError();
        }else{
            loginView.showProgress();
            loginInteractor.login(userName,password,this);
        }
    }

    /** Register a broadcast receiver to be notified when an internet change event occurs.**/
    @Override
    public void registerReceiver() {
        ObservableObject.getInstance().addObserver(this);
        IntentFilter internetFilter = new IntentFilter();
        internetFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(connectivityChangeReceiver,internetFilter);
    }

    /**Unregister internet broadcast receiver **/
    @Override
    public void unregisterReceiver() {
        context.unregisterReceiver(connectivityChangeReceiver);
    }


    @Override
    public void update(Observable o, Object arg) {
        if(loginView!=null){
            if(ObservableObject.getInstance().getValue().equals("DISCONNECTED_INTERNET")){
                loginView.setInternetConnectionError();
                loginView.setAvailableInternet(false);
            }else if(ObservableObject.getInstance().getValue().equals("CONNECTED_INTERNET")){
                loginView.dismissInternetConnectionError();
                loginView.setAvailableInternet(true);
            }
        }
    }
}
