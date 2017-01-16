package com.luckycode.zombieattack.di.component;

import com.luckycode.zombieattack.common.ActivityScope;
import com.luckycode.zombieattack.di.module.MainModule;
import com.luckycode.zombieattack.ui.activity.MainActivity;

import dagger.Subcomponent;


/**
 * Created by MarceloCuevas on 23/12/2016.
 */


@ActivityScope
@Subcomponent(
        modules = {
                MainModule.class,
        }
)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}