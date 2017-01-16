package com.luckycode.zombieattack.di.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.luckycode.zombieattack.io.apiServices.APIAdapter;
import com.luckycode.zombieattack.io.apiServices.EmailApiService;
import com.luckycode.zombieattack.io.apiServices.LoginAPIService;
import com.luckycode.zombieattack.io.apiServices.MainAPIService;
import com.luckycode.zombieattack.io.apiServices.RegisterAPIService;
import com.luckycode.zombieattack.utils.SessionManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Marcelo Cuevas on 09/12/2016.
 * The module due is create objects to solve dependencies
 * trough methods annotated with {@link Provides} annotation.
 *<p>
 * I use a Module based on this
 * <a href="http://frogermcs.github.io/dagger-1-to-2-migration/">tutorial</a>
 *</p>
 */

@Module
public class LuckyGameModule {

    private LuckyGameApp app;

    public LuckyGameModule (LuckyGameApp app) {
        this.app = app;
    }


    @Provides @Singleton public Application provideApplication() {
        return app;
    }

    @Provides @Singleton public Context provideContext() {
        return app;
    }

    @Provides @Singleton SessionManager provideSessionManager(Context context){
        return new SessionManager(context);
    }

    @Provides @Singleton SharedPreferences provideSharedPreferences(){
        return app.getSharedPreferences("MyPref",app.MODE_PRIVATE);
    }

    @Provides @Singleton public Retrofit provideRetrofitInstance() {
        return APIAdapter.getInstance();
    }

    @Provides @Singleton public LoginAPIService provideLoginAPIService(Retrofit retrofit){
        return retrofit.create(LoginAPIService.class);
    }

    @Provides @Singleton public RegisterAPIService provideRegisterAPIService(Retrofit retrofit){
        return retrofit.create(RegisterAPIService.class);
    }

    @Provides @Singleton public MainAPIService provideMainAPIService(Retrofit retrofit){
        return retrofit.create(MainAPIService.class);
    }

    @Provides @Singleton public EmailApiService provideNicknameApiService(Retrofit retrofit){
        return retrofit.create(EmailApiService.class);
    }

    @Provides @Singleton public GoogleSignInOptions provideGoogleSignInOptions(){
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }

    @Provides @Singleton public GoogleApiClient providesGoogleApiClient(Context context,GoogleSignInOptions gso){
        return new GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

    @Provides @Named("sign_in_api") public GoogleApiClient provideGoogleApíClient(Context context,GoogleSignInOptions gso){
        return new GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

    @Provides @Singleton public Vibrator provideVibrator(Context context){
        return (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
    }


}