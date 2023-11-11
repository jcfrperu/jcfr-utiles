package com.jcfr.utiles.excel;

import com.jcfr.utiles.string.JSUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Date;
import java.util.List;

/**
 * Esta clase sirve como utilitario para generar documentos EXCEL (Formato antiguo 2007, no son los .xlsx) utilizando
 * la libreria POI
 */
public class ExcelUtilXLS {

    private static final JSUtil JS = JSUtil.JSUtil;

    // libro
    private HSSFWorkbook workbook;

    // hoja de trabajo actual
    private HSSFSheet sheet;

    // fila actual
    private HSSFRow row;

    // posicion fila actual
    private int rowPosition;

    // creador de estilos
    private ExcelStyle styles;

    public ExcelUtilXLS() {

        // inicializar el libro
        workbook = new HSSFWorkbook();

        // crear la configuracion/generador de estilos
        styles = new ExcelStyle(workbook);
    }

    public ExcelUtilXLS(HSSFWorkbook workbook) {

        // workbook no puede ser null
        if (workbook == null) {
            throw new IllegalArgumentException("argument workbook cannot be null");
        }

        // guardar el workbook
        this.workbook = workbook;

        // crear un manejador de estilos
        this.styles = new ExcelStyle(workbook);

        // por default abre la primera hoja
        if (this.workbook.getNumberOfSheets() > 0) {
            this.sheet = this.workbook.getSheetAt(0);
        }
    }

    public void openSheet(int position) {

        checkSheetPosition(position);

        // setear la hoja de estilo actual
        sheet = workbook.getSheetAt(position);
    }

    // crear hoja de estilo y filas
    public void xlsSheet(String sheetName) {

        // crear nueva hoja
        sheet = workbook.createSheet(JS._vacio(sheetName) ? "sheet01" : JS.toBlank(sheetName));

        // setear posicion de la fila al inicio (siempre invocar a xlsRow() para crear fila)
        rowPosition = -1;
    }

    public void xlsRow() {
        checkSheet();

        // crear row en la siguiente rowPosition
        row = sheet.createRow(++rowPosition);
    }

    // crear nuevas de celdas
    public void xlsCell(int column, Double value) {
        if (value == null) {
            xlsCellNull(column);
        } else {
            xlsCell(column, value.doubleValue());
        }
    }

    public void xlsCell(int column, Double value, HSSFCellStyle customStyle) {
        if (value == null) {
            xlsCellNull(column, customStyle);
        } else {
            xlsCell(column, value.doubleValue(), customStyle);
        }
    }

    public void xlsCell(int column, Integer value) {
        if (value == null) {
            xlsCellNull(column);
        } else {
            xlsCell(column, value.intValue());
        }
    }

    public void xlsCell(int column, Integer value, HSSFCellStyle customStyle) {
        if (value == null) {
            xlsCellNull(column, customStyle);
        } else {
            xlsCell(column, value.intValue(), customStyle);
        }
    }


    public void xlsCell(int column, String value) {
        HSSFCell cell = row.createCell(column);
        cell.setCellValue(new HSSFRichTextString(value == null ? "" : value));
        cell.setCellStyle(styles.getDefaultNormalStyle());
    }

    public void xlsCell(int column, String value, HSSFCellStyle customStyle) {
        HSSFCell cell = row.createCell(column);
        cell.setCellValue(new HSSFRichTextString(value == null ? "" : value));
        cell.setCellStyle(customStyle);
    }

    public void xlsCell(int column, Date value) {
        HSSFCell cell = row.createCell(column);
        if (value != null) cell.setCellValue(value);
        cell.setCellStyle(styles.getDefaultDateStyle());
    }

    public void xlsCell(int column, Date value, HSSFCellStyle customStyle) {
        HSSFCell cell = row.createCell(column);
        if (value != null) cell.setCellValue(value);
        cell.setCellStyle(customStyle);
    }

    public void xlsCell(int column, Boolean value) {
        if (value == null) {
            xlsCellNull(column);
        } else {
            xlsCell(column, value.booleanValue());
        }
    }

