package com.jcfr.utiles.repositorios;

import com.jcfr.utiles.DateTime;
import com.jcfr.utiles.JConvertidor;
import com.jcfr.utiles.exceptions.JcFRException;
import com.jcfr.utiles.group.DataGrupoDouble;
import com.jcfr.utiles.group.DataGrupoSimple;
import com.jcfr.utiles.math.Alea;
import com.jcfr.utiles.math.JMUtil;
import com.jcfr.utiles.order.DTOrdenador;
import com.jcfr.utiles.order.OC;
import com.jcfr.utiles.selector.SelectorDT;
import com.jcfr.utiles.web.StringWeb;

import java.io.Serializable;
import java.util.*;

public class DataTable implements Serializable {

    private static final long serialVersionUID = -610802683803819186L;

    // FILAS
    private ArrayList<Object[]> list;
    // COLUMNAS
    private int nroCols;
    private HashMap<String, Integer> columns;
    private HashMap<String, String> columnsLabel;
    private ArrayList<String> columnsWithOrder;
    // INDICE
    private HashMap<String, Integer> index;
    private int indexPos;
    // NOMBRE TABLA
    private String tableName;

    // CONSTRUCTORES
    public DataTable() {
        this(Alea.newStringOnlyLetters(10), 16, 8);
    }

    public DataTable(String nombreTabla) {
        this(nombreTabla, 16, 8);
    }

    public DataTable(String nombreTabla, int nroFilasAprox) {
        this(nombreTabla, nroFilasAprox, 8);
    }

    public DataTable(int nroFilasAprox) {
        this(Alea.newStringOnlyLetters(10), nroFilasAprox, 8);
    }

    public DataTable(int nroFilasAprox, int nroColsAprox) {
        this(Alea.newStringOnlyLetters(10), nroFilasAprox, nroColsAprox);
    }

    public DataTable(String nombreTabla, int nroFilasAprox, int nroColsAprox) {

        if (nroColsAprox < 1) {
            this.list = new ArrayList<>(16);
            this.columns = new HashMap<>(8);
            this.columnsWithOrder = new ArrayList<>(8);
        } else {
            this.list = new ArrayList<>(nroFilasAprox);
            this.columns = new HashMap<>(nroColsAprox);
            this.columnsWithOrder = new ArrayList<>(nroColsAprox);
        }
        this.tableName = nombreTabla == null || nombreTabla.trim().length() == 0 ? Alea.newStringOnlyLetters(10) : nombreTabla.trim();
        this.indexPos = -1;
    }

    // METODOS PRIVADOS
    private int _bPC(String nombreColumna) {
        if (nombreColumna == null) throw new JcFRException("Nombre de columna no puede ser null!");
        Integer pos = columns.get(nombreColumna.trim());
        if (pos == null) throw new JcFRException("Columna " + nombreColumna + " no existe en la tabla!");
        return pos;
    }

    private DataTable _makeCopia(boolean incluirIndex, boolean incluirData, int tamAprox, int posIni, int posFin) {

        DataTable copy = new DataTable(tamAprox);
        for (String c : columnsWithOrder) {
            copy.addNombreColumnaFaster(c);
        }

        if (incluirIndex && indexPos >= 0) copy.setIndex(getIndexColumnName(), tamAprox);

        if (incluirData) {
            Object[] row;
            ArrayList<Object[]> listaRef = list;
            for (int i = posIni; i <= posFin; i++) {
                row = new Object[nroCols];
                System.arraycopy(listaRef.get(i), 0, row, 0, nroCols);
                copy.addFilaFasterWithIndex(row);
            }
        }

        return copy;
    }

    // ATRIBUTOS DEL DATATABLE
    public ArrayList<Object[]> getListaFilas() {
        return list;
    }

    public String getNombreTabla() {
        return tableName;
    }

    public void setNombreTabla(String nombreTabla) {
        this.tableName = nombreTabla == null || nombreTabla.trim().length() == 0 ? Alea.newStringOnlyLetters(10) : nombreTabla.trim();
    }

    public int getNroFilas() {
        return list.size();
    }

    public int getNroColumnas() {
        return nroCols;
    }

    public boolean hayFilas() {
        return list.size() > 0;
    }

    public boolean hayColumnas() {
        return nroCols > 0;
    }

    public boolean estaVacia() {
        return list.isEmpty();
    }

    public boolean existeFila(int fila) {
        return fila < list.size() && fila >= 0;
    }

    public boolean existeColumna(int columna) {
        return columna < nroCols && columna >= 0;
    }

    public boolean existeColumna(String nombreColumna) {
        try {
            int columna = _bPC(nombreColumna);
            return columna < nroCols && columna >= 0;
        } catch (JcFRException sos) {
            return false;
        }
    }

    public int buscarPosicionColumna(String nombreColumna) {
        return _bPC(nombreColumna);
    }

    public List<Object> getFila(int fila) {
        if (!existeFila(fila)) throw new JcFRException("Fila " + fila + " no existe en la Tabla!");

        ArrayList<Object> row = new ArrayList<>(nroCols);
        ArrayList<Object[]> listaRef = list;
        for (int j = 0; j < nroCols; j++) {
            row.add(listaRef.get(fila)[j]);
        }
        return row;
    }

