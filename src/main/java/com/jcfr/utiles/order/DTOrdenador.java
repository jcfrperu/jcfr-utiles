package com.jcfr.utiles.order;

import com.jcfr.utiles.exceptions.JcFRException;
import com.jcfr.utiles.repositorios.DataTable;
import com.jcfr.utiles.string.JSUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DTOrdenador {

    private DataTable dt;

    public DTOrdenador(DataTable dt) {
        if (dt == null) throw new JcFRException("DataTable es nulo!");
        this.dt = dt;
    }

    public void orderBy(String sentenciaOrderBy) {

        if (dt.estaVacia()) return; // no hace nada
        if (JSUtil.JSUtil._vacio(sentenciaOrderBy)) throw new JcFRException("Sentencia order by esta vacía!");

        String s = sentenciaOrderBy.trim();
        String[] c = s.split(","); // separa por comas

        if (c.length == 0)
            throw new JcFRException("Sentencia order by no tiene formato correcto! ( campo [asc | desc] )");

        // CALCULANDO LISTAS DE CAMPOS Y SUS RESPECTIVOS SENTIDOS DE ORDENACION
        List<Integer> sentidos = new ArrayList<>(5);
        List<String> campos = new ArrayList<>(5);

        String[] csub;
        for (int i = 0; i < c.length; i++) {
            if (JSUtil.JSUtil._vacio(c[i]))
                throw new JcFRException("Criterio de ordenación es nulo o vacío! ( campo [asc | desc] )");
            csub = c[i].trim().split(" "); // se para por espacios en blanco
            if (csub.length == 0)
                throw new JcFRException("Criterio de ordenación no tiene un formato correcto! ( campo [asc | desc] )");
            if (csub.length == 1) {
                if (csub[0] == null)
                    throw new JcFRException("Criterio de ordenación no tiene un formato correcto! ( campo [asc | desc] )");
                campos.add(csub[0].trim()); // si el campo no existe, en el metodo posterior a este lo valida
                sentidos.add(1); // por defecto es ascendente
            } else if (csub.length == 2) { // se considera como si tuviera siempre 2, desde la posicion 3 se ignora
                if (csub[0] == null)
                    throw new JcFRException("Criterio de ordenación no tiene un formato correcto! ( campo [asc | desc] )");
                if (csub[1] == null)
                    throw new JcFRException("Criterio de ordenación no tiene un formato correcto! ( campo [asc | desc] )");
                campos.add(csub[0].trim());
                sentidos.add(csub[1].trim().equalsIgnoreCase("desc") ? -1 : 1);
            } else {
                throw new JcFRException("Criterio de ordenación no tiene un formato correcto! ( campo [asc | desc] )");
            }
        }

        if (sentidos.size() != campos.size())
            throw new JcFRException("Nro de columnas a ordernar no coincide con el nro de sentidos de ordenación");

        int tamanio = campos.size();
        ArrayList<Integer> posColumnas = new ArrayList<>(tamanio);
        for (int i = 0; i < tamanio; i++) {
            posColumnas.add(dt.buscarPosicionColumna(campos.get(i)));
        }
        AOGC comparador = new AOGC(posColumnas);
        for (int i = tamanio; --i >= 0; ) {
            comparador.setSentido(i, sentidos.get(i));
        }

        Collections.sort(dt.getListaFilas(), comparador);
    }

    private static class AOGC implements Comparator<Object[]> {

        private int[] sentido;
        private int[] posicion;

        public AOGC(List<Integer> posicionColumnas) {
            if (posicionColumnas == null) throw new JcFRException("Posición de Columnas no puede ser null!");

            int nroCols = posicionColumnas.size();

            if (nroCols <= 0) throw new JcFRException("Debe especificar al menos una posición de columna!");
            sentido = new int[nroCols];
            posicion = new int[nroCols];

            for (int i = nroCols; --i >= 0; ) {
                sentido[i] = 1;
                posicion[i] = posicionColumnas.get(i);
                if (posicion[i] < 0) throw new JcFRException("Posición debe ser siempre mayor o igual a cero!");
            }
        }

        // NOTA: NO VALIDA EL NRO MAXIMO DE COLS DE O1 Y O2
        @Override
        public int compare(Object[] o1, Object[] o2) {
            int c;
            int pos;
            for (int i = 0; i < posicion.length; i++) {
                pos = posicion[i];
                c = OC.compare(o1[pos], o2[pos], sentido[i]);
                if (c != 0) return c;
            }
            return 0; // el ultimo campo va a tomar c = 0
        }

        public void setSentido(int posArregloSentido, int valorSentido) {
            if (posArregloSentido < 0 || posArregloSentido >= sentido.length)
                throw new JcFRException("Posición del sentido no está en el rango correcto!");
            sentido[posArregloSentido] = valorSentido;
        }
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
