package com.dmall.core.reader;

import com.dmall.lang.RowData;
import com.dmall.lang.SheetData;
import lombok.SneakyThrows;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wangxi
 * @date 2020/9/2
 * @comment Excel解析类
 */
public class ExcelReader extends AbstractExcelReader{
    @SneakyThrows
    @Override
    public List<SheetData> parse(InputStream inputStream) {
        return parse(inputStream,RowData.class);
    }

    @Override
    public List<SheetData> parse(String filePath) throws IOException {
        File file = new File(filePath);
        InputStream inputStream = new FileInputStream(file);
        return parse(inputStream,RowData.class);
    }

    @Override
    public <R> List<SheetData> parse(String filePath, Class<R> className) throws IOException {
        File file = new File(filePath);
        InputStream inputStream = new FileInputStream(file);
        return parse(inputStream,className);
    }

    @SneakyThrows
    @Override
    public <R> List<SheetData> parse(InputStream inputStream, Class<R> className) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        if (className == null) return parse(inputStream);
        List<SheetData> sheetDataList = Lists.newArrayList();
        Workbook workbook = getWorkBook(inputStream);
        int numberSheets = getNumberOfSheets(workbook);
        if (!className.equals(RowData.class)) {
            if (numberSheets > 1) throw  new RuntimeException("not support more than 1 sheet !!!");
        }
        for (int i = 0; i < numberSheets; i++) {
            SheetData sheetData = new SheetData();
            Sheet sheet = getSheet(workbook,i);
            List<?> rowDataList = parseSheet(sheet,className);
            sheetData.setRows(rowDataList);
            sheetData.setSheetIndex(i);
            sheetData.setSheetName(sheet.getSheetName());
            sheetDataList.add(sheetData);sheet.getSheetName();
        }
        stopWatch.stop();
        System.out.println(String.format("=============解析耗时: %d 秒============",stopWatch.getTime(TimeUnit.SECONDS)));
        return sheetDataList;
    }
}
