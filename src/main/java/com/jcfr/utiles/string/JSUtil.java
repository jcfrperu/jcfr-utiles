package com.jcfr.utiles.string;

import com.jcfr.utiles.DateTime;
import com.jcfr.utiles.JValidador;
import com.jcfr.utiles.math.Alea;
import com.jcfr.utiles.math.JMUtil;
import com.jcfr.utiles.order.OC;

public class JSUtil extends JValCon {

    public static final JSUtil JSUtil = new JSUtil();

    public JSUtil() {
    }

    private String _REPLICATE_PADR_FASTER(String valorAppend, String valor, int nroVeces) {
        StringBuilder sb = new StringBuilder(valor.length() * nroVeces + valorAppend.length());
        sb.append(valorAppend);
        for (int i = nroVeces; --i >= 0; ) {
            sb.append(valor);
        }
        return sb.toString();
    }

    private String _REPLICATE_PADR_FASTER(String valorAppend, char valor, int nroVeces) {
        StringBuilder sb = new StringBuilder(nroVeces + valorAppend.length());
        sb.append(valorAppend);
        for (int i = nroVeces; --i >= 0; ) {
            sb.append(valor);
        }
        return sb.toString();
    }

    private String _REPLICATE_PADL_FASTER(String valor, int nroVeces, String valorAppend) {
        StringBuilder sb = new StringBuilder(valor.length() * nroVeces + valorAppend.length());
        for (int i = nroVeces; --i >= 0; ) {
            sb.append(valor);
        }
        return sb.append(valorAppend).toString();
    }

    private String _REPLICATE_PADL_FASTER(char valor, int nroVeces, String valorAppend) {
        StringBuilder sb = new StringBuilder(nroVeces + valorAppend.length());
        for (int i = nroVeces; --i >= 0; ) {
            sb.append(valor);
        }
        return sb.append(valorAppend).toString();
    }

    public String addCaracter(String valor, int cadaCuanto, String separador) {
        return addCaracter(valor, cadaCuanto, separador, true);
    }

    public String addCaracter(String valor, int cadaCuanto, String separador, boolean incluirAlFinal) {

        StringBuilder origenSB = new StringBuilder(valor);

        // if ( reverse ) origenSB = origenSB.reverse();
        StringBuilder sb = new StringBuilder(valor.length() + 3);

        int n = origenSB.length();

        for (int i = 0; i < n; i++) {

            if (incluirAlFinal) {

                if ((i + 1) % cadaCuanto == 0) {
                    sb.append(origenSB.charAt(i)).append(separador);
                } else {
                    sb.append(origenSB.charAt(i));
                }

            } else {

                if ((i + 1) % cadaCuanto == 0 && i != n - 1) {
                    sb.append(origenSB.charAt(i)).append(separador);
                } else {
                    sb.append(origenSB.charAt(i));
                }

            }

        }

        // if ( reverse ) sb = sb.reverse();
        return sb.toString();
    }

    public String aLetras(int numero) {
        return new NumeroALetrasConverter().convertirLetras(numero);
    }

    public String sreplace(String fuente, char buscado, char charReemplazo) {
        if (fuente == null) return null;
        StringBuilder sb = new StringBuilder(fuente);

        for (int i = sb.length(); --i >= 0; ) {
            if (sb.charAt(i) == buscado) sb.setCharAt(i, charReemplazo);
        }

        return sb.toString();
    }

    public String sreplace(String fuente, char buscado1, char buscado2, char charReemplazo) {
        if (fuente == null) return null;
        StringBuilder sb = new StringBuilder(fuente);

        char charAt;
        for (int i = sb.length(); --i >= 0; ) {
            charAt = sb.charAt(i);
            if (charAt == buscado1 || charAt == buscado2) sb.setCharAt(i, charReemplazo);
        }

        return sb.toString();
    }

    public String sreplace(String fuente, char buscado1, char buscado2, char buscado3, char charReemplazo) {
        if (fuente == null) return null;
        StringBuilder sb = new StringBuilder(fuente);

        char charAt;
        for (int i = sb.length(); --i >= 0; ) {
            charAt = sb.charAt(i);
            if (charAt == buscado1 || charAt == buscado2 || charAt == buscado3) sb.setCharAt(i, charReemplazo);
        }

        return sb.toString();
    }

