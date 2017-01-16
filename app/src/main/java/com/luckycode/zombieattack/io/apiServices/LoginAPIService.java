package com.luckycode.zombieattack.io.apiServices;

import com.luckycode.zombieattack.io.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Marcelo Cuevas on 10/12/2016.
 */

public interface LoginAPIService {
    @FormUrlEncoded
    @POST(APIConstants.LOGIN_URL)
    Call<LoginResponse> login(@Field("userName")String userName,
                              @Field("password")String password);
}