    public List<Object> getFilaFaster(int fila) {
        ArrayList<Object> row = new ArrayList<>(nroCols);
        ArrayList<Object[]> listaRef = list;
        for (int j = 0; j < nroCols; j++) {
            row.add(listaRef.get(fila)[j]);
        }
        return row;
    }

    public Object[] getFilaArray(int fila) {
        if (!existeFila(fila)) throw new JcFRException("Fila " + fila + " no existe en la Tabla!");
        Object[] row = new Object[nroCols];
        System.arraycopy(list.get(fila), 0, row, 0, nroCols);
        return row;
    }

    public Object[] getFilaArrayFaster(int fila) {
        Object[] row = new Object[nroCols];
        System.arraycopy(list.get(fila), 0, row, 0, nroCols);
        return row;
    }

    public List<Object> getColumna(String nombreColumna) {
        return getColumna(_bPC(nombreColumna));
    }

    public List<Object> getColumna(int posColumna) {
        if (!existeColumna(posColumna)) throw new JcFRException("Columna " + posColumna + " no existe en la Tabla!");

        int nroFils = getNroFilas();
        ArrayList<Object> columna = new ArrayList<>(nroFils);

        ArrayList<Object[]> listaRef = list;
        for (int i = 0; i < nroFils; i++) {
            columna.add(listaRef.get(i)[posColumna]);
        }
        return columna;
    }

    public void cambiarNombreColumna(String nombreColumna, String nuevoNombre) {
        int pos = _bPC(nombreColumna);

        // VALIDACIONES PARA LA COLUMNA NUEVA
        if (nuevoNombre == null) throw new JcFRException("Nuevo Nombre de columna es null!");
        if (nuevoNombre.trim().length() == 0) throw new JcFRException("Nuevo Nombre de columna está vacío!");

        columnsWithOrder.set(pos, nuevoNombre.trim());

        columns.remove(nombreColumna.trim());
        columns.put(nuevoNombre.trim(), pos);
    }

    public List<String> getNombreColumnas() {
        ArrayList<String> copia = new ArrayList<>(nroCols);
        for (int i = 0; i < nroCols; i++) {
            copia.add(columnsWithOrder.get(i));
        }
        return copia;
    }

    public String[] getNombreColumnasArray() {
        return getNombreColumnas().toArray(new String[nroCols]);
    }

    public String getNombreColumna(int posColumna) {
        if (!existeColumna(posColumna)) throw new JcFRException("Posición de Columna " + posColumna + " no existe!");
        return columnsWithOrder.get(posColumna);
    }

    // MANIPULAR LOS LABELS DE LA CABECERA
    public List<String> getLabelColumnas() {
        ArrayList<String> labels = new ArrayList<>(nroCols);
        for (int i = 0; i < nroCols; i++) {
            labels.add(getLabelColumna(i));
        }
        return labels;
    }

    public String[] getLabelColumnasArray() {
        return getLabelColumnas().toArray(new String[nroCols]);
    }

    public String getLabelColumna(String nombreColumna) {
        return getLabelColumna(_bPC(nombreColumna));
    }

    public String getLabelColumna(int posColumna) {
        String nombreColumna = getNombreColumna(posColumna); // valida la posColumna
        if (columnsLabel == null) columnsLabel = new HashMap<>(nroCols);
        String label = columnsLabel.get(nombreColumna);
        return label == null ? nombreColumna : label; // si una columna no tiene su label, retorna el mismo nombre de columna
    }

    public void setLabelColumna(String[] labels) {
        if (labels == null) throw new JcFRException("Lista de Labels no puede ser nula!");

        int labelsSize = labels.length;
        if (labelsSize == 0) throw new JcFRException("Lista de Labels no estar vacía!");
        if (labelsSize != nroCols)
            throw new JcFRException("Lista de Labels debe tener " + nroCols + " elementos. Uno para cada columna!");

        for (int i = 0; i < labelsSize; i++) {
            setLabelColumna(i, labels[i]);
        }
    }

    public void setLabelColumna(List<String> labels) {
        if (labels == null) throw new JcFRException("Lista de Labels no puede ser nula!");

        int labelsSize = labels.size();
        if (labelsSize == 0) throw new JcFRException("Lista de Labels no estar vacía!");
        if (labelsSize != nroCols)
            throw new JcFRException("Lista de Labels debe tener " + nroCols + " elementos. Uno para cada columna!");

        for (int i = 0; i < labelsSize; i++) {
            setLabelColumna(i, labels.get(i));
        }
    }

    public void setLabelColumnaDyn(String... labels) {
        if (labels == null) throw new JcFRException("Lista de Labels no puede ser nula!");

        int labelsSize = labels.length;
        if (labelsSize == 0) throw new JcFRException("Lista de Labels no estar vacía!");
        if (labelsSize != nroCols)
            throw new JcFRException("Lista de Labels debe tener " + nroCols + " elementos. Uno para cada columna!");

        for (int i = 0; i < labelsSize; i++) {
            setLabelColumna(i, labels[i]);
        }
    }

