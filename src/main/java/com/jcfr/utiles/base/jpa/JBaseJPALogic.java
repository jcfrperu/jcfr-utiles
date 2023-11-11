package com.jcfr.utiles.base.jpa;

import com.jcfr.utiles.base.JBaseUtil;
import com.jcfr.utiles.exceptions.JcFRException;

import javax.persistence.EntityManager;
import java.util.*;

public abstract class JBaseJPALogic extends JBaseUtil {

    private EntityManager em;
    private LinkedHashMap<String, EntityManager> emMap;
    private static final String DEFAULT = "default";

    public JBaseJPALogic() {
        emMap = new LinkedHashMap<>(4);
    }

    public void setEntityManager(EntityManager em) {
        setEntityManager(DEFAULT, em);
    }

    public void setEntityManager(String name, EntityManager em) {
        if (name == null) throw new JcFRException("Método setEntityManager(): Argumento name puede ser nulo!");
        if (em == null) throw new JcFRException("Método setEntityManager(): Argumento em no puede ser nulo!");

        String nameID = JS.toBlank(name);
        emMap.put(nameID, em);
        if (nameID.equals(DEFAULT)) this.em = em;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public EntityManager getEntityManager(String name) {
        return emMap.get(JS.toBlank(name));
    }

    public List<EntityManager> getEntityManagerList() {
        ArrayList<EntityManager> lista = new ArrayList<>(emMap.size());

        for (String s : emMap.keySet()) {
            lista.add(emMap.get(s));
        }

        return lista;
    }

    public List<String> getEntityManagerNames() {
        ArrayList<String> lista = new ArrayList<>(emMap.size());

        for (String s : emMap.keySet()) {
            lista.add(s);
        }

        return lista;
    }

    public EntityManager[] getEntityManagerArray() {
        EntityManager[] lista = new EntityManager[emMap.size()];

        Iterator<String> it = emMap.keySet().iterator();

        int pos = 0;
        while (it.hasNext()) {
            lista[pos++] = emMap.get(it.next());
        }

        return lista;
    }

    public Map<String, EntityManager> getEntityManagerMap() {
        return emMap;
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
