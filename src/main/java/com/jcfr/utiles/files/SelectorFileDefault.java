package com.jcfr.utiles.files;

import com.jcfr.utiles.string.JSUtil;

import java.io.File;

import static com.jcfr.utiles.files.SelectorFileDefault.CriterioComoComparar.*;
import static com.jcfr.utiles.files.SelectorFileDefault.CriterioQueBuscar.ARCHIVOS;
import static com.jcfr.utiles.files.SelectorFileDefault.CriterioQueBuscar.CARPETAS;
import static com.jcfr.utiles.files.SelectorFileDefault.CriterioQueComparar.*;

public class SelectorFileDefault implements SelectorFile {

    enum CriterioQueComparar {

        COMPARAR_NOMBRE, COMPARAR_EXTENSION, COMPARAR_NOMBRE_SIN_EXTENSION_NI_PUNTO, COMPARAR_ABSOLUTEPATH, COMPARAR_NOMBRE_CARPETA_PADRE
    }

    enum CriterioComoComparar {

        EQUALS, NOT_EQUALS, STARTS, NOT_STARTS, CONTAINS, NOT_CONTAINS, ENDS, NOT_ENDS
    }

    enum CriterioQueBuscar {

        ARCHIVOS_Y_CARPETAS, ARCHIVOS, CARPETAS
    }

    private static final JSUtil JS = JSUtil.JSUtil;
    private static final JFUtil JF = JFUtil.JFUtil;
    private String filtro;
    private CriterioQueBuscar criterioQueBuscar;
    private CriterioQueComparar criterioQueComparar;
    private CriterioComoComparar criterioComoComparar;
    private boolean filtroCaseSensitive;

    public SelectorFileDefault(CriterioQueBuscar criterioQueBuscar, CriterioQueComparar criterioQueComparar, CriterioComoComparar criterioNombre, String filtro, boolean filtroCaseSensitive) {
        this.criterioQueBuscar = criterioQueBuscar;
        this.criterioQueComparar = criterioQueComparar;
        this.criterioComoComparar = criterioNombre;
        this.filtro = filtroCaseSensitive ? filtro : JS.toLowerBlank(filtro);
        this.filtroCaseSensitive = filtroCaseSensitive;
    }

    @Override
    public boolean incluir(File file) {
        if (file == null) return false;

        if (criterioQueBuscar == ARCHIVOS && !file.isFile()) return false;
        if (criterioQueBuscar == CARPETAS && !file.isDirectory()) return false;

        String valor = null;
        if (criterioQueComparar == COMPARAR_NOMBRE) {
            valor = file.getName();
        } else if (criterioQueComparar == COMPARAR_EXTENSION) {
            valor = JF.getExtension(file.getAbsolutePath());
        } else if (criterioQueComparar == COMPARAR_NOMBRE_SIN_EXTENSION_NI_PUNTO) {
            valor = JF.getNombreSinExtensionNiPunto(file.getAbsolutePath());
        } else if (criterioQueComparar == COMPARAR_NOMBRE_CARPETA_PADRE) {
            valor = JF.getNombre(file.getParent());
        }

        if (filtroCaseSensitive) valor = JS.toLowerBlank(valor);

        if (criterioComoComparar == STARTS) {
            if (valor != null && valor.startsWith(filtro)) return true;
        } else if (criterioComoComparar == CONTAINS) {
            if (valor != null && valor.contains(filtro)) return true;
        } else if (criterioComoComparar == ENDS) {
            if (valor != null && valor.endsWith(filtro)) return true;
        } else if (criterioComoComparar == EQUALS) {
            if (valor != null && valor.equals(filtro)) return true;
        } else if (criterioComoComparar == NOT_STARTS) {
            if (valor != null && !valor.startsWith(filtro)) return true;
        } else if (criterioComoComparar == NOT_CONTAINS) {
            if (valor != null && !valor.contains(filtro)) return true;
        } else if (criterioComoComparar == NOT_ENDS) {
            if (valor != null && !valor.endsWith(filtro)) return true;
        } else if (criterioComoComparar == NOT_EQUALS) {
            if (valor != null && !valor.equals(filtro)) return true;
        }

        return false;
    }
}
