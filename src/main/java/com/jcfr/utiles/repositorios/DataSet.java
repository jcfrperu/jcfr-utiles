package com.jcfr.utiles.repositorios;

import com.jcfr.utiles.exceptions.JcFRException;
import com.jcfr.utiles.string.JSUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataSet implements Serializable {

    private static final long serialVersionUID = 5933856787131956533L;

    private static final JSUtil JS = JSUtil.JSUtil;
    private ArrayList<DataTable> tablas;

    public DataSet() {
        tablas = new ArrayList<>(3);
    }

    public void addTabla(DataTable tabla) {
        if (tabla != null) tablas.add(tabla);
    }

    public List<DataTable> getTablas() {
        return tablas;
    }

    public DataTable[] getTablasArray() {
        int size = tablas.size();
        DataTable[] res = new DataTable[size];
        for (int i = 0; i < size; i++) {
            res[i] = tablas.get(i);
        }
        return res;
    }

    public DataTable getTablaAt(int pos) {
        return tablas.get(pos);
    }

    public DataTable getTablaAt(String nombreTabla) {
        for (DataTable dt : tablas) {
            if (JS._equiv(dt.getNombreTabla(), nombreTabla)) return dt;
        }
        throw new JcFRException("Tabla " + nombreTabla + " no existe!");
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
