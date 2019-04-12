package com.pagatodo.apolo.data.room.pojo;

public class Bines {

    boolean Activo;
    String Descripcion;
    int ID_Bin,ID_Banco,BIN;

    public Bines(boolean activo, String descripcion, int ID_Bin, int ID_Banco, int BIN) {
        Activo = activo;
        Descripcion = descripcion;
        this.ID_Bin = ID_Bin;
        this.ID_Banco = ID_Banco;
        this.BIN = BIN;
    }

    public Bines() {
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

    public int getID_Bin() {
        return ID_Bin;
    }

    public void setID_Bin(int ID_Bin) {
        this.ID_Bin = ID_Bin;
    }

    public int getID_Banco() {
        return ID_Banco;
    }

    public void setID_Banco(int ID_Banco) {
        this.ID_Banco = ID_Banco;
    }

    public int getBIN() {
        return BIN;
    }

    public void setBIN(int BIN) {
        this.BIN = BIN;
    }
}
