package com.pagatodo.apolo.data.model.factory;

public class request extends ModelPattern {

    private Long ID_Cliente;
    private String NombreCliente;
    private int ID_Banco;
    private String NoTarjeta;
    private String SolicitudImpresa;

    public request(Long ID_Cliente, String nombreCliente, int ID_Banco, String noTarjeta, String solicitudImpresa) {
        this.ID_Cliente = ID_Cliente;
        NombreCliente = nombreCliente;
        this.ID_Banco = ID_Banco;
        NoTarjeta = noTarjeta;
        SolicitudImpresa = solicitudImpresa;
    }

    public Long getID_Cliente() {
        return ID_Cliente;
    }

    public void setID_Cliente(Long ID_Cliente) {
        this.ID_Cliente = ID_Cliente;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        NombreCliente = nombreCliente;
    }

    public int getID_Banco() {
        return ID_Banco;
    }

    public void setID_Banco(int ID_Banco) {
        this.ID_Banco = ID_Banco;
    }

    public String getNoTarjeta() {
        return NoTarjeta;
    }

    public void setNoTarjeta(String noTarjeta) {
        NoTarjeta = noTarjeta;
    }

    public String getSolicitudImpresa() {
        return SolicitudImpresa;
    }

    public void setSolicitudImpresa(String solicitudImpresa) {
        SolicitudImpresa = solicitudImpresa;
    }
}
