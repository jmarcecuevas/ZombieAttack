package com.luckycode.zombieattack.di.module;

import com.luckycode.zombieattack.interactor.LoginInteractor;
import com.luckycode.zombieattack.interactor.MainInteractor;
import com.luckycode.zombieattack.interactor.RegisterInteractor;
import com.luckycode.zombieattack.io.apiServices.EmailApiService;
import com.luckycode.zombieattack.io.apiServices.LoginAPIService;
import com.luckycode.zombieattack.io.apiServices.MainAPIService;
import com.luckycode.zombieattack.io.apiServices.RegisterAPIService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MarceloCuevas on 13/12/2016.
 */

@Module
public class InteractorModule {

    @Provides
    public LoginInteractor provideLoginInteractor(LoginAPIService loginAPIService){
        return new LoginInteractor(loginAPIService);
    }

   @Provides
    public RegisterInteractor provideRegisterInteractor(RegisterAPIService registerAPIService,EmailApiService emailApiService){
        return new RegisterInteractor(registerAPIService,emailApiService);
    }

    @Provides
    public MainInteractor provideMainInteractor(MainAPIService mainAPIService){
        return new MainInteractor(mainAPIService);
    }

}
