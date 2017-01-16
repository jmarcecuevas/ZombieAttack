package com.luckycode.zombieattack.utils.Mapbox;

import com.luckycode.zombieattack.io.model.NearbyPlayer;

import java.util.ArrayList;

/**
 * Created by MarceloCuevas on 31/12/2016.
 */

public class MarkersManager{

    private static String TAG=MarkersManager.class.getSimpleName();
    private ArrayList<NearbyPlayer> mArray;
    private int index;

    public MarkersManager(ArrayList<NearbyPlayer> array){
        mArray=array;
    }

    public int size(){
        return mArray.size();
    }

    public ArrayList<NearbyPlayer> getArray(){
        return mArray;
    }

    public ArrayList<NearbyPlayer> validateArray(ArrayList<NearbyPlayer> array){
        for(int i=0;i<array.size();i++){
            if(contains(mArray,array.get(i))){
                mArray.get(index).setUpdated(true);
                mArray.get(index).setInRange(true);
                mArray.get(index).setLat(array.get(i).getLat());
                mArray.get(index).setLng(array.get(i).getLng());
            }else{
                array.get(i).setInRange(true);
                array.get(i).setUpdated(false);
                mArray.add(array.get(i));
            }
        }
        return mArray;
    }

    public boolean contains(ArrayList<NearbyPlayer> array, NearbyPlayer player) {
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getUserName().equals(player.getUserName())) {
                index = i;
                return true;
            }
        }
        return false;
    }

    public void delete(int index){
        mArray.remove(index);
    }

    public void setOutOfRange(){
        for(int i=0;i<mArray.size();i++){
            mArray.get(i).setInRange(false);
        }
    }
}
