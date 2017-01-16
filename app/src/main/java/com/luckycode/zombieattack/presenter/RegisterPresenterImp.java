package com.luckycode.zombieattack.presenter;

import android.content.Context;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.widget.EditText;

import com.luckycode.zombieattack.common.BasePresenter;
import com.luckycode.zombieattack.interactor.RegisterInteractor;
import com.luckycode.zombieattack.io.callback.RegisterCallback;
import com.luckycode.zombieattack.io.observer.ObservableObject;
import com.luckycode.zombieattack.io.receiver.ConnectivityChangeReceiver;
import com.luckycode.zombieattack.ui.adapter.TextWatcherAdapter;
import com.luckycode.zombieattack.ui.viewmodel.RegisterView;

import java.util.Observable;

/**
 * Created by MarceloCuevas on 19/12/2016.
 */

public class RegisterPresenterImp extends BasePresenter implements RegisterPresenter,RegisterCallback {

    private RegisterView registerView;
    private Context context;
    private ConnectivityChangeReceiver connectivityChangeReceiver;
    private RegisterInteractor registerInteractor;

    public RegisterPresenterImp (Context context,RegisterView registerView,RegisterInteractor registerInteractor,ConnectivityChangeReceiver connectivityChangeReceiver){
        this.context= context;
        this.registerView= registerView;
        this.registerInteractor= registerInteractor;
        this.connectivityChangeReceiver= connectivityChangeReceiver;
    }

    @Override
    public void onStart() {}

    @Override
    public void onStop() { registerView.cancelProgress();}

    @Override
    public void onPause() {
        unRegisterReceiver();
    }

    @Override
    public void onResume() {
        registerReceiver();
    }

    @Override
    public void onDestroy() { registerView=null;}

    /** Validate nickname **/
    @Override
    public void validateUserName(EditText userName) {
        userName.addTextChangedListener(new TextWatcherAdapter(userName) {
            @Override
            public void validate(EditText editText, String text) {
                if(text.length()<6){
                    registerView.setInvalidUserNameError();
                    registerView.setValidUserName(false);
                }else{
                    registerView.setValidUserName(true);
                }
            }
        });
    }

    /**Validate password **/
    @Override
    public void validatePassword(EditText password) {
        password.addTextChangedListener(new TextWatcherAdapter(password) {
            @Override
            public void validate(EditText editText, String text) {
                if(text.length()<6){
                    registerView.setValidPassword(false);
                    registerView.setInvalidPasswordError();
                }else{
                    registerView.setValidPassword(true);
                }
            }
        });
    }

    @Override
    public void register(String name, String userName, String email, String password) {
        if(TextUtils.isEmpty(email))
            registerView.setEmptyEmailError();
        else if(isValidEmail(email)){
            registerView.setValidEmail(true);
        }else if(!isValidEmail(email)){
            registerView.setInvalidEmailError();
            registerView.setValidEmail(false);
        }
        if(TextUtils.isEmpty(name)||TextUtils.isEmpty(userName)||TextUtils.isEmpty(password)) {
            if (TextUtils.isEmpty(name))
                registerView.setEmptyNameError();
            if (TextUtils.isEmpty(userName))
                registerView.setEmptyUserNameError();
            if (TextUtils.isEmpty(password)) {
                registerView.setEmptyPasswordError();
            }
        }else if(registerView.isUserNameValid() && registerView.isPasswordValid() && registerView.isEmailValid()){
            registerView.showProgress();
            registerInteractor.register(name,userName,email,password,this);
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    /** BroadCast Receiver for internet connectivity changes events **/
    @Override
    public void registerReceiver() {
        ObservableObject.getInstance().addObserver(this);
        IntentFilter internetFilter = new IntentFilter();
        internetFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(connectivityChangeReceiver,internetFilter);
    }

    @Override
    public void unRegisterReceiver() {
        context.unregisterReceiver(connectivityChangeReceiver);
    }


    @Override
    public void onSuccessRegister() {
        registerView.hideProgress();
        registerView.setSuccessRegister();
    }

    @Override
    public void onUserAlreadyExists() {
        registerView.hideProgress();
        registerView.setUserAlreadyExistsError();
    }

    @Override
    public void onUnknownError() {
        registerView.hideProgress();
        registerView.setUnknownError();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(registerView!=null){
            if(ObservableObject.getInstance().getValue().equals("DISCONNECTED_INTERNET")){
                registerView.setInternetConnectionError();
                registerView.setAvailableInternet(false);
            }else if(ObservableObject.getInstance().getValue().equals("CONNECTED_INTERNET")){
                registerView.dismissInternetConnectionError();
                registerView.setAvailableInternet(true);
            }
        }
    }
}
