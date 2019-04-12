package com.pagatodo.apolo.data.model.factory;

public class RequestImage extends  ModelPattern {

    String Nombre;
    String DocumentoBase64;
    int Longitud;
    String SolicitudImpresa;
    int IDP;


    public RequestImage(String nombre, int longitud, String solicitudImpresa, int IDP, String documentoBase64) {
        Nombre = nombre;
        Longitud = longitud;
        SolicitudImpresa = solicitudImpresa;
        this.IDP = IDP;
        DocumentoBase64 = documentoBase64;
    }
    public RequestImage(String nombre, int longitud, String solicitudImpresa, int IDP) {
        Nombre = nombre;
        Longitud = longitud;
        SolicitudImpresa = solicitudImpresa;
        this.IDP = IDP;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDocumentoBase64() {
        return DocumentoBase64;
    }

    public void setDocumentoBase64(String documentoBase64) {
        DocumentoBase64 = documentoBase64;
    }

    public int getLongitud() {
        return Longitud;
    }

    public void setLongitud(int longitud) {
        Longitud = longitud;
    }

    public String getSolicitudImpresa() {
        return SolicitudImpresa;
    }

    public void setSolicitudImpresa(String solicitudImpresa) {
        SolicitudImpresa = solicitudImpresa;
    }

    public int getIDP() {
        return IDP;
    }

    public void setIDP(int IDP) {
        this.IDP = IDP;
    }
}
