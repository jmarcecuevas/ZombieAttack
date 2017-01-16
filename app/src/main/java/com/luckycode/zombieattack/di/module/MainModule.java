package com.luckycode.zombieattack.di.module;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.luckycode.zombieattack.common.ActivityScope;
import com.luckycode.zombieattack.interactor.MainInteractor;
import com.luckycode.zombieattack.io.model.NearbyPlayer;
import com.luckycode.zombieattack.io.receiver.ConnectivityChangeReceiver;
import com.luckycode.zombieattack.io.receiver.GPSChangeReceiver;
import com.luckycode.zombieattack.presenter.MainPresenterImp;
import com.luckycode.zombieattack.ui.viewmodel.MainView;
import com.luckycode.zombieattack.utils.LocationApiManager;
import com.luckycode.zombieattack.utils.Mapbox.MapboxManager;
import com.luckycode.zombieattack.utils.Mapbox.MarkersManager;

import java.util.ArrayList;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MarceloCuevas on 20/12/2016.
 */

@Module
public class MainModule {
    private MainView mainView;

    public MainModule(MainView mainView){
        this.mainView=mainView;
    }

    @Provides @ActivityScope MainView provideMapView(){
        return mainView;
    }

    @Provides @ActivityScope GPSChangeReceiver provideGPSChangeReceiver(Context context){
        return new GPSChangeReceiver(context);
    }

    @Provides @ActivityScope ConnectivityChangeReceiver provideConnectivityChangeReceiver(Context context){
        return new ConnectivityChangeReceiver(context);
    }

    @Provides @Named("location_api") GoogleApiClient provideGoogleAPiClient(Context context){
        return new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .build();
    }

    @Provides @ActivityScope LocationRequest provideLocationRequest(){
        return new LocationRequest();
    }

    @Provides @ActivityScope LocationSettingsRequest.Builder provideLocationSettingsRequestBuilder(LocationRequest locationRequest){
        return new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
    }

    @Provides @ActivityScope LocationApiManager provideLocationApiManager(Context context, @Named("location_api") GoogleApiClient googleApiClient, LocationRequest
                                                           locationRequest, LocationSettingsRequest.Builder locationSettingsRequestBuilder){
        return new LocationApiManager(context,googleApiClient,locationRequest,locationSettingsRequestBuilder);
    }

    @Provides  @ActivityScope MapboxManager provideMapboxManager(Context context){
        return new MapboxManager(context);
    }

    @Provides @ActivityScope ArrayList<NearbyPlayer> provideArrayNearbyPlayers(){
        return new ArrayList<NearbyPlayer>();
    }

    @Provides @ActivityScope MarkersManager provideMarkersManager(ArrayList<NearbyPlayer> array){
        return new MarkersManager(array);
    }

    @Provides @ActivityScope MainPresenterImp providesMainPresenter(Context context, GPSChangeReceiver gpsChangeReceiver,ConnectivityChangeReceiver connectivityChangeReceiver,
                                                                    MainView mainView, MarkersManager markersManager, MainInteractor interactor, LocationApiManager locationApiManager){
        return new MainPresenterImp(context,gpsChangeReceiver,connectivityChangeReceiver,mainView,markersManager,interactor,locationApiManager);
    }
}
