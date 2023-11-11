package com.jcfr.utiles.base.jdo;

import com.jcfr.utiles.base.JBaseUtil;
import com.jcfr.utiles.exceptions.JBaseException;
import com.jcfr.utiles.exceptions.JcFRException;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import java.sql.SQLException;

public abstract class JBaseJDOManager extends JBaseUtil {

    public void begin(PersistenceManager... listaPM) throws Exception {
        if (listaPM == null) throw new JcFRException("Método begin(): Argumento listaPM no puede ser null!");

        Transaction tx;
        for (PersistenceManager pm : listaPM) {
            if (pm != null) {
                tx = pm.currentTransaction();
                if (tx != null) tx.begin();
            }
        }

    }

    public void commit(PersistenceManager... listaPM) throws Exception {
        if (listaPM == null) throw new JcFRException("Método commit(): Argumento listaPM no puede ser null!");

        Transaction tx;
        for (PersistenceManager pm : listaPM) {
            if (pm != null) {
                tx = pm.currentTransaction();
                if (tx != null && tx.isActive()) tx.commit();
            }
        }

    }

    public void handleRelease(PersistenceManager pm) {
        if (pm != null && !pm.isClosed()) pm.close();
    }

    public void handleRelease(PersistenceManager... listaPM) {
        if (listaPM == null) throw new JcFRException("Método handleRelease(): Argumento listaPM no puede ser null!");
        for (PersistenceManager pm : listaPM) {
            if (pm != null && !pm.isClosed()) pm.close();
        }
    }

    public JBaseException handleError(Exception sos) {

        if (sos instanceof JBaseException) return (JBaseException) sos;
        if (sos instanceof SQLException) return new JBaseException("JcFR-SQL-JDO-000", sos.getMessage());

        return new JBaseException("JcFR-JBM-JDO-000", sos.getMessage());
    }

    public JBaseException handleError(Exception sos, PersistenceManager... listaPM) {

        if (listaPM == null) throw new JcFRException("Método handleError(): Argumento listaPM no puede ser null!");

        Transaction tx;
        for (PersistenceManager pm : listaPM) {
            if (pm != null) {
                tx = pm.currentTransaction();
                if (tx != null && tx.isActive()) tx.rollback();
            }
        }

        if (sos instanceof JBaseException) return (JBaseException) sos;
        if (sos instanceof SQLException) return new JBaseException("JcFR-SQL-JDO-001", sos.getMessage());

        return new JBaseException("JcFR-JBM-JDO-001", sos.getMessage());
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
