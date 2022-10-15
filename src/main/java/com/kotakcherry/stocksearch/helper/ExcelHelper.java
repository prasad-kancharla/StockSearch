package com.kotakcherry.stocksearch.helper;

import com.kotakcherry.stocksearch.model.Stock;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"Id", "Name", "Sector", "MarketCap", "ClosePrice", "PERatio"};
    static String SHEET = "Stocks";

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<Stock> excelToStocks(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Stock> stocks = new ArrayList<Stock>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Stock stock = new Stock();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            stock.setId((long) currentCell.getNumericCellValue());
                            break;

                        case 1:
                            stock.setName(currentCell.getStringCellValue());
                            break;

                        case 2:
                            stock.setSector(currentCell.getStringCellValue());
                            break;

                        case 3:
                            stock.setMarketCap(currentCell.getNumericCellValue());
                            break;

                        case 4:
                            stock.setClosePrice(currentCell.getNumericCellValue());
                            break;
                        case 5:
                            stock.setPeRatio(currentCell.getNumericCellValue());
                            break;

                        default:
                            break;
                    }

                    cellIdx++;
                }

                stocks.add(stock);
            }

            workbook.close();

            return stocks;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
