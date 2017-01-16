package com.luckycode.zombieattack.io.apiServices;

import com.luckycode.zombieattack.io.model.EmailResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by MarceloCuevas on 09/01/2017.
 */

public interface EmailApiService {
    @FormUrlEncoded
    @POST(APIConstants.CHECK_EMAIL_URL)
    Call<EmailResponse> checkEmail(@Field("email")String email);
}
