package com.jcfr.utiles.files;

import com.jcfr.utiles.Constantes;
import com.jcfr.utiles.DateTime;
import com.jcfr.utiles.exceptions.JcFRException;
import com.jcfr.utiles.string.JSUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class JFUtil {

    public static final JFUtil JFUtil = new JFUtil();
    private static final JSUtil JS = JSUtil.JSUtil;
    private static final int BUFFER_COPY = 64 * Constantes.UNIDAD_KB;
    private static final int BUFFER_ZIP = 2 * BUFFER_COPY;

    public JFUtil() {
    }

    // CREAR BYTEARRAYCOPIAS
    public static InputStream crearCopia(InputStream input, int size) throws IOException {
        return new ByteArrayInputStream(toByteArray(input, size));
    }

    public static InputStream crearCopia(InputStream input) throws IOException {
        return new ByteArrayInputStream(toByteArray(input));
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        if (input == null) throw new IOException("InputStream no puede ser null");

        if (input.markSupported()) input.mark(0);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_COPY];
        int len;
        while ((len = input.read(buffer)) > 0) {
            output.write(buffer, 0, len);
        }

        if (input.markSupported()) input.reset();

        return output.toByteArray();
    }

    public static byte[] toByteArray(InputStream input, int size) throws IOException {
        if (input == null || size <= 0) throw new IOException("InputStream no puede ser null");
        if (size <= 0) throw new IOException("Size debe ser un número positivo");

        if (input.markSupported()) input.mark(0);

        byte[] data = new byte[size];
        input.read(data, 0, data.length);

        if (input.markSupported()) input.reset();

        return data;
    }

    // LISTAR ARCHIVOS
    public List<String> listarArchivos(String carpetaOrigen, int tamAprox) {

        checkCarpetaNoVacioExiste(carpetaOrigen, "Carpeta Origen");

        ArrayList<String> lista = new ArrayList<>(Math.max(16, tamAprox));

        searchFile(new File(carpetaOrigen), lista, new SelectorFile() {
            @Override
            public boolean incluir(File file) {
                return file != null && file.isFile();
            }
        });

        return lista;
    }

    public List<String> listarArchivosEquals(String carpetaOrigen, String nombre, int tamAprox) {

        checkNoNulo(nombre, "Nombre");
        checkNoVacio(nombre, "Nombre");
        checkCarpetaNoVacioExiste(carpetaOrigen, "Carpeta Origen");

        ArrayList<String> lista = new ArrayList<>(Math.max(16, tamAprox));

        searchFile(new File(carpetaOrigen), lista, new SelectorFileDefault(SelectorFileDefault.CriterioQueBuscar.ARCHIVOS, SelectorFileDefault.CriterioQueComparar.COMPARAR_NOMBRE,
                SelectorFileDefault.CriterioComoComparar.EQUALS, nombre, true));

        return lista;
    }

    public List<String> listarArchivosStarts(String carpetaOrigen, String nombre, int tamAprox) {

        checkNoNulo(nombre, "Nombre");
        checkNoVacio(nombre, "Nombre");
        checkCarpetaNoVacioExiste(carpetaOrigen, "Carpeta Origen");

        ArrayList<String> lista = new ArrayList<>(Math.max(16, tamAprox));

        searchFile(new File(carpetaOrigen), lista, new SelectorFileDefault(SelectorFileDefault.CriterioQueBuscar.ARCHIVOS, SelectorFileDefault.CriterioQueComparar.COMPARAR_NOMBRE,
                SelectorFileDefault.CriterioComoComparar.STARTS, nombre, true));

        return lista;
    }

    public List<String> listarArchivosContains(String carpetaOrigen, String nombre, int tamAprox) {

        checkNoNulo(nombre, "Nombre");
        // checkNoVacio( nombre, "Nombre" );
        checkCarpetaNoVacioExiste(carpetaOrigen, "Carpeta Origen");

        ArrayList<String> lista = new ArrayList<>(Math.max(16, tamAprox));

        searchFile(new File(carpetaOrigen), lista, new SelectorFileDefault(SelectorFileDefault.CriterioQueBuscar.ARCHIVOS, SelectorFileDefault.CriterioQueComparar.COMPARAR_NOMBRE,
                SelectorFileDefault.CriterioComoComparar.CONTAINS, nombre, true));

        return lista;
    }

    public List<String> listarArchivosEnds(String carpetaOrigen, String nombre, int tamAprox) {

        checkNoNulo(nombre, "Nombre");
        checkNoVacio(nombre, "Nombre");
        checkCarpetaNoVacioExiste(carpetaOrigen, "Carpeta Origen");

        ArrayList<String> lista = new ArrayList<>(Math.max(16, tamAprox));

        searchFile(new File(carpetaOrigen), lista, new SelectorFileDefault(SelectorFileDefault.CriterioQueBuscar.ARCHIVOS, SelectorFileDefault.CriterioQueComparar.COMPARAR_NOMBRE,
                SelectorFileDefault.CriterioComoComparar.ENDS, nombre, true));

        return lista;
    }

    public List<String> listarCarpetas(String carpetaOrigen, int tamAprox) {

        checkCarpetaNoVacioExiste(carpetaOrigen, "Carpeta Origen");

        ArrayList<String> lista = new ArrayList<>(Math.max(16, tamAprox));

        searchFile(new File(carpetaOrigen), lista, new SelectorFile() {
            @Override
            public boolean incluir(File file) {
                return file != null && file.isDirectory();
            }
        });

        return lista;
    }

    public List<String> listarCarpetasEquals(String carpetaOrigen, String nombre, int tamAprox) {

        checkNoNulo(nombre, "Nombre");
        checkNoVacio(nombre, "Nombre");
        checkCarpetaNoVacioExiste(carpetaOrigen, "Carpeta Origen");

        ArrayList<String> lista = new ArrayList<>(Math.max(16, tamAprox));

        searchFile(new File(carpetaOrigen), lista, new SelectorFileDefault(SelectorFileDefault.CriterioQueBuscar.CARPETAS, SelectorFileDefault.CriterioQueComparar.COMPARAR_NOMBRE,
                SelectorFileDefault.CriterioComoComparar.EQUALS, nombre, true));

        return lista;
    }

    public List<String> listarCarpetasStarts(String carpetaOrigen, String nombre, int tamAprox) {

        checkNoNulo(nombre, "Nombre");
        checkNoVacio(nombre, "Nombre");
        checkCarpetaNoVacioExiste(carpetaOrigen, "Carpeta Origen");

        ArrayList<String> lista = new ArrayList<>(Math.max(16, tamAprox));

        searchFile(new File(carpetaOrigen), lista, new SelectorFileDefault(SelectorFileDefault.CriterioQueBuscar.CARPETAS, SelectorFileDefault.CriterioQueComparar.COMPARAR_NOMBRE,
                SelectorFileDefault.CriterioComoComparar.STARTS, nombre, true));

        return lista;
    }

    public List<String> listarCarpetasContains(String carpetaOrigen, String nombre, int tamAprox) {

        checkNoNulo(nombre, "Nombre");
        // checkNoVacio( nombre, "Nombre" );
        checkCarpetaNoVacioExiste(carpetaOrigen, "Carpeta Origen");

        ArrayList<String> lista = new ArrayList<>(Math.max(16, tamAprox));

        searchFile(new File(carpetaOrigen), lista, new SelectorFileDefault(SelectorFileDefault.CriterioQueBuscar.CARPETAS, SelectorFileDefault.CriterioQueComparar.COMPARAR_NOMBRE,
                SelectorFileDefault.CriterioComoComparar.CONTAINS, nombre, true));

        return lista;
    }

    public List<String> listarCarpetasEnds(String carpetaOrigen, String nombre, int tamAprox) {

        checkNoNulo(nombre, "Nombre");
        checkNoVacio(nombre, "Nombre");
        checkCarpetaNoVacioExiste(carpetaOrigen, "Carpeta Origen");

        ArrayList<String> lista = new ArrayList<>(Math.max(16, tamAprox));

        searchFile(new File(carpetaOrigen), lista, new SelectorFileDefault(SelectorFileDefault.CriterioQueBuscar.CARPETAS, SelectorFileDefault.CriterioQueComparar.COMPARAR_NOMBRE,
                SelectorFileDefault.CriterioComoComparar.ENDS, nombre, true));

        return lista;
    }

    public List<String> listarArchivosYCarpetas(String carpetaOrigen, int tamAprox) {

        checkCarpetaNoVacioExiste(carpetaOrigen, "Carpeta Origen");

        ArrayList<String> lista = new ArrayList<>(Math.max(16, tamAprox));

        searchFile(new File(carpetaOrigen), lista, new SelectorFile() {
            @Override
            public boolean incluir(File file) {
                return file != null && (file.isFile() || file.isDirectory());
            }
        });

        return lista;
    }

    public List<String> listarArchivosYCarpetasEquals(String carpetaOrigen, String nombre, int tamAprox) {

        checkNoNulo(nombre, "Nombre");
        checkNoVacio(nombre, "Nombre");
        checkCarpetaNoVacioExiste(carpetaOrigen, "Carpeta Origen");

        ArrayList<String> lista = new ArrayList<>(Math.max(16, tamAprox));

        searchFile(new File(carpetaOrigen), lista, new SelectorFileDefault(SelectorFileDefault.CriterioQueBuscar.ARCHIVOS_Y_CARPETAS, SelectorFileDefault.CriterioQueComparar.COMPARAR_NOMBRE,
                SelectorFileDefault.CriterioComoComparar.EQUALS, nombre, true));

        return lista;
    }

    public List<String> listarArchivosYCarpetasStarts(String carpetaOrigen, String nombre, int tamAprox) {

        checkNoNulo(nombre, "Nombre");
        checkNoVacio(nombre, "Nombre");
        checkCarpetaNoVacioExiste(carpetaOrigen, "Carpeta Origen");

        ArrayList<String> lista = new ArrayList<>(Math.max(16, tamAprox));

        searchFile(new File(carpetaOrigen), lista, new SelectorFileDefault(SelectorFileDefault.CriterioQueBuscar.ARCHIVOS_Y_CARPETAS, SelectorFileDefault.CriterioQueComparar.COMPARAR_NOMBRE,
                SelectorFileDefault.CriterioComoComparar.STARTS, nombre, true));

        return lista;
    }

    public List<String> listarArchivosYCarpetasContains(String carpetaOrigen, String nombre, int tamAprox) {

        checkNoNulo(nombre, "Nombre");
        checkNoVacio(nombre, "Nombre");
        checkCarpetaNoVacioExiste(carpetaOrigen, "Carpeta Origen");

        ArrayList<String> lista = new ArrayList<>(Math.max(16, tamAprox));

        searchFile(new File(carpetaOrigen), lista, new SelectorFileDefault(SelectorFileDefault.CriterioQueBuscar.ARCHIVOS_Y_CARPETAS, SelectorFileDefault.CriterioQueComparar.COMPARAR_NOMBRE,
                SelectorFileDefault.CriterioComoComparar.CONTAINS, nombre, true));

        return lista;
    }

    public List<String> listarArchivosYCarpetasEnds(String carpetaOrigen, String nombre, int tamAprox) {

        checkNoNulo(nombre, "Nombre");
        checkNoVacio(nombre, "Nombre");
        checkCarpetaNoVacioExiste(carpetaOrigen, "Carpeta Origen");

        ArrayList<String> lista = new ArrayList<>(Math.max(16, tamAprox));

        searchFile(new File(carpetaOrigen), lista, new SelectorFileDefault(SelectorFileDefault.CriterioQueBuscar.ARCHIVOS_Y_CARPETAS, SelectorFileDefault.CriterioQueComparar.COMPARAR_NOMBRE,
                SelectorFileDefault.CriterioComoComparar.ENDS, nombre, true));

        return lista;
    }

    public List<String> listar(String carpetaOrigen, SelectorFile select, int tamAprox) {

        checkCarpetaNoVacioExiste(carpetaOrigen, "Carpeta Origen");

        ArrayList<String> lista = new ArrayList<>(Math.max(16, tamAprox));
        searchFile(new File(carpetaOrigen), lista, select);

        return lista;
    }

    private void searchFile(File path, ArrayList<String> lista, SelectorFile select) {
        if (path.exists()) {
            if (select.incluir(path)) lista.add(path.getAbsolutePath());
            if (path.isDirectory()) {
                File[] files = path.listFiles();
                if (files != null) {
                    for (File f : files) {
                        if (f.isDirectory()) searchFile(f, lista, select);
                        else if (select.incluir(f)) lista.add(f.getAbsolutePath());
                    }
                }
            }
        }
    }

    // TRATAMIENTO EN BYTES DE ARCHIVOS
    public byte[] leerArchivoBytes(String archivo) throws Exception {

        checkNoVacio(archivo, "Archivo");
        checkArchivo(archivo, "Archivo");

        InputStream in = null;
        ByteArrayOutputStream out = null;

        try {

            checkExiste(archivo, "Archivo");

            in = new FileInputStream(new File(archivo));
            out = new ByteArrayOutputStream(16 * BUFFER_COPY);

            byte[] buf = new byte[BUFFER_COPY];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.flush();

            return out.toByteArray();

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            closeQuietly(out);
            closeQuietly(in);
        }
    }

    public void crearArchivoBytes(String archivo, byte[] bytes) throws Exception {
        FileOutputStream out = null;
        try {

            out = new FileOutputStream(archivo);
            out.write(bytes, 0, bytes.length);
            out.flush();

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            closeQuietly(out);
        }

    }
    // CREAR ARCHIVOS DE TEXTO DE FORMA GENERICA

    public List<String> leerArchivoText(String archivo) throws Exception {
        return leerArchivoText(archivo, 16);
    }

    public String leerArchivoTextAsString(String archivo) throws Exception {
        return leerArchivoTextAsString(archivo, Constantes.ENTER_LINUX);
    }

    public String leerArchivoTextAsString(String archivo, String defaultEnter) throws Exception {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(archivo));

            StringBuilder lineas = new StringBuilder(10 * Constantes.UNIDAD_KB);

            String linea;
            while ((linea = reader.readLine()) != null) {
                lineas.append(linea).append(defaultEnter);
            }

            return lineas.toString();

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            closeQuietly(reader);
        }

    }

    public List<String> leerArchivoText(String archivo, int nroLineas) throws Exception {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(archivo));

            ArrayList<String> lineas = new ArrayList<>(Math.max(16, nroLineas));

            String linea;
            while ((linea = reader.readLine()) != null) {
                lineas.add(linea);
            }

            return lineas;

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            closeQuietly(reader);
        }

    }

    public void crearArchivoText(String archivo, List<String> lineas, String separador) throws Exception {

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(archivo);

            if (separador == null) {
                for (String item : lineas) {
                    writer.println(item);
                }
            } else {
                for (String item : lineas) {
                    writer.print(item + separador);
                }
            }

            writer.flush();

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            closeQuietly(writer);
        }

    }

    // BORRAR ARCHIVOS
    public void borrarFile(String file) {
        checkCarpetaNoVacioExiste(file, "File");
        deleteFile(new File(file));
    }

    private void deleteFile(File path) {
        if (path.exists()) {
            if (path.isDirectory()) {
                File[] files = path.listFiles();
                if (files != null) {
                    for (File f : files) {
                        if (f.isDirectory()) deleteFile(f);
                        else f.delete();
                    }
                }
            }
            path.delete();
        }
    }

    // CARPETAS
    public String crearCarpetaEn(String fileOrigen, String nombreNuevaCarpeta) {

        // file o carpeta origen
        checkNoVacioExiste(fileOrigen, "File Origen");
        checkNoVacio(nombreNuevaCarpeta, "Nombre Carpeta");

        if (nombreNuevaCarpeta.contains("/")) throw new JcFRException("Nombre Carpeta no debe contener el caracter /");
        if (nombreNuevaCarpeta.contains("\\"))
            throw new JcFRException("Nombre Carpeta no debe contener el caracter \\");

        File file = new File(fileOrigen);
        String rutaDir = file.isDirectory() ? file.getAbsolutePath() + "/" + nombreNuevaCarpeta : file.getParent() + "/" + nombreNuevaCarpeta;

        File dir = new File(rutaDir);
        boolean creado = true;
        if (!dir.exists()) creado = dir.mkdir();

        return creado ? dir.getAbsolutePath() : "";
    }

    // BORRAR ARCHIVO EN CARPETA
    // COPIAR ARCHIVO - ARCHIVO
    public void copiarArchivo(String archivoOrigen, String archivoDestino) throws Exception {
        copiarArchivoInterno(archivoOrigen, archivoDestino, false, true);
    }

    public void copiarArchivo(String archivoOrigen, String archivoDestino, boolean reemplazar) throws Exception {
        copiarArchivoInterno(archivoOrigen, archivoDestino, false, reemplazar);
    }

    public void copiarArchivoWait(String archivoOrigen, String archivoDestino) throws Exception {
        copiarArchivoInterno(archivoOrigen, archivoDestino, true, true);
    }

    public void copiarArchivoWait(String archivoOrigen, String archivoDestino, boolean reemplazar) throws Exception {
        copiarArchivoInterno(archivoOrigen, archivoDestino, true, reemplazar);
    }

    private void copiarArchivoInterno(String archivoOrigen, String archivoDestino, boolean espera, boolean reemplazar) throws Exception {

        checkNoVacio(archivoOrigen, "Archivo Origen");
        checkArchivo(archivoOrigen, "Archivo Origen");

        checkNoVacio(archivoDestino, "Archivo Destino");

        if (!espera) checkExiste(archivoOrigen, "Archivo Origen");

        if (!reemplazar && existe(archivoDestino)) return;

        InputStream in = null;
        OutputStream out = null;

        try {

            if (espera) {
                try {
                    int nroConteos = 5;
                    while (!existe(archivoOrigen) && nroConteos > 0) {
                        nroConteos--;
                        Thread.sleep(200);
                    }
                } catch (InterruptedException ignored) {
                }
            }

            checkExiste(archivoOrigen, "Archivo Origen");

            in = new FileInputStream(new File(archivoOrigen));
            out = new FileOutputStream(new File(archivoDestino));

            byte[] buf = new byte[BUFFER_COPY];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            closeQuietly(out);
            closeQuietly(in);
        }
    }

    // COPIAR ARCHIVO - DIRECTORIO
    // COPIAR DIRECTORIO - DIRECTORIO
    public void zipearArchivo(String archivoOrigen) throws Exception {
        String nombreArchivoZip = archivoOrigen + ".zip"; // inicialmente
        if (tieneExtension(archivoOrigen)) {
            if (!getExtension(archivoOrigen).equals("zip")) {
                nombreArchivoZip = getRutaExtensionReplaced(archivoOrigen, "zip");
            }
        }
        zipearArchivo(archivoOrigen, nombreArchivoZip);
    }

    public void zipearArchivo(String archivoOrigen, String archivoZip) throws Exception {

        checkArchivoNoVacioExiste(archivoOrigen, "Archivo Origen");
        checkNoVacio(archivoZip, "Archivo Zip");

        if (archivoZip.equals(archivoOrigen))
            throw new JcFRException("Archivo Zip no puede tener el mismo nombre que el Archivo Origen.");

        FileInputStream flujoEntrada = null;
        ZipOutputStream zipOut = null;

        try {
            flujoEntrada = new FileInputStream(archivoOrigen);

            zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(archivoZip)));
            byte[] data = new byte[BUFFER_ZIP];

            ZipEntry entry = new ZipEntry(getNombre(archivoOrigen));
            entry.setTime(DateTime.getNow().toLong());
            zipOut.putNextEntry(entry);
            int count;
            while ((count = flujoEntrada.read(data, 0, BUFFER_ZIP)) != -1) {
                zipOut.write(data, 0, count);
            }

        } catch (Exception sos) {
            throw new Exception(sos.getMessage());
        } finally {
            closeQuietly(flujoEntrada);
            closeQuietly(zipOut);
        }

    }

    public void zipearArchivosDeLaCarpeta(String carpetaOrigen) throws Exception {
        String rutaCarpetaOrigen = getRuta(carpetaOrigen); // ruta absoluta de la carpeta (le quita el /)
        zipearArchivosDeLaCarpeta(rutaCarpetaOrigen, rutaCarpetaOrigen + "/" + getNombre(rutaCarpetaOrigen) + ".zip");
    }

    public void zipearArchivosDeLaCarpeta(String carpetaOrigen, String archivoZip) throws Exception {

        checkCarpetaNoVacioExiste(carpetaOrigen, "Carpeta Origen");

        List<String> listaArchivos = new ArrayList<>();

        File[] files = new File(carpetaOrigen).listFiles();

        if (files != null) {
            for (File file : files) {
                if (file != null && file.isFile()) {
                    listaArchivos.add(file.getAbsolutePath());
                }
            }
        }

        zipearListaArchivos(listaArchivos, archivoZip);
    }

    public void zipearListaArchivos(List<String> listaArchivos, String archivoZip) throws Exception {

        if (JS._vacio(listaArchivos)) return;

        checkNoVacio(archivoZip, "Archivo Zip");

        FileInputStream flujoEntrada = null;

        ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(archivoZip)));
        byte[] data = new byte[BUFFER_ZIP];
        int count;

        for (String itemArchivo : listaArchivos) {
            if (esArchivo(itemArchivo)) {

                try {

                    if (getRuta(itemArchivo).equals(archivoZip))
                        continue; // no debe comprimir un archivo con el mismo nombre

                    flujoEntrada = new FileInputStream(itemArchivo);

                    ZipEntry entry = new ZipEntry(getNombre(itemArchivo));
                    entry.setTime(DateTime.getNow().toLong());
                    zipOut.putNextEntry(entry);
                    while ((count = flujoEntrada.read(data, 0, BUFFER_ZIP)) != -1) {
                        zipOut.write(data, 0, count);
                    }

                } catch (Exception sos) {
                    throw new Exception(sos.getMessage());
                } finally {
                    closeQuietly(flujoEntrada);
                    // TODO: deberia cerrarse aqui tambien el zipOut
                }
            }
        }

        closeQuietly(zipOut);
    }

    // VER ESTADOS
    public boolean existe(String rutaFile) {
        return !JS._vacio(rutaFile) && new File(rutaFile).exists();
    }

    public boolean esArchivo(String rutaFile) {
        return !JS._vacio(rutaFile) && new File(rutaFile).isFile();
    }

    public boolean esDirectorio(String rutaFile) {
        return !JS._vacio(rutaFile) && new File(rutaFile).isDirectory();
    }

    // RECUPERAR ATRIBUTOS
    public boolean tieneExtension(String archivo) {
        String nombre = getNombre(archivo);
        if (JS._vacio(nombre)) return false;
        int pos = nombre.lastIndexOf('.');
        return pos >= 0 && pos != nombre.length() - 1;
    }

    public String getRutaExtensionReplaced(String archivo, String nuevaExtension) {
        if (!tieneExtension(archivo)) return getRuta(archivo);
        String nombre = getNombre(archivo);
        int pos = nombre.lastIndexOf('.');
        return getRuta(getCarpeta(archivo) + "/" + nombre.substring(0, pos + 1) + nuevaExtension);
    }

    public String getExtension(String archivo) {
        String nombre = getNombre(archivo);
        if (JS._vacio(nombre)) return "";
        int pos = nombre.lastIndexOf('.');
        if (pos < 0) return "";
        return nombre.substring(pos + 1, nombre.length());
    }

    public String getExtensionConPunto(String archivo) {
        return "." + getExtension(archivo);
    }

    public String getNombre(String archivo) {
        return new File(archivo).getName();
    }

    public String getNombreSinExtension(String archivo) {
        // OPTIMIZAR
        if (!tieneExtension(archivo)) return getNombre(archivo);
        String nombre = getNombre(archivo);
        if (JS._vacio(nombre)) return "";
        int pos = nombre.lastIndexOf('.');
        if (pos < 0) return "";
        return nombre.substring(0, pos + 1);
    }

    public String getNombreSinExtensionNiPunto(String archivo) {
        // OPTIMIZAR
        if (!tieneExtension(archivo)) return getNombre(archivo);
        String nombre = getNombre(archivo);
        if (JS._vacio(nombre)) return "";
        int pos = nombre.lastIndexOf('.');
        if (pos < 0) return "";
        return nombre.substring(0, pos);
    }

    public String getRuta(String archivo) {
        // ruta absoluta de la carpeta (le quita el /)
        return new File(archivo).getAbsolutePath();
    }

    public String getCarpeta(String archivo) {
        return new File(archivo).getParent();
    }

    // CHECKEOS COMUNES
    private void checkNoNulo(String valor, String label) throws JcFRException {
        if (valor == null) throw new JcFRException(label + " no puede ser null!");
    }

    private void checkNoVacio(String valor, String label) throws JcFRException {
        if (JS._vacio(valor)) throw new JcFRException(label + " no puede estar vacío!");
    }

    private void checkExiste(String valor, String label) throws JcFRException {
        if (!new File(valor).exists()) throw new JcFRException(label + " no existe!");
    }

    private void checkArchivo(String valor, String label) throws JcFRException {
        if (!new File(valor).isFile()) throw new JcFRException(label + " debe ser un archivo!");
    }

    private void checkCarpeta(String valor, String label) throws JcFRException {
        checkNoVacio(valor, label);
        if (!new File(valor).isDirectory()) throw new JcFRException(label + " debe ser una carpeta!");
    }

    private void checkNoVacioExiste(String valor, String label) throws JcFRException {
        checkNoVacio(valor, label);
        checkExiste(valor, label);
    }

    private void checkArchivoNoVacioExiste(String valor, String label) throws JcFRException {
        checkNoVacio(valor, label);
        checkExiste(valor, label);
        checkArchivo(valor, label);
    }

    private void checkCarpetaNoVacioExiste(String valor, String label) throws JcFRException {
        checkNoVacio(valor, label);
        checkExiste(valor, label);
        checkCarpeta(valor, label);
    }

    public static void closeQuietly(InputStream in) {
        try {
            if (in != null) in.close();
        } catch (IOException ignored) {
        }
    }

    public static void closeQuietly(OutputStream out) {
        try {
            if (out != null) out.close();
        } catch (IOException ignored) {
        }
    }

    public static void closeQuietly(Reader reader) {
        try {
            if (reader != null) reader.close();
        } catch (IOException ignored) {
        }
    }

    public static void closeQuietly(Writer writer) {
        try {
            if (writer != null) writer.close();
        } catch (IOException ignored) {
        }
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }

}