    public void xlsCell(int column, Boolean value, HSSFCellStyle customStyle) {
        if (value == null) {
            xlsCellNull(column, customStyle);
        } else {
            xlsCell(column, value.booleanValue(), customStyle);
        }
    }

    public void xlsCellNull(int column) {
        HSSFCell cell = row.createCell(column);
        cell.setCellValue((String) null);
        cell.setCellStyle(styles.getDefaultNormalStyle());
    }

    public void xlsCellNull(int column, HSSFCellStyle customStyle) {
        HSSFCell cell = row.createCell(column);
        cell.setCellValue((String) null);
        cell.setCellStyle(customStyle);
    }

    private void xlsCell(int column, double value) {
        HSSFCell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(styles.getDefaultNormalStyle());
    }

    private void xlsCell(int column, double value, HSSFCellStyle customStyle) {
        HSSFCell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(customStyle);
    }

    private void xlsCell(int column, boolean value) {
        HSSFCell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(styles.getDefaultNormalStyle());
    }

    private void xlsCell(int column, boolean value, HSSFCellStyle customStyle) {
        HSSFCell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(customStyle);
    }

    // validaciones de tipo de celda
    public boolean isBlank(Cell cell) {

        return cell != null && cell.getCellType() == Cell.CELL_TYPE_BLANK;
    }

    public boolean isFormula(Cell cell) {

        return cell != null && cell.getCellType() == Cell.CELL_TYPE_FORMULA;
    }

    public boolean isBoolean(Cell cell) {

        return cell != null && cell.getCellType() == Cell.CELL_TYPE_BOOLEAN;
    }

    public boolean isString(Cell cell) {

        return cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING;
    }

    public boolean isDate(Cell cell) {
        return isNumeric(cell) && HSSFDateUtil.isCellDateFormatted(cell);
    }

    public boolean isNumber(Cell cell) {

        return isNumeric(cell) && !HSSFDateUtil.isCellDateFormatted(cell);
    }

    private boolean isNumeric(Cell cell) {

        return cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC;
    }

    // operaciones de juntar celdas
    public void joinColumns(int firstCol, int lastCol) {
        checkSheet();
        // unir celdas (en la fila actual)
        sheet.addMergedRegion(new CellRangeAddress(rowPosition, rowPosition, firstCol, lastCol));
    }

