package com.jcfr.utiles.repositorios;

import com.jcfr.utiles.DateTime;
import com.jcfr.utiles.JConvertidor;
import com.jcfr.utiles.exceptions.JcFRException;
import com.jcfr.utiles.math.JMUtil;
import com.jcfr.utiles.web.StringWeb;

import javax.swing.table.DefaultTableModel;
import java.util.Date;

public class JDataTable extends DefaultTableModel {

    private static final long serialVersionUID = 1676105058318654296L;

    private boolean tablaSoloLectura;

    public JDataTable() {
        super();
    }

    public JDataTable(int rowCount, int columnCount) {
        super(rowCount, columnCount);
    }

    public JDataTable(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public JDataTable(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return !tablaSoloLectura;
    }

    public Object[] getColumnValues(String nombreColumna) {
        return getColumnValues(findColumn(nombreColumna.trim()));
    }

    public Object[] getColumnValues(int posColumna) {

        Object[] res = new Object[getColumnCount()];

        for (int i = 0; i < getColumnCount(); i++) {
            res[i] = getValueAt(i, posColumna);
        }

        return res;
    }

    public String[] getColumnNames() {
        // Siempre se examinan las columnas, ya que pueden ser cambiadas en cualquier
        // momento por los métodos del modelo table.
        String[] res = new String[getColumnCount()];
        for (int i = 0; i < getColumnCount(); i++) {
            res[i] = getColumnName(i);
        }
        return res;
    }

    public int getNroFilas() {
        return getRowCount();
    }

    public int getNroColumnas() {
        return getColumnCount();
    }

    public boolean hayFilas() {
        return getRowCount() > 0;
    }

    public boolean existeFila(int fila) {
        return fila < getRowCount() && fila >= 0;
    }

    public boolean existeColumna(int columna) {
        return columna < getColumnCount() && columna >= 0;
    }

    public boolean existeColumna(String nombreColumna) {
        return findColumn(nombreColumna.trim()) != -1; // si es -1 no existe
    }

    // OBTENCION Y SETEO DE FILAS: métodos clásicos
    public void setValorAt(Object valor, int fila, int columna) {
        setValueAt(valor, fila, columna);
    }

    public void setValorAt(Object valor, int fila, String nombreColumna) {
        setValueAt(valor, fila, _bPC(nombreColumna));
    }

    private int _bPC(String nombreColumna) {
        return findColumn(nombreColumna.trim());
    }

    // OBTENCION Y SETEO DE FILAS: métodos cortos
    public void replace(Object valor, int fila, int columna) {
        setValueAt(valor, fila, columna);
    }

    public void replace(Object valor, int fila, String nombreColumna) {
        setValueAt(valor, fila, _bPC(nombreColumna));
    }

    public void replaceSuma(double valor, int fila, int columna) {
        setValueAt(gd(fila, columna) + valor, fila, columna);
    }

    public void replaceSuma(double valor, int fila, String nombreColumna) {
        int col = _bPC(nombreColumna);
        setValueAt(gd(fila, col) + valor, fila, col);
    }

    public void replaceSumaRound(double valor, int fila, int columna, int nroDecimales) {
        setValueAt(JMUtil.round(gd(fila, columna) + valor, nroDecimales), fila, columna);
    }

    public void replaceSumaRound(double valor, int fila, String nombreColumna, int nroDecimales) {
        int col = _bPC(nombreColumna);
        setValueAt(JMUtil.round(gd(fila, col) + valor, nroDecimales), fila, col);
    }

    public void replaceSumaR2(double valor, int fila, int columna) {
        setValueAt(JMUtil.round(gd(fila, columna) + valor, 2), fila, columna);
    }

    public void replaceSumaR2(double valor, int fila, String nombreColumna) {
        int col = _bPC(nombreColumna);
        setValueAt(JMUtil.round(gd(fila, col) + valor, 2), fila, col);
    }

    public void replaceRest(double valor, int fila, int columna) {
        setValueAt(gd(fila, columna) - valor, fila, columna);
    }

    public void replaceRest(double valor, int fila, String nombreColumna) {
        int col = _bPC(nombreColumna);
        setValueAt(gd(fila, col) - valor, fila, col);
    }

    public void replaceRestR2(double valor, int fila, int columna) {
        setValueAt(JMUtil.round(gd(fila, columna) - valor, 2), fila, columna);
    }

    public void replaceRestR2(double valor, int fila, String nombreColumna) {
        int col = _bPC(nombreColumna);
        setValueAt(JMUtil.round(gd(fila, col) - valor, 2), fila, col);
    }

    public void replaceRestRound(double valor, int fila, int columna, int nroDecimales) {
        setValueAt(JMUtil.round(gd(fila, columna) - valor, nroDecimales), fila, columna);
    }

    public void replaceRestRound(double valor, int fila, String nombreColumna, int nroDecimales) {
        int col = _bPC(nombreColumna);
        setValueAt(JMUtil.round(gd(fila, col) - valor, nroDecimales), fila, col);
    }

    public void replaceProd(double valor, int fila, int columna) {
        setValueAt(gd(fila, columna) * valor, fila, columna);
    }

    public void replaceProd(double valor, int fila, String nombreColumna) {
        int col = _bPC(nombreColumna);
        setValueAt(gd(fila, col) * valor, fila, col);
    }

    public void replaceDiv(double valor, int fila, int columna) {
        if (valor == 0)
            throw new JcFRException("División entre cero! (fila = " + fila + ", columna = " + columna + ")");
        setValueAt(gd(fila, columna) / valor, fila, columna);
    }

    public void replaceDiv(double valor, int fila, String nombreColumna) {
        int col = _bPC(nombreColumna);
        if (valor == 0)
            throw new JcFRException("División entre cero! (fila = " + fila + ", columna = " + nombreColumna + ")");
        setValueAt(gd(fila, col) / valor, fila, col);
    }

    public String gs(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? null : objTemp.toString();
    }

    public String gs(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? null : objTemp.toString();
    }

    public String gs(int fila, int columna, String defaultValue) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? defaultValue : objTemp.toString();
    }

