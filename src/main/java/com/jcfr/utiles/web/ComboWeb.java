package com.jcfr.utiles.web;

import com.jcfr.utiles.exceptions.JcFRException;
import com.jcfr.utiles.repositorios.DataTable;
import com.jcfr.utiles.string.JSUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboWeb implements Serializable {

    private static final long serialVersionUID = -5414704598378132407L;

    private ComboWebItem seleccionado; // es una copia del valor seleccionado
    private ArrayList<ComboWebItem> items;
    private HashMap<String, Object> itemsAsMap;

    private Object itemsAsObject;

    public ComboWeb() {
        items = new ArrayList<>();
        itemsAsMap = new HashMap<>();
    }

    public ComboWeb(List<? extends ComboWebItem> listaItems) {

        if (listaItems == null) {

            items = new ArrayList<>(1);
            itemsAsMap = new HashMap<>();

            items.add(new ComboWebItem("-1", "(combo nulo)"));
            itemsAsMap.put("-1", "(combo nulo)");

        } else if (listaItems.isEmpty()) {

            items = new ArrayList<>(1);
            itemsAsMap = new HashMap<>();

            items.add(new ComboWebItem("-1", "(sin valores)"));
            itemsAsMap.put("-1", "(sin valores)");

        } else {

            items = new ArrayList<>(listaItems.size());
            itemsAsMap = new HashMap<>();

            items.addAll(listaItems);

            for (ComboWebItem listaItem : listaItems) {
                itemsAsMap.put(listaItem.getId(), listaItem.getLabel());
            }

        }

        makeSeleccionado(items.get(0));
    }

    public ComboWeb(DataTable tablaItems) {

        if (tablaItems == null) {

            items = new ArrayList<>(1);
            itemsAsMap = new HashMap<>();

            items.add(new ComboWebItem("-1", "(combo nulo)"));
            itemsAsMap.put("-1", "(combo nulo)");

        } else if (tablaItems.estaVacia()) {

            items = new ArrayList<>(1);
            itemsAsMap = new HashMap<>();

            items.add(new ComboWebItem("-1", "(sin valores)"));
            itemsAsMap.put("-1", "(sin valores)");

        } else if (tablaItems.getNroColumnas() < 2) {

            throw new JcFRException("El DataTable debe tener al menos dos columnas!");

        } else {

            int nroFils = tablaItems.getNroFilas();

            items = new ArrayList<>(nroFils);
            itemsAsMap = new HashMap<>(nroFils);

            for (int i = 0; i < nroFils; i++) {
                items.add(new ComboWebItem(tablaItems.gs(i, 0), tablaItems.gs(i, 1)));
                itemsAsMap.put(tablaItems.gs(i, 0), tablaItems.gs(i, 1));
            }
        }

        makeSeleccionado(items.get(0));
    }

    public ComboWeb(DataTable tablaItems, String campoID, String campoLabel) {

        items = new ArrayList<>();
        itemsAsMap = new HashMap<>();

        if (tablaItems == null) {

            items.add(new ComboWebItem("-1", "(combo nulo)"));
            itemsAsMap.put("-1", "(combo nulo)");

        } else if (tablaItems.estaVacia()) {

            items.add(new ComboWebItem("-1", "(sin valores)"));
            itemsAsMap.put("-1", "(sin valores)");

        } else if (tablaItems.getNroColumnas() < 2) {

            throw new JcFRException("El DataTable debe tener al menos dos columnas!");

        } else {

            int nroFils = tablaItems.getNroFilas();
            for (int i = 0; i < nroFils; i++) {
                items.add(new ComboWebItem(tablaItems.gs(i, campoID), tablaItems.gs(i, campoLabel)));
                itemsAsMap.put(tablaItems.gs(i, campoID), tablaItems.gs(i, campoLabel));
            }

        }

        makeSeleccionado(items.get(0));
    }

    public void asegurarNoVacio() {
        if (items.isEmpty()) {
            items.add(new ComboWebItem("-1", "(sin valores)"));
            itemsAsMap.put("-1", "(sin valores)");
            makeSeleccionado(items.get(0));
        }
    }

    private void makeSeleccionado(ComboWebItem nuevo) {
        this.seleccionado = new ComboWebItem(nuevo.getId(), nuevo.getLabel());
    }

    public List<ComboWebItem> getItems() {
        return items;
    }

    private int buscarPosicion(String campoID) {
        List<ComboWebItem> itemsLocal = items;
        for (int i = itemsLocal.size(); --i >= 0; ) {
            if (JSUtil.JSUtil._equiv(campoID, itemsLocal.get(i).getId())) return i;
        }
        return -1;
    }

    public void removerItem(String campoID) {
        int posEncontrado = buscarPosicion(campoID);
        if (posEncontrado > -1) {
            items.remove(posEncontrado);
            if (hayItems()) makeSeleccionado(items.get(0));
        }
    }

    public boolean hayItems() {
        return !items.isEmpty();
    }

    public int getNroItems() {
        return items.size();
    }

    public ComboWebItem getItem(int pos) {
        return items.get(pos);
    }

    public void addItem(String campoID, String campoLabel) {
        items.add(new ComboWebItem(campoID, campoLabel));
        itemsAsMap.put(campoID, campoLabel);
        if (items.size() == 1) makeSeleccionado(items.get(0));
    }

    public void addItemSel(String campoID, String campoLabel) {
        addItem(campoID, campoLabel);
        setSeleccionado(campoID, campoLabel);
    }

    public void addItemSelIni(String campoID, String campoLabel) {
        addItem(campoID, campoLabel);
        ubicarAlInicio(campoID);
        setSeleccionado(campoID, campoLabel);
    }

    public ComboWebItem getSeleccionado() {
        return seleccionado;
    }

    public String getIdSeleccionado() {
        return seleccionado.getId();
    }

    public void setIdSeleccionado(String idSeleccionado) {
        setSeleccionado(idSeleccionado);
    }

    public void setSelID(Object idSeleccionado) {
        // este metodo se usa mucho, creado para acortar el codigo
        setSeleccionado(String.valueOf(idSeleccionado));
    }

    public void setSelID(String idSeleccionado) {
        // este metodo se usa mucho, creado para acortar el codigo
        setSeleccionado(idSeleccionado);
    }

    public String getLabelSeleccionado() {
        return seleccionado.getLabel();
    }

    public void setSeleccionado(ComboWebItem seleccionado) {
        makeSeleccionado(seleccionado);
    }

    public void setSeleccionado(String campoID, String label) {
        this.seleccionado = new ComboWebItem(campoID, label);
    }

    public void setSeleccionado(String campoID) {
        int posEncontrado = buscarPosicion(campoID);
        if (posEncontrado > -1) setSeleccionado(items.get(posEncontrado));
    }

    public void ubicarAlInicio(String campoID) {
        int posEncontrado = buscarPosicion(campoID);
        if (posEncontrado > -1) { // hace un swap con la 1era posicion
            ComboWebItem removido = items.remove(posEncontrado);
            items.add(0, removido);
            // aca no se actualizar el mapa
        }
    }

    @Override
    public String toString() {
        return "selected=" + seleccionado + ", items=" + items;
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }

    public Object getItemsAsObject() {
        return itemsAsObject;
    }

    public void setItemsAsObject(Object itemsAsObject) {
        this.itemsAsObject = itemsAsObject;
    }

    public Map<String, Object> getItemsAsMap() {
        return itemsAsMap;
    }

}