    public void setLabelColumna(String nombreColumna, String label) {
        setLabelColumna(_bPC(nombreColumna), label);
    }

    public void setLabelColumna(int posColumna, String label) {
        String nombreColumna = getNombreColumna(posColumna); // valida la posColumna
        if (columnsLabel == null) columnsLabel = new HashMap<>(columns.size());
        columnsLabel.put(nombreColumna, label);
    }

    public int addFilaFasterWithIndex(Object[] fila) {
        list.add(fila);
        int posSize = list.size() - 1;
        if (indexPos >= 0) {
            index.put(gsB(posSize, indexPos), posSize);
            return posSize;
        }
        return posSize;
    }

    public void addFilaFaster(Object[] fila) {
        list.add(fila);
    }

    public int addFila(Object[] fila) {
        if (fila == null) throw new JcFRException("No puede agregar una fila nula!");
        if (nroCols == 0) throw new JcFRException("No se ha agregado ninguna columna!");
        if (fila.length != nroCols) throw new JcFRException("Sólo pueden agregarse filas de " + nroCols + " columnas!");
        return addFilaFasterWithIndex(fila);
    }

    public int addFilaDyn(Object... fila) {
        if (fila == null) throw new JcFRException("No puede agregar una fila nula!");
        if (nroCols == 0) throw new JcFRException("No se ha agregado ninguna columna!");
        if (fila.length != nroCols) throw new JcFRException("Sólo pueden agregarse filas de " + nroCols + " columnas!");
        return addFilaFasterWithIndex(fila);
    }

    public int addFilaVacia() {
        if (nroCols == 0) throw new JcFRException("No se ha agregado ninguna columna!");
        return addFilaFasterWithIndex(new Object[nroCols]);
    }

    public void addFila(int posFila, Object[] fila) {
        if (fila == null) throw new JcFRException("No puede agregar una fila nula!");
        if (nroCols == 0) throw new JcFRException("No se ha agregado ninguna columna!");
        if (fila.length != nroCols) throw new JcFRException("Sólo pueden agregarse filas de " + nroCols + " columnas!");
        // posFila puede ser el siguiente al ultimo, en ese caso lo inserta normal (manejo del ArrayList)
        if (posFila > list.size() || posFila < 0)
            throw new JcFRException("Fila " + posFila + " no existe en la Tabla!");
        list.add(posFila, fila);
        if (indexPos >= 0) index.put(gsB(posFila, indexPos), posFila);
    }

    public Object[] removerFila(int pos) {
        if (!existeFila(pos)) throw new JcFRException("Fila " + pos + " no existe!");
        return list.remove(pos);
    }

    public Object[] removerFilaByIndex(String valor) {
        int pos = findByIndex(valor);
        if (pos >= 0) return list.remove(pos);
        return null;
    }

    public void setFila(int posFila, Object[] fila) {
        if (fila == null) throw new JcFRException("No puede agregar una fila nula!");
        if (fila.length != nroCols) throw new JcFRException("Sólo puede agregarse filas de " + nroCols + " columnas!");
        if (!existeFila(posFila)) throw new JcFRException("Fila " + posFila + " no existe en la Tabla!");
        list.set(posFila, fila);
        // TODO: POSIBLE HUECO. EXAMINAR LA IMPLICANCIA QUE NO ACTUALICE EL INDICE
    }

    public void setFilaFaster(int posFila, Object[] fila) {
        list.set(posFila, fila);
    }

    public int addNombreColumna(String nombreColumna) {
        if (nombreColumna == null) throw new JcFRException("Nombre de columna es null!");
        if (hayFilas()) throw new JcFRException("No puede agregar una columna si la tabla ya contiene data!");

        String ncTrim = nombreColumna.trim();
        columns.put(ncTrim, nroCols++);
        columnsWithOrder.add(ncTrim);

        return nroCols;
    }

    public void addNombreColumnaFaster(String nombreColumna) {
        String ncTrim = nombreColumna.trim();
        columns.put(ncTrim, nroCols++);
        columnsWithOrder.add(ncTrim);
    }

    public int addNombreColumnaSinRepetir(String nombreColumna) {
        if (nombreColumna == null) throw new JcFRException("Nombre de columna es null!");
        if (!columns.containsKey(nombreColumna.trim())) return addNombreColumna(nombreColumna);
        return nroCols;
    }

    public void addNombreColumna(String... nombreColumnas) {
        if (nombreColumnas == null || nombreColumnas.length == 0)
            throw new JcFRException("Debe agregar al menos un nombre de columna!");
        for (String nombre : nombreColumnas) {
            addNombreColumna(nombre);
        }
    }

    // MANIPULACION DE INDICES
    public void clearIndex() {
        if (index != null) index.clear();
        index = null;
        indexPos = -1;
    }

    public void setIndex(String campoUnico) {
        indexPos = _bPC(campoUnico);
        if (index == null) index = new HashMap<>(Math.max(16, getNroFilas() + 8));
        reindex();
    }

