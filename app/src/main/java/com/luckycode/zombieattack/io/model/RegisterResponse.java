package com.luckycode.zombieattack.io.model;

/**
 * Created by MarceloCuevas on 19/12/2016.
 */

public class RegisterResponse {

    private boolean success;
    private boolean userAlreadyExists;
    private boolean unknownError;

    public boolean isSuccessful() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean doesUserExist() {
        return userAlreadyExists;
    }

    public void setUserAlreadyExists(boolean userAlreadyExists) {
        this.userAlreadyExists = userAlreadyExists;
    }

    public boolean isUnknownError() {
        return unknownError;
    }

    public void setUnknownError(boolean unknownError) {
        this.unknownError = unknownError;
    }
}
