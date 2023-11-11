package com.jcfr.utiles;

import com.jcfr.utiles.web.ComboWebItem;

public class ListaItem extends ComboWebItem {

    private static final long serialVersionUID = -829582023050342104L;

    public ListaItem() {
        super();
    }

    public ListaItem(String id, String label) {
        super(id, label);
    }

    public ListaItem(String id, String label, boolean enabled) {
        super(id, label, enabled);
    }

    public ListaItem(String id, String label, String extra, boolean enabled) {
        super(id, label, extra, enabled);
    }

}
