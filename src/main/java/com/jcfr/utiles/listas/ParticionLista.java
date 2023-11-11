package com.jcfr.utiles.listas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParticionLista implements Serializable {

    private static final long serialVersionUID = -3050466688243603136L;

    private ArrayList<Object> items;
    private ArrayList<ParticionItem> itemsParticion;

    public ParticionLista() {
        this(16);
    }

    public ParticionLista(int tamAprox) {
        int tam = tamAprox < 16 ? 16 : tamAprox;
        this.items = new ArrayList<>(tam);
        this.itemsParticion = new ArrayList<>(tam);
    }

    public void addItem(int posicion, Object valor) {
        items.add(valor);
        itemsParticion.add(new ParticionItem(posicion, valor));
    }

    public Object getItem(int pos) {
        return items.get(pos);
    }

    public int getPosicionItem(int pos) {
        return itemsParticion.get(pos).pos;
    }

    public boolean hayItems() {
        return items.size() > 0;
    }

    public List<Object> getItems() {
        return items;
    }

    @SuppressWarnings("unused")
    private static class ParticionItem implements Serializable {

        private static final long serialVersionUID = 4358697115340428790L;

        int pos;
        Object valor;

        public ParticionItem(int pos, Object valor) {
            this.valor = valor;
            this.pos = pos;
        }
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
