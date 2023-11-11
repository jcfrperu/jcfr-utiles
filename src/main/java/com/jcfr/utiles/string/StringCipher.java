package com.jcfr.utiles.string;

public class StringCipher {

    // IDEAS:
    // PARA TENER UN ALFABETO MANEJABLE. TODO EL STRING/BYTES DE ENTRADA, PASARLO A BASE64 (INVESTIGAR SI ATRACA BYTES)
    // EL METODO DE CIFRADO REQUIERE QUE ESTE EN BASE64
    // UTILIZAR 10 PRIMERAS POSICIONES COMO TABLERO CONTROL, Y LAS ULTIMAS 10 COMO UN RANDOM
    // [tablero-control][data][random]
    // en los caracteres de tablero de control se tienen los caracteres que van a determinar el cifrado de data
    // opcionalmente: en este tablero de control, se tiene que segmento de data es tambien random -> [tablero-control][data1][random][data2][random]
    // TABLERO CONTROL:
    // SE TENDRAN 2 DIGITOS NUMERICOS UBICADOS EN CUALQUIER POSICION DENTRO DE LOS 10 DIGITOS DEL TABLERO, PERO SIEMPRE EN UNA ORDEN DE SECUENCIA
    // T1 = UN NUMERO DE 0,9 Y ES LO QUE VA SUMAR PARA TRANSFORMAR LA POSICION PAR
    // T2 = UN NUMERO DE 0,9 Y ES LO QUE VA SUMAR A LA POSICION IMPAR

    // private static JSUtil JS = JSUtil.JSUtil;
    // private static final StringBuilder MINUSCULAS = new StringBuilder( "aeioubcdtrm " );
    // private static final StringBuilder MAYUSCULAS = new StringBuilder( "" );
    // private static final StringBuilder LETRAS = new StringBuilder( MINUSCULAS.length() + MAYUSCULAS.length() ).append( MINUSCULAS ).append( MAYUSCULAS );
    // private static final StringBuilder NUMEROS = new StringBuilder( "" );
    // private static final StringBuilder LETRAS_NUMEROS = new StringBuilder( LETRAS.length() + NUMEROS.length() ).append( LETRAS ).append( NUMEROS );
    // private static final StringBuilder EXTENDIDO = new StringBuilder( "" );
    // private static final StringBuilder ALFABET = new StringBuilder( LETRAS_NUMEROS.length() + EXTENDIDO.length() ).append( LETRAS_NUMEROS ).append( EXTENDIDO );
    // private static final StringBuilder CIFRADO = new StringBuilder( LETRAS_NUMEROS.length() + EXTENDIDO.length() ).append( LETRAS_NUMEROS ).append( EXTENDIDO );

    // private String code;
    // private HashMap<Character, Character> cifrado = new HashMap<Character, Character>( ALFABET.length() );
    //
    // public StringCipher( String code ) {
    // this.code = JS.toBlank( code );
    // }

    // private void makeMapaCifrado() {
    // if ( code.equals( "none" ) || code.equals( "" ) ) return;
    // if ( code.startsWith( "base01" ) ) {
    // // generar programaticamente una disposicion determinista del alfabeto. puede que requiera ciertos parametros para el metodo
    // return;
    // }
    // if ( code.startsWith( "base02" ) ) {
    // // generar programaticamente una disposicion determinista del alfabeto. puede que requiera ciertos parametros para el metodo
    // return;
    // }
    // if ( code.startsWith( "base03" ) ) {
    // // generar programaticamente una disposicion determinista del alfabeto. puede que requiera ciertos parametros para el metodo
    // return;
    // }
    //
    // int fin = ALFABET.length() - 1;
    // for ( int i = fin; --i >= 0; ) {
    // cifrado.put( ALFABET.charAt( i ), ALFABET.charAt( i ) );
    // }
    //
    // String[] trozo = code.split( "-" );
    // int pos01 = 0;
    // int pos02 = 0;
    // for ( String t : trozo ) {
    // if ( t == null ) continue;
    // if ( t.length() != 4 ) continue;
    // pos01 = JS.toInt( t.charAt( 0 ) + "" + t.charAt( 1 ) );
    // pos02 = JS.toInt( t.charAt( 2 ) + "" + t.charAt( 3 ) );
    // }
    //
    // }
}
