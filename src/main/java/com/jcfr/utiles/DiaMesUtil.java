package com.jcfr.utiles;

import com.jcfr.utiles.string.JSUtil;

import java.io.Serializable;

public class DiaMesUtil implements Serializable {

    private static final long serialVersionUID = -1674216084771320545L;

    private static final JSUtil JS = JSUtil.JSUtil;

    public static final String[] DIAS_ABRV = new String[]{"ninguno", "lun", "mar", "mie", "jue", "vie", "sab", "dom"};

    public static final String[] DIAS = new String[]{"ninguno", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
    public static final String[] MESES_ABRV = new String[]{"ninguno", "ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "set", "oct", "nov", "dic"};
    public static final String[] MESES = new String[]{"ninguno", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Setiembre", "Octubre", "Noviembre", "Diciembre"};

    public static int getNroDia(String nombreDia) {
        // CASO PROBLEMATICO DE LAS TILDES
        if (JS._equivNoCase("miercoles", nombreDia)) return 3;
        if (JS._equivNoCase("sabado", nombreDia)) return 6;
        for (int i = 1; i <= 7; i++) {
            if (JS._equivNoCase(DIAS[i], nombreDia)) return i;
        }
        return -1;
    }

    public static int getNroDiaAbrv(String nombreDia) {
        // CASO PROBLEMATICO DE LAS TILDES
        if (JS._equivNoCase("mié", nombreDia)) return 3;
        if (JS._equivNoCase("sáb", nombreDia)) return 6;
        for (int i = 1; i <= 7; i++) {
            if (JS._equivNoCase(DIAS_ABRV[i], nombreDia)) return i;
        }
        return -1;
    }

    public static String getNombreDia(int nroMes) {
        return nroMes >= 1 && nroMes <= 7 ? DIAS[nroMes] : DIAS[0];
    }

    public static String getNombreDiaAbrv(int nroMes) {
        return nroMes >= 1 && nroMes <= 7 ? DIAS_ABRV[nroMes] : DIAS_ABRV[0];
    }

    public static int getNroMes(String nombreMes) {
        if (JS._equivNoCase("septiembre", nombreMes)) return 9;
        for (int i = 1; i <= 12; i++) {
            if (JS._equivNoCase(MESES[i], nombreMes)) return i;
        }
        return -1;
    }

    public static int getNroMesAbrv(String nombreMes) {
        if (JS._equivNoCase("sep", nombreMes)) return 9;
        for (int i = 1; i <= 12; i++) {
            if (JS._equivNoCase(MESES_ABRV[i], nombreMes)) return i;
        }
        return -1;
    }

    public static String getNombreMes(int nroMes) {
        return nroMes >= 1 && nroMes <= 12 ? MESES[nroMes] : MESES[0];
    }

    public static String getNombreMesAbrv(int nroMes) {
        return nroMes >= 1 && nroMes <= 12 ? MESES_ABRV[nroMes] : MESES_ABRV[0];
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
