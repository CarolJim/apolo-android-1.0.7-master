package com.pagatodo.apolo.data.model.webservice.request;

import com.pagatodo.apolo.data.model.factory.ModelPattern;
import com.pagatodo.apolo.data.model.factory.RequestImage;

public class LoadImageRequest extends ModelPattern {

    RequestImage request;

    public RequestImage getRequest() {
        return request;
    }

    public void setRequest(RequestImage request) {
        this.request = request;
    }
}
