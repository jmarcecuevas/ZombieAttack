package com.luckycode.zombieattack.ui.adapter;


import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.luckycode.zombieattack.R;


/**
 * Created by MarceloCuevas on 07/01/2017.
 */

/** Creates custom Dialogs **/

public class CustomDialog extends Dialog{
    public String nickname="";

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(R.layout.dialog);
    }
}
