package com.luckycode.zombieattack.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.luckycode.zombieattack.di.app.LuckyGameApp;
import com.luckycode.zombieattack.di.app.LuckyGameComponent;
import com.luckycode.zombieattack.ui.adapter.CustomDialog;

import butterknife.ButterKnife;

/**
 * Created by Marcelo Cuevas on 12/11/2016.
 *
 * <p>
 * The activity only will execute operations that affect the UI. These operations are defined
 * by a view model and are triggered by its presenter.
 * </p>
 *
 * <p>
 * Perhaps the activity only will work as a fragment container, if that is the case only
 * return null on {@link BaseActivity#getPresenter()}
 * </p>
 * <p>
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected boolean isInternetAvailable,isGPSAvailable,continueMusic,enterSettings;
    protected Snackbar internetSnackBar,GPSSnackBar;
    protected EditText ET_nickname;
    protected CustomDialog customDialog;


    /**
     * The onCreate base will set the view specified in {@link #getLayout()} and will
     * inject dependencies and views.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        injectViews();
        injectDependencies();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (getPresenter() != null)
            getPresenter().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (getPresenter() != null)
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

        if(getPresenter()!=null)
            getPresenter().onResume();
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


    public abstract void setUpComponent(LuckyGameComponent appComponent);

}