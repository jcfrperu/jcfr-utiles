package com.jcfr.utiles.string;

import com.jcfr.utiles.DateTime;
import com.jcfr.utiles.JValidador;
import com.jcfr.utiles.math.JMUtil;
import com.jcfr.utiles.repositorios.DataTable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

public class JValCon {

    public static final JValCon JVC = new JValCon();

    private static final JValidador VAL = JValidador.JValidador;
    // private static final JConvertidor CON = JConvertidor.JConvertidor;

    public boolean _equiv(String cadena1, String cadena2) {
        if (cadena1 != null) {
            String c1 = cadena1.trim();
            if (cadena2 != null) return c1.equals(cadena2.trim());
            return c1.isEmpty();
        } else {
            if (cadena2 != null) return cadena2.trim().isEmpty();
            return true;
        }

    }

    public boolean _equivNoCase(String cadena1, String cadena2) {
        return toLowerBlank(cadena1).equals(toLowerBlank(cadena2));
    }

    public boolean _equiv(DateTime dt1, DateTime dt2) {
        if (dt1 == null) return dt2 == null;
        if (dt2 == null) return false;

        return dt1.igualQue(dt2);
    }

    public boolean _equiv(Integer dt1, Integer dt2) {
        if (dt1 == null) return dt2 == null;
        if (dt2 == null) return false;

        return dt1.equals(dt2);
    }

    public boolean _equiv(Long dt1, Long dt2) {
        if (dt1 == null) return dt2 == null;
        if (dt2 == null) return false;

        return dt1.equals(dt2);
    }

    public boolean _enEsteMes(DateTime d1) {
        if (d1 == null) return false;
        DateTime hoy = DateTime.getToday();
        return d1.getMes() == hoy.getMes() && d1.getAnio() == hoy.getAnio();
    }

    public boolean _rango(double valor, double ini, double fin) {
        return valor >= ini && valor <= fin;
    }

    public boolean _rango(String numero, int ini, int fin) {
        if (!VAL.esNumeroEntero(numero)) return false;
        return _rango(Integer.parseInt(numero.trim()), ini, fin);
    }

    public boolean _rango(String numero, double ini, double fin) {
        if (!VAL.esNumero(numero)) return false;
        return _rango(toDouble(numero), ini, fin);
    }

    public boolean _vacio(DataTable dt) {
        return dt == null || dt.estaVacia() || dt.getNroColumnas() == 0;
    }

    public boolean _vacio(String... lista) {
        if (lista == null || lista.length == 0) return false;
        for (String item : lista) {
            if (_vacio(item)) return true;
        }
        return false;
    }

    public boolean _vacio(String cadena) {
        return cadena == null || cadena.trim().length() == 0;
    }

    public boolean _vacio_(String cadena) {
        return cadena == null || cadena.trim().length() == 0;
    }

    @SuppressWarnings("rawtypes")
    public boolean _vacio(Collection lista) {
        return lista == null || lista.isEmpty();
    }

    public boolean _fecha(String cadena) {
        return DateTime.tieneFormatoFecha(cadena, DateTime.FECHA_DDMMAAAA);
    }

    public boolean _fecha(String cadena, String formato) {
        return DateTime.tieneFormatoFecha(cadena, formato);
    }

    public boolean _minSize(String cadena, int minCaracteres) {
        return !_vacio(cadena) && cadena.trim().length() >= minCaracteres;
    }

    public boolean _maxSize(String cadena, int maxCaracteres) {
        return !_vacio(cadena) && cadena.trim().length() <= maxCaracteres;
    }

    public boolean _numero(String cadena) {
        return VAL.esNumero(cadena);
    }

    public boolean _numero(String cadena, double ini, double fin) {
        return VAL.esNumero(cadena) && _rango(toDouble(cadena), ini, fin);
    }

    public boolean _numeroEntero(String cadena) {
        return VAL.esNumeroEntero(cadena);
    }

    public boolean _numeroEntero(String cadena, int ini, int fin) {
        return VAL.esNumeroEntero(cadena) && _rango(new BigInteger(cadena.trim()).longValue(), ini, fin);
    }

    public boolean _numeroDecimal(String cadena) {
        return VAL.esNumeroDecimal(cadena);
    }

    public boolean _numeroDecimal(String cadena, double ini, double fin) {
        return VAL.esNumeroDecimal(cadena) && _rango(toDouble(cadena), ini, fin);
    }

    public boolean _lista(String valor, String... lista) {
        if (valor == null || lista == null || lista.length == 0) return false;
        String valorUpper = toUpperBlank(valor);
        for (String item : lista) {
            if (valorUpper.equals(toUpperBlank(item))) return true;
        }
        return false;
    }

