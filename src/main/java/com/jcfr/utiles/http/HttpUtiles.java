package com.jcfr.utiles.http;

import com.jcfr.utiles.Constantes;
import com.jcfr.utiles.math.Alea;
import com.jcfr.utiles.string.JSUtil;
import com.jcfr.utiles.web.StringWeb;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class HttpUtiles {

    private static final JSUtil JS = JSUtil.JSUtil;

    // constantes
    public static final int UNIDAD_KB = Constantes.UNIDAD_KB;
    public static final int UNIDAD_MB = Constantes.UNIDAD_MB;
    public static final int UNIDAD_GB = Constantes.UNIDAD_GB;

    public static final String ENTER = Constantes.ENTER;
    public static final String ENTER_LINUX = Constantes.ENTER_LINUX;

    // constantes propias del HTTP
    public static final int TIMEOUT_CONNECT_MIN = 1000;
    public static final int TIMEOUT_CONNECT_NORMAL = 10 * TIMEOUT_CONNECT_MIN;
    public static final int TIMEOUT_CONNECT_MAX = 20 * TIMEOUT_CONNECT_MIN;

    public static final int BUFFER_READ_FILE_MIN = 16 * UNIDAD_KB;
    public static final int BUFFER_READ_FILE_NORMAL = 256 * UNIDAD_KB;

    public static final int BUFFER_READ_PHOTO_MIN = 8 * UNIDAD_KB;
    public static final int BUFFER_READ_PHOTO_NORMAL = 128 * UNIDAD_KB;

    public static final String USER_AGENT_LINUX = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:61.0) Gecko/20100101 Firefox/61.0";
    public static final String USER_AGENT_WINDOWS = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0";

    // constantes charset string
    public static final String CHARSET_UTF8_STRING = "UTF-8";
    public static final String CHARSET_ISO_8859_1_STRING = "ISO-8859-1";

    // constantes charset
    public static final Charset CHARSET_UTF8 = Charset.forName(CHARSET_UTF8_STRING);
    public static final Charset CHARSET_ISO_8859_1 = Charset.forName(CHARSET_ISO_8859_1_STRING);

    // constantes content type
    public static final String CONTENT_TYPE_UTF8 = "text/html;charset=UTF-8";
    public static final String CONTENT_TYPE_ISO_8859_1 = "text/html;charset=ISO-8859-1";

    public static String getDefaultUserAgent(boolean isWindows) {
        return isWindows ? USER_AGENT_WINDOWS : USER_AGENT_WINDOWS;
    }

    public static String getDefaultEnter(boolean isWindows) {
        return isWindows ? ENTER : ENTER_LINUX;
    }

    public static void esperar(int ini, int fin) {
        try {
            Thread.sleep(Alea.newInt(Math.min(ini, fin), Math.max(ini, fin)));
        } catch (InterruptedException ignored) {
        }
    }

    public static String codeURL(String word) throws UnsupportedEncodingException {
        return URLEncoder.encode(word, CHARSET_UTF8_STRING);
    }

    public static String decodeURL(String word) throws UnsupportedEncodingException {
        return URLDecoder.decode(word, CHARSET_UTF8_STRING);
    }

    public static String code64(String word) {
        // return Base64.encode( word.getBytes() );
        return DatatypeConverter.printBase64Binary(word.getBytes());
    }

    public static String decode64(String word) {
        // return new String( Base64.decode( word ) );
        return new String(DatatypeConverter.parseBase64Binary(word));
    }

    public static String procesarHTML(String contenidoHTML, boolean quitarCutes, boolean replaceTildesEnies,
                                      boolean procesarTagsHtml) {

        String res = contenidoHTML;

        if (quitarCutes) {
            res = StringWeb.fromCute(res);
        }

        if (replaceTildesEnies) {
            res = JS.replaceTildesEnies(res);
        }

        if (procesarTagsHtml) {
            res = res.replace("<b>", "");
            res = res.replace("</b>", "");
            res = res.replace("<b/>", "");

            res = res.replace("<strong>", "");
            res = res.replace("</strong>", "");
            res = res.replace("<strong/>", "");
        }

        return res;
    }
}