    public String sreplace(String fuente, char buscado1, char buscado2, char buscado3, char buscado4, char charReemplazo) {
        if (fuente == null) return null;
        StringBuilder sb = new StringBuilder(fuente);

        char charAt;
        for (int i = sb.length(); --i >= 0; ) {
            charAt = sb.charAt(i);
            if (charAt == buscado1 || charAt == buscado2 || charAt == buscado3 || charAt == buscado4)
                sb.setCharAt(i, charReemplazo);
        }

        return sb.toString();
    }

    public String sreplace(String fuente, char buscado, String reemplazo) {
        if (fuente == null) return null;
        if (reemplazo == null) return fuente;

        int sizeFuente = fuente.length();
        StringBuilder sb = new StringBuilder(sizeFuente + reemplazo.length() * 4);

        char charAt;
        for (int i = 0; i < sizeFuente; i++) {
            charAt = fuente.charAt(i);
            if (charAt == buscado) sb.append(reemplazo);
            else sb.append(charAt);
        }
        return sb.toString();
    }

    public String sreplace(String fuente, char buscado1, char buscado2, String reemplazo) {
        if (fuente == null) return null;
        if (reemplazo == null) return fuente;

        int sizeFuente = fuente.length();
        StringBuilder sb = new StringBuilder(sizeFuente + reemplazo.length() * 8);

        char charAt;
        for (int i = 0; i < sizeFuente; i++) {
            charAt = fuente.charAt(i);
            if (charAt == buscado1 || charAt == buscado2) sb.append(reemplazo);
            else sb.append(charAt);
        }
        return sb.toString();
    }

    public String sreplace(String fuente, char buscado1, char buscado2, char buscado3, String reemplazo) {
        if (fuente == null) return null;
        if (reemplazo == null) return fuente;

        int sizeFuente = fuente.length();
        StringBuilder sb = new StringBuilder(sizeFuente + reemplazo.length() * 12);

        char charAt;
        for (int i = 0; i < sizeFuente; i++) {
            charAt = fuente.charAt(i);
            if (charAt == buscado1 || charAt == buscado2 || charAt == buscado3) sb.append(reemplazo);
            else sb.append(charAt);
        }
        return sb.toString();
    }

    public String sreplace(String fuente, char buscado1, char buscado2, char buscado3, char buscado4, String reemplazo) {
        if (fuente == null) return null;
        if (reemplazo == null) return fuente;

        int sizeFuente = fuente.length();
        StringBuilder sb = new StringBuilder(sizeFuente + reemplazo.length() * 16);

        char charAt;
        for (int i = 0; i < sizeFuente; i++) {
            charAt = fuente.charAt(i);
            if (charAt == buscado1 || charAt == buscado2 || charAt == buscado3 || charAt == buscado4)
                sb.append(reemplazo);
            else sb.append(charAt);
        }
        return sb.toString();
    }

    public String CUT(String fuente, int desde, int nroCaracteres) {

        if (fuente == null) return null;

        int ini = desde - 1;
        int fin = desde + nroCaracteres;

        if (nroCaracteres < 0) {
            ini = desde + nroCaracteres;
            fin = desde + 1;
        }

        if (ini < 0) ini = 0;
        if (fin > fuente.length()) fin = fuente.length() + 1;

        return SUBSTR(fuente, 1, ini) + SUBSTR(fuente, fin, fuente.length() - fin + 1);
    }

