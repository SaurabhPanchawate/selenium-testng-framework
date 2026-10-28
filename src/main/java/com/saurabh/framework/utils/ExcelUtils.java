package com.saurabh.framework.utils;

import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {

    private ExcelUtils() {
    }

    /**
     * Reads a sheet from the classpath (e.g. "testdata/LoginData.xlsx").
     * Row 1 is treated as the header and skipped. Every cell is returned as a String.
     * Blank rows are ignored.
     */
    public static Object[][] readSheet(String classpathFile, String sheetName) {
        try (InputStream in = ExcelUtils.class.getClassLoader().getResourceAsStream(classpathFile)) {
            if (in == null) {
                throw new RuntimeException("Excel file not found on classpath: " + classpathFile);
            }
            try (Workbook wb = WorkbookFactory.create(in)) {
                Sheet sheet = wb.getSheet(sheetName);
                if (sheet == null) {
                    throw new RuntimeException("Sheet not found: " + sheetName);
                }
                DataFormatter fmt = new DataFormatter();
                int colCount = sheet.getRow(0).getLastCellNum();
                List<Object[]> rows = new ArrayList<>();

                for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                    Row row = sheet.getRow(r);
                    if (row == null) {
                        continue;
                    }
                    Object[] data = new Object[colCount];
                    boolean allBlank = true;
                    for (int c = 0; c < colCount; c++) {
                        Cell cell = row.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        String value = (cell == null) ? "" : fmt.formatCellValue(cell).trim();
                        data[c] = value;
                        if (!value.isEmpty()) {
                            allBlank = false;
                        }
                    }
                    if (!allBlank) {
                        rows.add(data);
                    }
                }
                return rows.toArray(new Object[0][]);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to read Excel file: " + classpathFile, e);
        }
    }
}
