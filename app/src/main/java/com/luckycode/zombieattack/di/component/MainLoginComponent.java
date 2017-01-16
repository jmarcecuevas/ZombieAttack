package com.luckycode.zombieattack.di.component;

import com.luckycode.zombieattack.common.ActivityScope;
import com.luckycode.zombieattack.di.module.MainLoginModule;
import com.luckycode.zombieattack.ui.activity.MainLoginActivity;

import dagger.Subcomponent;

/**
 * Created by MarceloCuevas on 07/01/2017.
 */


@ActivityScope
@Subcomponent(modules = MainLoginModule.class)

public interface MainLoginComponent {
    void inject(MainLoginActivity mainLoginActivity);
}
