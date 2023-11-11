package com.jcfr.utiles.group;

import com.jcfr.utiles.exceptions.JcFRException;
import com.jcfr.utiles.order.OC;
import com.jcfr.utiles.repositorios.DataTable;

import java.io.Serializable;

// OTRAS OPERACIONES: getMediana(), getModa(), getSumaTop(int), getSumaDown(int), getPromedioTop(int), getPromedioDown(int)
public class DataGrupoSimple implements Serializable {

    private static final long serialVersionUID = 3897182601398429233L;

    private DataTable fuente;
    private String campoGroupBy1;
    private DataTable grupoSimple;
    private int posGrupoSimple = -1;

    public DataGrupoSimple(DataTable fuente, String campoGroupBy) {
        this(fuente, campoGroupBy, true);
    }

    public DataGrupoSimple(DataTable fuente, String campoGroupBy, boolean ordenar) {
        if (fuente == null) throw new JcFRException("Tabla fuente no puede ser nula!");
        if (!fuente.existeColumna(campoGroupBy))
            throw new JcFRException("Columna " + campoGroupBy + " no existe en la tabla fuente!");
        this.fuente = fuente;
        this.campoGroupBy1 = campoGroupBy;
        if (ordenar) this.fuente.orderBy(campoGroupBy);
        makeGrupos();
    }

    private void makeGrupos() {
        DataTable fuenteLocal = fuente;
        int fuenteSize = fuenteLocal.getNroFilas();

        grupoSimple = new DataTable(fuenteSize / 6);
        grupoSimple.addNombreColumna("grupoSimple", "pos_ini", "pos_fin", "count");

        int i = 0;
        int countSimple;
        Object valorSimple;

        while (i < fuenteSize) {
            countSimple = 0;
            valorSimple = fuenteLocal.getValorAt(i, campoGroupBy1);

            while (i < fuenteSize && OC.compare(valorSimple, fuenteLocal.getValorAt(i, campoGroupBy1)) == 0) {
                countSimple++;
                i++;
            }
            grupoSimple.addFilaFaster(new Object[]{valorSimple, i - countSimple, i - 1, countSimple});
        }
    }

    // OPERACION COUNT
    public int getCount() {
        return grupoSimple.gi(posGrupoSimple, "count");
    }

    // OPERACION SUMA
    public double getSuma(String columna) {
        if (!fuente.existeColumna(columna))
            throw new JcFRException("Columna " + columna + " no existe en la tabla fuente!");
        return new OperadorDeGrupos(fuente, grupoSimple, posGrupoSimple, columna).operar(OperacionGrupoEnum.SUMA).getSuma();
    }

    public double getSuma(String columna, int nroDecimales) {
        if (!fuente.existeColumna(columna))
            throw new JcFRException("Columna " + columna + " no existe en la tabla fuente!");
        return new OperadorDeGrupos(fuente, grupoSimple, posGrupoSimple, columna).operar(OperacionGrupoEnum.SUMA, nroDecimales).getSuma();
    }

    public double getSumaR2(String columna) {
        return getSuma(columna, 2);
    }

    // OPERACION PROMEDIO
    public double getPromedio(String columna) {
        if (!fuente.existeColumna(columna))
            throw new JcFRException("Columna " + columna + " no existe en la tabla fuente!");
        return new OperadorDeGrupos(fuente, grupoSimple, posGrupoSimple, columna).operar(OperacionGrupoEnum.PROMEDIO).getPromedio();
    }

    public double getPromedio(String columna, int nroDecimales) {
        if (!fuente.existeColumna(columna))
            throw new JcFRException("Columna " + columna + " no existe en la tabla fuente!");
        return new OperadorDeGrupos(fuente, grupoSimple, posGrupoSimple, columna).operar(OperacionGrupoEnum.PROMEDIO, nroDecimales).getPromedio();
    }

    public double getPromedioR2(String columna) {
        return getPromedio(columna, 2);
    }

    // OPERACION GENERICA
    public OperacionGrupo getOperacion(String columna) {
        if (!fuente.existeColumna(columna))
            throw new JcFRException("Columna " + columna + " no existe en la tabla fuente!");
        return new OperadorDeGrupos(fuente, grupoSimple, posGrupoSimple, columna).operar(OperacionGrupoEnum.OPERAR_TODO);
    }

    public OperacionGrupo getOperacion(String columna, int nroDecimales) {
        if (!fuente.existeColumna(columna))
            throw new JcFRException("Columna " + columna + " no existe en la tabla fuente!");
        return new OperadorDeGrupos(fuente, grupoSimple, posGrupoSimple, columna).operar(OperacionGrupoEnum.OPERAR_TODO, nroDecimales);
    }

    public OperacionGrupo getOperacionR2(String columna) {
        return getOperacion(columna, 2);
    }

    // METODOS DE NAVEGACION
    public boolean next() {
        posGrupoSimple++;
        if (posGrupoSimple < grupoSimple.getNroFilas()) {
            return true;
        } else {
            posGrupoSimple--;
            return false;
        }
    }

    public int getInicio() {
        return grupoSimple.gi(posGrupoSimple, "pos_ini");
    }

    public int getFinal() {
        return grupoSimple.gi(posGrupoSimple, "pos_fin");
    }

    public int getIterador() {
        return posGrupoSimple;
    }

    public Object getValue() {
        return grupoSimple.getValorAt(posGrupoSimple, "grupoSimple");
    }

    public DataTable getGrupos() {
        return grupoSimple;
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
