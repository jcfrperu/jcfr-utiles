package com.jcfr.utiles.object;

import java.io.*;

public class ByteObjectUtiles {

    public static Object clone(Object object) {
        byte[] bytes = toBytes(object);
        return fromBytes(bytes);
    }

    public static byte[] toBytes(Object object) {

        if (object == null) return null;

        ByteArrayOutputStream bytesOut = null;
        ObjectOutputStream objectOut = null;

        try {

            bytesOut = new ByteArrayOutputStream();

            objectOut = new ObjectOutputStream(bytesOut);

            objectOut.writeObject(object);

            return bytesOut.toByteArray();

        } catch (Exception sos) {

            return null;

        } finally {

            closeQuietly(objectOut);
            closeQuietly(bytesOut);

        }

    }

    public static Object fromBytes(byte[] object) {

        if (object == null || object.length == 0) return null;

        ByteArrayInputStream bytesIn = null;
        ObjectInputStream objectIn = null;

        try {

            bytesIn = new ByteArrayInputStream(object);

            objectIn = new ObjectInputStream(bytesIn);

            return objectIn.readObject();

        } catch (Exception sos) {

            return null;

        } finally {

            closeQuietly(objectIn);
            closeQuietly(bytesIn);

        }

    }

    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

}