    public String replaceTildesEnies(String cadena) {
        if (cadena == null) return null;
        StringBuilder copy = new StringBuilder(cadena);

        char c;
        for (int i = copy.length(); --i >= 0; ) {
            c = copy.charAt(i);

            if (c == 'á') copy.setCharAt(i, 'a');
            else if (c == 'é') copy.setCharAt(i, 'e');
            else if (c == 'Á') copy.setCharAt(i, 'A');
            else if (c == 'É') copy.setCharAt(i, 'E');
            else if (c == 'Ñ') copy.setCharAt(i, 'N');
            else if (c == 'ñ') copy.setCharAt(i, 'n');
            else if (c == 'í') copy.setCharAt(i, 'i');
            else if (c == 'ó') copy.setCharAt(i, 'o');
            else if (c == 'ú') copy.setCharAt(i, 'u');
            else if (c == 'Í') copy.setCharAt(i, 'I');
            else if (c == 'Ó') copy.setCharAt(i, 'O');
            else if (c == 'Ú') copy.setCharAt(i, 'U');
            else if (c == 'Ü') copy.setCharAt(i, 'U');
            else if (c == 'ü') copy.setCharAt(i, 'u');
            else if (c == 'ê') copy.setCharAt(i, 'e');
            else if (c == 'Ê') copy.setCharAt(i, 'E'); // de aca en adelante solo casos raros
            else if (c == 'à') copy.setCharAt(i, 'a');
            else if (c == 'â') copy.setCharAt(i, 'a');
            else if (c == 'ã') copy.setCharAt(i, 'a');
            else if (c == 'ä') copy.setCharAt(i, 'a');
            else if (c == 'å') copy.setCharAt(i, 'a');
            else if (c == 'À') copy.setCharAt(i, 'A');
            else if (c == 'Â') copy.setCharAt(i, 'A');
            else if (c == 'Ã') copy.setCharAt(i, 'A');
            else if (c == 'Ä') copy.setCharAt(i, 'A');
            else if (c == 'Å') copy.setCharAt(i, 'A');
            else if (c == 'è') copy.setCharAt(i, 'e');
            else if (c == 'ê') copy.setCharAt(i, 'e');
            else if (c == 'ë') copy.setCharAt(i, 'e');
            else if (c == 'È') copy.setCharAt(i, 'E');
            else if (c == 'Ë') copy.setCharAt(i, 'E');
            else if (c == 'ì') copy.setCharAt(i, 'i');
            else if (c == 'î') copy.setCharAt(i, 'i');
            else if (c == 'ï') copy.setCharAt(i, 'i');
            else if (c == 'ì') copy.setCharAt(i, 'i');
            else if (c == 'Ì') copy.setCharAt(i, 'I');
            else if (c == 'Î') copy.setCharAt(i, 'I');
            else if (c == 'Ï') copy.setCharAt(i, 'I');
            else if (c == 'ð') copy.setCharAt(i, 'o');
            else if (c == 'ò') copy.setCharAt(i, 'o');
            else if (c == 'ô') copy.setCharAt(i, 'o');
            else if (c == 'õ') copy.setCharAt(i, 'o');
            else if (c == 'ö') copy.setCharAt(i, 'o');
            else if (c == 'Ò') copy.setCharAt(i, 'O');
            else if (c == 'Ô') copy.setCharAt(i, 'O');
            else if (c == 'Õ') copy.setCharAt(i, 'O');
            else if (c == 'Ö') copy.setCharAt(i, 'O');
            else if (c == 'ù') copy.setCharAt(i, 'u');
            else if (c == 'û') copy.setCharAt(i, 'u');
            else if (c == 'Ù') copy.setCharAt(i, 'U');
            else if (c == 'Û') copy.setCharAt(i, 'U');
            else if (c == 'ý') copy.setCharAt(i, 'y');
            else if (c == 'ÿ') copy.setCharAt(i, 'y');
            else if (c == 'Ý') copy.setCharAt(i, 'Y');
        }
        return copy.toString();
    }

    public String handleCaracteresRaros(String cadena) {

        if (cadena == null) return null;

        StringBuilder sb = new StringBuilder(cadena.length());

        char c;
        int cadenaSize = cadena.length();
        for (int i = 0; i < cadenaSize; i++) {

            c = cadena.charAt(i);

            if (Character.isLetterOrDigit(c) || c == ' ') {

                if (c == 'º' || c == 'ª') continue;

                sb.append(c);
            }

        }

        return replaceTildesEnies(sb.toString());
    }

    public boolean tieneCaracteresRaros(String cadena) {

        if (cadena == null) return false;

        char c;
        int cadenaSize = cadena.length();
        for (int i = 0; i < cadenaSize; i++) {

            c = cadena.charAt(i);

            if (!Character.isLetterOrDigit(c) && c != ' ') return true;

        }

        return false;
    }

    public String quitarEspacios(String cadena, int nroEspacios) {
        if (cadena == null) return null;
        if (nroEspacios <= 0) return cadena;
        String s = cadena.trim();
        while (nroEspacios <= 2) {
            s = s.replace(REPLICATE(' ', nroEspacios), " ");
            nroEspacios--;
        }
        return s;
    }

