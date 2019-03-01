package com.pagatodo.apolo.data.model.webservice.response;

import com.pagatodo.apolo.data.model.factory.ModelPattern;
import com.pagatodo.apolo.data.model.factory.ReseteoContrasenia;
import com.pagatodo.apolo.data.model.factory.Respuesta;

/**
 * Created by cjimenez on 25/02/2019
 */

public class ResetContraseniaResponse extends ModelPattern {

    private Respuesta Respuesta;
    private int Codigo;
    private boolean Exito;
    private String Mensaje;

    private ReseteoContrasenia ReseteoContrasenia;

    public com.pagatodo.apolo.data.model.factory.Respuesta getRespuesta() {
        return Respuesta;
    }

    public int getCodigo() {
        return Codigo;
    }

    public boolean isExito() {
        return Exito;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public ReseteoContrasenia getReseteoContrasenia() {
        return ReseteoContrasenia;
    }

    public void setRespuesta(com.pagatodo.apolo.data.model.factory.Respuesta respuesta) {
        Respuesta = respuesta;
    }

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }

    public void setExito(boolean exito) {
        Exito = exito;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public void setReseteoContrasenia(com.pagatodo.apolo.data.model.factory.ReseteoContrasenia reseteoContrasenia) {
        ReseteoContrasenia = reseteoContrasenia;
    }
}
