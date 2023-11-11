package com.jcfr.utiles.movil;

import com.jcfr.utiles.string.JSUtil;

import java.util.HashMap;
import java.util.Iterator;

public class JTagMEMaker {

    public static final String END_TAG = "<*>";
    private static final JSUtil JS = JSUtil.JSUtil;
    private String rpta;
    private HashMap<String, String> tabla;
    private boolean parseTildesEnies;

    public JTagMEMaker() {
        this(32);
    }

    public JTagMEMaker(int tamAproxTags) {
        parseTildesEnies = true;
        tabla = new HashMap<>(Math.max(8, tamAproxTags));
    }

    // TAGS SIMPLES
    public void addTag(String tag, String valor) {
        tabla.put(JS.toLowerBlank(tag), JS.toBlank(valor));
    }

    public void addTag(String tag, String valor, String valueIfVacio) {
        tabla.put(JS.toLowerBlank(tag), JS.toBlank(JS._vacio(valor) ? valueIfVacio : valor));
    }

    // TAS PARA TABLA
    public void addTagTabla(String prefix, int fila, int columna, String valor) {
        tabla.put(JS.toLowerBlank(prefix + fila + "," + columna), JS.toBlank(valor));
    }

    public void addTagTabla(String prefix, int fila, int columna, String valor, String valueIfVacio) {
        tabla.put(JS.toLowerBlank(prefix + fila + "," + columna), JS.toBlank(JS._vacio(valor) ? valueIfVacio : valor));
    }

    public void addTagTablaSize(String prefix, int nroFils, int nroCols) {
        addTag(prefix + "fils", nroFils + "");
        addTag(prefix + "cols", nroCols + "");
    }

    // TAGS PARA HASH
    public void addTagHashSize(String prefix, int nroFilas) {
        addTag(prefix + "fils", nroFilas + "");
    }

    public void addTagHashClave(String prefix, int fila, String valor) {
        tabla.put(JS.toLowerBlank(prefix + fila + ",0"), JS.toBlank(valor));
    }

    public void addTagHashClave(String prefix, int fila, String valor, String valueIfVacio) {
        tabla.put(JS.toLowerBlank(prefix + fila + ",0"), JS.toBlank(JS._vacio(valor) ? valueIfVacio : valor));
    }

    public void addTagHashValor(String prefix, int fila, String valor) {
        tabla.put(JS.toLowerBlank(prefix + fila + ",1"), JS.toBlank(valor));
    }

    public void addTagHashValor(String prefix, int fila, String valor, String valueIfVacio) {
        tabla.put(JS.toLowerBlank(prefix + fila + ",1"), JS.toBlank(JS._vacio(valor) ? valueIfVacio : valor));
    }

    // TAGS DE CONTROL
    public void addTagOK() {
        rpta = "OK";
    }

    public void addTagERROR(String msg) {
        rpta = "ERROR";
        addTag("msg", JS.toBlank(msg));
    }

    // EXPORTAR
    public String exportar() {
        return exportar(60);
    }

    public String exportar(int tamAprox) {
        StringBuilder sb = new StringBuilder(tabla.size() * Math.max(20, tamAprox));

        if (rpta == null) addTagERROR("Tag rpta vacío");

        Iterator<String> it = tabla.keySet().iterator();

        String tag;

        // 1er se concatena su respuesta
        sb.append("<rpta>").append(rpta).append(END_TAG);

        while (it.hasNext()) {
            tag = it.next();

            sb.append("<").append(tag).append(">");

            if (parseTildesEnies) {
                sb.append(JS.replaceTildesEnies(tabla.get(tag)));
            } else {
                sb.append(tabla.get(tag));
            }

            sb.append(END_TAG);
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return "rpta=" + rpta + ", nroItems=" + tabla.size() + ", items=" + tabla;
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }

    public boolean isParseUTF8() {
        return parseTildesEnies;
    }

    public void setParseUTF8(boolean parseTildesEnies) {
        this.parseTildesEnies = parseTildesEnies;
    }
}
