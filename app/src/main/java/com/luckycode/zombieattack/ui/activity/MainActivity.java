package com.luckycode.zombieattack.ui.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.luckycode.zombieattack.R;
import com.luckycode.zombieattack.common.BaseActivity;
import com.luckycode.zombieattack.common.BasePresenter;
import com.luckycode.zombieattack.di.app.LuckyGameComponent;
import com.luckycode.zombieattack.di.module.MainModule;
import com.luckycode.zombieattack.io.model.NearbyPlayer;
import com.luckycode.zombieattack.presenter.MainPresenterImp;
import com.luckycode.zombieattack.ui.viewmodel.MainView;
import com.luckycode.zombieattack.utils.Mapbox.GPSStatusManager;
import com.luckycode.zombieattack.utils.Mapbox.MapboxManager;
import com.luckycode.zombieattack.utils.Mapbox.MyScaleGestures;
import com.luckycode.zombieattack.utils.MusicManager;
import com.luckycode.zombieattack.utils.SessionManager;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainView,
        OnMapReadyCallback{

    private static String TAG= MainActivity.class.getSimpleName();
    private MapboxMap mMap;
    private SharedPreferences sharedPreferences;
    @BindView(R.id.mapview)MapView mapView;
    @BindView(R.id.tv_nickname)TextView tv_nickname;
    @BindView(R.id.imagezombie)ImageView imageZombie;
    @Inject Vibrator v;
    @Inject MainPresenterImp mainPresenter;
    @Inject MapboxManager mapboxManager;
    @Inject SessionManager mSessionManager;
    @Inject @Named("sign_in_api") GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Check session status. If session is closed, finish this activity and
         * goes to MainLoginActivity. Otherwise continues in this activity**/
        if (!mSessionManager.checkStatusLogin())
            finish();

        sharedPreferences= getSharedPreferences(mSessionManager.getUsername(),MODE_PRIVATE);

        mapboxManager.initMapView(savedInstanceState,mapView,this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean music= sharedPreferences.getBoolean("music",true);
        enterSettings=false;
        if(music )
            MusicManager.start(this,1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!continueMusic || !enterSettings)
            MusicManager.pause();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Nullable
    @Override
    protected BasePresenter getPresenter() {
        return mainPresenter;
    }

    @Override
    public void setUpComponent(LuckyGameComponent appComponent) {
        appComponent.plus(new MainModule(this)).inject(this);
    }

    @Override
    public void vibrate() {
        boolean vibrate= sharedPreferences.getBoolean("vibration",true);
        if(vibrate)
            v.vibrate(1000);
    }

    @Override
    public void setNickname(String nickname) {
        tv_nickname.setText(nickname);
    }

    @Override
    public void updateLocationOnMap(String userName,Location location) {
        LatLng position= new LatLng(location.getLatitude(),location.getLongitude());
        mapboxManager.updateCameraPosition(mMap,position);
        mapboxManager.updateMyPosition(mMap,userName,position);
    }

    @Override
    public void showLocationOnMap(String userName,Location location) {
        LatLng position= new LatLng(location.getLatitude(),location.getLongitude());
        mapboxManager.initCameraPosition(mMap,position);
        mapboxManager.drawMyPosition(mMap,userName,position);
    }

    @Override
    public void showNearbyPlayerOnMap(NearbyPlayer nearbyPlayer) {
        nearbyPlayer.setMarker(mapboxManager.drawPlayer(mMap,nearbyPlayer.getMarker(),nearbyPlayer.getUserName(),new LatLng(nearbyPlayer.getLat(),nearbyPlayer
                .getLng())));
    }

    @Override
    public void updateNearbyPlayerLocation(Marker marker, LatLng location) {
        mapboxManager.updatePlayerPosition(marker,location);
    }

    @Override
    public void removeNearbyPlayerOnMap(NearbyPlayer nearbyPlayer){
        mapboxManager.removePlayerMarker(mMap,nearbyPlayer.getMarker());
    }

    @Override
    public void setInternetConnectionError() {
        internetSnackBar= Snackbar.make(mapView,getString(R.string.internetError),Snackbar.LENGTH_INDEFINITE);
        View sbView=internetSnackBar.getView();
        sbView.setBackgroundColor(Color.rgb(239,3,151));
        TextView sbText= (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN)
            sbText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        sbText.setGravity(Gravity.CENTER_HORIZONTAL);
        internetSnackBar.show();
    }

    @Override
    public void dismissInternetConnectionError() {
        if(internetSnackBar!=null)
            internetSnackBar.dismiss();
    }

    @Override
    public void setAvailableInternet(boolean availableInternet) {
        this.isInternetAvailable=availableInternet;
    }

    @Override
    public boolean isInternetAvailable() {
        return isInternetAvailable;
    }

    @Override
    public void setGPSConnectionError() {
        if(!GPSStatusManager.isGPSEnabled(this)){
            setAvailableGPS(false);
            GPSSnackBar= Snackbar.make(mapView,getString(R.string.GPSError),Snackbar.LENGTH_INDEFINITE);
            View sbView=GPSSnackBar.getView();
            sbView.setBackgroundColor(Color.rgb(239,3,151));
            TextView sbText= (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN)
                sbText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            sbText.setGravity(Gravity.CENTER_HORIZONTAL);
            GPSSnackBar.show();
        }
    }

    @Override
    public void dismissGPSConnectionError() {
        if(GPSSnackBar!=null)
            GPSSnackBar.dismiss();
    }

    @Override
    public void setAvailableGPS(boolean availableGPS) {
        isGPSAvailable=availableGPS;
    }

    @Override
    public boolean isGPSAvailable() {
        return isGPSAvailable;
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        mMap=mapboxMap;
        mapView.setOnTouchListener(new MyScaleGestures(this,mMap));
        mainPresenter.onMapReady();
    }

    @OnClick({R.id.imagezombie})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.imagezombie:
                continueMusic=true;
                enterSettings=true;
                startActivity(new Intent(this, SettingsActivity.class));
                overridePendingTransition(R.anim.zoom_back_in,R.anim.fade_out);
                break;
        }
    }

}
