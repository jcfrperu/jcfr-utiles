package com.jcfr.utiles.base.logic;

import com.jcfr.utiles.base.JBaseUtil;
import com.jcfr.utiles.exceptions.JcFRException;

import java.sql.Connection;
import java.util.*;

public abstract class JBaseLogic extends JBaseUtil {

    private Connection cnx;
    private LinkedHashMap<String, Connection> cnxMap;
    private static final String DEFAULT = "default";

    public JBaseLogic() {
        cnxMap = new LinkedHashMap<>(4);
    }

    public void setConexion(Connection cnx) {
        setConexion(DEFAULT, cnx);
    }

    public void setConexion(String name, Connection cnx) {
        if (name == null) throw new JcFRException("Método setConexion(): Argumento name puede ser nulo!");
        if (cnx == null) throw new JcFRException("Método setConexion(): Argumento cnx no puede ser nulo!");

        String nameID = JS.toBlank(name);
        cnxMap.put(nameID, cnx);
        if (nameID.equals(DEFAULT)) this.cnx = cnx;
    }

    public Connection getConexion() {
        return cnx;
    }

    public Connection getConexion(String name) {
        return cnxMap.get(JS.toBlank(name));
    }

    public List<Connection> getConexiones() {
        ArrayList<Connection> lista = new ArrayList<>(cnxMap.size());

        Iterator<String> it = cnxMap.keySet().iterator();

        while (it.hasNext()) {
            lista.add(cnxMap.get(it.next()));
        }

        return lista;
    }

    public List<String> getConexionesNames() {
        ArrayList<String> lista = new ArrayList<>(cnxMap.size());

        Iterator<String> it = cnxMap.keySet().iterator();

        while (it.hasNext()) {
            lista.add(it.next());
        }

        return lista;
    }

    public Connection[] getConexionesToArray() {
        Connection[] lista = new Connection[cnxMap.size()];

        Iterator<String> it = cnxMap.keySet().iterator();

        int pos = 0;
        while (it.hasNext()) {
            lista[pos++] = cnxMap.get(it.next());
        }

        return lista;
    }

    public Map<String, Connection> getConexionesMap() {
        return cnxMap;
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
