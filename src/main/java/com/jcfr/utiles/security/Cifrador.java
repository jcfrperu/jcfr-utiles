package com.jcfr.utiles.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;

public class Cifrador {

    public static final int ITERACIONES = 100;
    public static final int TAMANO_BUFFER = 1024;
    public static final String NOMBRE_ALGORITMO = "PBEWithMD5AndTripleDES";

    public Cifrador() {
    }

    public static byte[] getBytesFromString(String cadena) throws Exception {
        return getBytesFromString(cadena, null);
    }

    public static byte[] getBytesFromString(String cadena, String charsetName) throws Exception {
        return charsetName == null ? cadena.getBytes() : cadena.getBytes(charsetName);
    }

    public static String getStringFromBytes(byte[] bytes) throws Exception {
        return getStringFromBytes(bytes, "UTF-8");
    }

    public static String getStringFromBytes(byte[] bytes, String charsetName) throws Exception {
        return charsetName == null ? new String(bytes) : new String(bytes, charsetName);
    }

    public byte[] cifrar(byte[] bytesACifrar, String password) throws Exception {

        // CREACI?N DE LOS FLUJOS
        ByteArrayInputStream flujoEntrada = new ByteArrayInputStream(bytesACifrar);
        ByteArrayOutputStream arraySalida = new ByteArrayOutputStream();
        DataOutputStream flujoSalida = new DataOutputStream(arraySalida);

        // CLAVE SECRETA
        PBEKeySpec objeto_password = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory skf = SecretKeyFactory.getInstance(NOMBRE_ALGORITMO);
        SecretKey clave_secreta = skf.generateSecret(objeto_password);

        // GENERACI?N DEL SALT Y LAS ITERACIONES
        byte[] salt = new byte[8];
        new SecureRandom().nextBytes(salt);
        PBEParameterSpec paramSaltIteraciones = new PBEParameterSpec(salt, ITERACIONES);

        // INCIALIZANDO EL CIFRADOR
        Cipher cifrador = Cipher.getInstance(NOMBRE_ALGORITMO);
        cifrador.init(Cipher.ENCRYPT_MODE, clave_secreta, paramSaltIteraciones);

        // OBTENEMOS LOS PARAMETROS DEL CIFRADOR EN BYTES
        AlgorithmParameters ap = cifrador.getParameters();
        byte[] encoded = ap.getEncoded();

        // ESCRIBIMOS EN EL FLUJO DE SALIDA LA CABECERA (LA LONGITUD Y LOS BYTES DEL SALT)
        flujoSalida.writeInt(encoded.length);
        flujoSalida.write(encoded);

        // EMPEZAMOS A LEER EL FLUJO DE ENTRADA Y CIFRARLO
        byte[] buffer = new byte[TAMANO_BUFFER];
        int leidos = flujoEntrada.read(buffer); // leer el flujo de entrada
        while (leidos > 0) {
            flujoSalida.write(cifrador.update(buffer, 0, leidos));
            leidos = flujoEntrada.read(buffer); // siguiente bloque: leer el flujo de entrada
        }
        flujoSalida.write(cifrador.doFinal());

        // CERRAMOS EL FLUJO INTERNO
        flujoEntrada.close(); // aunque como es byteArray est? dem?s.

        // RETORNA EL REPOSITORIO DEL FLUJO DE SALIDA
        return arraySalida.toByteArray();
    }

    public byte[] descifrar(byte[] bytesCifrados, String password) throws Exception {

        // CREACI?N DE LOS FLUJOS
        DataInputStream flujoEntrada = new DataInputStream(new ByteArrayInputStream(bytesCifrados));
        ByteArrayOutputStream arrayPlano = new ByteArrayOutputStream();
        DataOutputStream flujoSalida = new DataOutputStream(arrayPlano);

        // CLAVE SECRETA
        PBEKeySpec objeto_password = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory skf = SecretKeyFactory.getInstance(NOMBRE_ALGORITMO);
        SecretKey clave_secreta = skf.generateSecret(objeto_password);

        // LEEMOS EL SALT GUARDADAS EN EL FLUJO
        int longitud_encoded = flujoEntrada.readInt();
        byte[] encoded = new byte[longitud_encoded];
        flujoEntrada.read(encoded);

        // GENERAMOS LOS PARAMETROS DEL ALGORITMOS CON EL SALT RECUPERADO
        AlgorithmParameters ap = AlgorithmParameters.getInstance(NOMBRE_ALGORITMO);
        ap.init(encoded);

        // GENERAMOS EL CIFRADOR
        Cipher cifrador = Cipher.getInstance(NOMBRE_ALGORITMO);
        cifrador.init(Cipher.DECRYPT_MODE, clave_secreta, ap);

        // EMPEZAMOS A LEER EL FLUJO DE ENTRADA Y DESCIFRARLO
        byte[] buffer = new byte[TAMANO_BUFFER];
        int bytes_leidos = flujoEntrada.read(buffer);
        while (bytes_leidos > 0) {
            flujoSalida.write(cifrador.update(buffer, 0, bytes_leidos));
            bytes_leidos = flujoEntrada.read(buffer);
        }
        flujoSalida.write(cifrador.doFinal());

        // CERRAMOS EL FLUJO INTERNO
        flujoEntrada.close(); // aunque como es byteArray esta como demas

        return arrayPlano.toByteArray();
    }
}
