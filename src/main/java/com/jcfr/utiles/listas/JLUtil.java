package com.jcfr.utiles.listas;

import com.jcfr.utiles.DateTime;
import com.jcfr.utiles.exceptions.JcFRException;
import com.jcfr.utiles.math.JMUtil;
import com.jcfr.utiles.order.OC;
import com.jcfr.utiles.string.JSUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public class JLUtil {

    public static final JLUtil JLUtil = new JLUtil();
    private static final JSUtil JS = JSUtil.JSUtil;

    private JLUtil() {
    }

    // tratamientos comunes de cadenas
    public List<String> quitarContains(List<String> listaFuente, String... itemsIgnorar) {
        return quitarContains(listaFuente, false, itemsIgnorar);
    }

    public List<String> quitarContains(List<String> listaFuente, boolean preprocesarEnComparacion, String... itemsIgnorar) {
        if (JS._vacio(listaFuente)) return listaFuente;

        ArrayList<String> lista = new ArrayList<>(Math.max(16, listaFuente.size()));

        boolean ignorar;
        for (String itemFuente : listaFuente) {

            ignorar = false;
            for (String itemIgnorar : itemsIgnorar) {

                if (JS._vacio(itemIgnorar)) continue;

                if (preprocesarEnComparacion) {
                    if (JS.replaceTildesEnies(JS.toLowerBlank(itemFuente)).contains(JS.replaceTildesEnies(JS.toLowerBlank(itemIgnorar)))) {
                        ignorar = true;
                        break;
                    }
                } else {
                    if (JS.toBlank(itemFuente).contains(JS.toBlank(itemIgnorar))) {
                        ignorar = true;
                        break;
                    }
                }

            }
            if (ignorar) continue;

            lista.add(itemFuente);
        }

        return lista;
    }

    public List<String> quitarStarts(List<String> listaFuente, String... itemsIgnorar) {
        return quitarStarts(listaFuente, false, itemsIgnorar);
    }

    public List<String> quitarStarts(List<String> listaFuente, boolean preprocesarEnComparacion, String... itemsIgnorar) {
        if (JS._vacio(listaFuente)) return listaFuente;

        ArrayList<String> lista = new ArrayList<>(Math.max(16, listaFuente.size()));

        boolean ignorar;
        for (String itemFuente : listaFuente) {

            ignorar = false;
            for (String itemIgnorar : itemsIgnorar) {

                if (JS._vacio(itemIgnorar)) continue;

                if (preprocesarEnComparacion) {
                    if (JS.replaceTildesEnies(JS.toLowerBlank(itemFuente)).startsWith(JS.replaceTildesEnies(JS.toLowerBlank(itemIgnorar)))) {
                        ignorar = true;
                        break;
                    }
                } else {
                    if (JS.toBlank(itemFuente).startsWith(JS.toBlank(itemIgnorar))) {
                        ignorar = true;
                        break;
                    }
                }

            }
            if (ignorar) continue;

            lista.add(itemFuente);
        }

        return lista;
    }

    public List<String> quitarEnds(List<String> listaFuente, String... itemsIgnorar) {
        return quitarEnds(listaFuente, false, itemsIgnorar);
    }

    public List<String> quitarEnds(List<String> listaFuente, boolean preprocesarEnComparacion, String... itemsIgnorar) {
        if (JS._vacio(listaFuente)) return listaFuente;

        ArrayList<String> lista = new ArrayList<>(Math.max(16, listaFuente.size()));

        boolean ignorar;
        for (String itemFuente : listaFuente) {

            ignorar = false;
            for (String itemIgnorar : itemsIgnorar) {

                if (JS._vacio(itemIgnorar)) continue;

                if (preprocesarEnComparacion) {
                    if (JS.replaceTildesEnies(JS.toLowerBlank(itemFuente)).endsWith(JS.replaceTildesEnies(JS.toLowerBlank(itemIgnorar)))) {
                        ignorar = true;
                        break;
                    }
                } else {
                    if (JS.toBlank(itemFuente).endsWith(JS.toBlank(itemIgnorar))) {
                        ignorar = true;
                        break;
                    }
                }

            }
            if (ignorar) continue;

            lista.add(itemFuente);
        }

        return lista;
    }

    public List<String> quitarEquals(List<String> listaFuente, String... itemsIgnorar) {
        return quitarEquals(listaFuente, false, itemsIgnorar);
    }

    public List<String> quitarEquals(List<String> listaFuente, boolean preprocesarEnComparacion, String... itemsIgnorar) {
        if (JS._vacio(listaFuente)) return listaFuente;

        ArrayList<String> lista = new ArrayList<>(Math.max(16, listaFuente.size()));

        boolean ignorar;
        for (String itemFuente : listaFuente) {

            ignorar = false;
            for (String itemIgnorar : itemsIgnorar) {

                if (JS._vacio(itemIgnorar)) continue;

                if (preprocesarEnComparacion) {
                    if (JS.replaceTildesEnies(JS.toLowerBlank(itemFuente)).equals(JS.replaceTildesEnies(JS.toLowerBlank(itemIgnorar)))) {
                        ignorar = true;
                        break;
                    }
                } else {
                    if (JS.toBlank(itemFuente).equals(JS.toBlank(itemIgnorar))) {
                        ignorar = true;
                        break;
                    }
                }

            }
            if (ignorar) continue;

            lista.add(itemFuente);
        }

        return lista;
    }

    public List<String> quitarRepetidos(List<String> listaFuente) {
        if (JS._vacio(listaFuente)) return listaFuente;

        ArrayList<String> lista = new ArrayList<>(Math.max(16, listaFuente.size()));

        for (String itemFuente : listaFuente) {
            // if ( JS._vacio( lista ) || !lista.contains( itemFuente ) ) lista.add( itemFuente );
            if (!lista.contains(itemFuente)) lista.add(itemFuente);
        }

        return lista;
    }

    public List<String> quitarVacios(List<String> listaFuente) {
        if (JS._vacio(listaFuente)) return listaFuente;

        ArrayList<String> lista = new ArrayList<>(Math.max(16, listaFuente.size()));

        for (String itemFuente : listaFuente) {
            if (JS._vacio(itemFuente)) continue;
            lista.add(itemFuente);
        }

        return lista;
    }

    // **** BUSCAR LAS LISTAS ****
    public boolean inListaInt(int valorBuscar, int... lista) {
        if (lista == null || lista.length == 0) return false;
        for (int item : lista) {
            if (item == valorBuscar) return true;
        }
        return false;
    }

    public boolean inListaLong(long valorBuscar, long... lista) {
        if (lista == null || lista.length == 0) return false;
        for (long item : lista) {
            if (item == valorBuscar) return true;
        }
        return false;
    }

    public boolean inListaDateTime(DateTime valorBuscar, DateTime... lista) {
        if (lista == null || lista.length == 0) return false;
        for (DateTime item : lista) {
            if (OC.compare(item, valorBuscar) == 0) return true;
        }
        return false;
    }

    public boolean inListaString(String valorBuscar, String... lista) {
        if (lista == null || lista.length == 0) return false;
        for (String item : lista) {
            if (OC.compare(item, valorBuscar) == 0) return true;
        }
        return false;
    }

    public boolean inListaInt(int valorBuscar, Object... lista) {
        if (lista == null || lista.length == 0) return false;
        for (Object item : lista) {
            if (item instanceof Integer && OC.compare(item, valorBuscar) == 0) return true;
        }
        return false;
    }

    public boolean inListaLong(long valorBuscar, Object... lista) {
        if (lista == null || lista.length == 0) return false;
        for (Object item : lista) {
            if (item instanceof Long && OC.compare(item, valorBuscar) == 0) return true;
        }
        return false;
    }

    public boolean inListaDateTime(DateTime valorBuscar, Object... lista) {
        if (lista == null || lista.length == 0) return false;
        for (Object item : lista) {
            if (item instanceof DateTime && OC.compare(item, valorBuscar) == 0) return true;
        }
        return false;
    }

    public boolean inListaString(String valorBuscar, Object... lista) {
        if (lista == null || lista.length == 0) return false;
        for (Object item : lista) {
            if (item instanceof String && OC.compare(item, valorBuscar) == 0) return true;
        }
        return false;
    }

    public boolean inLista(Object valorBuscar, Object... lista) {
        if (lista == null || lista.length == 0) return false;
        for (Object item : lista) {
            if (OC.compare(item, valorBuscar) == 0) return true;
        }
        return false;
    }

    public boolean inListaInt(int valorBuscar, List<Integer> lista) {
        if (lista == null || lista.isEmpty()) return false;
        for (Integer item : lista) {
            if (item != null && item == valorBuscar) return true;
        }
        return false;
    }

    public boolean inListaLong(long valorBuscar, List<Long> lista) {
        if (lista == null || lista.isEmpty()) return false;
        for (Long item : lista) {
            if (item != null && item == valorBuscar) return true;
        }
        return false;
    }

    public boolean inListaDateTime(DateTime valorBuscar, List<DateTime> lista) {
        if (lista == null || lista.isEmpty()) return false;
        for (DateTime item : lista) {
            if (OC.compare(item, valorBuscar) == 0) return true;
        }
        return false;
    }

    public boolean inListaString(String valorBuscar, List<String> lista) {
        if (lista == null || lista.isEmpty()) return false;
        for (String item : lista) {
            if (OC.compare(item, valorBuscar) == 0) return true;
        }
        return false;
    }

    public boolean inLista(Object valorBuscar, List<Object> lista) {
        if (lista == null || lista.isEmpty()) return false;
        for (Object item : lista) {
            if (OC.compare(item, valorBuscar) == 0) return true;
        }
        return false;
    }

    // **** CREAR LISTAS/ARRAYS, CON UNA CANTIDAD DE ELEMENTOS INICIAL Y UN VALOR POR DEFECTO ****
    public List<Boolean> makeListaBoolean(int size, Boolean valorInicial) {
        int n = Math.abs(size);
        List<Boolean> lista = new ArrayList<>(n);
        for (int i = n; --i >= 0; ) {
            lista.add(valorInicial);
        }
        return lista;
    }

    public List<Integer> makeListaInt(int size, Integer valorInicial) {
        int n = Math.abs(size);
        List<Integer> lista = new ArrayList<>(n);
        for (int i = n; --i >= 0; ) {
            lista.add(valorInicial);
        }
        return lista;
    }

    public List<Long> makeListaLong(int size, Long valorInicial) {
        int n = Math.abs(size);
        List<Long> lista = new ArrayList<>(n);
        for (int i = n; --i >= 0; ) {
            lista.add(valorInicial);
        }
        return lista;
    }

    public List<Double> makeListaDouble(int size, Double valorInicial) {
        int n = Math.abs(size);
        List<Double> lista = new ArrayList<>(n);
        for (int i = n; --i >= 0; ) {
            lista.add(valorInicial);
        }
        return lista;
    }

    public List<String> makeListaString(int size, String valorInicial) {
        int n = Math.abs(size);
        List<String> lista = new ArrayList<>(n);
        for (int i = n; --i >= 0; ) {
            lista.add(valorInicial);
        }
        return lista;
    }

    public List<DateTime> makeListaDateTime(int size, DateTime valorInicial) {
        int n = Math.abs(size);
        List<DateTime> lista = new ArrayList<>(n);
        for (int i = n; --i >= 0; ) {
            lista.add(valorInicial);
        }
        return lista;
    }

    public List<Object> makeLista(int size, Object valorInicial) {
        int n = Math.abs(size);
        List<Object> lista = new ArrayList<>(n);
        for (int i = n; --i >= 0; ) {
            lista.add(valorInicial);
        }
        return lista;
    }

    public boolean[] makeArrayBoolean(int size, boolean valorInicial) {
        int n = Math.abs(size);
        boolean[] lista = new boolean[n];
        for (int i = n; --i >= 0; ) {
            lista[i] = valorInicial;
        }
        return lista;
    }

    public int[] makeArrayInt(int size, int valorInicial) {
        int n = Math.abs(size);
        int[] lista = new int[n];
        for (int i = n; --i >= 0; ) {
            lista[i] = valorInicial;
        }
        return lista;
    }

    public long[] makeArrayLong(int size, long valorInicial) {
        int n = Math.abs(size);
        long[] lista = new long[n];
        for (int i = n; --i >= 0; ) {
            lista[i] = valorInicial;
        }
        return lista;
    }

    public double[] makeArrayDouble(int size, double valorInicial) {
        int n = Math.abs(size);
        double[] lista = new double[n];
        for (int i = n; --i >= 0; ) {
            lista[i] = valorInicial;
        }
        return lista;
    }

    public String[] makeArrayString(int size, String valorInicial) {
        int n = Math.abs(size);
        String[] lista = new String[n];
        for (int i = n; --i >= 0; ) {
            lista[i] = valorInicial;
        }
        return lista;
    }

    public DateTime[] makeArrayDateTime(int size, DateTime valorInicial) {
        int n = Math.abs(size);
        DateTime[] lista = new DateTime[n];
        for (int i = n; --i >= 0; ) {
            lista[i] = valorInicial;
        }
        return lista;
    }

    public Object[] makeArray(int size, Object valorInicial) {
        int n = Math.abs(size);
        Object[] lista = new Object[n];
        for (int i = n; --i >= 0; ) {
            lista[i] = valorInicial;
        }
        return lista;
    }

    // **** MAXIMO ****
    public Comparable maxLista(List<Comparable> lista) {
        if (lista == null || lista.isEmpty()) throw new JcFRException("Argumento lista en maxLista() está vacía.");
        Comparable max = null;
        for (Comparable item : lista) {
            if (item != null) {
                if (max == null) max = item;
                else if ((item).compareTo(max) > 0) max = item;
            }
        }
        if (max == null) throw new JcFRException("Todos los items del argumento lista en maxLista() fueron nulos.");
        return max;
    }

    public Comparable maxLista(Comparable... lista) {
        if (lista == null || lista.length == 0) throw new JcFRException("Argumento lista en maxLista() está vacía.");
        Comparable max = null;
        for (Comparable item : lista) {
            if (item != null) {
                if (max == null) max = item;
                else if ((item).compareTo(max) > 0) max = item;
            }
        }
        if (max == null) throw new JcFRException("Todos los items del argumento lista en maxLista() fueron nulos.");
        return max;
    }

    public Comparable maxArray(Comparable[] lista) {
        if (lista == null || lista.length == 0) throw new JcFRException("Argumento lista en maxArray() está vacía.");
        Comparable max = null;
        for (Comparable item : lista) {
            if (item != null) {
                if (max == null) max = item;
                else if ((item).compareTo(max) > 0) max = item;
            }
        }
        if (max == null) throw new JcFRException("Todos los items del argumento lista en maxArray() fueron nulos.");
        return max;
    }

    public int maxListaInt(List<Integer> lista) {
        if (lista == null || lista.isEmpty()) throw new JcFRException("Argumento lista en maxListaInt() está vacía.");
        Integer max = null;
        for (Integer item : lista) {
            if (item != null) {
                if (max == null) max = item;
                else if ((item).compareTo(max) > 0) max = item;
            }
        }
        if (max == null) throw new JcFRException("Todos los items del argumento lista en maxListaInt() fueron nulos.");
        return max;
    }

    public int maxListaInt(int... lista) {
        if (lista == null || lista.length == 0) throw new JcFRException("Argumento lista en maxListaInt() está vacía.");
        int max = lista[0];
        for (int i = lista.length; --i >= 0; ) {
            if (lista[i] > max) max = lista[i];
        }
        return max;
    }

    public int maxArrayInt(int[] lista) {
        if (lista == null || lista.length == 0) throw new JcFRException("Argumento lista en maxArrayInt() está vacía.");
        int max = lista[0];
        for (int i = lista.length; --i >= 0; ) {
            if (lista[i] > max) max = lista[i];
        }
        return max;
    }

    public long maxListaLong(List<Long> lista) {
        if (lista == null || lista.isEmpty()) throw new JcFRException("Argumento lista en maxListaLong() está vacía.");
        Long max = null;
        for (Long item : lista) {
            if (item != null) {
                if (max == null) max = item;
                else if ((item).compareTo(max) > 0) max = item;
            }
        }
        if (max == null) throw new JcFRException("Todos los items del argumento lista en maxListaLong() fueron nulos.");
        return max;
    }

    public long maxListaLong(long... lista) {
        if (lista == null || lista.length == 0)
            throw new JcFRException("Argumento lista en maxListaLong() está vacía.");
        long max = lista[0];
        for (int i = lista.length; --i >= 0; ) {
            if (lista[i] > max) max = lista[i];
        }
        return max;
    }

    public long maxArrayLong(long[] lista) {
        if (lista == null || lista.length == 0)
            throw new JcFRException("Argumento lista en maxArrayLong() está vacía.");
        long max = lista[0];
        for (int i = lista.length; --i >= 0; ) {
            if (lista[i] > max) max = lista[i];
        }
        return max;
    }

    public double maxListaDouble(List<Double> lista) {
        if (lista == null || lista.isEmpty())
            throw new JcFRException("Argumento lista en maxListaDouble() está vacía.");
        Double max = null;
        for (Double item : lista) {
            if (item != null) {
                if (max == null) max = item;
                else if ((item).compareTo(max) > 0) max = item;
            }
        }
        if (max == null)
            throw new JcFRException("Todos los items del argumento lista en maxListaDouble() fueron nulos.");
        return max;
    }

    public double maxListaDouble(double... lista) {
        if (lista == null || lista.length == 0)
            throw new JcFRException("Argumento lista en maxListaDouble() está vacía.");
        double max = lista[0];
        for (int i = lista.length; --i >= 0; ) {
            if (lista[i] > max) max = lista[i];
        }
        return max;
    }

    public double maxArrayDouble(double[] lista) {
        if (lista == null || lista.length == 0)
            throw new JcFRException("Argumento lista en maxArrayDouble() está vacía.");
        double max = lista[0];
        for (int i = lista.length; --i >= 0; ) {
            if (lista[i] > max) max = lista[i];
        }
        return max;
    }

    // **** MINIMO ****
    public Comparable minLista(List<Comparable> lista) {
        if (lista == null || lista.isEmpty()) throw new JcFRException("Argumento lista en minLista() está vacía.");
        Comparable min = null;
        for (Comparable item : lista) {
            if (item != null) {
                if (min == null) min = item;
                else if ((item).compareTo(min) < 0) min = item;
            }
        }
        if (min == null) throw new JcFRException("Todos los items del argumento lista en minLista() fueron nulos.");
        return min;
    }

    public Comparable minLista(Comparable... lista) {
        if (lista == null || lista.length == 0) throw new JcFRException("Argumento lista en minLista() está vacía.");
        Comparable min = null;
        for (Comparable item : lista) {
            if (item != null) {
                if (min == null) min = item;
                else if ((item).compareTo(min) < 0) min = item;
            }
        }
        if (min == null) throw new JcFRException("Todos los items del argumento lista en minLista() fueron nulos.");
        return min;
    }

    public Comparable minArray(Comparable[] lista) {
        if (lista == null || lista.length == 0) throw new JcFRException("Argumento lista en minArray() está vacía.");
        Comparable min = null;
        for (Comparable item : lista) {
            if (item != null) {
                if (min == null) min = item;
                else if ((item).compareTo(min) < 0) min = item;
            }
        }
        if (min == null) throw new JcFRException("Todos los items del argumento lista en minArray() fueron nulos.");
        return min;
    }

    public int minListaInt(List<Integer> lista) {
        if (lista == null || lista.isEmpty()) throw new JcFRException("Argumento lista en minListaInt() está vacía.");
        Integer min = null;
        for (Integer item : lista) {
            if (item != null) {
                if (min == null) min = item;
                else if ((item).compareTo(min) < 0) min = item;
            }
        }
        if (min == null) throw new JcFRException("Todos los items del argumento lista en minListaInt() fueron nulos.");
        return min;
    }

    public int minListaInt(int... lista) {
        if (lista == null || lista.length == 0) throw new JcFRException("Argumento lista en minListaInt() está vacía.");
        int min = lista[0];
        for (int i = lista.length; --i >= 0; ) {
            if (lista[i] < min) min = lista[i];
        }
        return min;
    }

    public int minArrayInt(int[] lista) {
        if (lista == null || lista.length == 0) throw new JcFRException("Argumento lista en minArrayInt() está vacía.");
        int min = lista[0];
        for (int i = lista.length; --i >= 0; ) {
            if (lista[i] < min) min = lista[i];
        }
        return min;
    }

    public long minListaLong(List<Long> lista) {
        if (lista == null || lista.isEmpty()) throw new JcFRException("Argumento lista en minListaLong() está vacía.");
        Long min = null;
        for (Long item : lista) {
            if (item != null) {
                if (min == null) min = item;
                else if ((item).compareTo(min) < 0) min = item;
            }
        }
        if (min == null) throw new JcFRException("Todos los items del argumento lista en minListaLong() fueron nulos.");
        return min;
    }

    public long minListaLong(long... lista) {
        if (lista == null || lista.length == 0)
            throw new JcFRException("Argumento lista en minListaLong() está vacía.");
        long min = lista[0];
        for (int i = lista.length; --i >= 0; ) {
            if (lista[i] < min) min = lista[i];
        }
        return min;
    }

    public long minArrayLong(long[] lista) {
        if (lista == null || lista.length == 0)
            throw new JcFRException("Argumento lista en minArrayLong() está vacía.");
        long min = lista[0];
        for (int i = lista.length; --i >= 0; ) {
            if (lista[i] < min) min = lista[i];
        }
        return min;
    }

    public double minListaDouble(List<Double> lista) {
        if (lista == null || lista.isEmpty())
            throw new JcFRException("Argumento lista en minListaDouble() está vacía.");
        Double min = null;
        for (Double item : lista) {
            if (item != null) {
                if (min == null) min = item;
                else if ((item).compareTo(min) < 0) min = item;
            }
        }
        if (min == null)
            throw new JcFRException("Todos los items del argumento lista en minListaDouble() fueron nulos.");
        return min;
    }

    public double minListaDouble(double... lista) {
        if (lista == null || lista.length == 0)
            throw new JcFRException("Argumento lista en minListaDouble() está vacía.");
        double min = lista[0];
        for (int i = lista.length; --i >= 0; ) {
            if (lista[i] < min) min = lista[i];
        }
        return min;
    }

    public double minArrayDouble(double[] lista) {
        if (lista == null || lista.length == 0)
            throw new JcFRException("Argumento lista en minArrayDouble() está vacía.");
        double min = lista[0];
        for (int i = lista.length; --i >= 0; ) {
            if (lista[i] < min) min = lista[i];
        }
        return min;
    }

    // **** MEDIA ****
    public double mediaListaInt(List<Integer> lista) {
        if (lista == null || lista.isEmpty()) throw new JcFRException("Argumento lista en mediaListaInt() está vacía.");
        double suma = 0;
        int count = 0;
        for (Integer item : lista) {
            if (item != null) {
                suma += item;
                count++;
            }
        }
        if (count == 0) throw new JcFRException("Todos los items del argumento lista en mediaListaInt() fueron nulos.");
        return suma / count;
    }

    public double mediaListaLong(List<Long> lista) {
        if (lista == null || lista.isEmpty())
            throw new JcFRException("Argumento lista en mediaListaLong() está vacía.");
        double suma = 0;
        int count = 0;
        for (Long item : lista) {
            if (item != null) {
                suma += item;
                count++;
            }
        }
        if (count == 0)
            throw new JcFRException("Todos los items del argumento lista en mediaListaLong() fueron nulos.");
        return suma / count;
    }

    public double mediaListaDouble(List<Double> lista) {
        if (lista == null || lista.isEmpty())
            throw new JcFRException("Argumento lista en mediaListaDouble() está vacía.");
        double suma = 0;
        int count = 0;
        for (Double item : lista) {
            if (item != null) {
                suma += item;
                count++;
            }
        }
        if (count == 0)
            throw new JcFRException("Todos los items del argumento lista en mediaListaDouble() fueron nulos.");
        return suma / count;
    }

    public double mediaListaInt(int... lista) {
        if (lista == null || lista.length == 0)
            throw new JcFRException("Argumento lista en mediaListaInt() está vacía.");
        double suma = 0;
        int count = 0;
        for (Integer item : lista) {
            if (item != null) {
                suma += item;
                count++;
            }
        }
        if (count == 0) throw new JcFRException("Todos los items del argumento lista en mediaListaInt() fueron nulos.");
        return suma / count;
    }

    public double mediaListaLong(long... lista) {
        if (lista == null || lista.length == 0)
            throw new JcFRException("Argumento lista en mediaListaLong() está vacía.");
        double suma = 0;
        int count = 0;
        for (Long item : lista) {
            if (item != null) {
                suma += item;
                count++;
            }
        }
        if (count == 0)
            throw new JcFRException("Todos los items del argumento lista en mediaListaLong() fueron nulos.");
        return suma / count;
    }

    public double mediaListaDouble(double... lista) {
        if (lista == null || lista.length == 0)
            throw new JcFRException("Argumento lista en mediaListaDouble() está vacía.");
        double suma = 0;
        int count = 0;
        for (Double item : lista) {
            if (item != null) {
                suma += item;
                count++;
            }
        }
        if (count == 0)
            throw new JcFRException("Todos los items del argumento lista en mediaListaDouble() fueron nulos.");
        return suma / count;
    }

    public double mediaArrayInt(int[] lista) {
        if (lista == null || lista.length == 0)
            throw new JcFRException("Argumento lista en mediaArrayInt() está vacía.");
        double suma = 0;
        for (int item : lista) {
            suma += item;
        }
        return suma / lista.length;
    }

    public double mediaArrayLong(long[] lista) {
        if (lista == null || lista.length == 0)
            throw new JcFRException("Argumento lista en mediaArrayLong() está vacía.");
        double suma = 0;
        for (long item : lista) {
            suma += item;
        }
        return suma / lista.length;
    }

    public double mediaArrayDouble(double[] lista) {
        if (lista == null || lista.length == 0)
            throw new JcFRException("Argumento lista en mediaArrayDouble() está vacía.");
        double suma = 0;
        for (double item : lista) {
            suma += item;
        }
        return suma / lista.length;
    }

    // **** SUMA ****
    public double sumaListaInt(List<Integer> lista) {
        if (lista == null || lista.isEmpty()) return 0.0;
        double suma = 0;
        for (Integer item : lista) {
            if (item != null) suma += item;
        }
        return suma;
    }

    public long sumaListaLong(List<Long> lista) {
        if (lista == null || lista.isEmpty()) return 0L;
        long suma = 0;
        for (Long item : lista) {
            if (item != null) suma += item;
        }
        return suma;
    }

    public double sumaListaDouble(List<Double> lista) {
        if (lista == null || lista.isEmpty()) return 0.0;
        double suma = 0;
        for (Double item : lista) {
            if (item != null) suma += item;
        }
        return suma;
    }

    public int sumaListaInt(int... lista) {
        if (lista == null || lista.length == 0) return 0;
        int suma = 0;
        for (Integer item : lista) {
            if (item != null) suma += item;
        }
        return suma;
    }

    public long sumaListaLong(long... lista) {
        if (lista == null || lista.length == 0) return 0L;
        long suma = 0;
        for (long item : lista) {
            suma += item;
        }
        return suma;
    }

    public double sumaListaDouble(double... lista) {
        if (lista == null || lista.length == 0) return 0.0;
        double suma = 0;
        for (double item : lista) {
            suma += item;
        }
        return suma;
    }

    public int sumaArrayInt(int[] lista) {
        if (lista == null || lista.length == 0) return 0;
        int suma = 0;
        for (int item : lista) {
            suma += item;
        }
        return suma;
    }

    public long sumaArrayLong(long[] lista) {
        if (lista == null || lista.length == 0) return 0L;
        long suma = 0;
        for (long item : lista) {
            suma += item;
        }
        return suma;
    }

    public double sumaArrayDouble(double[] lista) {
        if (lista == null || lista.length == 0) return 0.0;
        double suma = 0;
        for (double item : lista) {
            suma += item;
        }
        return suma;
    }

    // **** ROUND ****
    public List<Double> roundListaDouble(List<Double> lista, int nroDecimales) {
        if (lista == null || lista.isEmpty())
            throw new JcFRException("Argumento lista en roundListaDouble() está vacía.");

        // TODO/FIXME: fumadaza
        for (Double item : lista) {
            if (item != null) item = JMUtil.round(item, nroDecimales);
        }
        return lista;
    }

    public double[] roundArrayDouble(double[] lista, int nroDecimales) {
        if (lista == null || lista.length == 0)
            throw new JcFRException("Argumento lista en roundArrayDouble() está vacía.");
        // TODO/FIXME: fumadaza
        for (double item : lista) {
            item = JMUtil.round(item, nroDecimales);
        }
        return lista;
    }

    public int size(Object object) {

        if (object == null) return 0;

        if (object instanceof Collection) return ((Collection) object).size();
        if (object instanceof Map) return ((Map) object).size();
        if (object instanceof Object[]) return ((Object[]) object).length;
        if (object instanceof String) return ((String) object).length();
        if (object instanceof CharSequence) return ((CharSequence) object).length();

        return 0;
    }

    public int size(List lista) {

        if (lista == null) return 0;

        return lista.size();
    }

    public int sizeOrMin(List lista, int min) {

        if (lista == null) return min;

        int size = lista.size();

        return size == 0 ? min : size;
    }

    public String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
