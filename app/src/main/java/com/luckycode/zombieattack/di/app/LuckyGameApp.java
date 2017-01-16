package com.luckycode.zombieattack.di.app;


import android.app.Application;
import android.content.Context;

/**
 * Created by Marcelo Cuevas on 09/12/2016.
 */

public class LuckyGameApp extends Application {
    private LuckyGameComponent luckyComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setupGraph();
    }

    /**
     * The object graph contains all the instances of the objects
     * that resolve a dependency
     * */
    private void setupGraph() {
        luckyComponent= DaggerLuckyGameComponent.builder()
                .luckyGameModule(new LuckyGameModule(this))
                .build();
    }

    public LuckyGameComponent getComponent(){
        return luckyComponent;
    }

    public static LuckyGameApp getApp(Context context){
        return (LuckyGameApp) context.getApplicationContext();
    }
}