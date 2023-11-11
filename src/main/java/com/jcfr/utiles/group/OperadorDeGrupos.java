package com.jcfr.utiles.group;

import com.jcfr.utiles.exceptions.JcFRException;
import com.jcfr.utiles.math.JMUtil;
import com.jcfr.utiles.repositorios.DataTable;

import java.io.Serializable;

class OperadorDeGrupos implements Serializable {

    private static final long serialVersionUID = -4541583210885194561L;

    private int posGrupo;
    private DataTable grupo;
    private int posColumna;
    private DataTable fuente;

    public OperadorDeGrupos(DataTable fuente, DataTable grupo, int posGrupo, String columna) {
        this.posGrupo = posGrupo;
        this.fuente = fuente;
        this.grupo = grupo;
        this.posColumna = fuente.buscarPosicionColumna(columna);
    }

    public OperacionGrupo operar(OperacionGrupoEnum tipo) {
        OperacionGrupo dg = new OperacionGrupo(); // suma, contador, max y min siempre empiezan en cero

        dg.tipo = tipo;
        DataTable grupoRef = grupo;
        DataTable fuenteRef = fuente;

        if (tipo == OperacionGrupoEnum.OPERAR_TODO) {

            double valor;
            boolean init = false;
            int pos_fin = grupoRef.gi(posGrupo, "pos_fin");
            for (int k = grupoRef.gi(posGrupo, "pos_ini"); k <= pos_fin; k++) {
                valor = fuenteRef.gd(k, posColumna);

                if (init) {
                    if (valor > dg.max) dg.max = valor;
                    if (valor < dg.min) dg.min = valor;
                } else {
                    dg.max = valor;
                    dg.min = valor;
                    init = true;
                }

                dg.suma += valor;
                dg.contador++;
            }

            if (dg.contador == 0) throw new JcFRException("No hay elementos para calcular el promedio");
            dg.promedio = dg.suma / dg.contador;
            return dg;
        }

        if (tipo == OperacionGrupoEnum.SUMA) {
            int pos_fin = grupoRef.gi(posGrupo, "pos_fin");
            for (int k = grupoRef.gi(posGrupo, "pos_ini"); k <= pos_fin; k++) {
                dg.suma += fuenteRef.gd(k, posColumna);
            }
            return dg;
        }

        if (tipo == OperacionGrupoEnum.PROMEDIO) {
            int pos_fin = grupoRef.gi(posGrupo, "pos_fin");
            for (int k = grupoRef.gi(posGrupo, "pos_ini"); k <= pos_fin; k++) {
                dg.suma += fuenteRef.gd(k, posColumna);
                dg.contador++;
            }
            if (dg.contador == 0) throw new JcFRException("No hay elementos para calcular el promedio");
            dg.promedio = dg.suma / dg.contador;
            dg.suma = 0;
            dg.contador = 0;
            return dg;
        }

        if (tipo == OperacionGrupoEnum.SUMA_PROMEDIO) {
            int pos_fin = grupoRef.gi(posGrupo, "pos_fin");
            for (int k = grupoRef.gi(posGrupo, "pos_ini"); k <= pos_fin; k++) {
                dg.suma += fuenteRef.gd(k, posColumna);
                dg.contador++;
            }
            if (dg.contador == 0) throw new JcFRException("No hay elementos para calcular el promedio");
            dg.promedio = dg.suma / dg.contador;
            dg.contador = 0;
            return dg;
        }

        if (tipo == OperacionGrupoEnum.MIN_MAX) {

            double valor;
            boolean init = false;
            int pos_fin = grupoRef.gi(posGrupo, "pos_fin");
            for (int k = grupoRef.gi(posGrupo, "pos_ini"); k <= pos_fin; k++) {
                valor = fuenteRef.gd(k, posColumna);
                if (init) {
                    if (valor > dg.max) dg.max = valor;
                    if (valor < dg.min) dg.min = valor;
                } else {
                    dg.max = valor;
                    dg.min = valor;
                    init = true;
                }
            }
            return dg;
        }

        throw new JcFRException("Tipo Operación no soportada");
    }

