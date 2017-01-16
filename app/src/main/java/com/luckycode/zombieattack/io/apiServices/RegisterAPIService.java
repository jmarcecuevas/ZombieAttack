package com.luckycode.zombieattack.io.apiServices;

import com.luckycode.zombieattack.io.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by MarceloCuevas on 19/12/2016.
 */

public interface RegisterAPIService {
    @FormUrlEncoded
    @POST(APIConstants.REGISTER_URL)
    Call<RegisterResponse> inserUser(@Field("name")String name,
                                     @Field("userName")String userName,
                                     @Field("email")String email,
                                     @Field("password")String password);
}
