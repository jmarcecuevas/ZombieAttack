package com.luckycode.zombieattack.io.callback;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by MarceloCuevas on 09/01/2017.
 * This is a "callback" that will be used to register results
 * in checking email operation*/

public interface EmailCallback {
    void onEmailAlreadyExists(GoogleSignInAccount acc,String userName);
    void onEmailOK(GoogleSignInAccount acc);
}

