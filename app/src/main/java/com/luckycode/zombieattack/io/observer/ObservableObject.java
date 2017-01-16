package com.luckycode.zombieattack.io.observer;

import java.util.Observable;

/**
 * Created by MarceloCuevas on 16/12/2016.
 */

public class ObservableObject extends Observable {
    private String value;
    private static ObservableObject instance= new ObservableObject();

    public static ObservableObject getInstance(){
        return instance;
    }

    private ObservableObject(){
    }

    public void updateValue(String value){
        this.value=value;
        synchronized (this){
            setChanged();
            notifyObservers();
        }
    }

    public String getValue(){
        return value;
    }
}