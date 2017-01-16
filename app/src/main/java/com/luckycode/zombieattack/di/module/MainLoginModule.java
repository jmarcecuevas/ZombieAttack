package com.luckycode.zombieattack.di.module;

import android.content.Context;

import com.luckycode.zombieattack.interactor.RegisterInteractor;
import com.luckycode.zombieattack.io.receiver.ConnectivityChangeReceiver;
import com.luckycode.zombieattack.presenter.MainLoginPresenterImp;
import com.luckycode.zombieattack.ui.viewmodel.MainLoginView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MarceloCuevas on 07/01/2017.
 */

@Module
public class MainLoginModule {

    private MainLoginView mainLoginView;

    public MainLoginModule(MainLoginView mainLoginView){
        this.mainLoginView=mainLoginView;
    }

    @Provides MainLoginView provideMainLoginView(){
        return mainLoginView;
    }

    @Provides
    public ConnectivityChangeReceiver provideConnectivityChangeReceiver(Context context){
        return new ConnectivityChangeReceiver(context);
    }

    @Provides
    public MainLoginPresenterImp provideMainLoginPresenterImp(Context context, MainLoginView mainLoginView, RegisterInteractor registerInteractor, ConnectivityChangeReceiver connectivityChangeReceiver){
        return new MainLoginPresenterImp(context,mainLoginView,registerInteractor,connectivityChangeReceiver);
    }
}
