package com.jcfr.utiles.exceptions;

import java.io.Serializable;

public class JcFRException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -2338820794269848104L;

    public JcFRException(String mensaje) {
        super("JcFR Exception: " + mensaje);
    }

    public JcFRException(String codigo, String mensaje) {
        super("JcFR Exception: " + codigo + " - " + mensaje);
    }

    @Override
    public String toString() {
        return getMessage();
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
