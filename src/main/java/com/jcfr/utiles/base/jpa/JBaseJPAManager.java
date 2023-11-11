package com.jcfr.utiles.base.jpa;

import com.jcfr.utiles.base.JBaseUtil;
import com.jcfr.utiles.exceptions.JBaseException;
import com.jcfr.utiles.exceptions.JcFRException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.SQLException;

public abstract class JBaseJPAManager extends JBaseUtil {

    public void begin(EntityManager... listaEM) throws Exception {
        if (listaEM == null) throw new JcFRException("Método begin(): Argumento listaEM no puede ser null!");
        for (EntityManager em : listaEM) {
            if (em != null) {
                EntityTransaction tx = em.getTransaction();
                if (tx != null) tx.begin();
            }
        }
    }

    public void commit(EntityManager... listaEM) throws Exception {
        if (listaEM == null) throw new JcFRException("Método commit(): Argumento listaEM no puede ser null!");
        for (EntityManager em : listaEM) {
            if (em != null) {
                EntityTransaction tx = em.getTransaction();
                if (tx != null && tx.isActive()) tx.commit();
            }
        }
    }

    public void handleRelease(EntityManager em) {
        if (em != null) em.close();
    }

    public void handleRelease(EntityManager... listaEM) {
        if (listaEM == null) throw new JcFRException("Método handleRelease(): Argumento listaEM no puede ser null!");
        for (EntityManager em : listaEM) {
            if (em != null) em.close();
        }
    }

    public JBaseException handleError(Exception sos) {

        if (sos instanceof JBaseException) return (JBaseException) sos;
        if (sos instanceof SQLException) return new JBaseException("JcFR-SQL-JPA-000", sos.getMessage());

        return new JBaseException("JcFR-JBM-JPA-000", sos.getMessage());
    }

    public JBaseException handleError(Exception sos, EntityManager... listaEM) {

        if (listaEM == null) throw new JcFRException("Método handleError(): Argumento listaEM no puede ser null!");

        for (EntityManager em : listaEM) {
            if (em != null) {
                EntityTransaction tx = em.getTransaction();
                if (tx != null && tx.isActive()) tx.rollback();
            }
        }

        if (sos instanceof JBaseException) return (JBaseException) sos;
        if (sos instanceof SQLException) return new JBaseException("JcFR-SQL-JPA-000", sos.getMessage());

        return new JBaseException("JcFR-JBM-JPA-001", sos.getMessage());
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
