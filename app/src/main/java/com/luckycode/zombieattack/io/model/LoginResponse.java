package com.luckycode.zombieattack.io.model;

/**
 * Created by Marcelo Cuevas on 28/11/2016.
 */

public class LoginResponse {
    private boolean success;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccessful(){
        return success;
    }
}
