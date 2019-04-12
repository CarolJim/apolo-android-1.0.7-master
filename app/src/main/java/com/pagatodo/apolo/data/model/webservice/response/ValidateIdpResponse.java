package com.pagatodo.apolo.data.model.webservice.response;

import com.pagatodo.apolo.data.model.factory.ModelPattern;
import com.pagatodo.apolo.data.model.factory.Respuesta;

public class ValidateIdpResponse extends ModelPattern {


    private int IDP,ID_Cliente, ID_CreditoZELL;
    private String Nombre, SolicitudImpresa;


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




    public int getIDP() {
        return IDP;
    }

    public void setIDP(int IDP) {
        this.IDP = IDP;
    }

    public int getID_Cliente() {
        return ID_Cliente;
    }

    public void setID_Cliente(int ID_Cliente) {
        this.ID_Cliente = ID_Cliente;
    }

    public int getID_CreditoZELL() {
        return ID_CreditoZELL;
    }

    public void setID_CreditoZELL(int ID_CreditoZELL) {
        this.ID_CreditoZELL = ID_CreditoZELL;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getSolicitudImpresa() {
        return SolicitudImpresa;
    }

    public void setSolicitudImpresa(String solicitudImpresa) {
        SolicitudImpresa = solicitudImpresa;
    }
}
