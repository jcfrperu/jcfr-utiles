package com.jcfr.utiles;

import java.io.Serializable;

public class Constantes implements Serializable {

    private static final long serialVersionUID = 1917450810311252466L;

    public static final int UNIDAD_KB = 1024; // 1024 BYTES = 1KB
    public static final int UNIDAD_MB = 1024 * UNIDAD_KB; // 1024 KB = 1MB
    public static final int UNIDAD_GB = 1024 * UNIDAD_MB; // 1024 MB = 1GB
    public static final String ENTER = "\r\n";
    public static final String ENTER_LINUX = "\n";
    public static final double CONVERT_MILISEGS_TO_MINUTOS = 1000.00 * 60.00;
    public static final double CONVERT_MILISEGS_TO_HORAS = 60.00 * CONVERT_MILISEGS_TO_MINUTOS;
    public static final double CONVERT_MILISEGS_TO_DIAS = 24.00 * CONVERT_MILISEGS_TO_HORAS;
    public static final double CONVERT_BYTES_TO_KILOBYTES = 1024.00;
    public static final double CONVERT_BYTES_TO_MEGABYTES = 1024.00 * CONVERT_BYTES_TO_KILOBYTES;
    public static final double CONVERT_BYTES_TO_GIGABYTES = 1024.00 * CONVERT_BYTES_TO_MEGABYTES;

    public static final String MSG_CREDITOS = "Autor: JcFR - Juan César Farro Romero - SCJP 6.0, OCEJWCD 5.0 - juanceww82@gmail.com";
}
