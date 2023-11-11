package com.jcfr.utiles.math;

import com.jcfr.utiles.string.JSUtil;

import java.math.BigDecimal;

public class JMUtil {

    public static final int NRO_DEC_SAVE = 13; // EN TEORIA DEBERIA SER UN CARACTER MENOS DE DONDE EMPIEZA A DISTORSIONARSE LA ARITMETICA JAVA

    private JMUtil() {
    }

    public static double _RJ(double numero) {
        // return new BigDecimal( numero + "" ).setScale( NRO_DEC_RJ, BigDecimal.ROUND_HALF_UP ).doubleValue();
        return numero;
    }

    public static BigDecimal roundBD(double numero, int nroDecimales) {
        return new BigDecimal(_RJ(numero) + "").setScale(nroDecimales, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal _roundBD_NORJ(double numero, int nroDecimales) {
        return new BigDecimal(numero + "").setScale(nroDecimales, BigDecimal.ROUND_HALF_UP);
    }

    public static double round(double numero, int nroDecimales) {
        return new BigDecimal(_RJ(numero) + "").setScale(nroDecimales, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double _round_NORJ(double numero, int nroDecimales) {
        return new BigDecimal(numero + "").setScale(nroDecimales, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double round1(double numero) {
        return new BigDecimal(_RJ(numero) + "").setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double round2(double numero) {
        return new BigDecimal(_RJ(numero) + "").setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double round3(double numero) {
        return new BigDecimal(_RJ(numero) + "").setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double round4(double numero) {
        return new BigDecimal(_RJ(numero) + "").setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double round10(double numero) {
        return new BigDecimal(_RJ(numero) + "").setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double round15(double numero) {
        return new BigDecimal(_RJ(numero) + "").setScale(15, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static long roundLong(double numero, int nroDecimales) {
        return new BigDecimal(_RJ(numero) + "").setScale(nroDecimales, BigDecimal.ROUND_HALF_UP).longValue();
    }

    public static int roundInt(double numero, int nroDecimales) {
        return new BigDecimal(_RJ(numero) + "").setScale(nroDecimales, BigDecimal.ROUND_HALF_UP).intValue();
    }

    public static double roundFloat(double numero, int nroDecimales) {
        return new BigDecimal(_RJ(numero) + "").setScale(nroDecimales, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public static int extraerParteEntera(double numero) {
        return (int) numero;
    }

    public static int extraerParteEntera(double numero, int nroDecimales) {
        return (int) round(numero, nroDecimales);
    }

    public static String extraerDecimalesString(double numero) {
        String snumero = new BigDecimal(_RJ(numero) + "").toString();
        int pos = snumero.indexOf('.');
        if (pos < 0) return "";
        return snumero.substring(pos + 1);
    }

    public static String extraerDecimalesString(double numero, int nroDecimales) {
        String snumero = new BigDecimal(_RJ(numero) + "").setScale(nroDecimales, BigDecimal.ROUND_HALF_UP).toString();
        int pos = snumero.indexOf('.');
        if (pos < 0) return JSUtil.JSUtil.STR(0, nroDecimales);
        return snumero.substring(pos + 1);
    }

    public static double extraerParteDecimal(double numero, int nroDecimales) {
        return Double.parseDouble("0." + extraerDecimalesString(numero, nroDecimales));
    }

    public static double extraerParteDecimal(double numero) {
        return Double.parseDouble("0." + extraerDecimalesString(numero));
    }

    public static int newInt() {
        return Alea.newInt();
    }

    public static int newInt(int inicio, int termino) {
        return Alea.newInt(inicio, termino);
    }

    public static double newDouble() {
        return Alea.newDouble();
    }

    public static double newDouble(double inicio, double termino) {
        return Alea.newDouble(inicio, termino);
    }

    public static long newLong() {
        return Alea.newLong();
    }

    public static long newLong(long inicio, long termino) {
        return Alea.newLong(inicio, termino);
    }

    public static float newFloat() {
        return Alea.newFloat();
    }

    public static float newFloat(float inicio, float termino) {
        return Alea.newFloat(inicio, termino);
    }

    public static float newFloat(double inicio, double termino) {
        return Alea.newFloat(inicio, termino);
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
