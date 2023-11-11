package com.jcfr.utiles;

import java.io.Serializable;

public class SimpleBean implements Serializable {

    private static final long serialVersionUID = -904975161420449848L;

    protected String campo;

    public SimpleBean() {
        campo = " ";
    }

    public SimpleBean(String campo) {
        this.campo = campo;
    }

    public SimpleBean(String campo, boolean mayuscula) {
        if (mayuscula) {
            if (campo == null) this.campo = null;
            else this.campo = campo.toUpperCase();
        } else {
            this.campo = campo;
        }
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public int getSize() {
        return campo == null ? 0 : campo.length();
    }

    @Override
    public String toString() {
        return campo;
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
