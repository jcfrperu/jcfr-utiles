package com.jcfr.utiles.exceptions;

import com.jcfr.utiles.enums.JExceptionEnum;
import com.jcfr.utiles.string.JSUtil;

import java.io.Serializable;

public class JBaseException extends Exception implements Serializable {

    public static final String DEFAULT_CODIGO = "JBEDEF-000";

    private static final long serialVersionUID = 8761203602216128831L;

    protected JExceptionEnum tipo;
    protected String codigo;
    protected String mensaje;
    protected Object info; // objeto para pasar info adicional

    public JBaseException(String mensaje) {
        this(DEFAULT_CODIGO, mensaje, JExceptionEnum.ERROR, null);
    }

    public JBaseException(String codigo, String mensaje) {
        this(codigo, mensaje, JExceptionEnum.ERROR, null);
    }

    public JBaseException(String codigo, String mensaje, Object info) {
        this(codigo, mensaje, JExceptionEnum.ERROR, null);
    }

    public JBaseException(String codigo, String mensaje, JExceptionEnum tipo) {
        this(codigo, mensaje, tipo, null);
    }

    public JBaseException(String codigo, String mensaje, JExceptionEnum tipo, Object info) {
        super(mensaje);

        this.codigo = parseCode(codigo);
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.info = info;
    }

    private String parseCode(String codigoValue) {
        if (JSUtil.JSUtil._vacio(codigoValue)) return DEFAULT_CODIGO;
        return codigoValue;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public JExceptionEnum getTipo() {
        return tipo;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    @Override
    public String toString() {
        if (JSUtil.JSUtil._vacio(codigo)) return mensaje;
        return codigo + " - " + mensaje;
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }

}
