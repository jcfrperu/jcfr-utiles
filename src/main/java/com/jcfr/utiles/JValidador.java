package com.jcfr.utiles;

public class JValidador {

    public static final JValidador JValidador = new JValidador();

    private JValidador() {
    }

    public boolean esLetra(char caracter) {
        return Character.isLetter(caracter);
    }

    public boolean tieneElCaracter(String valor, char caracter) {
        return valor != null && valor.lastIndexOf(caracter) != -1;
    }

    public boolean esNumero(String valor) {
        return esNumeroDecimal(valor) || esNumeroEntero(valor);
    }

    public boolean esNumeroDecimal(String valor) {
        try {
            if (valor == null) return false;
            String valorTrim = valor.trim();
            if (valorTrim.equalsIgnoreCase("nan")) return false;
            Double.parseDouble(valorTrim);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public boolean esNumeroEntero(String valor) {
        try {
            if (valor == null) return false;
            Long.parseLong(valor.trim());
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public boolean esEmail(String valor) {
        if (valor == null) return false;
        String svalor = valor.trim();
        if (svalor.length() == 0) return false;
        String[] t = svalor.split("@");
        if (t.length != 2) return false;
        if (t[0] == null || t[0].length() == 0 || t[0].indexOf(' ') > -1) return false;
        if (t[1] == null || t[1].length() < 3 || t[1].indexOf(' ') > -1) return false;

        int maxPuntos = 2;
        for (int i = 0; i < t[1].length(); i++) {
            if (t[1].charAt(i) == '.') maxPuntos--;
            if (maxPuntos < 0) return false;
        }
        if (maxPuntos == 2) return false;
        return t[1].charAt(t[1].length() - 1) != '.';
    }

    public boolean tieneCaracteres(String valor) {
        return !tieneSoloNumeros(valor);
    }

    public boolean tieneLetras(String valor) {
        if (valor == null || valor.trim().length() == 0) return false;

        for (int i = valor.length(); --i >= 0; ) {
            if (Character.isLetter(valor.charAt(i))) return true;
        }
        return false;
    }

    public boolean tieneNumeros(String valor) {
        if (valor == null || valor.trim().length() == 0) return false;

        for (int i = valor.length(); --i >= 0; ) {
            if (Character.isDigit(valor.charAt(i))) return true;
        }

        return false;
    }

    public boolean tieneSoloCaracteres(String valor) {
        return !tieneNumeros(valor);
    }

    public boolean tieneSoloLetras(String valor) {
        if (valor == null || valor.trim().length() == 0) return false;

        for (int i = valor.length(); --i >= 0; ) {
            if (!Character.isLetter(valor.charAt(i))) return false;
        }

        return true;
    }

    public boolean tieneSoloLetrasUOtros(String valor, String listaCaracteres) {

        if (valor == null || valor.trim().length() == 0 || listaCaracteres == null) return false;

        boolean listaVacia = (listaCaracteres.length() == 0);

        char c;

        for (int i = valor.length(); --i >= 0; ) {
            c = valor.charAt(i);

            if (listaVacia) {
                if (!Character.isLetter(c)) return false;
            } else {
                if (!Character.isLetter(c) && !listaCaracteres.contains(String.valueOf(c))) return false;
            }
        }
        return true;
    }

    public boolean tieneSoloNumeros(String valor) {
        if (valor == null || valor.trim().length() == 0) return false;

        for (int i = valor.length(); --i >= 0; ) {
            if (!Character.isDigit(valor.charAt(i))) return false;
        }

        return true;
    }

    public boolean tieneTodasLasLetras(String valor, char[] letras) {
        if (valor == null || letras == null || letras.length == 0) return false;

        for (int i = letras.length; --i >= 0; ) {
            if (valor.indexOf(letras[i]) == -1 && Character.isLetter(letras[i])) return false;
        }

        return true;
    }

    public boolean tieneAlgunaDeLasLetras(String valor, char[] letras) {
        if (valor == null || letras == null || letras.length == 0) return false;

        for (int i = letras.length; --i >= 0; ) {
            if (valor.indexOf(letras[i]) != -1 && Character.isLetter(letras[i])) return true;
        }

        return false;
    }

    public boolean tieneLosCaracteres(String valor, char[] caracteres) {
        if (valor == null || valor.trim().length() == 0 || caracteres == null || caracteres.length == 0) return false;

        for (int i = caracteres.length; --i >= 0; ) {
            if (valor.indexOf(caracteres[i]) == -1) return false;
        }
        return true;
    }

    public boolean esLetraMayuscula(char l) {
        return Character.isUpperCase(l);
    }

    public boolean esLetraMinuscula(char l) {
        return Character.isLowerCase(l);
    }

    public boolean enRango(String valor, int izq, int der) {
        if (valor == null) return false;
        int nroDig = valor.trim().length();
        return izq <= nroDig && nroDig <= der;
    }

    public boolean esNumeroEntero(String valor, long izq, long der) {
        try {
            if (!esNumeroEntero(valor)) return false;
            long valorLong = Long.parseLong(valor.trim());
            return izq <= valorLong && valorLong <= der;
        } catch (NumberFormatException ex) {
            return false;
        }

    }

    public boolean esNumeroMayorIgualQue(String valor, double izq) {
        try {
            if (!esNumero(valor)) return false;
            double valorDouble = Double.parseDouble(valor.trim());
            return izq <= valorDouble;
        } catch (NumberFormatException ex) {
            return false;
        }

    }

    public boolean esNumeroMayorQue(String valor, double izq) {
        try {
            if (!esNumero(valor)) return false;
            double valorDouble = Double.parseDouble(valor.trim());
            return izq < valorDouble;
        } catch (NumberFormatException ex) {
            return false;
        }

    }

    public boolean esNumero(String valor, double izq, double der) {
        try {
            if (!esNumero(valor)) return false;
            double valorDouble = Double.parseDouble(valor.trim());
            return izq <= valorDouble && valorDouble <= der;
        } catch (NumberFormatException ex) {
            return false;
        }

    }

    public boolean tieneFormatoFecha(String fecha) {
        return DateTime.tieneFormatoFecha(fecha);
    }

    public boolean tieneFormatoFecha(String fecha, String formato) {
        return DateTime.tieneFormatoFecha(fecha, formato);
    }

    public boolean tieneFormatoHora(String hora) {
        return DateTime.tieneFormatoHora(hora);
    }

    public boolean tieneFormatoHora(String hora, String formato) {
        return DateTime.tieneFormatoHora(hora, formato);
    }

    public static String getCreditos() {
        return Constantes.MSG_CREDITOS;
    }
}
