package com.jcfr.utiles.group;

import com.jcfr.utiles.exceptions.JcFRException;
import com.jcfr.utiles.order.OC;
import com.jcfr.utiles.repositorios.DataTable;

import java.io.Serializable;

import static com.jcfr.utiles.group.GrupoEnum.G1;
import static com.jcfr.utiles.group.GrupoEnum.G2;

public class DataGrupoDouble implements Serializable {

    private static final long serialVersionUID = -5218365140797979821L;

    private DataTable fuente;
    private String campoGroupBy1;
    private String campoGroupBy2;
    private DataTable grupoDoble;
    private DataTable grupoSimple;
    private int posGrupo1;
    private int posGrupo2;

    public DataGrupoDouble(DataTable fuente, String campoGroupBy1, String campoGroupBy2) {
        this(fuente, campoGroupBy1, campoGroupBy2, true);
    }

    public DataGrupoDouble(DataTable fuente, String campoGroupBy1, String campoGroupBy2, boolean ordenar) {
        if (fuente == null) throw new JcFRException("Tabla fuente no puede ser nula!");
        if (!fuente.existeColumna(campoGroupBy1))
            throw new JcFRException("Columna " + campoGroupBy1 + " no existe en la tabla fuente!");
        if (!fuente.existeColumna(campoGroupBy2))
            throw new JcFRException("Columna " + campoGroupBy2 + " no existe en la tabla fuente!");
        this.fuente = fuente;
        this.campoGroupBy1 = campoGroupBy1;
        this.campoGroupBy2 = campoGroupBy2;
        if (ordenar) this.fuente.orderBy(campoGroupBy1 + ", " + campoGroupBy2);
        this.posGrupo1 = this.posGrupo2 = -1;
        makeGrupos();
    }

    private void makeGrupos() {
        DataTable fuenteLocal = fuente;
        int fuenteSize = fuenteLocal.getNroFilas();

        grupoDoble = new DataTable(fuenteSize / 3);
        grupoDoble.addNombreColumna("grupo1", "grupo2", "pos_ini", "pos_fin", "count");

        grupoSimple = new DataTable(fuenteSize / 6);
        grupoSimple.addNombreColumna("grupo1", "pos_ini", "pos_fin", "count");

        int i = 0;
        int countSimple;
        int countDoble;

        Object valorSimple;
        Object valorDoble;

        while (i < fuenteSize) {
            countSimple = 0;
            valorSimple = fuenteLocal.getValorAt(i, campoGroupBy1);

            while (i < fuenteSize && OC.compare(valorSimple, fuenteLocal.getValorAt(i, campoGroupBy1)) == 0) {
                countDoble = 0;
                valorDoble = fuenteLocal.getValorAt(i, campoGroupBy2);

                while (i < fuenteSize && OC.compare(valorSimple, fuenteLocal.getValorAt(i, campoGroupBy1)) == 0 && OC.compare(valorDoble, fuenteLocal.getValorAt(i, campoGroupBy2)) == 0) {
                    countSimple++;
                    countDoble++;
                    i++;
                }
                grupoDoble.addFilaFaster(new Object[]{valorSimple, valorDoble, i - countDoble, i - 1, countDoble});

            }
            grupoSimple.addFilaFaster(new Object[]{valorSimple, i - countSimple, i - 1, countSimple});
        }

    }

    // OPERACION COUNT
    public int getCount(GrupoEnum grupo) {
        if (grupo == G1) return grupoSimple.gi(posGrupo1, "count");
        if (grupo == G2) return grupoDoble.gi(posGrupo2, "count");

        throw new JcFRException("Grupo No Soportado");
    }

    // OPERACION SUMA
    public double getSuma(GrupoEnum grupo, String columna) {
        if (!fuente.existeColumna(columna))
            throw new JcFRException("Columna " + columna + " no existe en la tabla fuente!");

        if (grupo == G1)
            return new OperadorDeGrupos(fuente, grupoSimple, posGrupo1, columna).operar(OperacionGrupoEnum.SUMA).getSuma();
        if (grupo == G2)
            return new OperadorDeGrupos(fuente, grupoDoble, posGrupo2, columna).operar(OperacionGrupoEnum.SUMA).getSuma();

        throw new JcFRException("Grupo No Soportado");
    }

    public double getSuma(GrupoEnum grupo, String columna, int nroDecimales) {
        if (!fuente.existeColumna(columna))
            throw new JcFRException("Columna " + columna + " no existe en la tabla fuente!");

        if (grupo == G1)
            return new OperadorDeGrupos(fuente, grupoSimple, posGrupo1, columna).operar(OperacionGrupoEnum.SUMA, nroDecimales).getSuma();
        if (grupo == G2)
            return new OperadorDeGrupos(fuente, grupoDoble, posGrupo2, columna).operar(OperacionGrupoEnum.SUMA, nroDecimales).getSuma();

        throw new JcFRException("Grupo No Soportado");
    }

