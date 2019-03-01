package com.pagatodo.apolo.data.model.webservice.response;

import com.pagatodo.apolo.data.model.factory.ModelPattern;
import com.pagatodo.apolo.data.model.factory.Respuesta;
import com.pagatodo.apolo.data.room.entities.Promotor;

/**
 * Created by cjimenez on 25/02/2019
 */

public class ValidateUserResponse extends ModelPattern {

    private Respuesta Respuesta;
    private Promotor Promotor;

    private int Codigo;
    private boolean Exito;
    private String Mensaje;

    public int getCodigo() {
        return Codigo;
    }

    public boolean isExito() {
        return Exito;
    }

    public String getMensaje() {
        return Mensaje;
    }


    public Respuesta getRespuesta() {
        return Respuesta;
    }

    public Promotor getPromotor() {
        return Promotor;
    }
}
