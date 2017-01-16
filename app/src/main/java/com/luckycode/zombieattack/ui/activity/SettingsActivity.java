package com.luckycode.zombieattack.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.luckycode.zombieattack.R;
import com.luckycode.zombieattack.common.BaseActivity;
import com.luckycode.zombieattack.common.BasePresenter;
import com.luckycode.zombieattack.di.app.LuckyGameComponent;
import com.luckycode.zombieattack.di.module.SettingsModule;
import com.luckycode.zombieattack.ui.viewmodel.SettingsView;
import com.luckycode.zombieattack.utils.MusicManager;
import com.luckycode.zombieattack.utils.SessionManager;
import com.luckycode.zombieattack.utils.Settings;
import com.luckycode.zombieattack.utils.SettingsManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity implements SettingsView {

    private boolean continueMusic=true;
    private SettingsManager settingsManager;
    @BindView(R.id.music)CheckedTextView music;
    @BindView(R.id.vibration)CheckedTextView vibration;
    @BindView(R.id.account)TextView account;
    @BindView(R.id.signOut)TextView signOut;
    @BindView(R.id.exitSettings)ImageView exitSettings;
    @BindView(R.id.ok)ImageView ok;
    @BindView(R.id.numberPicker)NumberPicker numberPicker;
    @Inject SessionManager sessionManager;
    @Inject SharedPreferences sharedPreferences;

    @Override
    protected void onPause() {
        super.onPause();
        if(!continueMusic)
            MusicManager.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String userName=sharedPreferences.getString("userName","NULL");
        account.setText(userName);
        settingsManager=new SettingsManager(this,userName);
        setNumberPicker();
        music.setChecked(SettingsManager.getMusic());
        vibration.setChecked(SettingsManager.getVibration());

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_settings;
    }

    @Nullable
    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void setUpComponent(LuckyGameComponent appComponent) {
        appComponent.plus(new SettingsModule()).inject(this);
    }

    @Override
    public void setNumberPicker() {
        numberPicker.setMinValue(Settings.MIN_SEARCH_RADIUS_IN_MTRS);
        numberPicker.setMaxValue(Settings.MAX_SEARCH_RADIUS_IN_MTRS);
        numberPicker.setValue(SettingsManager.getRadius());
        numberPicker.setWrapSelectorWheel(false);
    }

    @Override
    public void savePreferences() {
        SettingsManager.savePreferences(numberPicker.getValue(),music.isChecked(),vibration.isChecked());
        finish(); overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    @Override
    public void exitSettings() {
        finish();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    @Override
    public void signOut() {
        continueMusic=false;
        sessionManager.logoutUser();
        finishAffinity();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }

    @OnClick({R.id.music,R.id.vibration,R.id.exitSettings,R.id.ok,R.id.signOut})
    public void OnClick(View view){
        switch(view.getId()){
            case R.id.music:
                if(music.isChecked()){
                    music.setChecked(false);
                    continueMusic=false;
                }else{
                    music.setChecked(true);
                    continueMusic=true;
                }
                break;
            case R.id.vibration:
                if(vibration.isChecked())
                    vibration.setChecked(false);
                else
                    vibration.setChecked(true);
                break;
            case R.id.ok:
                savePreferences();
                break;
            case R.id.signOut:
                signOut();
                break;
        }
    }

}