    public String gs(int fila, String nombreColumna, String defaultValue) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? defaultValue : objTemp.toString();
    }

    public String gsB(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? "" : objTemp.toString().trim();
    }

    public String gsB(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? "" : objTemp.toString().trim();
    }

    public String gsT(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? null : objTemp.toString().trim();
    }

    public String gsT(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? null : objTemp.toString().trim();
    }

    public String gsUpB(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? "" : objTemp.toString().trim().toUpperCase();
    }

    public String gsUpB(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? "" : objTemp.toString().trim().toUpperCase();
    }

    public String gsLowB(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? "" : objTemp.toString().trim().toLowerCase();
    }

    public String gsLowB(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? "" : objTemp.toString().trim().toLowerCase();
    }

    // toNullIf
    public String gsNI(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        if (objTemp == null) return null;
        String res = objTemp.toString().trim();
        return res.length() == 0 ? null : res;
    }

    public String gsNI(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        if (objTemp == null) return null;
        String res = objTemp.toString().trim();
        return res.length() == 0 ? null : res;
    }

    public String gsNUpI(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        if (objTemp == null) return null;
        String res = objTemp.toString().trim();
        return res.length() == 0 ? null : res.toUpperCase();
    }

    public String gsNUpI(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        if (objTemp == null) return null;
        String res = objTemp.toString().trim();
        return res.length() == 0 ? null : res.toUpperCase();
    }

    public String gsNLowI(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        if (objTemp == null) return null;
        String res = objTemp.toString().trim();
        return res.length() == 0 ? null : res.toLowerCase();
    }

    public String gsNLowI(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        if (objTemp == null) return null;
        String res = objTemp.toString().trim();
        return res.length() == 0 ? null : res.toLowerCase();
    }

    public DateTime gdt(int fila, int columna) {
        return JConvertidor.JConvertidor.toDateTime(getValorAt(fila, columna));
    }

    public DateTime gdt(int fila, String nombreColumna) {
        return JConvertidor.JConvertidor.toDateTime(getValorAt(fila, _bPC(nombreColumna)));
    }

    public int gi(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? 0 : JConvertidor.JConvertidor.toInt(objTemp);
    }

    public int gi(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? 0 : JConvertidor.JConvertidor.toInt(objTemp);
    }

    public long gl(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? 0L : JConvertidor.JConvertidor.toLong(objTemp);
    }

    public long gl(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? 0L : JConvertidor.JConvertidor.toLong(objTemp);
    }

    public double gd(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? 0.0 : JConvertidor.JConvertidor.toDouble(objTemp);
    }

    public double gdR(int fila, int columna, int nroDecimales) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? 0.0 : JMUtil.round(JConvertidor.JConvertidor.toDouble(objTemp), nroDecimales);
    }

    public String gdRS(int fila, int columna, int nroDecimales) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? "" : JMUtil.roundBD(JConvertidor.JConvertidor.toDouble(objTemp), nroDecimales).toString();
    }

    public double gdR2(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? 0.0 : JMUtil.round(JConvertidor.JConvertidor.toDouble(objTemp), 2);
    }

    public double gdR3(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? 0.0 : JMUtil.round(JConvertidor.JConvertidor.toDouble(objTemp), 3);
    }

    public double gd(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? 0.0 : JConvertidor.JConvertidor.toDouble(objTemp);
    }

    public double gdR(int fila, String nombreColumna, int nroDecimales) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? 0.0 : JMUtil.round(JConvertidor.JConvertidor.toDouble(objTemp), nroDecimales);
    }

    public String gdRS(int fila, String nombreColumna, int nroDecimales) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? "" : JMUtil.roundBD(JConvertidor.JConvertidor.toDouble(objTemp), nroDecimales).toString();
    }

    public double gdR2(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? 0.0 : JMUtil.round(JConvertidor.JConvertidor.toDouble(objTemp), 2);
    }

    public double gdR3(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? 0.0 : JMUtil.round(JConvertidor.JConvertidor.toDouble(objTemp), 3);
    }

    // OBTENCION Y SETEO DE FILAS: métodos clásicos
    public Object getValorAt(int fila, int columna) {
        return getValueAt(fila, columna);
    }

    public Object getValorAt(int fila, String nombreColumna) {
        return getValueAt(fila, _bPC(nombreColumna));
    }

    public Object getObjectAt(int fila, int columna) {
        return getValorAt(fila, columna);
    }

    public Object getObjectAt(int fila, String nombreColumna) {
        return getValorAt(fila, _bPC(nombreColumna));
    }

    public char getCharAt(int fila, int columna) {
        return JConvertidor.JConvertidor.toChar(getValorAt(fila, columna));
    }

    public char getCharAt(int fila, String nombreColumna) {
        return JConvertidor.JConvertidor.toChar(getValorAt(fila, _bPC(nombreColumna)));
    }

    public boolean getBooleanAt(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp != null && JConvertidor.JConvertidor.toBoolean(objTemp);
    }

    public boolean getBooleanAt(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp != null && JConvertidor.JConvertidor.toBoolean(objTemp);
    }

    public byte[] getByteArray(int fila, String nombreColumna) {
        return (byte[]) getValorAt(fila, _bPC(nombreColumna));
    }

    public byte[] getByteArray(int fila, int columna) {
        return (byte[]) getValorAt(fila, columna);
    }

    public byte getByteAt(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? (byte) 0 : JConvertidor.JConvertidor.toByte(objTemp);
    }

    public byte getByteAt(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? (byte) 0 : JConvertidor.JConvertidor.toByte(objTemp);

    }

    public short getShortAt(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? (short) 0 : JConvertidor.JConvertidor.toShort(objTemp);
    }

    public short getShortAt(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? (short) 0 : JConvertidor.JConvertidor.toShort(objTemp);
    }

    public int getIntAt(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? 0 : JConvertidor.JConvertidor.toInt(objTemp);
    }

    public int getIntAt(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? 0 : JConvertidor.JConvertidor.toInt(objTemp);
    }

    public long getLongAt(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? 0L : JConvertidor.JConvertidor.toLong(objTemp);
    }

    public long getLongAt(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? 0L : JConvertidor.JConvertidor.toLong(objTemp);
    }

    public float getFloatAt(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? (float) 0.0 : JConvertidor.JConvertidor.toFloat(objTemp);
    }

    public float getFloatAt(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? (float) 0.0 : JConvertidor.JConvertidor.toFloat(objTemp);
    }

    public double getDoubleAt(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? 0.0 : JConvertidor.JConvertidor.toDouble(objTemp);
    }

    public double getDoubleRoundAt(int fila, int columna, int nroDecimales) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? 0.0 : JMUtil.round(JConvertidor.JConvertidor.toDouble(objTemp), nroDecimales);
    }

    public double getDoubleAt(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? 0.0 : JConvertidor.JConvertidor.toDouble(objTemp);
    }

    public double getDoubleAt(int fila, String nombreColumna, int nroDecimales) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? 0.0 : JMUtil.round(JConvertidor.JConvertidor.toDouble(objTemp), nroDecimales);
    }

    public String getStringAt(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? null : objTemp.toString();
    }

    public String getStringAt(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? null : objTemp.toString();
    }

    public String getStringBlankAt(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? "" : objTemp.toString().trim();
    }

    public String getStringBlankAt(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? "" : objTemp.toString().trim();
    }

    public StringWeb getStringWebAt(int fila, int columna) {
        Object objTemp = getValorAt(fila, columna);
        return objTemp == null ? null : new StringWeb(objTemp.toString());
    }

    public StringWeb getStringWebAt(int fila, String nombreColumna) {
        Object objTemp = getValorAt(fila, _bPC(nombreColumna));
        return objTemp == null ? null : new StringWeb(objTemp.toString());
    }

    public Date getDateAt(int fila, int columna) {
        return JConvertidor.JConvertidor.toDate(getValorAt(fila, columna));
    }

    public Date getDateAt(int fila, String nombreColumna) {
        return JConvertidor.JConvertidor.toDate(getValorAt(fila, _bPC(nombreColumna)));
    }

    public java.sql.Date getDateSQLAt(int fila, int columna) {
        return JConvertidor.JConvertidor.toDateSQL(getValorAt(fila, columna));
    }

    public java.sql.Date getDateSQLAt(int fila, String nombreColumna) {
        return JConvertidor.JConvertidor.toDateSQL(getValorAt(fila, _bPC(nombreColumna)));
    }

    public DateTime getDateTimeAt(int fila, int columna) {
        return JConvertidor.JConvertidor.toDateTime(getValorAt(fila, columna));
    }

    public DateTime getDateTimeAt(int fila, String nombreColumna) {
        return JConvertidor.JConvertidor.toDateTime(getValorAt(fila, _bPC(nombreColumna)));
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }

    public boolean isTablaSoloLectura() {
        return tablaSoloLectura;
    }

    public void setTablaSoloLectura(boolean tablaSoloLectura) {
        this.tablaSoloLectura = tablaSoloLectura;
    }
}
