package com.pagatodo.apolo.data.room.entities;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

public class Headers extends ModelPattern {

    String Password ;
    String IMEI ;

    public Headers(){

    }


    public Headers(String password, String IMEI){

        Password = password;
        this.IMEI = IMEI;
    }


    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

}
