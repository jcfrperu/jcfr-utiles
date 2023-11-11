package com.jcfr.utiles.base.logic;

import com.jcfr.utiles.base.JBaseUtil;
import com.jcfr.utiles.exceptions.JBaseException;
import com.jcfr.utiles.exceptions.JcFRException;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class JBaseManager extends JBaseUtil {

    public void begin(Connection... listaCnx) throws Exception {
        if (listaCnx == null) throw new JcFRException("Método begin(): Argumento listaCnx no puede ser null!");
        for (Connection cnx : listaCnx) {
            if (cnx != null) cnx.setAutoCommit(false);
        }

    }

    public void commit(Connection... listaCnx) throws Exception {
        if (listaCnx == null) throw new JcFRException("Método commit(): Argumento listaCnx no puede ser null!");
        for (Connection cnx : listaCnx) {
            if (cnx != null && !cnx.isClosed() && !cnx.getAutoCommit()) cnx.commit();
        }
    }

    public void handleRelease(Connection cnx) {
        try {
            if (cnx != null && !cnx.isClosed()) cnx.close();
        } catch (SQLException ignored) {
        }
    }

    public void handleRelease(Connection... listaCnx) {
        if (listaCnx == null) throw new JcFRException("Método handleRelease(): Argumento listaCnx no puede ser null!");
        for (Connection cnx : listaCnx) {
            try {
                if (cnx != null && !cnx.isClosed()) cnx.close();
            } catch (SQLException ignored) {
            }
        }
    }

    public JBaseException handleError(Exception sos) {

        if (sos instanceof JBaseException) return (JBaseException) sos;
        if (sos instanceof SQLException) return new JBaseException("JcFR-SQL-JBM-000", sos.getMessage());

        return new JBaseException("JcFR-JBM-000", sos.getMessage());
    }

    public JBaseException handleError(Exception sos, Connection... listaCnx) {

        if (listaCnx == null) throw new JcFRException("Método handleError(): Argumento listaCnx no puede ser null!");

        for (Connection cnx : listaCnx) {
            try {
                if (cnx != null && !cnx.isClosed() && !cnx.getAutoCommit()) cnx.rollback();
            } catch (SQLException ignored) {
            }
        }

        if (sos instanceof JBaseException) return (JBaseException) sos;
        if (sos instanceof SQLException) return new JBaseException("JcFR-SQL-JBM-000", sos.getMessage());

        return new JBaseException("JcFR-JBM-000", sos.getMessage());
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
