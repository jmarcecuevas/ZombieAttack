package com.luckycode.zombieattack.di.module;

import android.content.Context;

import com.luckycode.zombieattack.interactor.LoginInteractor;
import com.luckycode.zombieattack.interactor.RegisterInteractor;
import com.luckycode.zombieattack.io.receiver.ConnectivityChangeReceiver;
import com.luckycode.zombieattack.presenter.LoginPresenterImp;
import com.luckycode.zombieattack.presenter.RegisterPresenterImp;
import com.luckycode.zombieattack.ui.viewmodel.LoginView;
import com.luckycode.zombieattack.ui.viewmodel.RegisterView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Marcelo Cuevas on 10/12/2016.
 */

@Module
public class LogRegModule {

    private LoginView loginView;
    private RegisterView registerView;

    public LogRegModule(LoginView view) {
        loginView = view;
    }

   public LogRegModule(RegisterView view){
        registerView= view;
   }

    @Provides
    public LoginView provideLoginView() {
        return loginView;
    }

    @Provides
    public RegisterView provideRegisterView(){ return registerView; }

    @Provides
    public ConnectivityChangeReceiver provideConnectivityChangeReceiver(Context context){
        return new ConnectivityChangeReceiver(context);
    }

    @Provides
    public LoginPresenterImp providePresenter(Context context,LoginView view, LoginInteractor interactor,ConnectivityChangeReceiver connectivityChangeReceiver) {
        return new LoginPresenterImp(context,view, interactor,connectivityChangeReceiver);
    }

    @Provides
    public RegisterPresenterImp provideRegisterPresenter(Context context, RegisterView view, RegisterInteractor interactor,ConnectivityChangeReceiver connectivityChangeReceiver){
        return new RegisterPresenterImp(context,view,interactor,connectivityChangeReceiver);
    }
}


