package com.luckycode.zombieattack.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.luckycode.zombieattack.R;
import com.luckycode.zombieattack.common.BasePresenter;
import com.luckycode.zombieattack.common.LogRegBaseActivity;
import com.luckycode.zombieattack.di.app.LuckyGameComponent;
import com.luckycode.zombieattack.di.module.LogRegModule;
import com.luckycode.zombieattack.presenter.RegisterPresenterImp;
import com.luckycode.zombieattack.ui.viewmodel.RegisterView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends LogRegBaseActivity implements RegisterView{
    @BindView(R.id.name)EditText name;
    @BindView(R.id.userName)EditText userName;
    @BindView(R.id.email)EditText email;
    @BindView(R.id.userPassword)EditText password;
    @Inject RegisterPresenterImp registerPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerPresenter.validateUserName(userName);
        registerPresenter.validatePassword(password);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Nullable
    @Override
    protected BasePresenter getPresenter() {
        return registerPresenter;
    }

    @Override
    protected void setUpComponent(LuckyGameComponent component) {
        component.plus(new LogRegModule(this)).inject(this);
    }

    @Override
    public void setAvailableInternet(boolean availableInternet) { isInternetAvailable=availableInternet;}

    @Override
    public boolean isInternetAvailable() {
        return isInternetAvailable;
    }

    @Override
    public void showProgress() { progressDialog.show();}

    @Override
    public void hideProgress() { progressDialog.hide();}

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
        if(internetSnackBar!=null){
            internetSnackBar.dismiss();
        }
    }

    @Override
    public void setEmptyNameError() { name.setError(getString(R.string.emptyName));}

    @Override
    public void setEmptyUserNameError() {userName.setError(getString(R.string.emptyUsername));}

    @Override
    public void setEmptyPasswordError() {password.setError(getString(R.string.emptyPassword));}

    @Override
    public void setEmptyEmailError() {email.setError(getString(R.string.emptyEmail));
    }

    @Override
    public void setValidUserName(boolean validUserName) {
        this.validUserName=validUserName;
    }

    @Override
    public void setValidEmail(boolean validEmail) {
        this.validEmail=validEmail;
    }

    @Override
    public void setSuccessRegister() {
        Toast.makeText(this,getString(R.string.RegisterOK),Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public boolean isUserNameValid() {return validUserName;}

    @Override
    public boolean isEmailValid() {
        return validEmail;
    }

    @Override
    public void setInvalidUserNameError() {
        userName.setError(getString(R.string.shortUserName));
    }

    @Override
    public void setInvalidPasswordError() {
        password.setError(getString(R.string.shortPassword));
    }

    @Override
    public void setInvalidEmailError() {
        email.setError(getString(R.string.invalidEmail));
    }

    @Override
    public void setUserAlreadyExistsError() {
        alertDialog.setMessage(getString(R.string.invalidUsername));
        alertDialog.show();
    }

    @Override
    public void backToLogin() {
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }

    @Override
    public void setUnknownError() {alertDialog.setMessage(getString(R.string.unknownError));}

    @Override
    public void setValidPassword(boolean validPassword) {this.validPassword=validPassword; }

    @Override
    public boolean isPasswordValid() {
        return validPassword;
    }

    @OnClick({R.id.btnRegister,R.id.tvLogin})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.btnRegister:
                if(isInternetAvailable())
                    registerPresenter.register(name.getText().toString(),userName.getText().toString(),
                            email.getText().toString(),password.getText().toString());
                break;
            case R.id.tvLogin:
                backToLogin();
                break;
        }
    }
}