    public void joinRowsColumns(int firstRow, int lastRow, int firstCol, int lastCol) {
        checkSheet();
        // unir celdas (en filas y columnas arbitrarias
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    // setear posicion y hojas de trabajo actuales
    public HSSFSheet getSheet() {
        return sheet;
    }

    public HSSFRow getRow() {
        return row;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(int rowPosition) {

        checkRowPosition(rowPosition);

        this.rowPosition = rowPosition;
        this.row = sheet.getRow(rowPosition);
    }

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

    // clase para manejo de estilos
    public ExcelStyle getStyles() {
        return styles;
    }

    public void setStyles(ExcelStyle styles) {
        if (styles == null) throw new IllegalArgumentException("argument styles cannot be null");
        this.styles = styles;
    }

    public void checkSheetPosition(int sheetPosition) {
        checkWorkbook();
        if (sheetPosition < 0 || sheetPosition > workbook.getNumberOfSheets()) {
            throw new IllegalArgumentException("argument sheetPosition is not a valid range value");
        }
    }

    public void checkRowPosition(int rowPosition) {
        checkSheet();
        if (rowPosition < sheet.getFirstRowNum() || rowPosition > sheet.getLastRowNum()) {
            throw new IllegalArgumentException("rowPosition (" + rowPosition + ") does not exist in current sheet (" + sheet.getSheetName() + ")");
        }
    }

    private void checkSheet() {
        if (sheet == null) throw new IllegalArgumentException("sheet must be initialised");
    }

    private void checkWorkbook() {
        if (workbook == null) throw new IllegalArgumentException("workbook must be initialised");
    }

    public HSSFCellStyle cloneStyleFrom(HSSFCell cell) {

        HSSFCellStyle style = workbook.createCellStyle();

        style.cloneStyleFrom(cell.getCellStyle());

        return style;
    }


    // METODOS ADICIONALES PARA CREAR BLOQUES (CABECERA MAS TABLA)
    public void xlsBloque(List<String> columnasTabla, int columnaInicio, List<String> itemsEncabezado, int joinItemsEncabezado) {

        xlsRow();

        // AGREGANDO LOS ITEMS DEL TITULO
        if (itemsEncabezado != null && !itemsEncabezado.isEmpty()) {
            HSSFCellStyle estilo = styles.createStyleBloqueEncabezado(false);
            int joinSize = Math.max(2, joinItemsEncabezado);
            for (String item : itemsEncabezado) {
                xlsRow();
                xlsCell(columnaInicio, item, estilo);
                joinColumns(columnaInicio, joinSize);
            }
        }

        xlsRow();
        xlsRow();

        // AGREGANDO LOS ITEMS EN LAS CABECERA DE LA TABLA
        if (columnasTabla != null && !columnasTabla.isEmpty()) {
            HSSFCellStyle estilo = styles.createStyleBloqueTablaHeader(false);
            int size = columnasTabla.size();
            int colInicio = columnaInicio;
            for (int c = 1; c <= size; c++) {
                xlsCell(colInicio++, columnasTabla.get(c - 1), estilo);
            }
        }

    }


    // CLASE INTERNA PARA MANEJAR ESTILOS
    public static class ExcelStyle {

        // libro
        private HSSFWorkbook workbook;

        // formato por default
        private short fontSize;
        private short fontColor;
        private String fontName;

        // estilos por default/comunes
        private HSSFCellStyle defaultDateStyle;
        private HSSFCellStyle defaultNormalStyle;

        public ExcelStyle(HSSFWorkbook workbook) {

            this(workbook, "Arial", HSSFColor.DARK_BLUE.index, (short) 10);
        }

        public ExcelStyle(HSSFWorkbook workbook, String fontName, short fontColor, short fontSize) {

            if (workbook == null) throw new IllegalArgumentException("argument workbook cannot be null");

            this.workbook = workbook;
            this.fontName = fontName;
            this.fontSize = fontSize;
            this.fontColor = fontColor;

            // crear los estilos basicos
            this.defaultDateStyle = createStyleDate(false);
            this.defaultNormalStyle = createStyleNormal(false);
        }

        public HSSFCellStyle createStyleNormal(boolean borders) {
            // ESTILO GENERAL, NORMAL PARA TODAS LAS CELDAS
            HSSFCellStyle style = workbook.createCellStyle();

            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(fontSize);
            font.setFontName(fontName);
            font.setColor(fontColor);
            style.setFont(font);
            if (borders) {
                fillBorders(style, true, true, true, true);
            }
            return style;
        }

        public HSSFCellStyle createStyleBloqueEncabezado(boolean borders) {
            // ESTILO GENERAL, NORMAL PARA TODAS LAS CELDAS
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(fontSize);
            font.setFontName(fontName);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setColor(fontColor);
            style.setFont(font);
            if (borders) {
                fillBorders(style, true, true, true, true);
            }
            return style;
        }

        public HSSFCellStyle createStyleBloqueTablaHeader(boolean borders) {
            // ESTILO GENERAL, NORMAL PARA TODAS LAS CELDAS
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(fontSize);
            font.setFontName(fontName);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setColor(fontColor);
            style.setFont(font);
            if (borders) {
                fillBorders(style, true, true, true, true);
            }
            return style;
        }

        public HSSFCellStyle createStyleNegrita(boolean borders) {
            // ESTILO GENERAL, NORMAL PARA TODAS LAS CELDAS
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(fontSize);
            font.setFontName(fontName);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setColor(fontColor);
            style.setFont(font);
            if (borders) {
                fillBorders(style, true, true, true, true);
            }
            return style;
        }

        public HSSFCellStyle createStyleNormalCenter(boolean borders) {
            // CREANDO ESTILO CENTRADA
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(fontSize);
            font.setFontName(fontName);
            font.setColor(fontColor);
            style.setFont(font);
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            if (borders) {
                fillBorders(style, true, true, true, true);
            }
            return style;
        }

        public HSSFCellStyle createStyleNormalDerecha(boolean borders) {
            // CREANDO ESTILO DERECHA
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(fontSize);
            font.setFontName(fontName);
            font.setColor(fontColor);
            style.setFont(font);
            style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            if (borders) {
                fillBorders(style, true, true, true, true);
            }
            return style;
        }

        public HSSFCellStyle createStyleNegritaCenter(boolean borders) {
            // CREANDO ESTILO CENTRADA
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(fontSize);
            font.setFontName(fontName);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setColor(fontColor);
            style.setFont(font);
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            if (borders) {
                fillBorders(style, true, true, true, true);
            }
            return style;
        }

        public HSSFCellStyle createStyleNegritaDerecha(boolean borders) {
            // CREANDO ESTILO DERECHA
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(fontSize);
            font.setFontName(fontName);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setColor(fontColor);
            style.setFont(font);
            style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            if (borders) {
                fillBorders(style, true, true, true, true);
            }
            return style;
        }

        public HSSFCellStyle createStyleTotal(boolean borders) {
            // ESTILO PARA EL TEXTO EN EL TOTALIZADO
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(fontSize);
            font.setFontName(fontName);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setColor(HSSFColor.GREY_80_PERCENT.index);
            style.setFont(font);
            if (borders) {
                fillBorders(style, true, true, true, true);
            }
            return style;
        }

        public HSSFCellStyle createStyleTotalRight(boolean borders) {
            // ESTILO PARA EL TEXTO EN EL TOTALIZADO DERECHA
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(fontSize);
            font.setFontName(fontName);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setColor(HSSFColor.GREY_80_PERCENT.index);
            style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            style.setFont(font);
            if (borders) {
                fillBorders(style, true, true, true, true);
            }
            return style;
        }

        public HSSFCellStyle createStyleTotalCenter(boolean borders) {
            // ESTILO PARA EL TEXTO EN EL TOTALIZADO CENTRADO
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(fontSize);
            font.setFontName(fontName);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setColor(HSSFColor.GREY_80_PERCENT.index);
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setFont(font);
            if (borders) {
                fillBorders(style, true, true, true, true);
            }
            return style;
        }

        public HSSFCellStyle createStyle2Digits(boolean borders) {
            // ESTILO PARA LOS NUMEROS REDONDEADOS A 2 DIGITOS
            HSSFCellStyle style = workbook.createCellStyle();
            style.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(fontSize);
            font.setFontName(fontName);
            font.setColor(fontColor);
            style.setFont(font);
            if (borders) {
                fillBorders(style, true, true, true, true);
            }
            return style;
        }

        public HSSFCellStyle createStyle2DigitsTotal(boolean borders) {
            // ESTILO PARA LOS NUMEROS REDONDEADOS A 2 DIGITOS QUE TOTALIAZN
            HSSFCellStyle style = workbook.createCellStyle();
            style.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(fontSize);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setFontName(fontName);
            font.setColor(HSSFColor.GREY_80_PERCENT.index);
            style.setFont(font);
            if (borders) {
                fillBorders(style, true, true, true, true);
            }
            return style;
        }

        public HSSFCellStyle createStyle3Digits(boolean borders) {
            // ESTILO PARA LOS NUMEROS REDONDEADOS A 3 DIGITOS
            HSSFCellStyle style = workbook.createCellStyle();
            style.setDataFormat(workbook.createDataFormat().getFormat("0.000"));
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(fontSize);
            font.setFontName(fontName);
            font.setColor(fontColor);
            style.setFont(font);
            if (borders) {
                fillBorders(style, true, true, true, true);
            }
            return style;
        }

        public HSSFCellStyle createStyle3DigitsTotal(boolean borders) {
            // ESTILO PARA LOS NUMEROS REDONDEADOS A 3 DIGITOS QUE TOTALIZAN
            HSSFCellStyle style = workbook.createCellStyle();
            style.setDataFormat(workbook.createDataFormat().getFormat("0.000"));
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(fontSize);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setFontName(fontName);
            font.setColor(HSSFColor.GREY_80_PERCENT.index);
            style.setFont(font);
            if (borders) {
                fillBorders(style, true, true, true, true);
            }
            return style;
        }

        public HSSFCellStyle createStyleNroDigits(int nroDigits, boolean borders) {
            // ESTILO PARA LOS NUMEROS REDONDEADOS A 3 DIGITOS
            HSSFCellStyle style = workbook.createCellStyle();
            style.setDataFormat(workbook.createDataFormat().getFormat("0." + REPLICATE('0', nroDigits)));
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(fontSize);
            font.setFontName(fontName);
            font.setColor(fontColor);
            style.setFont(font);
            if (borders) {
                fillBorders(style, true, true, true, true);
            }
            return style;
        }

        public HSSFCellStyle createStyleNroDigitsTotal(int nroDigits, boolean borders) {
            // ESTILO PARA LOS NUMEROS REDONDEADOS A 3 DIGITOS QUE TOTALIAZN
            HSSFCellStyle style = workbook.createCellStyle();
            style.setDataFormat(workbook.createDataFormat().getFormat("0." + REPLICATE('0', nroDigits)));
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(fontSize);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setFontName(fontName);
            font.setColor(HSSFColor.GREY_80_PERCENT.index);
            style.setFont(font);
            if (borders) {
                fillBorders(style, true, true, true, true);
            }
            return style;
        }

        public HSSFCellStyle createStyleDate(boolean borders) {
            // ESTILO PARA LAS FECHAS
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(fontSize);
            font.setFontName(fontName);
            font.setColor(fontColor);
            style.setFont(font);
            HSSFDataFormat format = workbook.createDataFormat();
            style.setDataFormat(format.getFormat("dd/mm/yyyy"));
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            if (borders) {
                fillBorders(style, true, true, true, true);
            }
            return style;
        }

        public HSSFCellStyle createStyleDatetime(boolean borders) {
            // ESTILO PARA LAS FECHAS
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(fontSize);
            font.setFontName(fontName);
            font.setColor(fontColor);
            style.setFont(font);
            HSSFDataFormat format = workbook.createDataFormat();
            style.setDataFormat(format.getFormat("dd/mm/yyyy hh:mm:ss AM/PM"));
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            if (borders) {
                fillBorders(style, true, true, true, true);
            }
            return style;
        }

        public void fillBorders(HSSFCellStyle style, boolean arriba, boolean derecha, boolean abajo, boolean izquierda) {

            fillBorders(style, arriba, derecha, abajo, izquierda, HSSFColor.GREY_80_PERCENT.index, HSSFCellStyle.BORDER_THIN);
        }

        public void fillBorders(HSSFCellStyle style, boolean arriba, boolean derecha, boolean abajo, boolean izquierda, short color, short border) {

            if (style == null) throw new IllegalArgumentException("argument style cannot be null");

            if (arriba) {
                style.setTopBorderColor(color);
                style.setBorderTop(border);
            }

            if (derecha) {
                style.setRightBorderColor(color);
                style.setBorderRight(border);
            }

            if (abajo) {
                style.setBottomBorderColor(color);
                style.setBorderBottom(border);
            }

            if (izquierda) {
                style.setLeftBorderColor(color);
                style.setBorderLeft(border);
            }
        }

        private String REPLICATE(char valor, int nroVeces) {
            if (nroVeces <= 0) return "";
            StringBuilder s = new StringBuilder(nroVeces);
            for (int i = nroVeces; --i >= 0; ) {
                s.append(valor);
            }
            return s.toString();
        }


        public HSSFCellStyle getDefaultDateStyle() {
            return defaultDateStyle;
        }

        public void setDefaultDateStyle(HSSFCellStyle defaultDateStyle) {
            if (defaultDateStyle == null) {
                throw new IllegalArgumentException("argument defaultDateStyle cannot be null");
            }
            this.defaultDateStyle = defaultDateStyle;
        }

        public HSSFCellStyle getDefaultNormalStyle() {
            return defaultNormalStyle;
        }

        public void setDefaultNormalStyle(HSSFCellStyle defaultNormalStyle) {
            if (defaultNormalStyle == null) {
                throw new IllegalArgumentException("argument defaultNormalStyle cannot be null");
            }
            this.defaultNormalStyle = defaultNormalStyle;
        }


    }

}