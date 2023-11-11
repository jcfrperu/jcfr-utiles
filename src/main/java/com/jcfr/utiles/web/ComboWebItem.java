package com.jcfr.utiles.web;

import com.jcfr.utiles.string.JSUtil;

import java.io.Serializable;

public class ComboWebItem implements Serializable {

    private static final long serialVersionUID = 4090351475253642329L;

    private static final JSUtil JS = JSUtil.JSUtil;

    private String id;  // campo valor
    private String label; // campo descripcion
    private String extra; // campo para guardar info adicional tal vez en formato JSON
    private boolean enabled;

    public ComboWebItem() {
        this("", "", "", true);
    }

    public ComboWebItem(String id, String label) {
        this(id, label, "", true);
    }

    public ComboWebItem(String id, String label, boolean enabled) {
        this(id, label, "", enabled);
    }

    public ComboWebItem(String id, String label, String extra, boolean enabled) {
        this.id = id == null ? "" : id.trim();
        this.label = label == null ? "" : label.trim();
        this.extra = extra == null ? "" : extra.trim();
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof ComboWebItem) {
            ComboWebItem objCast = (ComboWebItem) obj;
            return JS._equiv(objCast.getId(), this.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 61 * hash + (this.label != null ? this.label.hashCode() : 0);
        hash = 61 * hash + (this.extra != null ? this.extra.hashCode() : 0);
        hash = 61 * hash + (this.enabled ? 1 : 0);
        return hash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "id=" + id + ", label=" + label + ", enabeld=" + enabled;
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
