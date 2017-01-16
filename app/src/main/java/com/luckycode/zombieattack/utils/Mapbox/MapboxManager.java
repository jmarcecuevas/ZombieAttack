package com.luckycode.zombieattack.utils.Mapbox;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.luckycode.zombieattack.R;
import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

/**
 * Created by MarceloCuevas on 23/12/2016.
 */

public class MapboxManager  {
    private static final String TAG= MapboxManager.class.getSimpleName();
    private Context mContext;
    private Marker mPositionMarker;
    private CameraPosition mCameraPosition;

    public MapboxManager(Context context){
        mContext=context;
        MapboxAccountManager.start(mContext,mContext.getString(R.string.accessToken));
    }

    public void initMapView(Bundle savedInstanceState,MapView mMapView,OnMapReadyCallback callback){
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(callback);
        mMapView.setStyleUrl(mContext.getString(R.string.mapStyle));
    }

    public void initCameraPosition(MapboxMap mapboxMap,LatLng position){
        if(mapboxMap!=null){
            Log.i(TAG,"STARTING MAPBOX CAMERA");
            mCameraPosition= new CameraPosition.Builder()
                    .target(position)
                    .build();
            mapboxMap.setCameraPosition(mCameraPosition);
        }
    }

    public void updateCameraPosition(MapboxMap mapboxMap,LatLng position){
        Log.e(TAG,"ANIMATATE CAMERA");
        mCameraPosition= new CameraPosition.Builder()
                .target(position)
                .build();
        mapboxMap.easeCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition),5000);
    }

    public void drawMyPosition(MapboxMap mapboxMap,String userName,LatLng position){
        if(mPositionMarker==null){
            Log.i(TAG,"DRAWING MY POSITION ON MAP");
            IconFactory iconFactory = IconFactory.getInstance(mContext);
            Drawable iconDrawable = ContextCompat.getDrawable(mContext, R.drawable.player);
            Icon icon = iconFactory.fromDrawable(iconDrawable);
            mPositionMarker= mapboxMap.addMarker(new MarkerViewOptions().position(position).title(userName).icon(icon));
        }
    }

    public void updateMyPosition(MapboxMap mapboxMap,String userName,LatLng position){
        if(mPositionMarker==null){
            IconFactory iconFactory = IconFactory.getInstance(mContext);
            Drawable iconDrawable = ContextCompat.getDrawable(mContext, R.drawable.player);
            Icon icon = iconFactory.fromDrawable(iconDrawable);
            mPositionMarker=mapboxMap.addMarker(new MarkerViewOptions().position(position).title(userName));
        }
        Log.i(TAG,"UPDATING MY POSITION ON MAP");
        ValueAnimator mPositionMarkerAnimator = ObjectAnimator.ofObject(mPositionMarker, "position", new LatLngEvaluator(), mPositionMarker.getPosition()
                    , position);
        mPositionMarkerAnimator.setDuration(5000);
        mPositionMarkerAnimator.start();
    }

    public Marker drawPlayer(MapboxMap mapboxMap,Marker marker,String userName,LatLng position){
        Log.i(TAG,"DRAWING PLAYER WITH NICKNAME: "+userName);
        IconFactory iconFactory = IconFactory.getInstance(mContext);
        Drawable iconDrawable = ContextCompat.getDrawable(mContext, R.drawable.devil);
        Icon icon = iconFactory.fromDrawable(iconDrawable);
        marker=mapboxMap.addMarker(new MarkerViewOptions().position(position).title(userName).icon(icon));
        return marker;
    }

    public void updatePlayerPosition(Marker marker,LatLng position){
        Log.i(TAG,"UPDATING PLAYER POSITION: "+marker.getTitle());
        ValueAnimator playerPositionMarkerAnimator= ObjectAnimator.ofObject(marker,"position",new LatLngEvaluator(),marker.getPosition()
        ,position);
        playerPositionMarkerAnimator.setDuration(5000);
        playerPositionMarkerAnimator.start();
    }

    public void removePlayerMarker(MapboxMap mapboxMap,Marker marker){
        Log.i(TAG,"REMOVING PLAYER: "+marker.getTitle());
        mapboxMap.removeMarker(marker);
    }


    public static class LatLngEvaluator implements TypeEvaluator<LatLng> {
        /** This class is used to interpolate the
         * marker animation.
         */

        private LatLng latLng = new LatLng();

        @Override
        public LatLng evaluate(float fraction, LatLng startValue, LatLng endValue) {
            latLng.setLatitude(startValue.getLatitude()
                    + ((endValue.getLatitude() - startValue.getLatitude()) * fraction));
            latLng.setLongitude(startValue.getLongitude()
                    + ((endValue.getLongitude() - startValue.getLongitude()) * fraction));
            return latLng;
        }
    }

}
