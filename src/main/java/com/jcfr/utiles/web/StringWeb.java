package com.jcfr.utiles.web;

import com.jcfr.utiles.Constantes;

import java.io.Serializable;

public class StringWeb implements Serializable {

    private static final long serialVersionUID = -1392828549649961569L;

    private String valor;

    public StringWeb(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public String getEntersPorBr() {
        if (valor == null) return null;
        return valor.replace(Constantes.ENTER, "<br />");
    }

    public String getEntersLinuxPorBr() {
        if (valor == null) return null;
        return valor.replace(Constantes.ENTER_LINUX, "<br />");
    }

    public String getEntersPorEspacios() {
        if (valor == null) return null;
        return valor.replace(Constantes.ENTER, " ");
    }

    public String getCuteRecortar(int tamAproxRecortar, boolean incluirPuntos) {

        if (valor == null) return null;

        // Primero se recorta para no tener que cortar un &nbps; (&eacute;, &ntilde;, etc)
        // ya que el texto saldria modificado (tendria valores &nbps que no son parte del string original).
        // El unico problema de este orden, es que lacadena no siempre va a quedar con la misma longitud
        // que nroCaracAprox.

        if (incluirPuntos) {
            // los puntos al final se agregan unicamente si se recorto algo, indicando que hay mas caracteres
            if (valor.length() <= tamAproxRecortar) {
                // no hay necesidad de cut ni de colocar los tres puntos
                return getCuteFormat();
            } else {
                // return new StringWeb(getCut(tamAproxRecortar)).getCuteFormat() + "...";
                return StringWeb.toCute(StringWeb.cut(valor, tamAproxRecortar)) + "...";
            }
        }

        // si no se incluyen puntos
        return StringWeb.toCute(StringWeb.cut(valor, tamAproxRecortar));
        // return new StringWeb(getCut(tamAproxRecortar)).getCuteFormat();
    }

    public String getCut(int tamanio) {
        return StringWeb.cut(valor, tamanio);
    }

    public String getCuteFormat() {
        return StringWeb.toCute(valor);
    }

    public String getJavascriptFormat() {
        return StringWeb.toJavascript(valor);
    }

    @Override
    public String toString() {
        return StringWeb.toCute(valor);
    }

    // metodos estaticos que se colocan por practicismo
    public static String cut(String cadena, int tamanio) {
        if (cadena == null) return null;
        return cadena.length() > tamanio ? cadena.substring(0, tamanio) : cadena;
    }

    public static String cutTrim(String cadena, int tamanio) {
        if (cadena == null) return null;

        String cad = cadena.trim();
        return cad.length() > tamanio ? cad.substring(0, tamanio) : cad;
    }

    public static String toCute(String cadenaConTildes) {
        if (cadenaConTildes == null) return null;

        StringBuilder sb = new StringBuilder((int) (1.4 * cadenaConTildes.length()));

        char c;
        int nro = cadenaConTildes.length();

        for (int i = 0; i < nro; i++) {
            c = cadenaConTildes.charAt(i);
            if (c == 'á') sb.append("&aacute;");
            else if (c == 'é') sb.append("&eacute;");
            else if (c == 'í') sb.append("&iacute;");
            else if (c == 'ó') sb.append("&oacute;");
            else if (c == 'ú') sb.append("&uacute;");
            else if (c == 'ñ') sb.append("&ntilde;");
            else if (c == 'Á') sb.append("&Aacute;");
            else if (c == 'É') sb.append("&Eacute;");
            else if (c == 'Í') sb.append("&Iacute;");
            else if (c == 'Ó') sb.append("&Oacute;");
            else if (c == 'Ú') sb.append("&Uacute;");
            else if (c == 'Ñ') sb.append("&Ntilde;");
            else sb.append(c);
        }

        return sb.toString();
    }

    public static String toJavascript(String cadenaConTildes) {
        if (cadenaConTildes == null) return null;

        StringBuilder sb = new StringBuilder((int) (1.4 * cadenaConTildes.length()));

        char c;
        int nro = cadenaConTildes.length();

        for (int i = 0; i < nro; i++) {
            c = cadenaConTildes.charAt(i);
            if (c == 'á') sb.append("\\xE1");
            else if (c == 'é') sb.append("\\xE9");
            else if (c == 'í') sb.append("\\xED");
            else if (c == 'ó') sb.append("\\xF3");
            else if (c == 'ú') sb.append("\\xFA");
            else if (c == 'ñ') sb.append("\\xF1");
            else if (c == 'Á') sb.append("\\xC1");
            else if (c == 'É') sb.append("\\xC9");
            else if (c == 'Í') sb.append("\\xCD");
            else if (c == 'Ó') sb.append("\\xD3");
            else if (c == 'Ú') sb.append("\\xDA");
            else if (c == 'Ñ') sb.append("\\xD1");
            else sb.append(c);

        }

        return sb.toString();
    }

    public static String fromCute(String cadenaConCutes) {
        if (cadenaConCutes == null) return null;

        String res = cadenaConCutes;

        res = res.replace("&aacute;", "á");
        res = res.replace("&eacute;", "é");
        res = res.replace("&iacute;", "í");
        res = res.replace("&oacute;", "ó");
        res = res.replace("&uacute;", "ú");
        res = res.replace("&ntilde;", "ñ");
        res = res.replace("&Aacute;", "Á");
        res = res.replace("&Eacute;", "É");
        res = res.replace("&Iacute;", "Í");
        res = res.replace("&Oacute;", "Ó");
        res = res.replace("&Uacute;", "Ú");
        res = res.replace("&Ntilde;", "Ñ");
        res = res.replace("&nbsp;", " ");

        return res;
    }

    public static String fromJavascript(String cadenaConCutes) {
        if (cadenaConCutes == null) return null;

        String res = cadenaConCutes;

        res = res.replace("\\xE1", "á");
        res = res.replace("\\xE9", "é");
        res = res.replace("\\xED", "í");
        res = res.replace("\\xF3", "ó");
        res = res.replace("\\xFA", "ú");
        res = res.replace("\\xF1", "ñ");
        res = res.replace("\\xC1", "Á");
        res = res.replace("\\xC9", "É");
        res = res.replace("\\xCD", "Í");
        res = res.replace("\\xD3", "Ó");
        res = res.replace("\\xDA", "Ú");
        res = res.replace("\\xD1", "Ñ");

        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof StringWeb) {
            StringWeb objCast = (StringWeb) obj;
            return objCast.equals(this);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + (this.valor != null ? this.valor.hashCode() : 0);
        return hash;
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
