package com.jcfr.utiles.selector;

import com.jcfr.utiles.exceptions.JcFRException;
import com.jcfr.utiles.repositorios.DataTable;
import com.jcfr.utiles.string.JSUtil;

public class SelectorDTStringEquiv implements SelectorDT {

    private String nombreColumna;
    private int comparer;
    private String valorToBlank;
    private boolean caseSensitive;

    public SelectorDTStringEquiv(String nombreColumna, String operador, String valor) {
        this(nombreColumna, operador, valor, true);
    }

    public SelectorDTStringEquiv(String nombreColumna, String operador, String valor, boolean caseSensitive) {
        this.nombreColumna = nombreColumna;
        this.comparer = OperadorParser.parse(operador);
        if (comparer < 0) throw new JcFRException("Operador " + operador + " no soportado!");
        this.caseSensitive = caseSensitive;
        this.valorToBlank = caseSensitive ? JSUtil.JSUtil.toBlank(valor) : JSUtil.JSUtil.toLowerBlank(valor);
    }

    @Override
    public boolean incluir(DataTable dt, int pos) {

        if (caseSensitive) {
            if (comparer == 1) return dt.gsB(pos, nombreColumna).compareTo(valorToBlank) == 0;
            if (comparer == 2) return dt.gsB(pos, nombreColumna).compareTo(valorToBlank) > 0;
            if (comparer == 3) return dt.gsB(pos, nombreColumna).compareTo(valorToBlank) >= 0;
            if (comparer == 4) return dt.gsB(pos, nombreColumna).compareTo(valorToBlank) < 0;
            if (comparer == 5) return dt.gsB(pos, nombreColumna).compareTo(valorToBlank) <= 0;
            if (comparer == 6) return dt.gsB(pos, nombreColumna).compareTo(valorToBlank) != 0;
        } else {
            if (comparer == 1) return dt.gsLowB(pos, nombreColumna).compareTo(valorToBlank) == 0;
            if (comparer == 2) return dt.gsLowB(pos, nombreColumna).compareTo(valorToBlank) > 0;
            if (comparer == 3) return dt.gsLowB(pos, nombreColumna).compareTo(valorToBlank) >= 0;
            if (comparer == 4) return dt.gsLowB(pos, nombreColumna).compareTo(valorToBlank) < 0;
            if (comparer == 5) return dt.gsLowB(pos, nombreColumna).compareTo(valorToBlank) <= 0;
            if (comparer == 6) return dt.gsLowB(pos, nombreColumna).compareTo(valorToBlank) != 0;
        }

        throw new JcFRException("Operador no soportado!");
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
