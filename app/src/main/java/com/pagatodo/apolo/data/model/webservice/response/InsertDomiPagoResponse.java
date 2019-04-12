package com.pagatodo.apolo.data.model.webservice.response;

import com.pagatodo.apolo.data.model.factory.ModelPattern;
import com.pagatodo.apolo.data.model.factory.Respuesta;

public class InsertDomiPagoResponse extends ModelPattern {

    private Respuesta Respuesta;
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

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }

    public void setExito(boolean exito) {
        Exito = exito;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public Respuesta getRespuesta() {
        return Respuesta;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.Respuesta = respuesta;
    }
}