    public String quitarEspacios(String cadena) {
        if (cadena == null) return null;
        return cadena.trim().replace("          ", " ").replace("         ", " ").replace("        ", " ").replace("       ", " ").replace("      ", " ").replace("     ", " ").replace("    ", " ")
                .replace("   ", " ").replace("  ", " ");
    }

    public String newString(int nroCaracteres) {
        return Alea.newStringOnlyLetters(nroCaracteres);
    }

    public String newStringID(String prefijo) {
        return Alea.newStringID(prefijo);
    }

    public String newStringID(String prefijo, int nroCaracteresExtra) {
        return Alea.newStringID(prefijo, nroCaracteresExtra);
    }

    public String newStringMax(int nroCaracteresMax) {
        return Alea.newStringOnlyLetters(Alea.newInt(1, Math.max(1, Math.abs(nroCaracteresMax))));
    }

    public String newStringExtented(int nroCaracteres) {
        return Alea.newStringExtended(nroCaracteres);
    }

    public String newStringMaxExtented(int nroCaracteresMax) {
        return Alea.newStringExtended(Alea.newInt(1, Math.max(1, Math.abs(nroCaracteresMax))));
    }

    public boolean esClaveSegura(String clave) {
        return esClaveSegura(clave, 7);
    }

    public boolean esClaveMuySegura(String clave) {
        return esClaveSegura(clave, 11);
    }

    public boolean esClaveParanoica(String clave) {
        return esClaveSegura(clave, 15);
    }

    public boolean esClaveSegura(String clave, int minCaracteres) {
        String s = toTrim(clave);
        int nroMinCaracteres = Math.max(2, minCaracteres); // DEBE AL MENOS TENER 2 CARACTERES, PA QUE PUEDA TENER LETRAS Y NUMEROS
        if (s.length() < nroMinCaracteres) return false; // DEBE TENER MÍNIMO minCaracteres DÍGITOS
        if (!JValidador.JValidador.tieneLetras(s)) return false; // DEBE TENER LETRAS
        return JValidador.JValidador.tieneNumeros(s);
    }

    public boolean esEmail(String valor) {
        return JValidador.JValidador.esEmail(valor);
    }

    // CLIPPER
    public String STRTRAN(String fuente, String buscado, String reemplazo) {
        return fuente == null ? null : fuente.replace(buscado, reemplazo);
    }

    public String DTOC(DateTime fecha) {
        return fecha == null ? "  /  /    " : fecha.getFechaString();
    }

    public String DTOC(DateTime fecha, String siEsNull) {
        return fecha == null ? siEsNull : fecha.getFechaString();
    }

    public String ALLTRIM(String valor) {
        return valor == null ? "" : valor.trim();
    }

    public String SPACE(int nroVeces) {
        return REPLICATE(' ', nroVeces);
    }

    public String REPLICATE(String valor, int nroVeces) {
        if (nroVeces <= 0) return "";
        StringBuilder s = new StringBuilder(valor.length() * nroVeces);
        for (int i = nroVeces; --i >= 0; ) {
            s.append(valor);
        }
        return s.toString();
    }

    public String REPLICATE(char valor, int nroVeces) {
        if (nroVeces <= 0) return "";
        StringBuilder s = new StringBuilder(nroVeces);
        for (int i = nroVeces; --i >= 0; ) {
            s.append(valor);
        }
        return s.toString();
    }

    public String STR(double valor, int nroDigitos, int nroDecimales) {
        String sround = JMUtil.roundBD(valor, nroDecimales).toString();
        int dif = nroDigitos - sround.length();
        if (dif <= 0) return sround;
        return _REPLICATE_PADL_FASTER(' ', dif, sround);
    }

    public String STR_CEROS(double valor, int nroDigitos, int nroDecimales) {
        if (valor < 0) {
            String sround = JMUtil.roundBD(-valor, nroDecimales).toString();
            int nro = sround.length() + 1; // se lo trata como si tuviera el signo menos, para que toda la logica sea la misma
            if (nro >= nroDigitos) return "-" + sround;
            return "-" + _REPLICATE_PADL_FASTER('0', nroDigitos - nro, sround);
        }
        String sround = JMUtil.roundBD(valor, nroDecimales).toString();
        int dif = nroDigitos - sround.length();
        if (dif <= 0) return sround;
        return _REPLICATE_PADL_FASTER('0', dif, sround);
    }

