package com.study.util;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class ExcelUtil {
    public static Integer getCellIntegerValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        Integer cellValue = null;
        if (cell.getCellType() == CellType.NUMERIC) {
            Double value = cell.getNumericCellValue();
            cellValue = value.intValue();
        }
        return cellValue;
    }

    public static Float getCellFloatValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        Float cellValue = null;
        if (cell.getCellType() == CellType.NUMERIC) {
            cellValue = (float)cell.getNumericCellValue();
        }
        return cellValue;
    }

    public static String getCellStringValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        String cellValue = "";

        if (cell.getCellType() == CellType.STRING) {
            cellValue = cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            cellValue = String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.FORMULA) {
            cellValue = cell.getCellFormula();
        }
        return cellValue;
    }
}
