package com.pagatodo.apolo.data.room.pojo;

import java.io.Serializable;

public class Bancos implements Serializable {

    boolean Activo;
    String Descripcion;
    int ID_Banco;

    public Bancos() {
    }

    public Bancos(boolean activo, String descripcion, int ID_Banco) {
        Activo = activo;
        Descripcion = descripcion;
        this.ID_Banco = ID_Banco;
    }

    public boolean isActivo() {
        return Activo;
    }

    public void setActivo(boolean activo) {
        Activo = activo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getID_Banco() {
        return ID_Banco;
    }

    public void setID_Banco(int ID_Banco) {
        this.ID_Banco = ID_Banco;
    }
}