    public boolean _listaCaseSensitive(String valor, String... lista) {
        if (valor == null || lista == null || lista.length == 0) return false;
        for (String item : lista) {
            if (valor.equals(item)) return true;
        }
        return false;
    }

    public DateTime toDateTime(String fecha) {
        return DateTime.getInstancia(fecha);
    }

    public DateTime toDateTime(String fecha, String formato) {
        return DateTime.getInstancia(fecha, formato);
    }

    public DateTime toDateTime(java.util.Date fecha) {
        return DateTime.getInstancia(fecha);
    }

    public int toInt(String valor) {
        return Integer.parseInt(valor.trim());
    }

    public long toLong(String valor) {
        return Long.parseLong(valor.trim());
    }

    public String toUpper(String cadena) {
        return cadena == null ? null : cadena.toUpperCase();
    }

    public String toUpperBlank(String cadena) {
        return cadena == null ? "" : cadena.trim().toUpperCase();
    }

    public String toLower(String cadena) {
        return cadena == null ? null : cadena.toLowerCase();
    }

    public String toLowerBlank(String cadena) {
        return cadena == null ? "" : cadena.trim().toLowerCase();
    }

    public String toBlank(String cadena) {
        return cadena == null ? "" : cadena.trim();
    }

    public String toTrim(String cadena) {
        return cadena == null ? null : cadena.trim();
    }

    public String toString(DateTime fecha) {
        return fecha == null ? "" : fecha.getFechaString();
    }

    public String toString(DateTime fecha, String valorIfNull) {
        return fecha == null ? valorIfNull : fecha.getFechaString();
    }

    public String toString(Date fecha) {
        return fecha == null ? "" : DateTime.getInstancia(fecha).getFechaString();
    }

    public String toString(Date fecha, String valorIfNull) {
        return fecha == null ? valorIfNull : DateTime.getInstancia(fecha).getFechaString();
    }

    public String toString(double valor) {
        return new BigDecimal(JMUtil._RJ(valor) + "").toString();
    }

    public String toString(int valor) {
        return new Integer(valor + "").toString();
    }

    public String toString(long valor) {
        return new BigInteger(valor + "").toString();
    }

    public String toString(double valor, int nroDecimales) {
        return JMUtil.roundBD(valor, nroDecimales).toString();
    }

    public String toNullIf(String valor) {
        return valor == null || valor.trim().length() == 0 ? null : valor.trim();
    }

    public String toNullUpperIf(String valor) {
        return valor == null || valor.trim().length() == 0 ? null : valor.trim().toUpperCase();
    }

    public String toNullLowerIf(String valor) {
        return valor == null || valor.trim().length() == 0 ? null : valor.trim().toLowerCase();
    }

    public String toNullIfNotTrim(String valor) {
        return valor == null || valor.trim().length() == 0 ? null : valor;
    }

    public String toFechaString(java.sql.Date fecha) {
        DateTime dt = DateTime.getInstancia(fecha);
        return dt == null ? "" : dt.getFechaString();
    }

    public String toFechaString(java.sql.Date fecha, String valorIfNull) {
        DateTime dt = DateTime.getInstancia(fecha);
        return dt == null ? valorIfNull : dt.getFechaString();
    }

    public java.util.Date toDate(DateTime fecha) {
        return fecha == null ? null : fecha.toDate();
    }

    public java.util.Date toDate(String fecha) {
        DateTime dtFecha = DateTime.getInstancia(fecha);
        return dtFecha == null ? null : dtFecha.toDate();
    }

    public java.sql.Date toDateSQL(DateTime fecha) {
        return fecha == null ? null : fecha.toDateSQL();
    }

    public java.sql.Timestamp toTimeStamp(DateTime fecha) {
        return fecha == null ? null : fecha.toTimestamp();
    }

    public java.sql.Date toDateSQL(String fecha) {
        DateTime dtFecha = DateTime.getInstancia(fecha);
        return dtFecha == null ? null : dtFecha.toDateSQL();
    }

    public double toDouble(String valor) {
        return Double.parseDouble(valor.trim());
    }

    public double toDoubleRound(String valor, int nroDecimales) {
        return JMUtil.round(Double.parseDouble(valor.trim()), nroDecimales);
    }

    public double toDoubleR2(String valor) {
        return JMUtil.round(Double.parseDouble(valor.trim()), 2);
    }

    public double toDoubleR3(String valor) {
        return JMUtil.round(Double.parseDouble(valor.trim()), 3);
    }

    public double toDoubleR4(String valor) {
        return JMUtil.round(Double.parseDouble(valor.trim()), 4);
    }

    public String toString(Object valor) {
        return valor == null ? "null" : valor.toString();
    }

    public String toString(Object valor, String valorIfNull) {
        return valor == null ? valorIfNull : valor.toString();
    }

