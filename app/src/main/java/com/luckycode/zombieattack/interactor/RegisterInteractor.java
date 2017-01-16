package com.luckycode.zombieattack.interactor;

import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.luckycode.zombieattack.io.apiServices.EmailApiService;
import com.luckycode.zombieattack.io.apiServices.RegisterAPIService;
import com.luckycode.zombieattack.io.callback.EmailCallback;
import com.luckycode.zombieattack.io.callback.RegisterCallback;
import com.luckycode.zombieattack.io.model.EmailResponse;
import com.luckycode.zombieattack.io.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by MarceloCuevas on 19/12/2016.
 */

public class RegisterInteractor {
    private static String TAG= RegisterInteractor.class.getSimpleName();
    private RegisterAPIService registerAPIService;
    private EmailApiService emailApiService;

    public RegisterInteractor(RegisterAPIService registerAPIService,EmailApiService emailApiService){
        this.registerAPIService=registerAPIService;
        this.emailApiService=emailApiService;
    }

    public void register(String name, String userName, String email, String password, final RegisterCallback registerCallback){
        Call<RegisterResponse> call= registerAPIService.inserUser(name,userName,email,password);
        call.enqueue(new retrofit2.Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse mResponse= response.body();
                if(mResponse!=null){
                    if(mResponse.isSuccessful()){
                        Log.i(TAG,"SUCCESSFUL REGISTER");
                        registerCallback.onSuccessRegister();
                    }
                    else if(mResponse.doesUserExist()){
                        Log.i(TAG,"USERNAME ALREADY EXISTS");
                        registerCallback.onUserAlreadyExists();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e(TAG,"UNKNOWN ERROR");
                registerCallback.onUnknownError();
            }
        });
    }

    /** It Checks whether email exists on database**/
    public void checkEmail(final GoogleSignInAccount acc, final EmailCallback emailCallback){
        Call<EmailResponse> call= emailApiService.checkEmail(acc.getEmail());
        call.enqueue(new retrofit2.Callback<EmailResponse>() {
            @Override
            public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {
                EmailResponse mResponse= response.body();
                if(mResponse!=null){
                    if(mResponse.doesEmailExists()){
                        Log.i(TAG,"EMAIL ALREADY EXISTS");
                        emailCallback.onEmailAlreadyExists(acc,mResponse.getUserName());
                    }else{
                        Log.i(TAG,"EMAIL DOESN'T EXISTS ON DATABASE(It's able to register)");
                        emailCallback.onEmailOK(acc);
                    }
                }
            }

            @Override
            public void onFailure(Call<EmailResponse> call, Throwable t) {
                Log.e(TAG,"UNKNOWN ERROR WHILE TRYING TO CHECK EMAIL ON DATABASE");
            }
        });
    }
}
