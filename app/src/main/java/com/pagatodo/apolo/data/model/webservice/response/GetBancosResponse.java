package com.pagatodo.apolo.data.model.webservice.response;

import com.pagatodo.apolo.data.model.factory.ModelPattern;
import com.pagatodo.apolo.data.room.pojo.Bancos;

import java.util.List;


public class GetBancosResponse extends ModelPattern {

    private List<Bancos> ListBancos;

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

    public List<Bancos> getListBancos() {
        return ListBancos;
    }
}
