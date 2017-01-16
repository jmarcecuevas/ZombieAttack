package com.luckycode.zombieattack.di.component;

import com.luckycode.zombieattack.common.ActivityScope;
import com.luckycode.zombieattack.di.module.SettingsModule;
import com.luckycode.zombieattack.ui.activity.SettingsActivity;

import dagger.Subcomponent;

/**
 * Created by MarceloCuevas on 11/01/2017.
 */

@ActivityScope
@Subcomponent(modules = SettingsModule.class)

public interface SettingsComponent {
    void inject(SettingsActivity settingsActivity);
}