    public void setIndex(String campoUnico, int tamAprox) {
        indexPos = _bPC(campoUnico);
        if (index == null) index = new HashMap<>(Math.max(16, tamAprox) + 8);
        reindex();
    }

    public void reindex() {
        if (indexPos < 0) throw new JcFRException("No se ha seteado ningún índice!");
        index.clear();
        for (int i = getNroFilas(); --i >= 0; ) {
            index.put(gsB(i, indexPos), i); // CLAVE, POSICION EN EL ARREGLO
        }
    }

    public boolean hasIndex() {
        return indexPos >= 0;
    }

    public String getIndexColumnName() {
        return indexPos >= 0 ? getNombreColumna(indexPos) : "_none";
    }

    public List<String> getIndexValues() {
        if (indexPos < 0) throw new JcFRException("No se ha seteado ningún índice!");

        ArrayList<String> indexValues = new ArrayList<>(Math.max(16, index.size()));

        for (String s : index.keySet()) {
            indexValues.add(s);
        }

        return indexValues;
    }

    // MANIPULACIONES COMUNES DE LA TABLA
    public void replaceNullValues(String nombreColumna, Object nuevoValor) {
        replaceNullValues(_bPC(nombreColumna), nuevoValor);
    }

    public void replaceNullValues(int posColumna, Object nuevoValor) {
        if (!existeColumna(posColumna))
            throw new JcFRException("Columna Nro " + posColumna + " no existe en la Tabla!");

        ArrayList<Object[]> listaRef = list;
        for (int i = getNroFilas(); --i >= 0; ) {
            if (listaRef.get(i)[posColumna] == null) setValorAt(nuevoValor, i, posColumna);
        }
    }

    public DataTable crearCopiaDeCampos() {
        return _makeCopia(true, false, 16, 0, getNroFilas() - 1);
    }

    public DataTable crearCopia() {
        return _makeCopia(true, true, getNroFilas(), 0, getNroFilas() - 1);
    }

    public DataTable crearCopia(int posIni, int posFin) {
        if (!existeFila(posIni)) throw new JcFRException("No existe la fila: " + posIni);
        if (!existeFila(posFin)) throw new JcFRException("No existe la fila: " + posFin);

        return _makeCopia(true, true, posFin - posIni + 1, posIni, posFin);
    }

    public DataTable crearCopiaSinIndice() {
        return _makeCopia(false, true, getNroFilas(), 0, getNroFilas() - 1);
    }

    // DISTINCT
    public List<Object> distinct(String nombreColumna) {

        int posColumna = _bPC(nombreColumna);
        int nroFilas = getNroFilas();

        HashMap<Object, Object> mapaDis = new HashMap<>(Math.max(16, nroFilas / 2));

        ArrayList<Object[]> listaRef = list;
        for (int i = nroFilas; --i >= 0; ) {
            mapaDis.put(listaRef.get(i)[posColumna], "");
        }

        Iterator<Object> it = mapaDis.keySet().iterator();

        ArrayList<Object> dis = new ArrayList<>(mapaDis.size());
        while (it.hasNext()) {
            dis.add(it.next());
        }
        return dis;
    }

    public Map<Object, Integer> distinctCount(String nombreColumna) {

        int pos = _bPC(nombreColumna);

        int nroFilas = getNroFilas();
        Object clave;
        HashMap<Object, Integer> mapaDis = new HashMap<>(Math.max(16, nroFilas / 2));

        ArrayList<Object[]> listaRef = list;
        for (int i = nroFilas; --i >= 0; ) {
            clave = listaRef.get(i)[pos];
            if (mapaDis.get(clave) == null) mapaDis.put(clave, 1);
            else mapaDis.put(clave, mapaDis.get(clave) + 1);
        }

        return mapaDis;
    }

    // ORDENADOR
    public DataTable orderBy(String sentenciaOrderBy) {
        new DTOrdenador(this).orderBy(sentenciaOrderBy);
        if (indexPos >= 0) reindex();
        return this;
    }

    // AGRUPADOR
    public DataGrupoDouble groupBy(String campoGroupBy1, String campoGroupBy2) {
        return new DataGrupoDouble(this, campoGroupBy1, campoGroupBy2);
    }

    public DataGrupoSimple groupBy(String campoGroupBy1) {
        return new DataGrupoSimple(this, campoGroupBy1);
    }

    // FINDS
    public int findByIndex(String valor) {
        if (indexPos < 0) throw new JcFRException("No se ha seteado ningún índice!");
        Integer pos = index.get(valor == null ? "" : valor.trim());
        return pos == null ? -1 : pos;
    }

    public String findByIndexString(String valor, String nombreColumna) {
        if (indexPos < 0) throw new JcFRException("No se ha seteado ningún índice!");
        Integer pos = index.get(valor == null ? "" : valor.trim());
        return pos == null ? "" : gs(pos, nombreColumna);
    }

