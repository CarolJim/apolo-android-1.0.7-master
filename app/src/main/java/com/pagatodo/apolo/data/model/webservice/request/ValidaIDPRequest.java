package com.pagatodo.apolo.data.model.webservice.request;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

public class ValidaIDPRequest extends ModelPattern {

    String IDP;

    public ValidaIDPRequest(String IDP) {
        this.IDP = IDP;
    }
}