    public OperacionGrupo operar(OperacionGrupoEnum tipo, int nroDecimales) {
        OperacionGrupo dg = new OperacionGrupo(); // suma, contador, max y min siempre empiezan en cero

        dg.tipo = tipo;
        DataTable grupoRef = grupo;
        DataTable fuenteRef = fuente;

        if (tipo == OperacionGrupoEnum.SUMA) {
            int pos_fin = grupoRef.gi(posGrupo, "pos_fin");
            for (int k = grupoRef.gi(posGrupo, "pos_ini"); k <= pos_fin; k++) {
                dg.suma += fuenteRef.gdR(k, posColumna, nroDecimales);
            }
            dg.suma = JMUtil.round(dg.suma, nroDecimales);
            return dg;
        }

        if (tipo == OperacionGrupoEnum.OPERAR_TODO) {
            double valor;
            boolean init = false;
            int pos_fin = grupoRef.gi(posGrupo, "pos_fin");
            for (int k = grupoRef.gi(posGrupo, "pos_ini"); k <= pos_fin; k++) {
                valor = fuenteRef.gdR(k, posColumna, nroDecimales);

                if (init) {
                    if (valor > dg.max) dg.max = valor;
                    if (valor < dg.min) dg.min = valor;
                } else {
                    dg.max = valor;
                    dg.min = valor;
                    init = true;
                }

                dg.suma += valor;
                dg.contador++;
            }

            if (dg.contador == 0) throw new JcFRException("No hay elementos para calcular el promedio");
            dg.suma = JMUtil.round(dg.suma, nroDecimales);
            dg.promedio = JMUtil.round(dg.suma / dg.contador, nroDecimales);
            return dg;
        }

        if (tipo == OperacionGrupoEnum.PROMEDIO) {
            int pos_fin = grupoRef.gi(posGrupo, "pos_fin");
            for (int k = grupoRef.gi(posGrupo, "pos_ini"); k <= pos_fin; k++) {
                dg.suma += fuenteRef.gdR(k, posColumna, nroDecimales);
                dg.contador++;
            }

            if (dg.contador == 0) throw new JcFRException("No hay elementos para calcular el promedio");
            dg.suma = JMUtil.round(dg.suma, nroDecimales);
            dg.promedio = JMUtil.round(dg.suma / dg.contador, nroDecimales);
            dg.suma = dg.contador = 0;
            return dg;
        }

        if (tipo == OperacionGrupoEnum.SUMA_PROMEDIO) {
            int pos_fin = grupoRef.gi(posGrupo, "pos_fin");
            for (int k = grupoRef.gi(posGrupo, "pos_ini"); k <= pos_fin; k++) {
                dg.suma += fuenteRef.gdR(k, posColumna, nroDecimales);
                dg.contador++;
            }

            if (dg.contador == 0) throw new JcFRException("No hay elementos para calcular el promedio");
            dg.suma = JMUtil.round(dg.suma, nroDecimales);
            dg.promedio = JMUtil.round(dg.suma / dg.contador, nroDecimales);
            dg.contador = 0;
            return dg;
        }

        if (tipo == OperacionGrupoEnum.MIN_MAX) {
            double valor;
            boolean init = false;
            int pos_fin = grupoRef.gi(posGrupo, "pos_fin");
            for (int k = grupoRef.gi(posGrupo, "pos_ini"); k <= pos_fin; k++) {
                valor = fuenteRef.gdR(k, posColumna, nroDecimales);
                if (init) {
                    if (valor > dg.max) dg.max = valor;
                    if (valor < dg.min) dg.min = valor;
                } else {
                    dg.max = valor;
                    dg.min = valor;
                    init = true;
                }
            }
            return dg;
        }

        throw new JcFRException("Tipo Operación no soportada");
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
