package com.luckycode.zombieattack.di.component;

import com.luckycode.zombieattack.common.ActivityScope;
import com.luckycode.zombieattack.di.module.LogRegModule;
import com.luckycode.zombieattack.presenter.LoginPresenterImp;
import com.luckycode.zombieattack.presenter.RegisterPresenterImp;
import com.luckycode.zombieattack.ui.activity.LoginActivity;
import com.luckycode.zombieattack.ui.activity.RegisterActivity;

import dagger.Subcomponent;

/**
 * Created by MarceloCuevas on 21/11/2016.
 */


@ActivityScope
@Subcomponent(modules = {
        LogRegModule.class}
)
public interface LogRegComponent{
    void inject(LoginActivity loginActivity);
    void inject(RegisterActivity registerActivity);
    LoginPresenterImp getLoginPresenter();
    RegisterPresenterImp getRegisterPresenter();
}
