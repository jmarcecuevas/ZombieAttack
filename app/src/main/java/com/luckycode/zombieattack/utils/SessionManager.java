package com.luckycode.zombieattack.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.luckycode.zombieattack.ui.activity.MainLoginActivity;

/**
 * Created by MarceloCuevas on 23/12/2016.
 */

public class SessionManager {

    private GoogleApiClient googleApiClient;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "MyPref";

    private static final String IS_LOGIN = "IsLoggedIn";

    // User name
    public static final String KEY_NAME = "name";

    //Nickname
    public static final String KEY_USERNAME="userName";

    // Email address
    public static final String KEY_EMAIL = "email";

    //User Password
    public static final String KEY_PASSWORD= "password";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create session
     */

    public void createLoginSession(String name,String userName,String email,GoogleApiClient googleApiClient) {
        this.googleApiClient= googleApiClient;
        googleApiClient.connect();

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_NAME, name);

        editor.putString(KEY_USERNAME, userName);

        editor.putString(KEY_EMAIL,email);

        //commit changes
        editor.commit();
    }


    public void createLoginSession(String userName,String password){
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(KEY_USERNAME,userName);
        editor.putString(KEY_PASSWORD,password);
        editor.commit();
    }

    /**
     * Check login method wil check user login status.
     * If false it will redirect user to MainLoginActivity
     */
    public boolean checkStatusLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainLoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


            // Staring Login Activity
            _context.startActivity(i);

            return false;
        }
        return true;
    }

    public String getUsername(){
        return pref.getString(KEY_USERNAME,"NULL");
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirects user to MainLoginActivity
        Intent i = new Intent(_context, MainLoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if(googleApiClient!=null){
            signOutGoogle(googleApiClient);
            revokeAccess(googleApiClient);
        }

        //Starting MainLoginActivity
        _context.startActivity(i);
    }


    private void signOutGoogle(GoogleApiClient googleApiClient) {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
                    }
                });
    }

    /** Disconnect google account from the app **/
    private void revokeAccess(GoogleApiClient googleApiClient) {
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    }
                });
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
