package com.pagatodo.apolo.data.model.webservice.response;

import com.pagatodo.apolo.data.model.factory.ModelPattern;
import com.pagatodo.apolo.data.model.factory.Respuesta;

public class LoadImageResponse extends ModelPattern {

        Respuesta Respuesta;

    public Respuesta getRespuesta() {
        return Respuesta;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.Respuesta = respuesta;
    }
}
