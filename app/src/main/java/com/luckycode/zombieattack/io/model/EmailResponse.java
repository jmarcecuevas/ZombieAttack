package com.luckycode.zombieattack.io.model;

/**
 * Created by MarceloCuevas on 09/01/2017.
 */

public class EmailResponse {
    private boolean emailAlreadyExists;
    private String userName;

    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }
    public boolean doesEmailExists(){
        return emailAlreadyExists;
    }

    public void setEmailAlreadyExists(boolean emailAlreadyExists){
        this.emailAlreadyExists=emailAlreadyExists;
    }

}
