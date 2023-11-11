package com.jcfr.utiles.selector;

class OperadorParser {

    public static int parse(String operador) {

        if (operador == null) return -1;

        String op = operador.trim().toLowerCase();

        // COMUNES: OC.compare()
        if (op.equals("=") || op.equals("==")) return 1;
        if (op.equals(">")) return 2;
        if (op.equals(">=")) return 3;
        if (op.equals("<")) return 4;
        if (op.equals("<=")) return 5;
        if (op.equals("!=") || op.equals("<>")) return 6;
        if (op.equals("is")) return 7;
        if (op.equals("is not")) return 8;

        // // ESPECIALES CON STRING
        // if ( op.equals( "equiv" ) ) return 9;
        // if ( op.equals( "equiv nocase" ) ) return 9;
        // if ( op.equals( "like" ) ) return 9;
        // if ( op.equals( "starts" ) ) return 10;
        // if ( op.equals( "ends" ) ) return 11;
        // if ( op.equals( "like nocase" ) || op.equals( "ilike" ) ) return 12;

        return -1;
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
