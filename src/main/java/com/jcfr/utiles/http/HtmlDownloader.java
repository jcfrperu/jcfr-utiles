package com.jcfr.utiles.http;

import com.jcfr.utiles.files.JFUtil;
import com.jcfr.utiles.math.Alea;
import com.jcfr.utiles.string.JSUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HtmlDownloader {

    private static final JSUtil JS = JSUtil.JSUtil;

    public static final String METADATA_DECODE_UTF8 = "URL_UTF8";
    public static final String METADATA_DECODE_BASE64 = "URL_BASE64";
    public static final String METADATA_DECODE_DEFAULT = METADATA_DECODE_UTF8;

    private String msgError = "";
    private boolean descargaOK = false;
    private boolean windows = true;
    private int connectTimeOut = HttpUtiles.TIMEOUT_CONNECT_NORMAL;
    private int bufferReadFile = HttpUtiles.BUFFER_READ_FILE_NORMAL;

    // para codificar la respuesta
    private Charset charSet = null;
    // para enviar el post
    private String contentType = null;

    private boolean incluyeSaltos = true;
    private String contenido = "";

    // por default usa los user agent de la clase HttpUtiles, pero esta puede estar desactualizada.
    // por eso se creo una personalizada
    private String customUserAgent = "";

    // DO POST
    public String doPost(String url, Map<String, String> params) {

        post(url, params);

        if (!isDescargaOK()) {
            HttpUtiles.esperar(100, 200);
            post(url, params);
        }

        if (!isDescargaOK()) {
            HttpUtiles.esperar(200, 1000);
            post(url, params);
        }

        if (!isDescargaOK()) {
            HttpUtiles.esperar(1000, 2000);
            post(url, params);
        }

        return contenido;
    }

    private String getUserAgent() {
        
        if (JS._vacio(customUserAgent)) {
            return  HttpUtiles.getDefaultUserAgent(windows);
        }
        
        return customUserAgent;
    }
    
    private void post(String urlToPost, Map<String, String> paramsToPost) {

        BufferedReader in = null;

        try {

            msgError = null;
            contenido = null;
            descargaOK = false;

            URL urlDir = new URL(JS.toBlank(urlToPost));
            URLConnection urlConn = urlDir.openConnection();

            // avoid caching
            urlConn.setUseCaches(false);

            // true indicates the server returns response
            urlConn.setDoInput(true);

            // true indicates POST request
            urlConn.setDoOutput(true);

            // setear headers
            urlConn.addRequestProperty("User-Agent", getUserAgent());
            urlConn.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            urlConn.addRequestProperty("Accept-Language", "es-es,es;q=0.8,en-us;q=0.5,en;q=0.3");
            urlConn.setConnectTimeout(connectTimeOut);

            // setear header Content-Type
            if (!JS._vacio(contentType)) {
                urlConn.addRequestProperty("Content-Type", contentType);
            }

            Map<String, String> params = paramsToPost;
            if (params == null) params = new HashMap<>();

            // agregar un parametro random para evitar caching
            params.put(Alea.newStringOnlyLetters(6), Alea.newStringOnlyLetters(6));

            // trabajar el output
            if (params.size() > 0) {

                StringBuilder sbParams = new StringBuilder(params.size() * 100 + 50);

                String key;
                String value;

                // creates the params string. cada parametro debe venir
                // codificado
                Iterator<String> paramIterator = params.keySet().iterator();
                while (paramIterator.hasNext()) {

                    key = paramIterator.next();
                    value = params.get(key);

                    sbParams.append(key);
                    sbParams.append("=").append(HttpUtiles.codeURL(value));
                    sbParams.append("&");
                }

                // sends POST data
                OutputStreamWriter writer = new OutputStreamWriter(urlConn.getOutputStream());
                writer.write(sbParams.toString());
                writer.flush();
            }

            // trabajar el input
            if (charSet == null) {
                in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), charSet));
            }

            String linea;
            String enter = HttpUtiles.getDefaultEnter(windows);
            StringBuilder sb = new StringBuilder(bufferReadFile);

            if (incluyeSaltos) {
                while ((linea = in.readLine()) != null) {
                    sb.append(linea).append(" ").append(enter);
                }
            } else {
                while ((linea = in.readLine()) != null) {
                    sb.append(linea).append(" ");
                }
            }

            descargaOK = true;
            contenido = JS.toBlank(sb.toString());

        } catch (Exception sos) {
            descargaOK = false;
            contenido = null;
            msgError = sos.getMessage();
        } finally {
            JFUtil.closeQuietly(in);
        }
    }

    // DO GET
    public String doGet(String url) {

        get(url);

        if (!isDescargaOK()) {
            HttpUtiles.esperar(100, 200);
            get(url);
        }

        if (!isDescargaOK()) {
            HttpUtiles.esperar(200, 1000);
            get(url);
        }

        if (!isDescargaOK()) {
            HttpUtiles.esperar(1000, 2000);
            get(url);
        }

        return contenido;
    }

    public String doGet(String url, Map<String, String> parametros) {

        String singleURL = parseToSingleURL(url, parametros);

        get(singleURL);

        if (!isDescargaOK()) {
            HttpUtiles.esperar(100, 200);
            get(singleURL);
        }

        if (!isDescargaOK()) {
            HttpUtiles.esperar(200, 1000);
            get(singleURL);
        }

        if (!isDescargaOK()) {
            HttpUtiles.esperar(1000, 2000);
            get(singleURL);
        }

        return contenido;
    }

    private void get(String urlParam) {

        BufferedReader in = null;

        try {

            msgError = null;
            contenido = null;
            descargaOK = false;

            URL urlDir = new URL(urlParam);
            URLConnection urlConn = urlDir.openConnection();

            // setear headers
            urlConn.addRequestProperty("User-Agent", getUserAgent());
            urlConn.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            urlConn.addRequestProperty("Accept-Language", "es-es,es;q=0.8,en-us;q=0.5,en;q=0.3");
            urlConn.setConnectTimeout(connectTimeOut);

            // setear header Content-Type
            if (!JS._vacio(contentType)) {
                urlConn.addRequestProperty("Content-Type", contentType);
            }

            if (charSet == null) {
                in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), charSet));
            }

            String linea;
            String enter = HttpUtiles.getDefaultEnter(windows);
            StringBuilder sb = new StringBuilder(bufferReadFile);

            if (incluyeSaltos) {
                while ((linea = in.readLine()) != null) {
                    sb.append(linea).append(" ").append(enter);
                }
            } else {
                while ((linea = in.readLine()) != null) {
                    sb.append(linea).append(" ");
                }
            }

            descargaOK = true;
            contenido = JS.toBlank(sb.toString());

        } catch (Exception sos) {
            descargaOK = false;
            contenido = null;
            msgError = sos.getMessage();
        } finally {
            JFUtil.closeQuietly(in);
        }
    }

    private String parseToSingleURL(String url, Map<String, String> params) {

        if (url == null) throw new IllegalArgumentException("param url no puede ser null");
        if (params == null || params.isEmpty()) return url;

        StringBuilder sb = new StringBuilder(url.length() + 25 * params.size());

        sb.append(url);
        sb.append("?");

        String next;
        Iterator<String> it = params.keySet().iterator();
        while (it.hasNext()) {
            next = it.next();

            sb.append(next).append("=");
            try {
                sb.append(HttpUtiles.codeURL(params.get(next)));
            } catch (UnsupportedEncodingException ignored) {
            }
            sb.append("&");
        }

        String singleURL = sb.toString();
        if (singleURL.endsWith("&")) {
            singleURL = singleURL.substring(0, singleURL.length() - 1);
        }

        return singleURL;
    }

    public String procesarHTML(boolean quitarCutes, boolean replaceTildesEnies, boolean procesarTagsHtml) {
        return HttpUtiles.procesarHTML(contenido, quitarCutes, replaceTildesEnies, procesarTagsHtml);
    }

    // UTILES
    public String getMetadata(String metatag) throws Exception {
        return getMetadata(metatag, METADATA_DECODE_DEFAULT);
    }

    public String getMetadata(String metatag, String tipoDecode) throws Exception {

        if (contenido == null) return null;

        // estos patrones no generan problemas de codificacion (metatag es un string simple)
        int posIni = contenido.indexOf("**metadata-" + metatag + "-begin**");
        int posFin = contenido.indexOf("**metadata-" + metatag + "-end**");

        String metadata;
        if (posIni >= 0 && posFin >= 0 && posFin >= posIni) {

            metadata = contenido.substring(posIni, posFin);
            metadata = metadata.replace("**metadata-" + metatag + "-begin**", "");
            metadata = JS.toBlank(metadata);

            if (JS._equiv(tipoDecode, METADATA_DECODE_UTF8)) {
                metadata = HttpUtiles.decodeURL(metadata);
            }

            if (JS._equiv(tipoDecode, METADATA_DECODE_BASE64)) {
                metadata = HttpUtiles.decode64(metadata);
            }

            metadata = JS.toBlank(metadata);

            return metadata;
        }

        return null;
    }

    // ACCESADORES
    public String getMsgError() {
        return msgError;
    }

    public boolean isDescargaOK() {
        return descargaOK;
    }

    public boolean isWindows() {
        return windows;
    }

    public void setWindows(boolean windows) {
        this.windows = windows;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = Math.max(HttpUtiles.TIMEOUT_CONNECT_MIN, connectTimeOut);
    }

    public int getBufferReadFile() {
        return bufferReadFile;
    }

    public void setBufferReadFile(int bufferReadFile) {
        this.bufferReadFile = Math.max(HttpUtiles.BUFFER_READ_FILE_MIN, bufferReadFile);
    }

    public Charset getCharSet() {
        return charSet;
    }

    public void setCharSet(Charset charSet) {
        this.charSet = charSet;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCustomUserAgent() {
        return customUserAgent;
    }

    public void setCustomUserAgent(String customUserAgent) {
        this.customUserAgent = customUserAgent;
    }
}
