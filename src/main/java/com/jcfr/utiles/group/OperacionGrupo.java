package com.jcfr.utiles.group;

import java.io.Serializable;

public class OperacionGrupo implements Serializable {

    private static final long serialVersionUID = -3963462385614539083L;

    double suma;
    double promedio;
    double max;
    double min;
    long contador;
    OperacionGrupoEnum tipo;

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public long getContador() {
        return contador;
    }

    public void setContador(long contador) {
        this.contador = contador;
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }

    public OperacionGrupoEnum getTipo() {
        return tipo;
    }
}