    public String STR(long valor, int nroDigitos) {
        String svalor = valor + "";
        int dif = nroDigitos - svalor.length();
        if (dif <= 0) return svalor;
        return _REPLICATE_PADL_FASTER(' ', dif, svalor);
    }

    public String STR_CEROS(long valor, int nroDigitos) {
        if (valor < 0) {
            long valorPos = -valor;
            int nro = (valorPos + "").length() + 1; // se lo trata como si tuviera el signo menos, para que toda la logica sea la misma
            if (nro >= nroDigitos) return "-" + valorPos;
            return "-" + _REPLICATE_PADL_FASTER('0', nroDigitos - nro, valorPos + "");
        }
        String svalor = valor + "";
        int dif = nroDigitos - svalor.length();
        if (dif <= 0) return svalor;
        return _REPLICATE_PADL_FASTER('0', dif, svalor);
    }

    public String STR(int valor, int nroDigitos) {
        String svalor = valor + "";
        int dif = nroDigitos - svalor.length();
        if (dif <= 0) return svalor;
        return _REPLICATE_PADL_FASTER(' ', dif, svalor);
    }

    public String STR_CEROS(int valor, int nroDigitos) {
        if (valor < 0) {
            long valorPos = -valor;
            int nro = (valorPos + "").length() + 1; // se lo trata como si tuviera el signo menos, para que toda la logica sea la misma
            if (nro >= nroDigitos) return "-" + valorPos;
            return "-" + _REPLICATE_PADL_FASTER('0', nroDigitos - nro, valorPos + "");
        }
        String svalor = valor + "";
        int dif = nroDigitos - svalor.length();
        if (dif <= 0) return svalor;
        return _REPLICATE_PADL_FASTER('0', dif, svalor);
    }

    public String PADL_NOTRUNK(String valor, int nroDigitos) {
        if (_vacio(valor)) return REPLICATE(' ', nroDigitos);
        String sround = valor.trim();
        int dif = nroDigitos - sround.length();
        if (dif <= 0) return sround;
        return _REPLICATE_PADL_FASTER(' ', dif, sround);
    }

    public String PADL_NOTRUNK(String valor, int nroDigitos, String relleno) {
        if (_vacio(valor)) return REPLICATE(relleno, nroDigitos);
        String sround = valor.trim();
        int dif = nroDigitos - sround.length();
        if (dif <= 0) return sround;
        return _REPLICATE_PADL_FASTER(relleno, dif, sround);
    }

    public String PADL_NOTRUNK(String valor, int nroDigitos, char relleno) {
        if (_vacio(valor)) return REPLICATE(relleno, nroDigitos);
        String sround = valor.trim();
        int dif = nroDigitos - sround.length();
        if (dif <= 0) return sround;
        return _REPLICATE_PADL_FASTER(relleno, dif, sround);
    }

    public String PADL(String valor, int nroDigitos, boolean trunk) {
        return trunk ? PADL(valor, nroDigitos) : PADL_NOTRUNK(valor, nroDigitos);
    }

    public String PADL(String valor, int nroDigitos) {
        if (_vacio(valor)) return REPLICATE(' ', nroDigitos);
        String sround = valor.trim();
        int dif = nroDigitos - sround.length();
        if (dif <= 0) return sround.substring(0, nroDigitos);
        return _REPLICATE_PADL_FASTER(' ', dif, sround);
    }

    public String PADL(String valor, int nroDigitos, char relleno) {
        if (_vacio(valor)) return REPLICATE(relleno, nroDigitos);
        String sround = valor.trim();
        int dif = nroDigitos - sround.length();
        if (dif <= 0) return sround.substring(0, nroDigitos);
        return _REPLICATE_PADL_FASTER(relleno, dif, sround);
    }

    public String PADL(String valor, int nroDigitos, String relleno) {
        if (_vacio(valor)) return REPLICATE(relleno, nroDigitos);
        String sround = valor.trim();
        int dif = nroDigitos - sround.length();
        if (dif <= 0) return sround.substring(0, nroDigitos);
        return _REPLICATE_PADL_FASTER(relleno, dif, sround);
    }