    public String findByIndexString(String valor, String nombreColumna, String valueNoExiste) {
        if (indexPos < 0) throw new JcFRException("No se ha seteado ningún índice!");
        Integer pos = index.get(valor == null ? "" : valor.trim());
        return pos == null ? valueNoExiste : gs(pos, nombreColumna);
    }

    public double findByIndexDouble(String valor, String nombreColumna) {
        if (indexPos < 0) throw new JcFRException("No se ha seteado ningún índice!");
        Integer pos = index.get(valor == null ? "" : valor.trim());
        return pos == null ? 0.0 : gd(pos, nombreColumna);
    }

    public double findByIndexDouble(String valor, String nombreColumna, double valueNoExiste) {
        if (indexPos < 0) throw new JcFRException("No se ha seteado ningún índice!");
        Integer pos = index.get(valor == null ? "" : valor.trim());
        return pos == null ? valueNoExiste : gd(pos, nombreColumna);
    }

    public int findByIndexInt(String valor, String nombreColumna) {
        if (indexPos < 0) throw new JcFRException("No se ha seteado ningún índice!");
        Integer pos = index.get(valor == null ? "" : valor.trim());
        return pos == null ? 0 : gi(pos, nombreColumna);
    }

    public int findByIndexInt(String valor, String nombreColumna, int valueNoExiste) {
        if (indexPos < 0) throw new JcFRException("No se ha seteado ningún índice!");
        Integer pos = index.get(valor == null ? "" : valor.trim());
        return pos == null ? valueNoExiste : gi(pos, nombreColumna);
    }

    public long findByIndexLong(String valor, String nombreColumna) {
        if (indexPos < 0) throw new JcFRException("No se ha seteado ningún índice!");
        Integer pos = index.get(valor == null ? "" : valor.trim());
        return pos == null ? 0L : gl(pos, nombreColumna);
    }

    public long findByIndexLong(String valor, String nombreColumna, long valueNoExiste) {
        if (indexPos < 0) throw new JcFRException("No se ha seteado ningún índice!");
        Integer pos = index.get(valor == null ? "" : valor.trim());
        return pos == null ? valueNoExiste : gl(pos, nombreColumna);
    }

    public DateTime findByIndexDateTime(String valor, String nombreColumna) {
        if (indexPos < 0) throw new JcFRException("No se ha seteado ningún índice!");
        Integer pos = index.get(valor == null ? "" : valor.trim());
        return pos == null ? null : gdt(pos, nombreColumna);
    }

    public DateTime findByIndexDateTime(String valor, String nombreColumna, DateTime valueNoExiste) {
        if (indexPos < 0) throw new JcFRException("No se ha seteado ningún índice!");
        Integer pos = index.get(valor == null ? "" : valor.trim());
        return pos == null ? valueNoExiste : gdt(pos, nombreColumna);
    }

    public int findByColumn(Object valor, String nombreColumna) {
        return findByColumn(valor, _bPC(nombreColumna));
    }

    public int findByColumn(Object valor, int posColumna) {
        if (!existeColumna(posColumna)) throw new JcFRException("Posición " + posColumna + " de columna no existe!");

        ArrayList<Object[]> listaRef = list;
        for (int i = getNroFilas(); --i >= 0; ) {
            if (OC.compare(valor, listaRef.get(i)[posColumna]) == 0) return i;
        }
        return -1;
    }

    public int findBySortedColumnUnique(Object valor, String sortedColumn) {
        if (sortedColumn == null || sortedColumn.trim().length() == 0)
            throw new JcFRException("Debe especificar la columna de búsqueda!");
        int posColumna = _bPC(sortedColumn);

        int binIzq = 0;
        int binDer = getNroFilas() - 1;
        int binCentro;
        int binComp;
        ArrayList<Object[]> listaRef = list;

        while (binIzq <= binDer) {
            binCentro = (binIzq + binDer) / 2;
            binComp = OC.compare(valor, listaRef.get(binCentro)[posColumna]);
            if (binComp < 0) binDer = binCentro - 1;
            else if (binComp > 0) binIzq = binCentro + 1;
            else return binCentro;
        }
        return -1; // SI izq > der no lo encontró
    }

    public int findBySortedColumn(Object valor, String sortedColumn) {
        if (sortedColumn == null || sortedColumn.trim().length() == 0)
            throw new JcFRException("Debe especificar la columna de búsqueda!");
        int posColumna = _bPC(sortedColumn);

        int binIzq = 0;
        int binDer = getNroFilas() - 1;
        int binCentro;
        int binComp;
        ArrayList<Object[]> listaRef = list;

        while (binIzq <= binDer) {
            binCentro = (binIzq + binDer) / 2;
            binComp = OC.compare(valor, listaRef.get(binCentro)[posColumna]);
            if (binComp < 0) binDer = binCentro - 1;
            else if (binComp > 0) binIzq = binCentro + 1;
            else { // SI LO ENCONTRÓ, BUSCA LA 1ERA COINCIDENCIA

                int i = binCentro - 1;
                while (i >= 0 && OC.compare(valor, listaRef.get(i)[posColumna]) == 0) {
                    i--;
                }
                return i + 1;

            }
        }
        return -1; // SI izq > der no lo encontró

    }

