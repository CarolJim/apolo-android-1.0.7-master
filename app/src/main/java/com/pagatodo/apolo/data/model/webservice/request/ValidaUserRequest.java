package com.pagatodo.apolo.data.model.webservice.request;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

public class ValidaUserRequest  extends ModelPattern {


    String Promotor ;
    String Password ;
    String IMEI ;

    public ValidaUserRequest() {
    }

    public ValidaUserRequest(String promotor, String password, String IMEI) {
        Promotor = promotor;
        Password = password;
        this.IMEI = IMEI;
    }
}
