package com.luckycode.zombieattack.presenter;

import android.content.Context;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.luckycode.zombieattack.common.BasePresenter;
import com.luckycode.zombieattack.interactor.RegisterInteractor;
import com.luckycode.zombieattack.io.callback.EmailCallback;
import com.luckycode.zombieattack.io.callback.RegisterCallback;
import com.luckycode.zombieattack.io.observer.ObservableObject;
import com.luckycode.zombieattack.io.receiver.ConnectivityChangeReceiver;
import com.luckycode.zombieattack.ui.viewmodel.MainLoginView;
import com.luckycode.zombieattack.utils.SettingsManager;

import java.util.Observable;

/**
 * Created by MarceloCuevas on 08/01/2017.
 */

public class MainLoginPresenterImp extends BasePresenter implements MainLoginPresenter,RegisterCallback,EmailCallback{

    private static String TAG=MainLoginPresenterImp.class.getSimpleName();
    private Context context;
    private MainLoginView mainLoginView;
    private ConnectivityChangeReceiver connectivityChangeReceiver;
    private RegisterInteractor registerInteractor;
    private SettingsManager settingsManager;

    public MainLoginPresenterImp(Context context,MainLoginView mainLoginView,RegisterInteractor registerInteractor,ConnectivityChangeReceiver connectivityChangeReceiver){
        this.context=context;
        this.mainLoginView=mainLoginView;
        this.registerInteractor= registerInteractor;
        this.connectivityChangeReceiver=connectivityChangeReceiver;
    }

    @Override
    public void registerReceiver() {
        ObservableObject.getInstance().addObserver(this);
        IntentFilter internetFilter = new IntentFilter();
        internetFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(connectivityChangeReceiver,internetFilter);
    }

    @Override
    public void unregisterReceiver() {
        context.unregisterReceiver(connectivityChangeReceiver);
    }

    @Override
    public void onStart() {}

    @Override
    public void onStop() {}

    @Override
    public void onPause() {unregisterReceiver();}

    @Override
    public void onResume() {registerReceiver();}

    @Override
    public void onDestroy() {mainLoginView=null;}

    @Override
    public void update(Observable observable, Object o) {
        if(mainLoginView!=null){
            if(ObservableObject.getInstance().getValue().equals("DISCONNECTED_INTERNET")){
                mainLoginView.setInternetConnectionError();
                mainLoginView.setAvailableInternet(false);
            }else if(ObservableObject.getInstance().getValue().equals("CONNECTED_INTERNET")){
                mainLoginView.dismissInternetConnectionError();
                mainLoginView.setAvailableInternet(true);
            }
        }
    }

    @Override
    public void handleSignInWithGoogleResult(GoogleSignInResult result) {
        if(result.isSuccess()) {
            GoogleSignInAccount acc = result.getSignInAccount();
            registerInteractor.checkEmail(acc, this);
        }
    }

    @Override
    public void register(String name,String nickName,String email) {
        Log.e(TAG,nickName);
        if(TextUtils.isEmpty(nickName)){
            mainLoginView.setEmptyNicknameError();
        }else if(nickName.length()<6){
            mainLoginView.setShortNicknameError();
        }else{
           registerInteractor.register(name,nickName,email,"",this);
        }
    }

    @Override
    public void onSuccessRegister() {
        mainLoginView.setSuccessRegister();
    }

    @Override
    public void onUserAlreadyExists() {
        mainLoginView.setInvalidNicknameError();
    }

    @Override
    public void onUnknownError() {}

    @Override
    public void onEmailAlreadyExists(GoogleSignInAccount acc,String userName) {
        mainLoginView.showProgress();
        mainLoginView.setSuccessLogin(acc,userName);
        settingsManager= new SettingsManager(context,userName);
    }

    @Override
    public void onEmailOK(GoogleSignInAccount acc) {
        mainLoginView.enterNickname(acc);
    }
}
