package com.pagatodo.apolo.data.model.webservice.response;

import com.pagatodo.apolo.data.model.factory.ModelPattern;
import com.pagatodo.apolo.data.model.factory.Respuesta;
import com.pagatodo.apolo.data.room.entities.Promotor;

public class ValidateUserResponse extends ModelPattern {

    private Respuesta respuesta ;
    private Promotor promotor;


    public Respuesta getRespuesta() {
        return respuesta;
    }

    public Promotor getPromotor() {
        return promotor;
    }
}
