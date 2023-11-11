package com.jcfr.utiles.math;

import com.jcfr.utiles.DateTime;
import com.jcfr.utiles.exceptions.JcFRException;
import com.jcfr.utiles.listas.JLUtil;
import com.jcfr.utiles.string.JSUtil;

import java.util.Random;

public class Alea {

    private static Random rand = new Random();
    private static final JSUtil JS = JSUtil.JSUtil;
    private static final StringBuilder MINUSCULAS = new StringBuilder("abcdefghijklmnopqrstuvwxyz");
    private static final StringBuilder MAYUSCULAS = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    private static final StringBuilder LETRAS = new StringBuilder(MINUSCULAS.length() + MAYUSCULAS.length()).append(MINUSCULAS).append(MAYUSCULAS);
    private static final StringBuilder NUMEROS = new StringBuilder("0123456789");
    private static final StringBuilder LETRAS_NUMEROS = new StringBuilder(LETRAS.length() + NUMEROS.length()).append(LETRAS).append(NUMEROS);
    private static final StringBuilder EXTENDIDO = new StringBuilder("+-._*=:/$%&(){}?@#[]!;<>,");
    private static final StringBuilder TODO = new StringBuilder(LETRAS_NUMEROS.length() + EXTENDIDO.length()).append(LETRAS_NUMEROS).append(EXTENDIDO);

    private Alea() {
    }

    public static String newStringID() {
        return newStringID("", 0);
    }

    public static String newStringID(String prefijo) {
        return newStringID(prefijo, 0);
    }

    public static String newStringID(String prefijo, int nroCaracteresExtra) {

        String prefix = JS.toBlank(prefijo);
        int nroCaracteresSize = Math.max(0, nroCaracteresExtra) + 8; // 20 caracteres de clave

        StringBuilder sb = new StringBuilder(prefix.length() + nroCaracteresSize + 13);

        DateTime now = DateTime.getNow();

        sb.append(prefix);
        sb.append(JS.STR_CEROS(31 * now.getMes() + now.getDia(), 3));
        sb.append(JS.STR_CEROS(61 * now.getHoras() + 13 * now.getMinutos(), 4));
        sb.append(JS.STR_CEROS(999 * now.getSegundos() + 3 * now.getMiliSegundos(), 5));

        int fin = LETRAS_NUMEROS.length() - 1;
        for (int i = nroCaracteresSize; --i >= 0; ) {
            sb.append(LETRAS_NUMEROS.charAt(newInt(0, fin)));
        }

        return sb.toString();

    }

    public static String newNumerosID() {
        return newNumerosID("", 0);
    }

    public static String newNumerosID(String prefijo) {
        return newNumerosID(prefijo, 0);
    }

    public static String newNumerosID(String prefijo, int nroDigitosExtra) {
        String prefix = JS.toBlank(prefijo);
        int nroCaracteresSize = Math.max(0, nroDigitosExtra) + 7; // 25 caracteres de clave

        StringBuilder sb = new StringBuilder(prefix.length() + nroCaracteresSize + 19);

        DateTime now = DateTime.getNow();

        sb.append(prefix);
        sb.append(JS.STR_CEROS(now.getAnio(), 4));
        sb.append(JS.STR_CEROS(now.getMes(), 2));
        sb.append(JS.STR_CEROS(now.getDia(), 2));

        sb.append(JS.STR_CEROS(now.getHoras(), 2));
        sb.append(JS.STR_CEROS(now.getMinutos(), 2));
        sb.append(JS.STR_CEROS(now.getSegundos(), 2));
        sb.append(JS.STR_CEROS(now.getMiliSegundos(), 4));

        for (int i = nroCaracteresSize; --i >= 0; ) {
            sb.append(newInt(0, 9));
        }

        return sb.toString();
    }

    public static String newString(String prefijo, int nroCaracteres) {
        String prefix = JS.toBlank(prefijo);
        StringBuilder sb = new StringBuilder(Math.max(0, nroCaracteres + prefix.length()));
        sb.append(prefix);
        int fin = LETRAS_NUMEROS.length() - 1;
        for (int i = nroCaracteres; --i >= 0; ) {
            sb.append(LETRAS_NUMEROS.charAt(newInt(0, fin)));
        }
        return sb.toString();
    }

    public static String newString(int nroCaracteres) {
        StringBuilder sb = new StringBuilder(Math.max(0, nroCaracteres));
        int fin = LETRAS_NUMEROS.length() - 1;
        for (int i = nroCaracteres; --i >= 0; ) {
            sb.append(LETRAS_NUMEROS.charAt(newInt(0, fin)));
        }
        return sb.toString();
    }

    public static Long newLongID() {

        DateTime now = DateTime.getNow();

        String sb = String.valueOf(newInt(0, 7)) +
                newInt(0, 9999999) +
                (30 * now.getMes() + now.getDia()) +
                (3600 * now.getHoras() + 60 * now.getMinutos() + now.getSegundos()) +
                now.getMiliSegundos();

        return new Long(sb);
    }

