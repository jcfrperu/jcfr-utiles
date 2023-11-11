package com.jcfr.utiles.security;

import com.jcfr.utiles.DateTime;
import com.jcfr.utiles.math.Alea;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class SimpleCoder {

    // estas posiciones son por distribucion: PAR (Long), IMPAR (Int)
    private int posBloque;
    private int posMaximo;

    private Random rand;
    private Map<Integer, Object> valores;

    public SimpleCoder(int posMaxBloqueNumeros, int posMaxBloqueCaracteres) {

        if (!(posMaxBloqueCaracteres > posMaxBloqueNumeros))
            throw new IllegalArgumentException("argumento posMaxBloqueCaracteres debe ser mayor que posMaxBloqueNumeros");

        this.posBloque = posMaxBloqueNumeros;
        this.posMaximo = posMaxBloqueCaracteres;

        this.rand = new Random();
        this.valores = new LinkedHashMap<>();
    }

    public void addLongInfo(int posicion, long valor) {

        if (!(posicion % 2 == 0)) throw new IllegalArgumentException("posicion debe ser par");
        if (!(posicion <= posBloque))
            throw new IllegalArgumentException("posicion debe ser menor o igual a " + posBloque);

        valores.put(posicion, valor);
    }

    public void addIntegerInfo(int posicion, int valor) {

        if (posicion % 2 == 0) throw new IllegalArgumentException("posicion debe ser impar");
        if (!(posicion <= posBloque))
            throw new IllegalArgumentException("posicion debe ser menor o igual a " + posBloque);

        valores.put(posicion, valor);
    }

    public void addStringInfo(int posicion, String valor) {

        if (valor == null || valor.isEmpty()) throw new IllegalArgumentException("valor no puede ser null ni vacio");

        if (!(posicion > posBloque)) throw new IllegalArgumentException("posicion debe ser mayor a " + posBloque);

        // al maximo permitido, se le resta el doble del tamaño del size y un
        // tamaño de 10 para ofuscar al final
        int maximoRealPermitido = posMaximo - 2 * valor.length() - 10;
        if (!(posicion <= maximoRealPermitido))
            throw new IllegalArgumentException("posicion debe ser menor o igual a " + maximoRealPermitido);

        valores.put(posicion, valor);
    }

    public Result codificar() throws Exception {

        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;

        Result result = new Result();

        try {

            // validaciones previas
            if (valores == null || valores.isEmpty()) {
                throw new Exception("Debe ingresar al menos un elemento para cifrar");
            }

            // cadena que permitira desofuscar
            StringBuilder secret = new StringBuilder("");

            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);

            int pos = 0;

            // recorrer toda la trama
            while (pos < posMaximo) {

                // bloque de numeros
                if (pos <= posBloque) {

                    if (pos % 2 == 0) {

                        // manejo de longs
                        if (valores.get(pos) == null) {

                            // si no esta en la tabla de valores se randomiza
                            dos.writeLong(genAleatorioLong());

                        } else {

                            secret.append(pos).append("-");

                            // escribir data con el patron ofuscado
                            escribirDataLong(dos, (Long) valores.get(pos), pos);
                        }

                    } else {

                        // manejo de ints
                        if (valores.get(pos) == null) {

                            // si no esta en la tabla de valores se randomiza
                            dos.writeInt(genAleatorioInt());

                        } else {

                            secret.append(pos).append("-");

                            // escribir data con el patron ofuscado
                            escribirDataInt(dos, (Integer) valores.get(pos), pos);
                        }

                    }

                    pos++;

                } else {

                    // manejo de string
                    if (valores.get(pos) == null) {

                        // si no esta en la tabla de valores se randomiza
                        if (pos % 2 == 0) {
                            dos.writeLong(genAleatorioLong());
                        } else {
                            dos.writeInt(genAleatorioInt());
                        }

                        pos++;

                    } else {

                        secret.append(pos).append("-");

                        String stringValue = (String) valores.get(pos);

                        // escribir el nro de caracteres de la cadena que se
                        // quiere ocultar
                        if (pos % 2 == 0) {

                            escribirDataLong(dos, (long) stringValue.length(), pos);

                        } else {

                            escribirDataInt(dos, stringValue.length(), pos);
                        }

                        pos++;

                        // escribir cada uno de los chars en formato int
                        for (int i = 0; i < stringValue.length(); i++) {

                            int charAt = stringValue.charAt(i);

                            if (pos % 2 == 0) {

                                escribirDataLong(dos, (long) charAt, pos);

                            } else {

                                escribirDataInt(dos, charAt, pos);
                            }

                            pos++;

                        }

                    }

                }

            }

            // agregar las posiciones
            secret.append(posBloque).append("-").append(posMaximo);

            // making the result
            result.setSecretKey(secret.toString());
            result.setResult(Base64.encodeBase64String(baos.toByteArray()));

            return result;

        } finally {

            closeQuietly(baos);
            closeQuietly(dos);
        }

    }

    private void escribirDataInt(DataOutputStream dos, int intValue, int posicion) throws Exception {
        dos.writeInt(13 * intValue + posicion);
    }

    private void escribirDataLong(DataOutputStream dos, long longValue, int posicion) throws Exception {
        dos.writeLong(3 * longValue + posicion);
    }

    private long genAleatorioLong() {

        if (Math.random() < 0.10) {

            // generar randoms parecidos a lo que usualmente se guardarian en
            // los long (fechas)
            DateTime fecha = DateTime.getNow();

            // generar fechas cercanas a la actual
            if (Math.random() < 0.5) {
                return fecha.addHoras(Alea.newInt(-5, 5)).addMinutos(Alea.newInt(-60, 60)).toLong();
            }

            // generar fechas mas lejanas
            return fecha.addAnios(Alea.newInt(-5, 5)).addDias(Alea.newInt(-100, 100)).toLong();
        }

        // en el resto de casos generar numeros positivos
        return Math.abs(rand.nextLong());
    }

    private int genAleatorioInt() {

        if (Math.random() < 0.10) {

            // generar randoms parecidos a lo que usualmente se guardarian en
            // los int (numeros pequeños)
            return Alea.newInt(0, 10000);
        }

        return rand.nextInt();
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


    public static class Result {

        private String secretKey;
        private String result;

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

    }

}