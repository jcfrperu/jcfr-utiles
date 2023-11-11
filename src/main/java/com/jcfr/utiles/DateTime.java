package com.jcfr.utiles;

import com.jcfr.utiles.exceptions.JcFRException;
import com.jcfr.utiles.string.JSUtil;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateTime implements Serializable, Comparable<Object> {

    private static final long serialVersionUID = 1L;

    public static final String HORA_HHMM = "HH:MM";
    public static final String HORA_HHMMSS = "HH:MM:SS";
    public static final String HORA_HHMMSSMM = "HH:MM:SS:MM";
    public static final String FECHA_DDMMAAAA = "DD/MM/AAAA";
    public static final String FECHA_AAAAMMDD = "AAAA/MM/DD";
    private Calendar c;
    private static final JSUtil JS = JSUtil.JSUtil;

    private DateTime(Calendar calendario) {
        c = calendario;
    }

    private DateTime(Calendar calendario, Date fecha) {
        c = calendario;
        c.setTime(fecha);
    }

    public static DateTime getNow() {
        return new DateTime(GregorianCalendar.getInstance());
    }

    public static int restarMeses(DateTime d1, DateTime d2) {
        if (d1 == null) throw new JcFRException("1er argumento del método restarMeses() es nulo!");
        if (d2 == null) throw new JcFRException("2do argumento del método restarMeses() es nulo!");

        int contador = 0;
        DateTime fecha = d1;

        if (d2.despuesQue(d1)) {

            do {

                fecha = fecha.addMeses(1);
                contador++;

            } while (fecha.antesQue(d2));

        } else if (d2.antesQue(d1)) {

            do {

                fecha = fecha.addMeses(-1);
                contador--;

            } while (fecha.despuesQue(d2));

        }

        return contador;
    }

    public static int restarDias(DateTime d1, DateTime d2) {
        if (d1 == null) throw new JcFRException("1er argumento del método restarDias() es nulo!");
        if (d2 == null) throw new JcFRException("2do argumento del método restarDias() es nulo!");
        return (int) (Math.floor((d1.toLong() - d2.toLong()) / Constantes.CONVERT_MILISEGS_TO_DIAS));
    }

    public static double restarDiasDouble(DateTime d1, DateTime d2) {
        if (d1 == null) throw new JcFRException("1er argumento del método restarDiasDouble() es nulo!");
        if (d2 == null) throw new JcFRException("2do argumento del método restarDiasDouble() es nulo!");
        return (double) (d1.toLong() - d2.toLong()) / Constantes.CONVERT_MILISEGS_TO_DIAS;
    }

    public static long restarHoras(DateTime d1, DateTime d2) {
        if (d1 == null) throw new JcFRException("1er argumento del método restarHoras() es nulo!");
        if (d2 == null) throw new JcFRException("2do argumento del método restarHoras() es nulo!");
        return (int) (Math.floor((d1.toLong() - d2.toLong()) / Constantes.CONVERT_MILISEGS_TO_HORAS));
    }

    public static double restarHorasDouble(DateTime d1, DateTime d2) {
        if (d1 == null) throw new JcFRException("1er argumento del método restarHorasDouble() es nulo!");
        if (d2 == null) throw new JcFRException("2do argumento del método restarHorasDouble() es nulo!");
        return (double) (d1.toLong() - d2.toLong()) / Constantes.CONVERT_MILISEGS_TO_HORAS;
    }

    public static long restarMinutos(DateTime d1, DateTime d2) {
        if (d1 == null) throw new JcFRException("1er argumento del método restarMinutos() es nulo!");
        if (d2 == null) throw new JcFRException("2do argumento del método restarMinutos() es nulo!");
        return (int) (Math.floor((d1.toLong() - d2.toLong()) / Constantes.CONVERT_MILISEGS_TO_MINUTOS));
    }

    public static double restarMinutosDouble(DateTime d1, DateTime d2) {
        if (d1 == null) throw new JcFRException("1er argumento del método restarMinutosDouble() es nulo!");
        if (d2 == null) throw new JcFRException("2do argumento del método restarMinutosDouble() es nulo!");
        return (double) (d1.toLong() - d2.toLong()) / Constantes.CONVERT_MILISEGS_TO_MINUTOS;
    }

    public static long restarSegundos(DateTime d1, DateTime d2) {
        if (d1 == null) throw new JcFRException("1er argumento del método restarSegundos() es nulo!");
        if (d2 == null) throw new JcFRException("2do argumento del método restarSegundos() es nulo!");
        return (int) (Math.floor((d1.toLong() - d2.toLong()) / (1000.00)));
    }

    public static double restarSegundosDouble(DateTime d1, DateTime d2) {
        if (d1 == null) throw new JcFRException("1er argumento del método restarSegundosDouble() es nulo!");
        if (d2 == null) throw new JcFRException("2do argumento del método restarSegundosDouble() es nulo!");
        return (double) (d1.toLong() - d2.toLong()) / (1000.00);
    }

    public static long restarMilisegundos(DateTime d1, DateTime d2) {
        if (d1 == null) throw new JcFRException("1er argumento del método restarMilisegundos() es nulo!");
        if (d2 == null) throw new JcFRException("2do argumento del método restarMilisegundos() es nulo!");
        return d1.toLong() - d2.toLong();
    }

    public static DateTime getMayor(DateTime d1, DateTime d2) {
        if (d1 == null) throw new JcFRException("1er argumento del método getMayor() es nulo!");
        if (d2 == null) throw new JcFRException("2do argumento del método getMayor() es nulo!");
        return d1.despuesIgualQue(d2) ? d1 : d2;
    }

    public static DateTime getMenor(DateTime d1, DateTime d2) {
        if (d1 == null) throw new JcFRException("1er argumento del método getMenor() es nulo!");
        if (d2 == null) throw new JcFRException("2do argumento del método getMenor() es nulo!");
        return d1.antesIgualQue(d2) ? d1 : d2;
    }

    public static DateTime getInicioMes() {
        return DateTime.getToday().makeInicioMes();
    }

    public static DateTime getQuincenaMes() {
        return DateTime.getToday().makeQuincenaMes();
    }

    public static DateTime getInicioSemana() {
        return DateTime.getToday().makeInicioSemana();
    }

    public static DateTime getFinMes() {
        return DateTime.getToday().makeFinMes();
    }

    public static DateTime getToday() {
        // hoy dia, seteado la hora a las 00:00
        DateTime dt = new DateTime(GregorianCalendar.getInstance());
        int hs = dt.getHoras();
        int ms = dt.getMinutos();
        int ss = dt.getSegundos();
        int mms = dt.getMiliSegundos();
        return dt.addMilisegundos(-mms).addSegundos(-ss).addMinutos(-ms).addHoras(-hs);
    }

    public static boolean tieneFormatoFecha(String fecha) {
        return DateTime.tieneFormatoFecha(fecha, DateTime.FECHA_DDMMAAAA) || DateTime.tieneFormatoFecha(fecha, DateTime.FECHA_AAAAMMDD);
    }

    public static boolean tieneFormatoFecha(String fecha, String formato) {
        if (fecha == null || formato == null) return false;

        String fechaTrim = fecha.trim();
        if (fechaTrim.length() == 0) return false;

        String[] trozoFecha = JS.sreplace(fechaTrim, '-', '.', "/").split("/");
        if (trozoFecha.length != 3) return false;

        String formatoTUR = JS.sreplace(formato.trim().toUpperCase(), '-', '.', "/");

        if (formatoTUR.equals(DateTime.FECHA_DDMMAAAA)) {
            if (!DateTime._esIntFaster(trozoFecha[0], 1, 31)) return false;
            if (!DateTime._esIntFaster(trozoFecha[1], 1, 12)) return false;
            return DateTime._esIntFaster(trozoFecha[2], 0, 9999);
        }

        if (formatoTUR.equals(DateTime.FECHA_AAAAMMDD)) {
            if (!DateTime._esIntFaster(trozoFecha[0], 0, 9999)) return false;
            if (!DateTime._esIntFaster(trozoFecha[1], 1, 12)) return false;
            return DateTime._esIntFaster(trozoFecha[2], 1, 31);
        }

        return false;
    }

    public static boolean tieneFormatoHora(String hora) {
        return DateTime.tieneFormatoHora(hora, DateTime.HORA_HHMMSS) || DateTime.tieneFormatoHora(hora, DateTime.HORA_HHMM) || DateTime.tieneFormatoHora(hora, DateTime.HORA_HHMMSSMM);
    }

    public static boolean tieneFormatoHora(String hora, String formato) {
        if (hora == null || formato == null) return false;

        String horaTrim = hora.trim();
        if (horaTrim.length() == 0) return false;

        String[] trozoHora = JS.sreplace(horaTrim, '-', '.', "/").split(":");
        if (trozoHora.length < 2) return false;

        String formatoTUR = JS.sreplace(formato.trim().toUpperCase(), '-', '.', ":");

        if (formatoTUR.equals(DateTime.HORA_HHMM) && trozoHora.length == 2) {
            if (!DateTime._esIntFaster(trozoHora[0], 0, 23)) return false;
            return DateTime._esIntFaster(trozoHora[1], 0, 59);
        }

        if (formatoTUR.equals(DateTime.HORA_HHMMSS) && trozoHora.length == 3) {
            if (!DateTime._esIntFaster(trozoHora[0], 0, 23)) return false;
            if (!DateTime._esIntFaster(trozoHora[1], 0, 59)) return false;
            return DateTime._esIntFaster(trozoHora[1], 0, 59);
        }

        if (formatoTUR.equals(DateTime.HORA_HHMMSSMM) && trozoHora.length == 4) {
            if (!DateTime._esIntFaster(trozoHora[0], 0, 23)) return false;
            if (!DateTime._esIntFaster(trozoHora[1], 0, 59)) return false;
            if (!DateTime._esIntFaster(trozoHora[1], 0, 59)) return false;
            return DateTime._esIntFaster(trozoHora[1], 0, 999);
        }

        return false;
    }

    public static DateTime getInstancia() {
        return new DateTime(GregorianCalendar.getInstance());
    }

    public static DateTime getInstancia(Date fecha) {
        if (fecha == null) return null;
        return new DateTime(GregorianCalendar.getInstance(), fecha);
    }

    public static DateTime getInstancia(long fecha) {
        if (fecha < 0) return null;
        return new DateTime(GregorianCalendar.getInstance(), new Date(fecha));
    }

    public static DateTime getInstancia(String fecha) {

        if (DateTime.tieneFormatoFecha(fecha, DateTime.FECHA_DDMMAAAA)) {
            String[] trozos = JS.sreplace(fecha.trim(), '-', '.', '/').split("/");
            DateTime dt = DateTime.getToday();
            dt.setCalendar(Integer.parseInt(trozos[0]), Integer.parseInt(trozos[1]) - 1, Integer.parseInt(trozos[2]));
            return dt;
        }

        if (DateTime.tieneFormatoFecha(fecha, FECHA_AAAAMMDD)) {
            String[] trozos = JS.sreplace(fecha.trim(), '-', '.', '/').split("/");
            DateTime dt = DateTime.getToday();
            dt.setCalendar(Integer.parseInt(trozos[2]), Integer.parseInt(trozos[1]) - 1, Integer.parseInt(trozos[0]));
            return dt;
        }

        return null;
    }

    public static DateTime getInstancia(String fecha, String formato) {
        if (DateTime.tieneFormatoFecha(fecha, formato)) {
            DateTime dt = DateTime.getToday();
            dt.setFechaFromString(fecha, formato);
            return dt;
        }
        return null;
    }

    public void setFechaFromString(String fecha) {
        setFechaFromString(fecha, DateTime.FECHA_DDMMAAAA);
    }

    public void setFechaFromString(String fecha, String formato) {
        if (!DateTime.tieneFormatoFecha(fecha, formato)) return; // ver lo que asegura el metodo tieneFormatoFecha()

        String formatoPr = JS.sreplace(formato.trim().toUpperCase(), '-', '.', "/");
        String[] trozoFechaPr = JS.sreplace(fecha.trim().toUpperCase(), '-', '.', "/").split("/");

        setDate(DateTime.getToday().toDate());

        if (formatoPr.equals(DateTime.FECHA_DDMMAAAA)) {
            c.set(Calendar.DATE, Integer.parseInt(trozoFechaPr[0]));
            c.set(Calendar.MONTH, Integer.parseInt(trozoFechaPr[1]) - 1);
            c.set(Calendar.YEAR, Integer.parseInt(trozoFechaPr[2]));
        } else {
            c.set(Calendar.DATE, Integer.parseInt(trozoFechaPr[2]));
            c.set(Calendar.MONTH, Integer.parseInt(trozoFechaPr[1]) - 1);
            c.set(Calendar.YEAR, Integer.parseInt(trozoFechaPr[0]));
        }

    }

    public void setHoraFromString(String fecha) {
        setHoraFromString(fecha, DateTime.HORA_HHMMSS);
    }

    public void setHoraFromString(String hora, String formato) {
        if (!DateTime.tieneFormatoHora(hora, formato)) return; // ver lo que asegura el metodo tieneFormatoFecha()

        String formatoPr = JS.sreplace(formato.trim().toUpperCase(), '-', '.', "/");
        String[] trozoHora = JS.sreplace(hora.trim().toUpperCase(), '-', '.', "/").split(":");

        setDate(DateTime.getToday().toDate());

        if (formatoPr.equals(DateTime.HORA_HHMMSS)) {

            c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(trozoHora[0]));
            c.set(Calendar.MINUTE, Integer.parseInt(trozoHora[1]));
            c.set(Calendar.SECOND, Integer.parseInt(trozoHora[2]));

        } else if (formatoPr.equals(DateTime.HORA_HHMM)) {

            c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(trozoHora[0]));
            c.set(Calendar.MINUTE, Integer.parseInt(trozoHora[1]));
            c.set(Calendar.SECOND, Integer.parseInt(trozoHora[2]));

        } else {

            c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(trozoHora[0]));
            c.set(Calendar.MINUTE, Integer.parseInt(trozoHora[1]));
            c.set(Calendar.SECOND, Integer.parseInt(trozoHora[2]));
            c.set(Calendar.MILLISECOND, Integer.parseInt(trozoHora[3]));

        }

    }

    public DateTime makeInicioMes() {
        return DateTime.getInstancia("01/" + getMes() + "/" + getAnio());
    }

    public DateTime makeInicioSemana() {
        return addDias(-getDiaSemana() + 1);
    }

    public DateTime makeQuincenaMes() {
        return DateTime.getInstancia("15/" + getMes() + "/" + getAnio());
    }

    public DateTime makeFinMes() {
        return makeInicioMes().addMeses(1).addDias(-1);
    }

    // ESTE METODO SOLO DEBE INVOCARSE EN GET_INTANCIA
    private void setCalendar(int dia, int mes, int anio) {
        c.set(Calendar.DATE, dia);
        c.set(Calendar.MONTH, mes);
        c.set(Calendar.YEAR, anio);
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) return 1; // una fecha valida siempre es mayor que una nula
        if (o instanceof DateTime) {
            DateTime objCast = (DateTime) o;
            if (this.igualQue(objCast)) return 0;
            if (this.antesQue(objCast)) return -1;
            if (this.despuesQue(objCast)) return 1;
        }
        return -1;
    }

    public boolean antesQue(DateTime fecha) {
        if (fecha == null) return false;
        return c.before(fecha.getCalendar());
    }

    public boolean despuesQue(DateTime fecha) {
        if (fecha == null) return true;
        return c.after(fecha.getCalendar());
    }

    public boolean entre(DateTime inicio, DateTime termino) {
        return despuesQue(inicio) && antesQue(termino);
    }

    public boolean antesIgualQue(DateTime fecha) {
        if (fecha == null) return false;
        return c.compareTo(fecha.getCalendar()) <= 0;
    }

    public boolean despuesIgualQue(DateTime fecha) {
        if (fecha == null) return true;
        return c.compareTo(fecha.getCalendar()) >= 0;
    }

    public boolean entreIgual(DateTime inicio, DateTime termino) {
        return despuesIgualQue(inicio) && antesIgualQue(termino);
    }

    public boolean igualQue(DateTime fecha) {
        return equals(fecha);
    }

    public long toLong() {
        return toDate().getTime();
    }

    public Date toDate() {
        return new Date(c.getTime().getTime());
    }

    public java.sql.Date toDateSQL() {
        return new java.sql.Date(c.getTime().getTime());
    }

    public Time toTime() {
        return new Time(c.getTime().getTime());
    }

    public Timestamp toTimestamp() {
        return new Timestamp(c.getTime().getTime());
    }

    public Timestamp toDateTime() {
        return new Timestamp(c.getTime().getTime());
    }

    public int getAnio() {
        return c.get(Calendar.YEAR);
    }

    public int getMes() {
        // VER: En java los meses empiezan en 0, ver si esto trae problemas luego.
        return c.get(Calendar.MONTH) + 1;
    }

    public int getMesJava() {
        // VER: En java los meses empiezan en 0, ver si esto trae problemas luego.
        return c.get(Calendar.MONTH);
    }

    public String getMesJavaString() {
        return new DateFormatSymbols().getMonths()[getMesJava()];
    }

    public String getMesString() {
        return DiaMesUtil.getNombreMes(getMes());
    }

    public String getMesAbrvString() {
        return DiaMesUtil.getNombreMesAbrv(getMes());
    }

    public int getDia() {
        return c.get(Calendar.DATE);
    }

    public int getDiaSemanaJava() {
        return c.get(Calendar.DAY_OF_WEEK);
    }

    public int getDiaSemana() {
        int diaJava = getDiaSemanaJava();
        if (diaJava >= 2 && diaJava <= 7) return diaJava - 1;
        return 7;
    }

    public String getDiaSemanaJavaString() {
        return new DateFormatSymbols().getWeekdays()[getDiaSemanaJava()];
    }

    public String getDiaSemanaJavaString(Locale locate) {
        return new java.text.DateFormatSymbols(locate).getWeekdays()[getDiaSemanaJava()];
    }

    public String getDiaSemanaString() {
        return DiaMesUtil.getNombreDia(getDiaSemana());
    }

    public String getDiaSemanaAbrvString() {
        return DiaMesUtil.getNombreDiaAbrv(getDiaSemana());
    }

    public int getHoras() {
        return c.get(Calendar.HOUR_OF_DAY); // FORMATO 24 horas
    }

    public int getHoraAMPM() {
        return c.get(Calendar.HOUR);
    }

    public int getMinutos() {
        return c.get(Calendar.MINUTE);
    }

    public int getSegundos() {
        return c.get(Calendar.SECOND);
    }

    public int getMiliSegundos() {
        return c.get(Calendar.MILLISECOND);
    }

    public boolean isAM() {
        return c.get(Calendar.AM_PM) == Calendar.AM; // retorna el AM del la hora
    }

    public boolean isPM() {
        return c.get(Calendar.AM_PM) == Calendar.PM; // retorna el AM del la hora
    }

    public DateTime crearCopia() {
        return new DateTime((Calendar) c.clone()); // crear copia de calendar
    }

    public Calendar getCalendar() {
        return c;
    }

    // NOTA: TODOS LOS METODOS DE ABAJO RETORNAN UNA NUEVA INSTANCIA
    public DateTime addAnios(int valor) {
        Calendar copia = (Calendar) c.clone();
        copia.add(Calendar.YEAR, valor);
        return new DateTime(copia);
    }

    public DateTime addMeses(int valor) {
        Calendar copia = (Calendar) c.clone();
        copia.add(Calendar.MONTH, valor);
        return new DateTime(copia);
    }

    public DateTime addDias(int valor) {
        Calendar copia = (Calendar) c.clone();
        copia.add(Calendar.DAY_OF_MONTH, valor);
        return new DateTime(copia);
    }

    public DateTime addHoras(int valor) {
        Calendar copia = (Calendar) c.clone();
        copia.add(Calendar.HOUR_OF_DAY, valor);
        return new DateTime(copia);
    }

    public DateTime addMinutos(int valor) {
        Calendar copia = (Calendar) c.clone();
        copia.add(Calendar.MINUTE, valor);
        return new DateTime(copia);
    }

    public DateTime addSegundos(int valor) {
        Calendar copia = (Calendar) c.clone();
        copia.add(Calendar.SECOND, valor);
        return new DateTime(copia);
    }

    public DateTime addMilisegundos(int valor) {
        Calendar copia = (Calendar) c.clone();
        copia.add(Calendar.MILLISECOND, valor);
        return new DateTime(copia);
    }

    public void setDate(Date nuevaFecha) {
        c.setTime(nuevaFecha);
    }

    // FORMATOS FECHA DE JAVA
    public String toDateJavaString(int dateFormat) {
        return DateFormat.getDateInstance(dateFormat).format(toDate());
    }

    public String toDateJavaString(int estiloParaDate, Locale locate) {
        return DateFormat.getDateInstance(estiloParaDate == -4 ? 3 : estiloParaDate, locate).format(toDate());
    }

    public String toTimeJavaString(int estiloParaTime, Locale locate) {
        return DateFormat.getTimeInstance(estiloParaTime == -4 ? 3 : estiloParaTime, locate).format(toDate());
    }

    public String toTimeJavaString(int dateFormat) {
        return DateFormat.getTimeInstance(dateFormat).format(toDate());
    }

    public String toTimeAMPMJavaString(String simpleDateFormat) {
        return new SimpleDateFormat(simpleDateFormat).format(toDate());
    }

    public String toTimeAMPMJavaString(String simpleDateFormat, Locale locate) {
        return new SimpleDateFormat(simpleDateFormat, locate).format(toDate());
    }

    public String toDateTimeJavaString(int estiloParaDate, int estiloParaTime) {
        return DateFormat.getDateTimeInstance(estiloParaDate, estiloParaTime).format(toDate());
    }

    public String toDateTimeJavaString(int estiloParaDate, int estiloParaTime, Locale locate) {
        return DateFormat.getDateTimeInstance(estiloParaDate, estiloParaTime, locate).format(toDate());
    }

    // FORMTOS DE FECHA PARA USO LOCAL
    public String getFechaStringStandard() {
        return getFechaStringStandard("/");
    }

    public String getFechaStringStandardGuion() {
        return getFechaStringStandard("-");
    }

    public String getFechaStringStandard(String separador) {
        int dia = getDia();
        int mes = getMes();
        return _parseAnioActualFaster() + separador + (mes < 10 ? "0" + mes : mes) + separador + (dia < 10 ? "0" + dia : dia);
    }

    public String getFechaHoraString() {
        return getFechaString() + " " + getHoraString();
    }

    public String getFechaHoraString(String separador) {
        return getFechaString() + separador + getHoraString();
    }

    public String getFechaString() {
        int dia = getDia();
        int mes = getMes();
        return (dia < 10 ? "0" + dia : dia) + "/" + (mes < 10 ? "0" + mes : mes) + "/" + _parseAnioActualFaster();
    }

    public String getFechaStringGuion() {
        int dia = getDia();
        int mes = getMes();
        return (dia < 10 ? "0" + dia : dia) + "-" + (mes < 10 ? "0" + mes : mes) + "-" + _parseAnioActualFaster();
    }

    public String getFechaString(String separador) {
        int dia = getDia();
        int mes = getMes();
        return (dia < 10 ? "0" + dia : dia) + separador + (mes < 10 ? "0" + mes : mes) + separador + _parseAnioActualFaster();
    }

    public String getHoraString() {
        return new SimpleDateFormat("hh:mm:ss a").format(toDate()).toUpperCase();
    }

    public String getHora24String() {
        return new SimpleDateFormat("HH:mm:ss").format(toDate());
    }

    private static boolean _esIntFaster(String valor, int izq, int der) {
        try {
            if (valor == null) return false;
            int valorInt = Integer.parseInt(valor); // ANTES: valor.trim() , AHORA: YA NO
            return izq <= valorInt && valorInt <= der;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private String _parseAnioActualFaster() {
        int anio = getAnio();

        if (anio > 999) return "" + anio;
        if (anio > 99) return "0" + anio;
        if (anio > 9) return "00" + anio;

        return "000" + anio;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DateTime) {
            DateTime objCast = (DateTime) obj;
            if (objCast == this) return true;
            return c.compareTo(objCast.getCalendar()) == 0;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.c != null ? this.c.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return getFechaString();
    }

    public static String getCreditos() {
        return com.jcfr.utiles.Constantes.MSG_CREDITOS;
    }
}
