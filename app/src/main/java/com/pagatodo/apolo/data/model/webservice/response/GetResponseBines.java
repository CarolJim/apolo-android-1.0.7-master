package com.pagatodo.apolo.data.model.webservice.response;

import com.pagatodo.apolo.data.room.pojo.Bancos;
import com.pagatodo.apolo.data.room.pojo.Bines;

import java.util.List;

public class GetResponseBines {

    private List<Bines> ListBines;

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

    public List<Bines> getListBines() {
        return ListBines;
    }

}
