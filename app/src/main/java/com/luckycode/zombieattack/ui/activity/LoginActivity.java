package com.luckycode.zombieattack.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.luckycode.zombieattack.R;
import com.luckycode.zombieattack.common.BasePresenter;
import com.luckycode.zombieattack.common.LogRegBaseActivity;
import com.luckycode.zombieattack.di.app.LuckyGameComponent;
import com.luckycode.zombieattack.di.module.LogRegModule;
import com.luckycode.zombieattack.presenter.LoginPresenterImp;
import com.luckycode.zombieattack.ui.viewmodel.LoginView;
import com.luckycode.zombieattack.utils.MusicManager;
import com.luckycode.zombieattack.utils.SessionManager;
import com.luckycode.zombieattack.utils.SettingsManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends LogRegBaseActivity implements LoginView{


    private SettingsManager settingsManager;
    private boolean continueMusic;
    @BindView(R.id.userName)EditText userName;
    @BindView(R.id.userPassword)EditText password;
    @BindView(R.id.back_button)ImageView back_button;
    @Inject LoginPresenterImp loginPresenter;
    @Inject SessionManager sessionManager;
    //@Inject @Named("menu_music") MediaPlayer mediaPlayer;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onPause() {
        super.onPause();
        if (!continueMusic)
            MusicManager.pause();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /** Dagger2 **/
    @Override
    public void setUpComponent(LuckyGameComponent appComponent) {
        appComponent.plus(new LogRegModule(this)).inject(this);
    }

    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Nullable @Override
    protected BasePresenter getPresenter() {return loginPresenter;}

    @Override
    public void setAvailableInternet(boolean availableInternet) {this.isInternetAvailable=availableInternet; }

    @Override
    public boolean isInternetAvailable() {
        return isInternetAvailable;
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void cancelProgress() {
        if(progressDialog!=null && progressDialog.isShowing())
            progressDialog.cancel();
    }

    /** Snackbar to show internet connectivity error **/
    @Override
    public void setInternetConnectionError() {
        internetSnackBar= Snackbar.make(userName,getString(R.string.internetError),Snackbar.LENGTH_INDEFINITE);
        View sbView=internetSnackBar.getView();
        sbView.setBackgroundColor(Color.rgb(239,3,151));
        TextView sbText= (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN)
            sbText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        sbText.setGravity(Gravity.CENTER_HORIZONTAL);
        internetSnackBar.show();
    }

    /** Dismiss snackbar **/
    @Override
    public void dismissInternetConnectionError() {
        if(internetSnackBar!=null)
            internetSnackBar.dismiss();
    }

    @Override
    public void setUserNameError() {
        userName.setError(getString(R.string.emptyUsername));
    }

    @Override
    public void setPasswordError() {
        password.setError(getString(R.string.emptyPassword));
    }

    @Override
    public void setInvalidCredentialsError() {
        alertDialog.setMessage(getString(R.string.failedLogin));
        alertDialog.show();
    }

    @Override
    public void setSuccessLogin() {
        continueMusic=false;
        sessionManager.createLoginSession(userName.getText().toString(),password.getText().toString());
        settingsManager= new SettingsManager(this,userName.getText().toString());
        Log.e("CREOOOO","SEETTINGS");
        /*if(sharedPreferences.contains(userName.getText().toString())){
            sharedPreferences= getSharedPreferences(userName.getText().toString(),MODE_PRIVATE);
        }*/
        sharedPreferences=getSharedPreferences(userName.getText().toString(),MODE_PRIVATE);
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }

    @Override
    public void goToRegister() {
        continueMusic=true;
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }

    @Override
    public void backToMainLogin() {
        startActivity(new Intent(this,MainLoginActivity.class));
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }

    @OnClick({R.id.btnlogin,R.id.tvRegister,R.id.back_button})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnlogin:
                if(isInternetAvailable())
                    loginPresenter.validateCredentials(String.valueOf(userName.getText()),String.valueOf(password.getText()));
                break;
            case R.id.tvRegister:
                goToRegister();
                break;
            case R.id.back_button:
                backToMainLogin();
                break;
        }
    }
}
