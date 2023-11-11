package com.jcfr.utiles.order;

import com.jcfr.utiles.ListaItem;
import com.jcfr.utiles.string.JSUtil;

import java.util.Comparator;

public class ListaItemComparatorByID implements Comparator<ListaItem> {

    private static final JSUtil JS = JSUtil.JSUtil;

    @Override
    public int compare(ListaItem o1, ListaItem o2) {
        if (o1 == null) {
            return o2 == null ? 0 : -1;
        } else {
            return o2 == null ? 1 : JS.toBlank(o1.getId()).compareTo(JS.toBlank(o2.getId()));
        }
    }
}
