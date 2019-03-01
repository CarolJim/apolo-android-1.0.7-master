package com.pagatodo.apolo.data.model.factory;

public class ReseteoContrasenia extends ModelPattern {

    private String Mensaje;
    private boolean UsuarioValido;

    public String getMensaje() {
        return Mensaje;
    }

    public boolean isUsuarioValido() {
        return UsuarioValido;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public void setUsuarioValido(boolean usuarioValido) {
        UsuarioValido = usuarioValido;
    }
}