    // EXISTENCIA
    public boolean existsByIndex(String valor) {
        return findByIndex(valor) >= 0;
    }

    public boolean existsByColumn(Object valor, String nombreColumna) {
        return findByColumn(valor, _bPC(nombreColumna)) >= 0;
    }

    public boolean existsByColumn(Object valor, int posColumna) {
        return findByColumn(valor, posColumna) >= 0;
    }

    public boolean existsBySortedColumn(String valor, String sortedColumn) {
        return findBySortedColumn(valor, sortedColumn) >= 0;
    }

    // SELECTOR
    public DataTable select(SelectorDT filtro) {

        if (filtro == null) throw new JcFRException("selector no puede ser null!");

        int nroFils = getNroFilas();
        DataTable select = _makeCopia(false, false, (int) Math.max(16, (3.0 / 4.0) * nroFils), 0, nroFils - 1);

        for (int i = 0; i < nroFils; i++) {
            if (filtro.incluir(this, i)) {
                Object[] row = new Object[nroCols];
                System.arraycopy(list.get(i), 0, row, 0, nroCols);
                select.addFilaFasterWithIndex(row);
            }
        }

        return select;
    }

    // OBTENCION Y SETEO DE FILAS: métodos cortos
    public void replace(Object valor, int fila, int columna) {
        list.get(fila)[columna] = valor;
        if (indexPos == columna && indexPos >= 0) index.put(gsB(fila, indexPos), fila);
    }

    public void replace(Object valor, int fila, String nombreColumna) {
        int columna = _bPC(nombreColumna);
        list.get(fila)[columna] = valor;
        if (indexPos == columna && indexPos >= 0) index.put(gsB(fila, indexPos), fila);
    }

    public void replaceSuma(double valor, int fila, int columna) {
        replace(gd(fila, columna) + valor, fila, columna);
    }

    public void replaceSuma(double valor, int fila, String nombreColumna) {
        int col = _bPC(nombreColumna);
        replace(gd(fila, col) + valor, fila, col);
    }

    public void replaceSumaRound(double valor, int fila, int columna, int nroDecimales) {
        replace(JMUtil.round(gd(fila, columna) + valor, nroDecimales), fila, columna);
    }

    public void replaceSumaRound(double valor, int fila, String nombreColumna, int nroDecimales) {
        int col = _bPC(nombreColumna);
        replace(JMUtil.round(gd(fila, col) + valor, nroDecimales), fila, col);
    }

    public void replaceSumaR2(double valor, int fila, int columna) {
        replace(JMUtil.round(gd(fila, columna) + valor, 2), fila, columna);
    }

    public void replaceSumaR2(double valor, int fila, String nombreColumna) {
        int col = _bPC(nombreColumna);
        replace(JMUtil.round(gd(fila, col) + valor, 2), fila, col);
    }

    public void replaceRest(double valor, int fila, int columna) {
        replace(gd(fila, columna) - valor, fila, columna);
    }

    public void replaceRest(double valor, int fila, String nombreColumna) {
        int col = _bPC(nombreColumna);
        replace(gd(fila, col) - valor, fila, col);
    }

    public void replaceRestR2(double valor, int fila, int columna) {
        replace(JMUtil.round(gd(fila, columna) - valor, 2), fila, columna);
    }

    public void replaceRestR2(double valor, int fila, String nombreColumna) {
        int col = _bPC(nombreColumna);
        replace(JMUtil.round(gd(fila, col) - valor, 2), fila, col);
    }

    public void replaceRestRound(double valor, int fila, int columna, int nroDecimales) {
        replace(JMUtil.round(gd(fila, columna) - valor, nroDecimales), fila, columna);
    }

    public void replaceRestRound(double valor, int fila, String nombreColumna, int nroDecimales) {
        int col = _bPC(nombreColumna);
        replace(JMUtil.round(gd(fila, col) - valor, nroDecimales), fila, col);
    }

    public void replaceProd(double valor, int fila, int columna) {
        replace(gd(fila, columna) * valor, fila, columna);
    }

    public void replaceProd(double valor, int fila, String nombreColumna) {
        int col = _bPC(nombreColumna);
        replace(gd(fila, col) * valor, fila, col);
    }

    public void replaceDiv(double valor, int fila, int columna) {
        if (valor == 0)
            throw new JcFRException("División entre cero! (fila = " + fila + ", columna = " + columna + ")");
        replace(gd(fila, columna) / valor, fila, columna);
    }

    public void replaceDiv(double valor, int fila, String nombreColumna) {
        int col = _bPC(nombreColumna);
        if (valor == 0)
            throw new JcFRException("División entre cero! (fila = " + fila + ", columna = " + nombreColumna + ")");
        replace(gd(fila, col) / valor, fila, col);
    }

    public String gs(int fila, int columna) {
        Object objTemp = list.get(fila)[columna];
        return objTemp == null ? null : objTemp.toString();
    }

