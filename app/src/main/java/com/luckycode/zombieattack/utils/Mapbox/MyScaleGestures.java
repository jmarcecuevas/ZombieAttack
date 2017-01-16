package com.luckycode.zombieattack.utils.Mapbox;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.maps.MapboxMap;

/**
 * Created by MarceloCuevas on 15/01/2017.
 */

public class MyScaleGestures implements View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener {

    private View view;
    private ScaleGestureDetector gestureScale;
    private MapboxMap mapboxMap;


    public MyScaleGestures(Context c, MapboxMap mapboxMap) {
        gestureScale = new ScaleGestureDetector(c, this);
        this.mapboxMap = mapboxMap;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        this.view = view;
        gestureScale.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {

        Log.e("HOOOOOOLAAA", "BOLUDOOOOOO");
        double zoom1 = mapboxMap.getCameraPosition().zoom;
        double tilt = mapboxMap.getCameraPosition().tilt;
        double zoom2 = zoom1 + Math.log(detector.getScaleFactor()) / Math.log(1.5d);
        double resta = zoom2 - zoom1;
        Log.e("RESTA: ", String.valueOf(resta));
        double resultado = resta / 0.0285;
        Log.e("RESULTADO TILT: ", String.valueOf(resultado));
        double tilt2 = tilt + resultado;
        Log.e("RESULTADOTOTAL TILT: ", String.valueOf(tilt2));
        //double layout=resta/0.01666;
        double layout = resta / 0.008333;
        Log.e("LAYOUT: ", String.valueOf(layout));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mapboxMap.getCameraPosition().target)
                .zoom(zoom2)
                .tilt(tilt2)
                .build();
        mapboxMap.setCameraPosition(cameraPosition);


        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
    }
}
