package com.luckycode.zombieattack.io.apiServices;

import com.luckycode.zombieattack.io.model.NearbyPlayer;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by MarceloCuevas on 29/12/2016.
 */

public interface MainAPIService {
    @FormUrlEncoded
    @POST(APIConstants.UPDATE_LOCATION_URL)
    Call<ArrayList<NearbyPlayer>> updateLocation(@Field("userName")String userName,
                                                 @Field("latitude")double latitude,
                                                 @Field("longitude")double longitude,
                                                 @Field("radius")double radius
                                                 );
}
