package com.jcfr.utiles.xml;

import com.jcfr.utiles.string.JSUtil;

import java.util.HashMap;
import java.util.Iterator;

public class SimpleTablaXMLMaker {

    private HashMap<String, String> tabla;
    private static final JSUtil JS = JSUtil.JSUtil;

    public SimpleTablaXMLMaker() {
        this(16);
    }

    public SimpleTablaXMLMaker(int tamAproxTags) {
        tabla = new HashMap<>(Math.max(8, tamAproxTags));
    }

    public void addTag(String tag, String valor) {
        tabla.put(JS.toLowerBlank(tag), JS.toBlank(valor));
    }

    public void addTagOK() {
        addTag("rpta", "OK");
    }

    public void addTagERROR(String msg) {
        addTag("msg", JS.toBlank(msg));
        addTag("rpta", "ERROR");
    }

    public String exportar() {
        return exportar(true, 60);
    }

    public String exportar(boolean incluirXMLCabecera) {
        return exportar(incluirXMLCabecera, 50);
    }

    public String exportar(boolean incluirXMLCabecera, int tamAprox) {
        StringBuilder sb = new StringBuilder(tabla.size() * Math.max(20, tamAprox));

        if (tabla.get("rpta") == null) addTagERROR("Tag rpta no fue asignado");

        if (incluirXMLCabecera) sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");

        sb.append("<table>");

        Iterator<String> it = tabla.keySet().iterator();

        String tag;
        while (it.hasNext()) {
            tag = it.next();

            sb.append("<").append(tag).append(">");
            sb.append(tabla.get(tag));
            sb.append("</").append(tag).append(">");

        }

        if (incluirXMLCabecera) sb.append("</table>");

        return sb.toString();
    }
}