    public double round(double valor, int nroDecimales) {
        return JMUtil.round(valor, nroDecimales);
    }

    public double round1(double valor) {
        return JMUtil.round1(valor);
    }

    public double round2(double valor) {
        return JMUtil.round2(valor);
    }

    public double round3(double valor) {
        return JMUtil.round3(valor);
    }

    public double round4(double valor) {
        return JMUtil.round4(valor);
    }

    public double round10(double valor) {
        return JMUtil.round10(valor);
    }

    public double round15(double valor) {
        return JMUtil.round15(valor);
    }

    public String roundString(double numero, int nroDecimales) {
        return JMUtil.roundBD(numero, nroDecimales).toString();
    }

    public String round1String(double numero) {
        return JMUtil.roundBD(numero, 1).toString();
    }

    public String round2String(double numero) {
        return JMUtil.roundBD(numero, 2).toString();
    }

    public String round3String(double numero) {
        return JMUtil.roundBD(numero, 3).toString();
    }

    public String round4String(double numero) {
        return JMUtil.roundBD(numero, 4).toString();
    }

    public String round10String(double numero) {
        return JMUtil.roundBD(numero, 10).toString();
    }

    public String round15String(double numero) {
        return JMUtil.roundBD(numero, 15).toString();
    }

    public String _fechaString(String fecha, String descripcion) {
        if (_vacio(fecha)) return descripcion + " no puede estar vacía";
        if (!_fecha(fecha, DateTime.FECHA_DDMMAAAA))
            return descripcion + " no tiene formato de fecha correcto (" + DateTime.FECHA_DDMMAAAA + ")";

        // AHORA: COMO YA SE SABE QUE ES FECHA EL REPLACE CORRE SIN PROBLEMAS. ACEPTA SOLO FORMATO DIA MES ANIO CON SEPARADOR - /
        String[] trozos = fecha.trim().replace('-', '/').replace('.', '/').split("/");

        DateTime fechaDT = DateTime.getInstancia(fecha); // fecha convertida y posible cambio por mes de febrero

        if (fechaDT != null && !(fechaDT.getDia() == Integer.parseInt(trozos[0]) &&
                fechaDT.getMes() == Integer.parseInt(trozos[1]) && fechaDT.getAnio() == Integer.parseInt(trozos[2]))) {
            return descripcion + " no es una fecha existente";
        }


        return "";
    }

    public String _fechaString(String fecha, String formato, String descripcion) {

        return _fechaString(fecha, formato, descripcion, true);

    }

    public String _fechaString(String fecha, String formato, String descripcion, boolean incluirFormatoEnMensaje) {
        if (_vacio(fecha)) return descripcion + " no puede estar vacía";

        if (incluirFormatoEnMensaje) {
            if (!_fecha(fecha, formato)) return descripcion + " no tiene formato de fecha correcto (" + formato + ")";
        } else {
            if (!_fecha(fecha, formato)) return descripcion + " no tiene formato de fecha correcto";
        }

        // AHORA: COMO YA SE SABE QUE ES FECHA EL REPLACE CORRE SIN PROBLEMAS. ACEPTA SOLO FORMATO DIA MES ANIO CON SEPARADOR - /
        String[] trozos = fecha.trim().replace('-', '/').replace('.', '/').split("/");

        DateTime fechaDT = DateTime.getInstancia(fecha, formato); // fecha convertida y posible cambio por mes de febrero

        if (DateTime.FECHA_DDMMAAAA.equals(formato)) {

            if (fechaDT != null && !(fechaDT.getDia() == Integer.parseInt(trozos[0]) &&
                    fechaDT.getMes() == Integer.parseInt(trozos[1]) && fechaDT.getAnio() == Integer.parseInt(trozos[2]))) {
                return descripcion + " no es una fecha existente";
            }

        } else if (DateTime.FECHA_AAAAMMDD.equals(formato)) {

            if (fechaDT != null && !(fechaDT.getDia() == Integer.parseInt(trozos[2]) &&
                    fechaDT.getMes() == Integer.parseInt(trozos[1]) && fechaDT.getAnio() == Integer.parseInt(trozos[0]))) {
                return descripcion + " no es una fecha existente";
            }

        }

        return "";
    }

    public String _campoMaxSizeNoVacio(String cadena, int sizeMax, String descripcion) {
        if (_vacio(cadena)) return descripcion + " no puede estar vacío";
        if (toBlank(cadena).length() > Math.abs(sizeMax))
            return descripcion + " no debe superar los " + Math.abs(sizeMax) + " caracteres";
        return "";
    }

    public String _campoMinSizeNoVacio(String cadena, int sizeMin, String descripcion) {
        if (_vacio(cadena)) return descripcion + " no puede estar vacío";
        if (toBlank(cadena).length() < Math.abs(sizeMin))
            return descripcion + " no puede tener menos de " + Math.abs(sizeMin) + " caracteres";
        return "";
    }

