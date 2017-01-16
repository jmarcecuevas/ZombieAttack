package com.luckycode.zombieattack.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MarceloCuevas on 11/01/2017.
 */

public class SettingsManager {

    private Context mContext;
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;
    private static String KEY_MUSIC="music";
    private static String KEY_VIBRATION="vibration";
    private static String KEY_SEARCH_RADIUS="searchRadius";


    public SettingsManager(Context context,String nickName){
        mContext=context;
        pref= context.getSharedPreferences(nickName,mContext.MODE_PRIVATE);
        editor= pref.edit();
    }

    public static void savePreferences(int radius,boolean music,boolean vibration){
        editor.putInt(KEY_SEARCH_RADIUS,radius);
        editor.putBoolean(KEY_MUSIC,music);
        editor.putBoolean(KEY_VIBRATION,vibration);
        editor.commit();
    }

    public static boolean getMusic(){return pref.getBoolean(KEY_MUSIC,true);}

    public static boolean getVibration(){return pref.getBoolean(KEY_VIBRATION,true);}

    public static int getRadius(){
        return pref.getInt(KEY_SEARCH_RADIUS,70);
    }

}