    public String PADL(double valor, int nroDigitos, int nroDecimales) {
        return STR(valor, nroDigitos, nroDecimales);
    }

    public String PADL(long valor, int nroDigitos) {
        return STR(valor, nroDigitos);
    }

    public String PADR(String valor, int nroDigitos, boolean trunk) {
        return trunk ? PADR(valor, nroDigitos) : PADR_NOTRUNK(valor, nroDigitos);
    }

    public String PADR_NOTRUNK(String valor, int nroDigitos) {
        if (_vacio(valor)) return REPLICATE(' ', nroDigitos);
        String sround = valor.trim();
        int dif = nroDigitos - sround.length();
        if (dif <= 0) return sround;
        return _REPLICATE_PADR_FASTER(sround, ' ', dif);
    }

    public String PADR_NOTRUNK(String valor, int nroDigitos, String relleno) {
        if (_vacio(valor)) return REPLICATE(relleno, nroDigitos);
        String sround = valor.trim();
        int dif = nroDigitos - sround.length();
        if (dif <= 0) return sround;
        return _REPLICATE_PADR_FASTER(sround, relleno, dif);
    }

    public String PADR(String valor, int nroDigitos) {
        if (_vacio(valor)) return REPLICATE(' ', nroDigitos);
        String sround = valor.trim();
        int dif = nroDigitos - sround.length();
        if (dif <= 0) return sround.substring(0, nroDigitos);
        return _REPLICATE_PADR_FASTER(sround, ' ', dif);
    }

    public String PADR(String valor, int nroDigitos, char relleno) {
        if (_vacio(valor)) return REPLICATE(relleno, nroDigitos);
        String sround = valor.trim();
        int dif = nroDigitos - sround.length();
        if (dif <= 0) return sround.substring(0, nroDigitos);
        return _REPLICATE_PADR_FASTER(sround, relleno, dif);
    }

    public String PADR(String valor, int nroDigitos, String relleno) {
        if (_vacio(valor)) return REPLICATE(relleno, nroDigitos);
        String sround = valor.trim();
        int dif = nroDigitos - sround.length();
        if (dif <= 0) return sround.substring(0, nroDigitos);
        return _REPLICATE_PADR_FASTER(sround, relleno, dif);
    }

    public String PADR(double valor, int nroDigitos, int nroDecimales) {
        String sround = JMUtil.roundBD(valor, nroDecimales).toString();
        int dif = nroDigitos - sround.length();
        if (dif <= 0) return sround;
        return _REPLICATE_PADR_FASTER(sround, ' ', dif);
    }

    public String PADR(long valor, int nroDigitos) {
        String svalor = valor + "";
        int dif = nroDigitos - svalor.length();
        if (dif <= 0) return svalor;
        return _REPLICATE_PADR_FASTER(svalor, ' ', dif);
    }

    public String PADC(String valor, int nroDigitos) {
        if (_vacio(valor)) return REPLICATE(' ', nroDigitos);
        String s = valor.trim();
        if (s.length() >= nroDigitos) return valor.trim();
        while (!(s.length() >= nroDigitos)) {
            s = " " + s;
            if (s.length() >= nroDigitos) return s;
            s = s + " ";
            if (s.length() >= nroDigitos) return s;
        }
        return REPLICATE(' ', nroDigitos); // este caso no deberia darse
    }

    public String SUBSTR(String valor, int inicio, int nroDigitos) {
        if (valor == null) return REPLICATE(' ', nroDigitos); // solo temporal
        int len = valor.length();
        if (inicio < 0) {
            int lenMasInicio = len + inicio;
            if (inicio + nroDigitos >= 0) return valor.substring(lenMasInicio, len);
            return valor.substring(lenMasInicio, lenMasInicio + nroDigitos);
        } else {
            int inicioMenosUno = inicio - 1;
            if (nroDigitos >= len) return valor.substring(inicioMenosUno);
            return valor.substring(inicioMenosUno, inicioMenosUno + nroDigitos);
        }
    }

    public int COMPARE(Object o1, Object o2) {
        return OC.compare(o1, o2);
    }

    public int COMPARE(Object o1, Object o2, int sentido) {
        return OC.compare(o1, o2, sentido);
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
