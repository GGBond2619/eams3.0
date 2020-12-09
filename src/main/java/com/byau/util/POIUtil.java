
package com.byau.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class POIUtil {
    public static final String XLS = "xls";
    public static final String XLSX = "xlsx";

    public POIUtil() {
    }

    public static List<String[]> readExcelFile(MultipartFile excelFile, int startRow) throws IOException {
        checkFile(excelFile);
        Workbook workbook = getWorkBook(excelFile);
        List<String[]> list = new ArrayList();
        if (workbook != null) {
            for(int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); ++sheetNum) {
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet != null) {
                    int lastRowNum = sheet.getLastRowNum();
                    if (startRow < 0 || startRow > lastRowNum) {
                        throw new RuntimeException("wrong startRow");
                    }

                    for(int rowNum = startRow; rowNum <= lastRowNum; ++rowNum) {
                        Row row = sheet.getRow(rowNum);
                        if (row != null) {
                            int firstCellNum = row.getFirstCellNum();
                            int lastCellNum = row.getPhysicalNumberOfCells();
                            String[] cells = new String[row.getPhysicalNumberOfCells()];

                            for(int cellNum = firstCellNum; cellNum < lastCellNum; ++cellNum) {
                                Cell cell = row.getCell(cellNum);
                                cells[cellNum] = getCellValue(cell);
                            }

                            list.add(cells);
                        }
                    }
                }
            }
        }

        return list;
    }

    public static Workbook createExcelFile(List<String> attributes, List<List<String>> data, String extension) {
        Workbook workbook = null;
        if (StringUtils.isBlank(extension)) {
            return null;
        } else {
            if (extension.equalsIgnoreCase("xls")) {
                workbook = new HSSFWorkbook();
            } else if (extension.equalsIgnoreCase("xlsx")) {
                workbook = new XSSFWorkbook();
            }

            if (workbook != null) {
                Sheet sheet = ((Workbook)workbook).createSheet("sheet1");
                Row row0 = sheet.createRow(0);

                int i;
                for(i = 0; i < attributes.size(); ++i) {
                    Cell cell = row0.createCell(i);
                    cell.setCellValue(((String)attributes.get(i)).trim());
                }

                if (CollectionUtils.isNotEmpty(data)) {
                    for(i = 0; i < data.size(); ++i) {
                        List<String> rowInfo = (List)data.get(i);
                        Row row = sheet.createRow(i + 1);

                        for(int j = 0; j < rowInfo.size(); ++j) {
                            row.createCell(j).setCellValue((String)rowInfo.get(j));
                        }
                    }
                }
            }

            System.out.println("POIUtil createExcelFile");
            return (Workbook)workbook;
        }
    }

    private static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        } else {
            if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                cell.setCellType(CellType.STRING);
            }

            switch(cell.getCellTypeEnum()) {
                case NUMERIC:
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                case STRING:
                    cellValue = String.valueOf(cell.getStringCellValue());
                    break;
                case BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case FORMULA:
                    cellValue = String.valueOf(cell.getCellFormula());
                    break;
                case BLANK:
                    cellValue = "";
                    break;
                case ERROR:
                    cellValue = "非法字符";
                    break;
                default:
                    cellValue = "未知类型";
            }

            return cellValue;
        }
    }

    public static Workbook getWorkBook(MultipartFile excelFile) {
        String fileName = excelFile.getOriginalFilename();
        Object workbook = null;

        try {
            InputStream is = excelFile.getInputStream();
            if (fileName.endsWith("xls")) {
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return (Workbook)workbook;
    }

    public static void checkFile(MultipartFile excelFile) throws IOException {
        if (null == excelFile) {
            throw new FileNotFoundException("文件不存在");
        } else {
            String fileName = excelFile.getOriginalFilename();
            if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) {
                throw new IOException(fileName + "不是excel文件");
            }
        }
    }
}