    public static String newString(int nroCaracteres, boolean minusculas, boolean mayusculas, boolean numeros, boolean extendidos) {
        StringBuilder sb = new StringBuilder(Math.max(0, nroCaracteres));

        StringBuilder alfaDyna = new StringBuilder(90);

        if (minusculas) alfaDyna.append(MINUSCULAS);
        if (mayusculas) alfaDyna.append(MAYUSCULAS);
        if (numeros) alfaDyna.append(NUMEROS);
        if (extendidos) alfaDyna.append(EXTENDIDO);

        if (alfaDyna.length() == 0) throw new JcFRException("Debe seleccionar al menos un alfabeto!");

        int fin = alfaDyna.length() - 1;
        for (int i = nroCaracteres; --i >= 0; ) {
            sb.append(alfaDyna.charAt(newInt(0, fin)));
        }

        return sb.toString();
    }

    public static String newStringExtended(int nroCaracteres) {
        StringBuilder sb = new StringBuilder(Math.max(0, nroCaracteres));

        int fin = TODO.length() - 1;
        for (int i = nroCaracteres; --i >= 0; ) {
            sb.append(TODO.charAt(newInt(0, fin)));
        }

        return sb.toString();
    }

    public static String newStringOnlyLetters(String prefijo, int nroCaracteres) {
        String prefix = JS.toBlank(prefijo);
        StringBuilder sb = new StringBuilder(Math.max(0, nroCaracteres + prefix.length()));
        sb.append(prefix);

        int fin = LETRAS.length() - 1;
        for (int i = nroCaracteres; --i >= 0; ) {
            sb.append(LETRAS.charAt(newInt(0, fin)));
        }

        return sb.toString();
    }

    public static String newStringOnlyLetters(int nroCaracteres) {
        StringBuilder sb = new StringBuilder(Math.max(0, nroCaracteres));

        int fin = LETRAS.length() - 1;
        for (int i = nroCaracteres; --i >= 0; ) {
            sb.append(LETRAS.charAt(newInt(0, fin)));
        }

        return sb.toString();
    }

    public static String newStringOnlyNumbers(String prefijo, int nroCaracteres) {
        String prefix = JS.toBlank(prefijo);
        StringBuilder sb = new StringBuilder(Math.max(0, nroCaracteres + prefix.length()));
        sb.append(prefix);

        int fin = NUMEROS.length() - 1;
        for (int i = nroCaracteres; --i >= 0; ) {
            sb.append(NUMEROS.charAt(newInt(0, fin)));
        }

        return sb.toString();
    }

    public static String newStringOnlyNumbers(int nroCaracteres) {
        StringBuilder sb = new StringBuilder(Math.max(0, nroCaracteres));

        int fin = NUMEROS.length() - 1;
        for (int i = nroCaracteres; --i >= 0; ) {
            sb.append(NUMEROS.charAt(newInt(0, fin)));
        }

        return sb.toString();
    }

    public static int newInt() {
        return rand.nextInt();
    }

    public static int newInt(int inicio, int termino) {
        return (int) Math.round(rand.nextDouble() * (termino - inicio) + inicio);
    }

    public static int newIntIn(int... lista) {
        if (lista == null || lista.length == 0)
            throw new JcFRException("Lista de valores posibles no puede estar vacía");

        return lista[newInt(0, lista.length - 1)];
    }

    public static int newIntNotIn(int... lista) {
        if (lista == null || lista.length == 0)
            throw new JcFRException("Lista de valores posibles no puede estar vacía");

        int res = rand.nextInt();

        while (JLUtil.JLUtil.inListaInt(res, lista)) {
            res = rand.nextInt();
        }
        return res;
    }

    public static double newDouble() {
        return rand.nextDouble();
    }

    public static double newDouble(double inicio, double termino) {
        return rand.nextDouble() * (termino - inicio) + inicio;
    }

    public static long newLong() {
        return rand.nextLong();
    }

    public static long newLong(long inicio, long termino) {
        return Math.round(rand.nextDouble() * (termino - inicio) + inicio);
    }

    public static long newLongIn(long... lista) {
        if (lista == null || lista.length == 0)
            throw new JcFRException("Lista de valores posibles no puede estar vacía");

        return lista[newInt(0, lista.length - 1)];
    }

    public static long newLongNotIn(long... lista) {
        if (lista == null || lista.length == 0)
            throw new JcFRException("Lista de valores posibles no puede estar vacía");

        long res = rand.nextLong();

        while (JLUtil.JLUtil.inListaLong(res, lista)) {
            res = rand.nextLong();
        }
        return res;
    }

    public static float newFloat() {
        return rand.nextFloat();
    }

    public static float newFloat(float inicio, float termino) {
        return newFloat() * (termino - inicio) + inicio;
    }

    public static float newFloat(double inicio, double termino) {
        return newFloat() * ((float) termino - (float) inicio) + (float) inicio;
    }

    public static void resetRandom() {
        rand = new Random();
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
