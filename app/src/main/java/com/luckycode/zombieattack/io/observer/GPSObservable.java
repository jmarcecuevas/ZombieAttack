package com.luckycode.zombieattack.io.observer;

import java.util.Observable;

/**
 * Created by MarceloCuevas on 04/01/2017.
 */

public class GPSObservable extends Observable {
    private boolean value;
    private static GPSObservable instance= new GPSObservable();

    public static GPSObservable getInstance(){
        return instance;
    }

    private GPSObservable(){
    }

    public void updateValue(boolean value){
        this.value=value;
        synchronized (this){
            setChanged();
            notifyObservers();
        }
    }

    public boolean getValue(){
        return value;
    }
}
