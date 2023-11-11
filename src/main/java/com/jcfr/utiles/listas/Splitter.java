package com.jcfr.utiles.listas;

import com.jcfr.utiles.exceptions.JcFRException;
import com.jcfr.utiles.string.JSUtil;

import java.util.ArrayList;
import java.util.List;

public class Splitter {

    private static final JSUtil JS = JSUtil.JSUtil;
    private String separador;

    public Splitter(String separador) {
        if (separador == null) throw new JcFRException("Separador no puede estar vacío");
        this.separador = separador;
    }

    public String toString(List<String> listaSplit) {

        if (JS._vacio(listaSplit)) return "";

        // remover los items vacios, y sacar el ancho promedio
        int listaSplitSize = listaSplit.size();
        ArrayList<String> lista = new ArrayList<>(listaSplitSize);

        String item;
        int sumaSize = 0;

        for (int i = 0; i < listaSplitSize; i++) {
            item = JS.toBlank(listaSplit.get(i));

            if (item.length() > 0) { // solo aquellos items con data
                sumaSize += item.length();
                lista.add(item);
            }
        }

        // lista no tiene elementos vacios y todos son trim.
        int listaSize = lista.size();
        StringBuilder sb = new StringBuilder(sumaSize + listaSize * separador.length());
        for (int i = 0; i < listaSize; i++) {
            if (i == 0) sb.append(lista.get(i));
            else sb.append(separador).append(lista.get(i));
        }

        return sb.toString();
    }

    public List<String> toLista(String fuente) {

        String[] trozo = JS.toBlank(fuente).split(separador);
        if (trozo.length == 0) return new ArrayList<>(5);

        int trozoSize = trozo.length;
        ArrayList<String> lista = new ArrayList<>(trozoSize);
        for (int i = 0; i < trozoSize; i++) {
            if (!JS._vacio(trozo[i])) lista.add(JS.toBlank(trozo[i]));
        }

        return lista;
    }
}
