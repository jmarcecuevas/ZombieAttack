package com.luckycode.zombieattack.interactor;

import com.luckycode.zombieattack.io.apiServices.LoginAPIService;
import com.luckycode.zombieattack.io.callback.LoginCallback;
import com.luckycode.zombieattack.io.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Marcelo Cuevas on 10/12/2016.
 */

public class LoginInteractor {
    private LoginAPIService loginAPIService;

    public LoginInteractor(LoginAPIService loginAPIService){
        this.loginAPIService=loginAPIService;
    }

    public void login(String userName, String password, final LoginCallback callback){
        Call<LoginResponse> call= loginAPIService.login(userName,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse mResponse= response.body();
                if(mResponse!=null){
                    if(mResponse.isSuccessful())
                        callback.onSuccessLogin();
                    else
                        callback.onFailedLogin();
                }

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }

}
