package com.luckycode.zombieattack.interactor;

import android.util.Log;

import com.luckycode.zombieattack.io.apiServices.MainAPIService;
import com.luckycode.zombieattack.io.callback.NearbyUsersCallback;
import com.luckycode.zombieattack.io.model.NearbyPlayer;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MarceloCuevas on 29/12/2016.
 */

public class MainInteractor {
    private static String TAG= MainInteractor.class.getSimpleName();
    private MainAPIService mMainAPIService;

    public MainInteractor(MainAPIService mainAPIService){
        mMainAPIService=mainAPIService;
    }

    public void updateLocation(String userName, double latitude, double longitude,int radius,final NearbyUsersCallback callback) {
        double value= radius/1000.0;
        Call<ArrayList<NearbyPlayer>> call= mMainAPIService.updateLocation(userName,latitude,longitude,value);
        call.enqueue(new Callback<ArrayList<NearbyPlayer>>() {
            @Override
            public void onResponse(Call<ArrayList<NearbyPlayer>> call, Response<ArrayList<NearbyPlayer>> response) {
                ArrayList<NearbyPlayer> mResponse= response.body();
                if(mResponse!=null)
                    if(mResponse.size()==0){
                        Log.i(TAG,"EMPTY NEARBY USERS SEARCH");
                        callback.onEmptyNearbyUsersSearch();
                    }else{
                        Log.i(TAG,"NEARBY USERS FOUND");
                        callback.onNearbyUsersFound(mResponse);
                    }
            }

            @Override
            public void onFailure(Call<ArrayList<NearbyPlayer>> call, Throwable t) {
                Log.e(TAG,"ON FAILURE");
            }
        });



    }
}