    public String gs(int fila, int columna, String defaultValue) {
        Object objTemp = list.get(fila)[columna];
        return objTemp == null ? defaultValue : objTemp.toString();
    }

    public String gs(int fila, String nombreColumna) {
        Object objTemp = list.get(fila)[_bPC(nombreColumna)];
        return objTemp == null ? null : objTemp.toString();
    }

    public String gs(int fila, String nombreColumna, String defaultValue) {
        Object objTemp = list.get(fila)[_bPC(nombreColumna)];
        return objTemp == null ? defaultValue : objTemp.toString();
    }

    public String gsB(int fila, int columna) {
        Object objTemp = list.get(fila)[columna];
        return objTemp == null ? "" : objTemp.toString().trim();
    }

    public String gsB(int fila, int columna, String defaultValue) {
        Object objTemp = list.get(fila)[columna];
        return objTemp == null ? defaultValue : objTemp.toString().trim();
    }

    public String gsB(int fila, String nombreColumna) {
        Object objTemp = list.get(fila)[_bPC(nombreColumna)];
        return objTemp == null ? "" : objTemp.toString().trim();
    }

    public String gsB(int fila, String nombreColumna, String defaultValue) {
        Object objTemp = list.get(fila)[_bPC(nombreColumna)];
        return objTemp == null ? defaultValue : objTemp.toString().trim();
    }

    public String gsT(int fila, int columna) {
        Object objTemp = list.get(fila)[columna];
        return objTemp == null ? null : objTemp.toString().trim();
    }

    public String gsT(int fila, int columna, String defaultValue) {
        Object objTemp = list.get(fila)[columna];
        return objTemp == null ? defaultValue : objTemp.toString().trim();
    }

    public String gsT(int fila, String nombreColumna) {
        Object objTemp = list.get(fila)[_bPC(nombreColumna)];
        return objTemp == null ? null : objTemp.toString().trim();
    }

    public String gsT(int fila, String nombreColumna, String defaultValue) {
        Object objTemp = list.get(fila)[_bPC(nombreColumna)];
        return objTemp == null ? defaultValue : objTemp.toString().trim();
    }

    public String gsUpB(int fila, int columna) {
        Object objTemp = list.get(fila)[columna];
        return objTemp == null ? "" : objTemp.toString().trim().toUpperCase();
    }

    public String gsUpB(int fila, String nombreColumna) {
        Object objTemp = list.get(fila)[_bPC(nombreColumna)];
        return objTemp == null ? "" : objTemp.toString().trim().toUpperCase();
    }

    public String gsUpB(int fila, int columna, String defaultValue) {
        Object objTemp = list.get(fila)[columna];
        return objTemp == null ? defaultValue : objTemp.toString().trim().toUpperCase();
    }

    public String gsUpB(int fila, String nombreColumna, String defaultValue) {
        Object objTemp = list.get(fila)[_bPC(nombreColumna)];
        return objTemp == null ? defaultValue : objTemp.toString().trim().toUpperCase();
    }

    public String gsLowB(int fila, int columna) {
        Object objTemp = list.get(fila)[columna];
        return objTemp == null ? "" : objTemp.toString().trim().toLowerCase();
    }

    public String gsLowB(int fila, String nombreColumna) {
        Object objTemp = list.get(fila)[_bPC(nombreColumna)];
        return objTemp == null ? "" : objTemp.toString().trim().toLowerCase();
    }

    public String gsLowB(int fila, int columna, String defaultValue) {
        Object objTemp = list.get(fila)[columna];
        return objTemp == null ? defaultValue : objTemp.toString().trim().toLowerCase();
    }

    public String gsLowB(int fila, String nombreColumna, String defaultValue) {
        Object objTemp = list.get(fila)[_bPC(nombreColumna)];
        return objTemp == null ? defaultValue : objTemp.toString().trim().toLowerCase();
    }

    // toNullIf
    public String gsNI(int fila, int columna) {
        Object objTemp = list.get(fila)[columna];
        if (objTemp == null) return null;
        String res = objTemp.toString().trim();
        return res.length() == 0 ? null : res;
    }

    public String gsNI(int fila, String nombreColumna) {
        Object objTemp = list.get(fila)[_bPC(nombreColumna)];
        if (objTemp == null) return null;
        String res = objTemp.toString().trim();
        return res.length() == 0 ? null : res;
    }

    public String gsNUpI(int fila, int columna) {
        Object objTemp = list.get(fila)[columna];
        if (objTemp == null) return null;
        String res = objTemp.toString().trim();
        return res.length() == 0 ? null : res.toUpperCase();
    }

    public String gsNUpI(int fila, String nombreColumna) {
        Object objTemp = list.get(fila)[_bPC(nombreColumna)];
        if (objTemp == null) return null;
        String res = objTemp.toString().trim();
        return res.length() == 0 ? null : res.toUpperCase();
    }

    public String gsNLowI(int fila, int columna) {
        Object objTemp = list.get(fila)[columna];
        if (objTemp == null) return null;
        String res = objTemp.toString().trim();
        return res.length() == 0 ? null : res.toLowerCase();
    }

