package com.jcfr.utiles;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class JConvertidor {

    public static final JConvertidor JConvertidor = new JConvertidor();

    // NOTA: TODOS LOS toXXX() asumen que los parametros son de alguna manera factibles
    // para ser convertidos. Por lo que los ultimos returns (en algunos metodos)
    // en teoria jamas deberian darse.
    private JConvertidor() {
    }

    public long binToLong(String valorBinario) {
        long numero = 0;
        int size = valorBinario.length();
        int size_1 = size - 1;
        for (int i = 0; i < size; i++) {
            if (valorBinario.charAt(i) == '1') numero += ((long) Math.pow(2, size_1 - i));
        }
        return numero;
    }

    public char toChar(String valor) {
        return valor.charAt(0);
    }

    public char toChar(Object valor) {
        return valor.toString().charAt(0);
    }

    public boolean toBoolean(Object valor) {
        if (valor instanceof Boolean) return (Boolean) valor;
        if (valor instanceof String) {
            String str = ((String) valor).trim().toLowerCase();
            return str.equals("true") || str.equals("yes") || str.equals("1") || str.equals("si") || str.equals("sí") || str.equals("verdadero") || str.equals("s") || str.equals("y");
        }
        return valor != null && Boolean.parseBoolean(valor.toString());
    }

    public int toInt(Object valor) {
        if (valor instanceof Number) return ((Number) valor).intValue();
        if (valor instanceof String) return Integer.parseInt((String) valor);
        return Integer.parseInt(String.valueOf(valor));
    }

    public short toShort(Object valor) {
        if (valor instanceof Number) return ((Number) valor).shortValue();
        if (valor instanceof String) return Short.parseShort((String) valor);
        return Short.parseShort(String.valueOf(valor));
    }

    public double toDouble(Object valor) {
        if (valor instanceof Number) return ((Number) valor).doubleValue();
        if (valor instanceof String) return Double.parseDouble((String) valor);
        return Double.parseDouble(String.valueOf(valor));
    }

    public float toFloat(Object valor) {
        if (valor instanceof Number) return ((Number) valor).floatValue();
        if (valor instanceof String) return Float.parseFloat((String) valor);
        return Float.parseFloat(String.valueOf(valor));
    }

    public long toLong(Object valor) {
        if (valor instanceof Number) return ((Number) valor).longValue();
        if (valor instanceof String) return Long.parseLong((String) valor);
        return Long.parseLong(String.valueOf(valor));
    }

    public byte toByte(Object valor) {
        if (valor instanceof Number) return ((Number) valor).byteValue();
        if (valor instanceof String) return Byte.parseByte((String) valor);
        return Byte.parseByte(String.valueOf(valor));
    }

    public Date toDate(Object valor) {
        if (valor == null) return null;
        if (valor instanceof DateTime) return ((DateTime) valor).toDate();
        if (valor instanceof Date) return new Date(((Date) valor).getTime()); // clase base
        if (valor instanceof String) { // SI ES STRING, HACER LO POSIBLE POR BUSCARLE EL FORMATO CORRECTO. NO SOPORTA HORA COMO STRING
            String valorString = ((String) valor).trim();
            DateTime dt = DateTime.getInstancia(valorString);
            return dt == null ? null : dt.toDate();
        }
        return null; // no se pudo hacer nada

    }

    public java.sql.Date toDateSQL(Object valor) {
        if (valor == null) return null;
        if (valor instanceof DateTime) return ((DateTime) valor).toDateSQL();
        if (valor instanceof Date) return new java.sql.Date(((Date) valor).getTime());
        if (valor instanceof String) { // SI ES STRING, HACER LO POSIBLE POR BUSCARLE EL FORMATO CORRECTO. NO SOPORTA HORA COMO STRING
            String valorString = ((String) valor).trim();
            DateTime dt = DateTime.getInstancia(valorString);
            if (dt != null) return dt.toDateSQL();
            try {
                return java.sql.Date.valueOf(valorString);
            } catch (Exception sos) {
                return null;
            }
        }
        return null;
    }

    public Time toTime(Object valor) {
        if (valor == null) return null;
        if (valor instanceof DateTime) return ((DateTime) valor).toTime();
        if (valor instanceof Date) return new Time(((Date) valor).getTime());
        if (valor instanceof String) {
            try {
                return Time.valueOf(((String) valor).trim());
            } catch (Exception sos) {
                return null;
            }
        }
        return null;
    }

    public Timestamp toTimestamp(Object valor) {
        if (valor == null) return null;
        if (valor instanceof DateTime) return ((DateTime) valor).toTimestamp();
        if (valor instanceof Date) return new Timestamp(((Date) valor).getTime()); // clase base
        if (valor instanceof String) {
            try {
                return Timestamp.valueOf(((String) valor).trim());
            } catch (Exception sos) {
                return null;
            }
        }
        return null;
    }

    public DateTime toDateTime(Date valor) {
        return DateTime.getInstancia(valor);
    }

    public DateTime toDateTime(String valor) {
        return DateTime.getInstancia(valor);
    }

    public DateTime toDateTime(Object valor) {
        if (valor == null) return null;
        if (valor instanceof Date) return DateTime.getInstancia((Date) valor);
        if (valor instanceof String) return DateTime.getInstancia((String) valor);
        if (valor instanceof Number) return DateTime.getInstancia(((Number) valor).longValue());
        if (valor instanceof DateTime) return (DateTime) valor;
        return null;
    }

    public static String getCreditos() {
        return Constantes.MSG_CREDITOS;
    }
}
