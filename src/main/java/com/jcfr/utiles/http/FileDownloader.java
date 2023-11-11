package com.jcfr.utiles.http;

import com.jcfr.utiles.files.JFUtil;
import com.jcfr.utiles.string.JSUtil;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class FileDownloader {

    private static final JSUtil JS = JSUtil.JSUtil;

    private String msgError = "";
    private boolean descargaOK = false;
    private boolean windows = true;
    private int connectTimeOut = HttpUtiles.TIMEOUT_CONNECT_NORMAL;
    private int bufferReadFile = HttpUtiles.BUFFER_READ_FILE_NORMAL;

    // por default usa los user agent de la clase HttpUtiles, pero esta puede estar desactualizada.
    // por eso se creo una personalizada
    private String customUserAgent = "";

    public boolean revisarSiFileExiste(String urlFile) {

        InputStream in = null;
        boolean existeFile;

        try {

            msgError = "";

            URL dir = new URL(JS.toBlank(urlFile));
            URLConnection yc = dir.openConnection();

            yc.addRequestProperty("User-Agent", getUserAgent());
            yc.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            yc.addRequestProperty("Accept-Language", "es-es,es;q=0.8,en-us;q=0.5,en;q=0.3");
            yc.setConnectTimeout(connectTimeOut);

            in = yc.getInputStream();

            existeFile = true;

        } catch (Exception sos) {
            existeFile = false;
            msgError = sos.getMessage();
        } finally {
            JFUtil.closeQuietly(in);
        }

        return existeFile;
    }

    public void descargarFile(String urlFile, String rutaFileOut) {

        InputStream in = null;
        FileOutputStream out = null;

        try {

            descargaOK = false;
            msgError = "";

            if (JS._vacio(rutaFileOut)) throw new Exception("Parámetro rutaFileOut no puede estar vacío");

            URL dir = new URL(JS.toBlank(urlFile));
            URLConnection yc = dir.openConnection();

            yc.addRequestProperty("User-Agent", getUserAgent());
            yc.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            yc.addRequestProperty("Accept-Language", "es-es,es;q=0.8,en-us;q=0.5,en;q=0.3");
            yc.setConnectTimeout(connectTimeOut);

            in = yc.getInputStream();

            int leidos;
            byte[] buffer = new byte[bufferReadFile];

            out = new FileOutputStream(rutaFileOut);
            while ((leidos = in.read(buffer, 0, buffer.length)) > 0) {
                out.write(buffer, 0, leidos);
            }

            descargaOK = true;

        } catch (Exception sos) {
            descargaOK = false;
            msgError = sos.getMessage();
        } finally {
            JFUtil.closeQuietly(in);
            JFUtil.closeQuietly(out);
        }
    }

    private String getUserAgent() {

        if (JS._vacio(customUserAgent)) {
            return  HttpUtiles.getDefaultUserAgent(windows);
        }

        return customUserAgent;
    }

    public void descargarFile(String urlFile, OutputStream out, boolean closeOut) {

        InputStream in = null;

        try {

            descargaOK = false;
            msgError = "";

            if (out == null) throw new Exception("Parámetro out no puede ser null");

            URL dir = new URL(JS.toBlank(urlFile));
            URLConnection yc = dir.openConnection();

            yc.addRequestProperty("User-Agent", getUserAgent());
            yc.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            yc.addRequestProperty("Accept-Language", "es-es,es;q=0.8,en-us;q=0.5,en;q=0.3");
            yc.setConnectTimeout(connectTimeOut);

            in = yc.getInputStream();

            int leidos;
            byte[] buffer = new byte[bufferReadFile];

            while ((leidos = in.read(buffer, 0, buffer.length)) > 0) {
                out.write(buffer, 0, leidos);
            }

            descargaOK = true;

        } catch (Exception sos) {
            descargaOK = false;
            msgError = sos.getMessage();
        } finally {
            JFUtil.closeQuietly(in);
            if (closeOut) {
                JFUtil.closeQuietly(out);
            }
        }
    }

    public byte[] descargarFile(String urlFile) {

        byte[] bytes = null;
        InputStream in = null;

        try {

            descargaOK = false;
            msgError = "";

            URL dir = new URL(JS.toBlank(urlFile));
            URLConnection yc = dir.openConnection();

            yc.addRequestProperty("User-Agent", getUserAgent());
            yc.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            yc.addRequestProperty("Accept-Language", "es-es,es;q=0.8,en-us;q=0.5,en;q=0.3");
            yc.setConnectTimeout(connectTimeOut);

            in = yc.getInputStream();

            bytes = JFUtil.toByteArray(in);

            descargaOK = true;

        } catch (Exception sos) {
            descargaOK = false;
            msgError = sos.getMessage();
        } finally {
            JFUtil.closeQuietly(in);
        }

        return bytes;
    }

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
    
    public String getCustomUserAgent() {
        return customUserAgent;
    }

    public void setCustomUserAgent(String customUserAgent) {
        this.customUserAgent = customUserAgent;
    }
}