    public String gsNLowI(int fila, String nombreColumna) {
        Object objTemp = list.get(fila)[_bPC(nombreColumna)];
        if (objTemp == null) return null;
        String res = objTemp.toString().trim();
        return res.length() == 0 ? null : res.toLowerCase();
    }

    public DateTime gdt(int fila, int columna) {
        return JConvertidor.JConvertidor.toDateTime(list.get(fila)[columna]);
    }

    public DateTime gdt(int fila, String nombreColumna) {
        return JConvertidor.JConvertidor.toDateTime(list.get(fila)[_bPC(nombreColumna)]);
    }

    public int gi(int fila, int columna) {
        Object objTemp = list.get(fila)[columna];
        return objTemp == null ? 0 : JConvertidor.JConvertidor.toInt(objTemp);
    }

    public int gi(int fila, String nombreColumna) {
        Object objTemp = list.get(fila)[_bPC(nombreColumna)];
        return objTemp == null ? 0 : JConvertidor.JConvertidor.toInt(objTemp);
    }

    public long gl(int fila, int columna) {
        Object objTemp = list.get(fila)[columna];
        return objTemp == null ? 0L : JConvertidor.JConvertidor.toLong(objTemp);
    }

    public long gl(int fila, String nombreColumna) {
        Object objTemp = list.get(fila)[_bPC(nombreColumna)];
        return objTemp == null ? 0L : JConvertidor.JConvertidor.toLong(objTemp);
    }

    public double gd(int fila, int columna) {
        Object objTemp = list.get(fila)[columna];
        return objTemp == null ? 0.0 : JConvertidor.JConvertidor.toDouble(objTemp);
    }

    public double gdR(int fila, int columna, int nroDecimales) {
        Object objTemp = list.get(fila)[columna];
        return objTemp == null ? 0.0 : JMUtil.round(JConvertidor.JConvertidor.toDouble(objTemp), nroDecimales);
    }

    public String gdRS(int fila, int columna, int nroDecimales) {
        Object objTemp = list.get(fila)[columna];
        return objTemp == null ? "" : JMUtil.roundBD(JConvertidor.JConvertidor.toDouble(objTemp), nroDecimales).toString();
    }

    public double gdR2(int fila, int columna) {
        Object objTemp = list.get(fila)[columna];
        return objTemp == null ? 0.0 : JMUtil.round(JConvertidor.JConvertidor.toDouble(objTemp), 2);
    }

    public double gdR3(int fila, int columna) {
        Object objTemp = list.get(fila)[columna];
        return objTemp == null ? 0.0 : JMUtil.round(JConvertidor.JConvertidor.toDouble(objTemp), 3);
    }

    public double gd(int fila, String nombreColumna) {
        Object objTemp = list.get(fila)[_bPC(nombreColumna)];
        return objTemp == null ? 0.0 : JConvertidor.JConvertidor.toDouble(objTemp);
    }

    public double gdR(int fila, String nombreColumna, int nroDecimales) {
        Object objTemp = list.get(fila)[_bPC(nombreColumna)];
        return objTemp == null ? 0.0 : JMUtil.round(JConvertidor.JConvertidor.toDouble(objTemp), nroDecimales);
    }

    public String gdRS(int fila, String nombreColumna, int nroDecimales) {
        Object objTemp = list.get(fila)[_bPC(nombreColumna)];
        double valTemp = objTemp == null ? 0.0 : JConvertidor.JConvertidor.toDouble(objTemp);
        return JMUtil.roundBD(valTemp, nroDecimales).toString();
    }

    public double gdR2(int fila, String nombreColumna) {
        Object objTemp = list.get(fila)[_bPC(nombreColumna)];
        return objTemp == null ? 0.0 : JMUtil.round(JConvertidor.JConvertidor.toDouble(objTemp), 2);
    }

    public double gdR3(int fila, String nombreColumna) {
        Object objTemp = list.get(fila)[_bPC(nombreColumna)];
        return objTemp == null ? 0.0 : JMUtil.round(JConvertidor.JConvertidor.toDouble(objTemp), 3);
    }

    // OBTENCION Y SETEO DE FILAS: método clásicos
    public void setValorAt(Object valor, int fila, int columna) {
        list.get(fila)[columna] = valor;
        if (indexPos == columna && indexPos >= 0) index.put(gsB(fila, indexPos), fila);
    }

    public void setValorAt(Object valor, int fila, String nombreColumna) {
        int columna = _bPC(nombreColumna);
        list.get(fila)[columna] = valor;
        if (indexPos == columna && indexPos >= 0) index.put(gsB(fila, indexPos), fila);
    }

    public Object getValorAt(int fila, int columna) {
        return list.get(fila)[columna];
    }

    public Object getValorAt(int fila, String nombreColumna) {
        return list.get(fila)[_bPC(nombreColumna)];
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

    @Override
    public String toString() {
        return "nombre = " + getNombreTabla() + ", filas = " + getNroFilas() + ", columnas = " + getNroColumnas() + ", indice = " + getIndexColumnName();
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
