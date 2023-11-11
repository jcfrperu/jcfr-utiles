package com.jcfr.utiles.security;

import com.jcfr.utiles.string.JSUtil;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleDecoder {

    private static final JSUtil JS = JSUtil.JSUtil;

    private SimpleDecoder() {
        // sealed
    }

    public static Map<Integer, Object> decodificar(String secretKey, String inputBase64) throws Exception {

        ByteArrayInputStream bais = null;
        DataInputStream dis = null;

        try {

            if (JS._vacio(inputBase64)) throw new IllegalArgumentException("inputBase64 no puede ser null ni vacío");

            // valores a retornar la decodificar
            Map<Integer, Object> valores = new LinkedHashMap<>();

            Map<String, Integer> posiciones = initDecoder(secretKey, valores);

            bais = new ByteArrayInputStream(Base64.decodeBase64(inputBase64));
            dis = new DataInputStream(bais);

            int pos = 0;

            // recorrer toda la trama
            while (pos < posiciones.get("posMaximo")) {

                // bloque de numeros
                if (pos <= posiciones.get("posBloque")) {

                    if (pos % 2 == 0) {

                        // manejo de longs
                        if (valores.containsKey(pos)) {
                            valores.put(pos, String.valueOf(leerDataLong(dis, pos)));
                        } else {
                            dis.readLong();
                        }

                    } else {

                        // manejo de ints
                        if (valores.containsKey(pos)) {
                            valores.put(pos, String.valueOf(leerDataInt(dis, pos)));
                        } else {
                            dis.readInt();
                        }
                    }

                    pos++;

                } else {

                    // bloque de letras
                    if (valores.containsKey(pos)) {

                        int posInicio = pos;

                        int sizeCadena;
                        if (pos % 2 == 0) {
                            sizeCadena = (int) leerDataLong(dis, pos);
                        } else {
                            sizeCadena = leerDataInt(dis, pos);
                        }
                        pos++;

                        StringBuilder sb = new StringBuilder(sizeCadena + 10);

                        for (int i = 0; i < sizeCadena; i++) {

                            int intAt;

                            if (pos % 2 == 0) {
                                intAt = (int) leerDataLong(dis, pos);
                            } else {
                                intAt = leerDataInt(dis, pos);
                            }

                            sb.append((char) intAt);

                            pos++;
                        }

                        valores.put(posInicio, sb.toString());

                    } else {

                        if (pos % 2 == 0) {
                            dis.readLong();
                        } else {
                            dis.readInt();
                        }

                        pos++;
                    }
                }

            }

            return valores;

        } finally {

            closeQuietly(bais);
            closeQuietly(dis);
        }

    }

    public static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }

    private static Map<String, Integer> initDecoder(String password, Map<Integer, Object> valores) {

        // este método lee el password, inicializa el mapa de valores y retorna
        // los separadores de bloque y final

        if (JS._vacio(password)) throw new IllegalArgumentException("password no puede ser null ni vacío");

        String[] posiciones = password.split("-");

        if (posiciones.length < 3) throw new IllegalArgumentException("password inválido");

        int posBloque = 0;
        int posMaximo = 0;

        for (int i = 0; i < posiciones.length; i++) {

            if (!JS._numeroEntero(posiciones[i])) throw new IllegalArgumentException("password inválido");

            if (i == posiciones.length - 1) {

                // si es el ultimo
                posMaximo = JS.toInt(posiciones[i]);

            } else if (i == posiciones.length - 2) {

                // si es el penultimo
                posBloque = JS.toInt(posiciones[i]);

            } else {

                // todos los demas
                valores.put(JS.toInt(posiciones[i]), null);
            }
        }

        if (posMaximo <= posBloque) throw new IllegalArgumentException("password inválido");

        Map<String, Integer> result = new HashMap<>();

        result.put("posBloque", posBloque);
        result.put("posMaximo", posMaximo);

        return result;
    }

    private static int leerDataInt(DataInputStream dis, int posicion) throws Exception {

        return (dis.readInt() - posicion) / 13;
    }

    private static long leerDataLong(DataInputStream dis, int posicion) throws Exception {

        return (dis.readLong() - posicion) / 3;
    }

}