    public String _campoMinMaxSizeNoVacio(String cadena, int sizeMin, int sizeMax, String descripcion) {
        if (_vacio(cadena)) return descripcion + " no puede estar vacío";
        if (toBlank(cadena).length() < Math.abs(sizeMin))
            return descripcion + " no puede tener menos de " + Math.abs(sizeMin) + " caracteres";
        if (toBlank(cadena).length() > Math.abs(sizeMax))
            return descripcion + " no debe superar los " + Math.abs(sizeMax) + " caracteres";
        return "";
    }

    public String _campoMaxSize(String cadena, int sizeMax, String descripcion) {
        if (_vacio(cadena)) return "";
        if (toBlank(cadena).length() > Math.abs(sizeMax))
            return descripcion + " no debe superar los " + Math.abs(sizeMax) + " caracteres";
        return "";
    }

    public String _campoMinSize(String cadena, int sizeMin, String descripcion) {
        if (_vacio(cadena)) return "";
        if (toBlank(cadena).length() < Math.abs(sizeMin))
            return descripcion + " no puede tener menos de " + Math.abs(sizeMin) + " caracteres";
        return "";
    }

    public String _campoMinMaxSize(String cadena, int sizeMin, int sizeMax, String descripcion) {
        if (_vacio(cadena)) return "";
        if (toBlank(cadena).length() < Math.abs(sizeMin))
            return descripcion + " no puede tener menos de " + Math.abs(sizeMin) + " caracteres";
        if (toBlank(cadena).length() > Math.abs(sizeMax))
            return descripcion + " no debe superar los " + Math.abs(sizeMax) + " caracteres";
        return "";

    }

    public String _campoNoVacio(String cadena, String descripcion) {
        if (_vacio(cadena)) return descripcion + " no puede estar vacío";
        return "";
    }

    public String _numeroEnteroString(String numero, String descripcion) {
        if (_vacio(numero)) return descripcion + " no puede estar vacío";
        if (!VAL.esNumeroEntero(numero)) return descripcion + " debe ser un número entero";
        return "";
    }

    public String _numeroEnteroString(String numero, String descripcion, int ini, int fin) {
        if (_vacio(numero)) return descripcion + " no puede estar vacío";
        if (!VAL.esNumeroEntero(numero)) return descripcion + " debe ser un número entero";
        long valor = new BigInteger(numero.trim()).longValue();
        if (!(valor >= ini && valor <= fin)) return descripcion + " debe estar en el rango de " + ini + " y " + fin;
        return "";
    }

    public String _numeroEnteroNoNegativoString(String numero, String descripcion) {
        if (_vacio(numero)) return descripcion + " no puede estar vacío";
        if (!VAL.esNumeroEntero(numero)) return descripcion + " debe ser un número entero";
        if (new BigInteger(numero.trim()).longValue() < 0) return descripcion + " no debe ser un número negativo";
        return "";
    }

    public String _numeroEnteroPositivoString(String numero, String descripcion) {
        if (_vacio(numero)) return descripcion + " no puede estar vacío";
        if (!VAL.esNumeroEntero(numero)) return descripcion + " debe ser un número entero";
        if (new BigInteger(numero.trim()).longValue() <= 0) return descripcion + " debe ser un número mayor que cero";
        return "";
    }

    public String _numeroString(String numero, String descripcion) {
        if (_vacio(numero)) return descripcion + " no puede estar vacío";
        if (!VAL.esNumero(numero)) return descripcion + " debe ser un número";
        return "";
    }

    public String _numeroString(String numero, String descripcion, double ini, double fin) {
        if (_vacio(numero)) return descripcion + " no puede estar vacío";
        if (!VAL.esNumero(numero)) return descripcion + " debe ser un número";
        double valor = toDouble(numero);
        if (!(valor >= ini && valor <= fin)) return descripcion + " debe ser en el rango de " + ini + " y " + fin;
        return "";
    }

    public String _numeroNoNegativoString(String numero, String descripcion) {
        if (_vacio(numero)) return descripcion + " no puede estar vacío";
        if (!VAL.esNumero(numero)) return descripcion + " debe ser un número";
        if (Double.parseDouble(numero.trim()) < 0) return descripcion + " no debe ser un número negativo";
        return "";
    }

    public String _numeroPositivoString(String numero, String descripcion) {
        if (_vacio(numero)) return descripcion + " no puede estar vacío";
        if (!VAL.esNumero(numero)) return descripcion + " debe ser un número";
        if (Double.parseDouble(numero.trim()) <= 0) return descripcion + " debe ser un número mayor que cero";
        return "";
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
