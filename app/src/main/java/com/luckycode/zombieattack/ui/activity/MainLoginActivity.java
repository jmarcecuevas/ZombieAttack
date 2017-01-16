package com.luckycode.zombieattack.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.luckycode.zombieattack.R;
import com.luckycode.zombieattack.common.BaseActivity;
import com.luckycode.zombieattack.common.BasePresenter;
import com.luckycode.zombieattack.di.app.LuckyGameComponent;
import com.luckycode.zombieattack.di.module.MainLoginModule;
import com.luckycode.zombieattack.presenter.MainLoginPresenterImp;
import com.luckycode.zombieattack.ui.adapter.CustomDialog;
import com.luckycode.zombieattack.ui.viewmodel.MainLoginView;
import com.luckycode.zombieattack.utils.MusicManager;
import com.luckycode.zombieattack.utils.SessionManager;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;

public class MainLoginActivity extends BaseActivity implements MainLoginView,GoogleApiClient.OnConnectionFailedListener{

    private static String TAG= MainLoginActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    @Inject SessionManager sessionManager;
    @Inject GoogleSignInOptions gso;
    @Inject @Named("sign_in_api") GoogleApiClient mGoogleApiClient;
    @Inject MainLoginPresenterImp mainLoginPresenter;
    @BindView(R.id.sign_in_google)Button signInGoogle;
    @BindView(R.id.sign_in_luckycode)Button signInLuckyCode;
    @BindView(R.id.progress)ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient.registerConnectionFailedListener(this);
        hideProgress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        continueMusic=false;
        Log.e("START","MUSIC");
        MusicManager.start(this,0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"PAUSE");
        if (!continueMusic){
            Log.e("PAUSE","MUSIC");
            MusicManager.pause();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main_login;
    }

    @Nullable
    @Override
    protected BasePresenter getPresenter() {
        return mainLoginPresenter;
    }

    @Override
    public void setUpComponent(LuckyGameComponent appComponent) {
        appComponent.plus(new MainLoginModule(this)).inject(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            mainLoginPresenter.handleSignInWithGoogleResult(result);
        }
    }

    @Override
    public void setAvailableInternet(boolean availableInternet) {
        this.isInternetAvailable=availableInternet;
    }

    @Override
    public boolean isInternetAvailable() {
        return isInternetAvailable;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setSuccessLogin(GoogleSignInAccount acc,String userName) {
        continueMusic=false;
        sessionManager.createLoginSession(acc.getDisplayName(),userName,acc.getEmail(),mGoogleApiClient);
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }

    @Override
    public void setSuccessRegister() {
        Toast.makeText(this,getString(R.string.RegisterOK),Toast.LENGTH_LONG).show();
        customDialog.dismiss();
    }

    @Override
    public void setEmptyNicknameError() {
        TextView textView=(TextView)customDialog.findViewById(R.id.tv_dialog);
        textView.setText(getString(R.string.emptyUsername));
    }

    @Override
    public void setShortNicknameError() {
        TextView textView=(TextView)customDialog.findViewById(R.id.tv_dialog);
        textView.setText(getString(R.string.shortUserName));
    }

    @Override
    public void setInvalidNicknameError() {
        TextView textView=(TextView)customDialog.findViewById(R.id.tv_dialog);
        textView.setText(getString(R.string.invalidUsername));
    }

    @Override
    public void setInternetConnectionError() {
        internetSnackBar= Snackbar.make(signInGoogle,getString(R.string.internetError),Snackbar.LENGTH_INDEFINITE);
        View sbView=internetSnackBar.getView();
        sbView.setBackgroundColor(Color.rgb(239,3,151));
        TextView sbText= (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN)
            sbText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        sbText.setGravity(Gravity.CENTER_HORIZONTAL);
        internetSnackBar.show();
    }

    @Override
    public void dismissInternetConnectionError() {
        if(internetSnackBar!=null)
            internetSnackBar.dismiss();
    }

    @Override
    public void signInWithLuckyCode() {
        continueMusic=true;
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }

    @Override
    public void signInWithGoogle() {
        continueMusic=true;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void enterNickname(final GoogleSignInAccount acc) {
        customDialog= new CustomDialog(this,R.style.Theme_Dialog_Translucent);
        ET_nickname=(EditText)customDialog.findViewById(R.id.content);
        Button ok=(Button)customDialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(isInternetAvailable())
                    mainLoginPresenter.register(acc.getDisplayName(),ET_nickname.getText().toString(),
                        acc.getEmail());
            }
        });
        customDialog.show();
    }

    @OnClick({R.id.sign_in_google,R.id.sign_in_luckycode})
    public void OnClick(View v){
        switch(v.getId()){
            case R.id.sign_in_google:
                if(isInternetAvailable())
                    signInWithGoogle();
                break;
            case R.id.sign_in_luckycode:
                if(isInternetAvailable())
                    signInWithLuckyCode();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}


