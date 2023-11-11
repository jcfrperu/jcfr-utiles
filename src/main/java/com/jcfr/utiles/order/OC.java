package com.jcfr.utiles.order;

public class OC {

    private OC() {
    }

    // CUALQUIER ITEM DISTINTO DE NULL SIEMPRE ES MAYOR QUE UN ITEM NULL, LO QUE PROVOCA QUE LOS NULLS VAYAN ARRIBA EN LA TABLA, Y LOS NOT NULLS DEBAJO
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static int compare(Object o1, Object o2) {
        if (o1 == null) return o2 == null ? 0 : -1; // CASO CONTRARIO: -1 -> 1
        else if (o2 == null) return 1; // CASO CONTRARIO: 1 -> -1
        return ((Comparable) o1).compareTo(o2);
    }

    public static int compare(Object o1, Object o2, int sentido) {
        return sentido < 0 ? compare(o2, o1) : compare(o1, o2);
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
