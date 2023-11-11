package com.jcfr.utiles.math;

import com.jcfr.utiles.exceptions.JcFRException;
import com.jcfr.utiles.string.JSUtil;

import java.util.HashMap;

public class JAcumulador {

    private HashMap<String, Double> variables;
    private static final JSUtil JS = JSUtil.JSUtil;

    public JAcumulador() {
        this(8);
    }

    public JAcumulador(int tamAproxVariables) {
        variables = new HashMap<>(Math.max(16, tamAproxVariables));
    }

    public void reset() {
        variables.clear();
    }

    public void reset(String... listaIds) {
        reset(0.00, listaIds);
    }

    public void reset(double valor, String... listaIds) {
        if (listaIds == null) throw new JcFRException("reset(): Argumento listaIds no puede ser nulo!");
        if (listaIds.length == 0) throw new JcFRException("reset(): Argumento listaIds debe contener elementos!");
        for (String id : listaIds) {
            variables.put(id, valor);
        }
    }

    // ACUMULAR
    public void sum(String id, double monto) {
        variables.put(id, get(id) + monto);
    }

    public void sum(String id, double monto, int nroDecimales) {
        variables.put(id, JMUtil.round(get(id) + JMUtil.round(monto, nroDecimales), nroDecimales));
    }

    public void sumR2(String id, double monto) {
        variables.put(id, JMUtil.round2(get(id) + JMUtil.round2(monto)));
    }

    public void sumR3(String id, double monto) {
        variables.put(id, JMUtil.round3(get(id) + JMUtil.round3(monto)));
    }

    public void sumSAVE(String id, double monto) {
        variables.put(id, JMUtil.round(get(id) + JMUtil.round(monto, JMUtil.NRO_DEC_SAVE), JMUtil.NRO_DEC_SAVE));
    }

    // OBTENER VALORES
    public double get(String id) {
        Double temp = variables.get(id);
        if (temp == null) {
            variables.put(id, 0.00);
            return 0.00;
        }
        return temp;
    }

    public double get(String id, int nroDecimales) {
        return JMUtil.round(get(id), nroDecimales);
    }

    public double getR2(String id) {
        return JMUtil.round2(get(id));
    }

    public double getR3(String id) {
        return JMUtil.round3(get(id));
    }

    public double getSAVE(String id) {
        return JMUtil.round(get(id), JMUtil.NRO_DEC_SAVE);
    }

    // OBTENER VALORES COMO STRING
    public String getString(String id) {
        return JS.toString(get(id));
    }

    public String getString(String id, int nroDecimales) {
        return JS.toString(get(id), nroDecimales);
    }

    public String getR2String(String id) {
        return JS.toString(get(id), 2);
    }

    public String getR3String(String id) {
        return JS.toString(get(id), 3);
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
