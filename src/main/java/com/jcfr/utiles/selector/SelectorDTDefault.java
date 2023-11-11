package com.jcfr.utiles.selector;

import com.jcfr.utiles.exceptions.JcFRException;
import com.jcfr.utiles.order.OC;
import com.jcfr.utiles.repositorios.DataTable;

public class SelectorDTDefault implements SelectorDT {

    private String nombreColumna;
    private int comparer;
    private Object valorFiltro;

    public SelectorDTDefault(String nombreColumna, String operador, Object valorFiltro) {
        this.nombreColumna = nombreColumna;
        this.comparer = OperadorParser.parse(operador);
        if (comparer < 0) throw new JcFRException("Operador " + operador + " no soportado!");
        this.valorFiltro = valorFiltro;
    }

    @Override
    public boolean incluir(DataTable dt, int pos) {
        Object valorTabla = dt.getValorAt(pos, nombreColumna);

        // TIENEN EL COMPORTAMIENTO "NATURAL" DEL SQL, DONDE NULL SIGNIFICA QUE NO TIENE VALOR DEFINIDO, POR TANTO CUALQUIER COMPARACIÓN NO DEBE INCLUIRSE EN EL SELECT.
        if (valorFiltro == null) {

            if (comparer == 7) return valorTabla == null;
            if (comparer == 8) return valorTabla != null;

            return false; // SOLO PARA solo pa 7 y 8 TIENE SENTIDO COMPARAR SIENDO EL VALORFILTRO NULL

        } else {
            if (valorTabla == null) {
                // COMO EL VALOR DE FILTRO ES NOT NULL, ENTONCES COMPARACIONES DEL TIPO 7 Y 8 NO SON APLICABLES
                if (comparer == 7) throw new JcFRException("Operador is, no es aplicable!");
                if (comparer == 8) throw new JcFRException("Operador is not, no es aplicable!");
                return false;
            }

            if (comparer == 1) return OC.compare(valorTabla, valorFiltro) == 0;
            if (comparer == 2) return OC.compare(valorTabla, valorFiltro) > 0;
            if (comparer == 3) return OC.compare(valorTabla, valorFiltro) >= 0;
            if (comparer == 4) return OC.compare(valorTabla, valorFiltro) < 0;
            if (comparer == 5) return OC.compare(valorTabla, valorFiltro) <= 0;
            if (comparer == 6) return OC.compare(valorTabla, valorFiltro) != 0;

            throw new JcFRException("Operador no soportado!");
        }
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
