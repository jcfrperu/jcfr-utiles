package com.jcfr.utiles.base.jdo;

import com.jcfr.utiles.base.JBaseUtil;
import com.jcfr.utiles.exceptions.JcFRException;

import javax.jdo.PersistenceManager;
import java.util.*;

public abstract class JBaseJDOLogic extends JBaseUtil {

    private PersistenceManager em;
    private LinkedHashMap<String, PersistenceManager> emMap;
    private static final String DEFAULT = "default";

    public JBaseJDOLogic() {
        emMap = new LinkedHashMap<>(4);
    }

    public void setPersistenceManager(PersistenceManager em) {
        setPersistenceManager(DEFAULT, em);
    }

    public void setPersistenceManager(String name, PersistenceManager em) {
        if (name == null) throw new JcFRException("Método setPersistenceManager(): Argumento name puede ser nulo!");
        if (em == null) throw new JcFRException("Método setPersistenceManager(): Argumento em no puede ser nulo!");

        String nameID = JS.toBlank(name);
        emMap.put(nameID, em);
        if (nameID.equals(DEFAULT)) this.em = em;
    }

    public PersistenceManager getPersistenceManager() {
        return em;
    }

    public PersistenceManager getPersistenceManager(String name) {
        return emMap.get(JS.toBlank(name));
    }

    public List<String> getPersistenceManagerNames() {
        ArrayList<String> lista = new ArrayList<>(emMap.size());

        for (String s : emMap.keySet()) {
            lista.add(s);
        }

        return lista;
    }

    public List<PersistenceManager> getPersistenceManagerList() {
        ArrayList<PersistenceManager> lista = new ArrayList<>(emMap.size());

        for (String s : emMap.keySet()) {
            lista.add(emMap.get(s));
        }

        return lista;
    }

    public PersistenceManager[] getPersistenceManagerArray() {
        PersistenceManager[] lista = new PersistenceManager[emMap.size()];

        Iterator<String> it = emMap.keySet().iterator();
        int pos = 0;
        while (it.hasNext()) {
            lista[pos++] = emMap.get(it.next());
        }

        return lista;
    }

    public Map<String, PersistenceManager> getPersistenceManagerMap() {
        return emMap;
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
