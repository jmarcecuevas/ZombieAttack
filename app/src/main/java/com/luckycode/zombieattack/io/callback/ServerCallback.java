package com.luckycode.zombieattack.io.callback;

import android.widget.TextView;

/**
 * Created by Marcelo Cuevas on 10/12/2016.
 */

public interface ServerCallback {
    void onNetworkError(TextView textView);
    void onServerError();
}
