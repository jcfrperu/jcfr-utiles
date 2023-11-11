package com.jcfr.utiles.listas;

import com.jcfr.utiles.exceptions.JcFRException;

import java.util.ArrayList;
import java.util.List;

public class PartidorListaMaker {

    private List<Object> items;

    public PartidorListaMaker(List<Object> itemsFuente) {
        if (itemsFuente == null || itemsFuente.isEmpty())
            throw new JcFRException("Lista de items no puede estar vacía.");
        this.items = itemsFuente;
    }

    public List<ParticionLista> partirCadaNroItems(int nroItems) {

        if (nroItems <= 0) throw new JcFRException("Parámetro de partirCadaNroItems(...) debe ser mayor que cero.");

        ArrayList<ParticionLista> divisiones = new ArrayList<>(1024);
        ParticionLista division = new ParticionLista(nroItems);

        int count = 0;

        List<Object> itemsRef = items;
        int itemsSize = itemsRef.size();

        for (int i = 0; i < itemsSize; i++) {
            division.addItem(i, itemsRef.get(i));
            count++;
            // count siempre avanza de 1 en 1, nunca tomara valores > max
            if (count == nroItems) {
                divisiones.add(division);
                division = new ParticionLista(nroItems + 1);
                count = 0;
            }
        }
        // posibles items en la ultima division
        if (division.hayItems()) divisiones.add(division);

        return divisiones;
    }

    public List<ParticionLista> partirAproxEnNroPartes(int nroPartes) {
        if (nroPartes <= 0)
            throw new JcFRException("Parámetro de partirAproxEnNroPartes(...) debe ser mayor que cero.");
        return partirCadaNroItems((int) Math.ceil((double) items.size() / nroPartes));
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
