package com.luckycode.zombieattack.common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.luckycode.zombieattack.R;
import com.luckycode.zombieattack.di.app.LuckyGameApp;
import com.luckycode.zombieattack.di.app.LuckyGameComponent;

import butterknife.ButterKnife;

/**
 * Created by MarceloCuevas on 19/12/2016.
 */

/** Base activity for LoginActivity and RegisterActivity **/

public abstract class LogRegBaseActivity extends AppCompatActivity {

    protected Snackbar internetSnackBar;
    protected AlertDialog alertDialog;
    protected ProgressDialog progressDialog;
    protected boolean isInternetAvailable,validUserName,validPassword,validEmail=true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        alertDialog= new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.loginStatus));
        progressDialog= new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.pleaseWait));
        injectDependencies();
        injectViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(getPresenter()!=null)
            getPresenter().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(getPresenter()!=null)
            getPresenter().onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(getPresenter()!=null)
            getPresenter().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getPresenter()!=null){
            getPresenter().onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(getPresenter()!=null)
            getPresenter().onDestroy();
    }

    /**
     * @return The layout that's gonna be the activity view.
     */
    protected abstract int getLayout();

    /**
     * @return The presenter attached to the activity. This must extends from {@link BasePresenter}
     */
    @Nullable
    protected abstract BasePresenter getPresenter();

    /**
     * Setup the object graph and inject the dependencies needed on this activity.
     */
    private void injectDependencies() {
        setUpComponent(LuckyGameApp.getApp(this).getComponent());
    }


    /**
     * Every object annotated with {@link butterknife.Bind} its gonna injected trough butterknife
     */
    private void injectViews() {
        ButterKnife.bind(this);
    }

    protected abstract void setUpComponent(LuckyGameComponent component);

}