    public double getSumaR2(GrupoEnum grupo, String columna) {
        return getSuma(grupo, columna, 2);
    }

    // OPERACION PROMEDIO
    public double getPromedio(GrupoEnum grupo, String columna) {
        if (!fuente.existeColumna(columna))
            throw new JcFRException("Columna " + columna + " no existe en la tabla fuente!");

        if (grupo == G1)
            return new OperadorDeGrupos(fuente, grupoSimple, posGrupo1, columna).operar(OperacionGrupoEnum.PROMEDIO).getPromedio();
        if (grupo == G2)
            return new OperadorDeGrupos(fuente, grupoDoble, posGrupo2, columna).operar(OperacionGrupoEnum.PROMEDIO).getPromedio();

        throw new JcFRException("Grupo No Soportado");
    }

    public double getPromedio(GrupoEnum grupo, String columna, int nroDecimales) {
        if (!fuente.existeColumna(columna))
            throw new JcFRException("Columna " + columna + " no existe en la tabla fuente!");

        if (grupo == G1)
            return new OperadorDeGrupos(fuente, grupoSimple, posGrupo1, columna).operar(OperacionGrupoEnum.PROMEDIO, nroDecimales).getPromedio();
        if (grupo == G2)
            return new OperadorDeGrupos(fuente, grupoDoble, posGrupo2, columna).operar(OperacionGrupoEnum.PROMEDIO, nroDecimales).getPromedio();

        throw new JcFRException("Grupo No Soportado");
    }

    public double getPromedioR2(GrupoEnum grupo, String columna) {
        return getPromedio(grupo, columna, 2);
    }

    // OPERACION GENERICA
    public OperacionGrupo getOperacion(GrupoEnum grupo, String columna) {
        if (!fuente.existeColumna(columna))
            throw new JcFRException("Columna " + columna + " no existe en la tabla fuente!");

        if (grupo == G1)
            return new OperadorDeGrupos(fuente, grupoSimple, posGrupo1, columna).operar(OperacionGrupoEnum.OPERAR_TODO);
        if (grupo == G2)
            return new OperadorDeGrupos(fuente, grupoDoble, posGrupo2, columna).operar(OperacionGrupoEnum.OPERAR_TODO);

        throw new JcFRException("Grupo No Soportado");
    }

    public OperacionGrupo getOperacion(GrupoEnum grupo, String columna, int nroDecimales) {
        if (!fuente.existeColumna(columna))
            throw new JcFRException("Columna " + columna + " no existe en la tabla fuente!");

        if (grupo == G1)
            return new OperadorDeGrupos(fuente, grupoSimple, posGrupo1, columna).operar(OperacionGrupoEnum.OPERAR_TODO, nroDecimales);
        if (grupo == G2)
            return new OperadorDeGrupos(fuente, grupoDoble, posGrupo2, columna).operar(OperacionGrupoEnum.OPERAR_TODO, nroDecimales);

        throw new JcFRException("Grupo No Soportado");
    }

    public OperacionGrupo getOperacionR2(GrupoEnum grupo, String columna) {
        return getOperacion(grupo, columna, 2);
    }

    // METODOS DE NAVEGACION
    public boolean next(GrupoEnum grupo) {
        if (grupo == G2) {
            posGrupo2++;
            if (posGrupo1 < grupoSimple.getNroFilas() && posGrupo2 < grupoDoble.getNroFilas()
                    && OC.compare(grupoSimple.getValorAt(posGrupo1, "grupo1"), grupoDoble.getValorAt(posGrupo2, "grupo1")) == 0) {
                return true;
            } else {
                posGrupo2--;
                return false;
            }
        } else {
            posGrupo1++;
            if (posGrupo1 < grupoSimple.getNroFilas()) {
                return true;
            } else {
                posGrupo1--;
                return false;
            }
        }
    }

    public int getInicio() {
        return grupoDoble.gi(posGrupo2, "pos_ini");
    }

    public int getFinal() {
        return grupoDoble.gi(posGrupo2, "pos_fin");
    }

    public int getIterador(GrupoEnum grupo) {
        if (grupo == G1) return posGrupo1;
        if (grupo == G2) return posGrupo2;
        throw new JcFRException("Grupo No Soportado");
    }

    public Object getValue(GrupoEnum grupo) {
        if (grupo == G1) return grupoSimple.getValorAt(posGrupo1, "grupo1");
        if (grupo == G2) return grupoDoble.getValorAt(posGrupo2, "grupo2");
        throw new JcFRException("Grupo No Soportado");
    }

    public DataTable getGrupos(GrupoEnum grupo) {
        if (grupo == G1) return grupoSimple;
        if (grupo == G2) return grupoDoble;
        throw new JcFRException("Grupo No Soportado");
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
