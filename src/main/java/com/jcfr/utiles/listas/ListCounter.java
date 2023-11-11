package com.jcfr.utiles.listas;

import com.jcfr.utiles.order.OC;

import java.util.*;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ListCounter {

    private ArrayList<Object> itemsRepetidos;
    private ArrayList<Object> itemsNoRepetidos;
    private HashMap<Object, Integer> mapa;
    private ArrayList<Object> itemsMasRepetidos;

    public ListCounter() {
        this(16);
    }

    public ListCounter(int tamAprox) {
        int tam = Math.max(16, tamAprox);
        int tamRep = Math.max(16, (int) ((25.0 / 100.0) * tam));

        mapa = new HashMap<>(tam);
        itemsRepetidos = new ArrayList<>(tamRep);
        itemsNoRepetidos = new ArrayList<>(Math.max(16, tam - tamRep));
        itemsMasRepetidos = new ArrayList<>(5);
    }

    public void add(Object[] items) {
        for (Object item : items) {
            addItem(item);
        }
    }

    public void add(List<Object> items) {
        for (Object item : items) {
            addItem(item);
        }
    }

    private void addItem(Object item) {
        Integer nroTemp = mapa.get(item);
        if (nroTemp == null) mapa.put(item, 1);
        else mapa.put(item, nroTemp + 1);
    }

    public void add(Object item) {
        addItem(item);
    }

    public void calcular() {
        Iterator it = mapa.keySet().iterator();
        Object clave;
        int valorInt;

        int max = 0;
        boolean seSeteoMaxMin = false;

        while (it.hasNext()) {
            clave = it.next();
            valorInt = mapa.get(clave);

            if (!seSeteoMaxMin) {
                max = valorInt;
                seSeteoMaxMin = true;
            }

            if (valorInt > max) {
                itemsMasRepetidos.clear();
                itemsMasRepetidos.add(clave);
                max = valorInt;
            } else if (valorInt == max) {
                itemsMasRepetidos.add(clave);
            }

            if (valorInt == 1) itemsNoRepetidos.add(clave);
            else itemsRepetidos.add(clave);

        }

        Comparator comparator = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return OC.compare(getNroVeces(o2), getNroVeces(o1));
            }
        };

        Collections.sort(itemsRepetidos, comparator);
    }

    public int getNroVeces(Object item) {
        Integer nroTemp = mapa.get(item);
        if (nroTemp == null) return 0;
        return nroTemp;
    }

    public boolean hayItemsDiferentes() {
        return mapa.size() > 1; // tambien cuenta los nulos como un item
    }

    public List<Object> getMasRepetidos() {
        return itemsMasRepetidos;
    }

    public List<Object> getRepetidos() {
        return itemsRepetidos;
    }

    public List<Object> getNoRepetidos() {
        return itemsNoRepetidos;
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
