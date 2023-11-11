package com.jcfr.utiles.string;

import com.jcfr.utiles.DateTime;

public class SecuenciaStringReader {

    private static final JSUtil JS = JSUtil.JSUtil;
    private String linea;
    private String value;
    private String valueAnt;
    private int index;
    private int indexAnt;

    public void resetIndex() {
        indexAnt = 1;
        index = 1;
    }

    public int getIndex() {
        return index;
    }

    public int getIndexAnterior() {
        return indexAnt;
    }

    public String getValor() {
        return value;
    }

    public String getValorAnterior() {
        return valueAnt;
    }

    public void setLine(String line) {
        this.linea = line;
    }

    public void newLine(String valor) {
        linea = valor;
        indexAnt = 1;
        index = 1;
    }

    public String gs(int ancho) {
        indexAnt = index;
        valueAnt = value;
        value = JS.SUBSTR(linea, index, ancho);
        index = index + ancho;
        return value;
    }

    public String gsB(int ancho) {
        return JS.toBlank(gs(ancho));
    }

    public String gsLowB(int ancho) {
        return JS.toLowerBlank(gs(ancho));
    }

    public String gsUpB(int ancho) {
        return JS.toUpperBlank(gs(ancho));
    }

    public String gsNI(int ancho) {
        return JS.toNullIf(gs(ancho));
    }

    public String gsNUpI(int ancho) {
        return JS.toNullUpperIf(gs(ancho));
    }

    public String gsNLowI(int ancho) {
        return JS.toNullLowerIf(gs(ancho));
    }

    public int gi(int ancho) {
        return JS.toInt(gs(ancho));
    }

    public long gl(int ancho) {
        return JS.toLong(gs(ancho));
    }

    public DateTime gdt(int ancho) {
        return JS.toDateTime(gs(ancho));
    }

    public double gd(int ancho) {
        return JS.toDouble(gs(ancho));
    }

    public double gdR(int ancho, int nroDecimales) {
        return JS.toDoubleRound(gs(ancho), nroDecimales);
    }

    public double gdR2(int ancho) {
        return JS.toDoubleRound(gs(ancho), 2);
    }

    public String gdRS(int ancho, int nroDecimales) {
        return JS.toString(JS.toDouble(gs(ancho)), nroDecimales);
    }
}
