package com.luckycode.zombieattack.io.apiServices;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.luckycode.zombieattack.io.model.NearbyPlayer;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Marcelo Cuevas on 10/12/2016.
 */

public class APIAdapter {
    private static Retrofit RETROFIT;

    public static Retrofit getInstance(){
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    /**This excludes Marker object used in NearbyPlayer
                     * class to avoid GSON problems.
                     */
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(NearbyPlayer.class)&& f.getName().equals("marker");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        //The adapter will be a singleton
        if(RETROFIT == null)
            RETROFIT = new Retrofit.Builder()
                    .baseUrl(APIConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        return RETROFIT;
    }


}
