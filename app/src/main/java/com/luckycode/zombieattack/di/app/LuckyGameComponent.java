package com.luckycode.zombieattack.di.app;

import android.app.Application;
import android.content.Context;

import com.luckycode.zombieattack.di.component.LogRegComponent;
import com.luckycode.zombieattack.di.component.MainComponent;
import com.luckycode.zombieattack.di.component.MainLoginComponent;
import com.luckycode.zombieattack.di.component.SettingsComponent;
import com.luckycode.zombieattack.di.module.InteractorModule;
import com.luckycode.zombieattack.di.module.LogRegModule;
import com.luckycode.zombieattack.di.module.MainLoginModule;
import com.luckycode.zombieattack.di.module.MainModule;
import com.luckycode.zombieattack.di.module.SettingsModule;
import com.luckycode.zombieattack.interactor.LoginInteractor;
import com.luckycode.zombieattack.interactor.MainInteractor;
import com.luckycode.zombieattack.interactor.RegisterInteractor;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Marcelo Cuevas on 09/12/2016.
 * <p>
 *     The component due is provide methods that the object graph can use to inject dependencies.
 *     It's like an API for our object graph. <br>
 *
 *     Those methods inject objects created on corresponding modules.
 * </p>
 */

@Singleton
@Component(
        modules = {
                LuckyGameModule.class,
                InteractorModule.class
        }
)
public interface LuckyGameComponent {

    void inject(Application luckyGameApplication);

    /** Subcomponents **/
    LogRegComponent plus(LogRegModule logRegModule);
    MainComponent plus(MainModule mapModule);
    MainLoginComponent plus(MainLoginModule mainLoginModule);
    SettingsComponent plus(SettingsModule settingsModule);

    Context getContext();
    LoginInteractor getLoginInteractor();
    RegisterInteractor getRegisterInteractor();
    MainInteractor getMainInteractor();
    //MainLoginInteractor getMainLoginInteractor();
}

