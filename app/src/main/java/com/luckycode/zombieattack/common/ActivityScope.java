package com.luckycode.zombieattack.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Marcelo Cuevas on 10/12/2016.
 * This class will be used with Dagger2 to provide components
 * a scope **/


@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
