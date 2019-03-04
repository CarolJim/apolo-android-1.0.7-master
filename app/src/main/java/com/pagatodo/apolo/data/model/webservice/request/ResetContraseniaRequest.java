package com.pagatodo.apolo.data.model.webservice.request;

import com.pagatodo.apolo.data.model.factory.ModelPattern;

/**
 * Created by cjimenez on 01/03/2019
 */

public class ResetContraseniaRequest extends ModelPattern {

    private int ID_Promotor;
    private String Contrasenia;
    private String IMEI;
    private boolean ResetContrasenia;
    private String Usuario;

    public ResetContraseniaRequest(){

    }

    public ResetContraseniaRequest(int idpromotor, String contrasenia, String IMEI, boolean resetContrasenia, String usuario){
        ID_Promotor = idpromotor;
        Contrasenia = contrasenia;
        this.IMEI = IMEI;
        ResetContrasenia = resetContrasenia;
        Usuario = usuario;

    }


}
