package com.dmall.core.reader;

import com.dmall.core.ExcelHeader;
import com.dmall.lang.ColumnData;
import com.dmall.lang.FieldData;
import com.dmall.lang.RowData;
import com.dmall.utils.CellUtil;
import lombok.SneakyThrows;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author wangxi
 * @date 2020/9/2
 * @comment 抽象类excelReader
 */
public abstract class AbstractExcelReader implements Reader  {

    private static Integer headerCount;

    @Override
    public Workbook getWorkBook(InputStream inputStream) throws IOException {
        return WorkbookFactory.create(inputStream);
    }

    @Override
    public Sheet getSheet(Workbook workbook,Integer sheetAt) {
        return workbook.getSheetAt(sheetAt);
    }

    @Override
    public Integer getNumberOfSheets(Workbook workbook) {
        return workbook.getNumberOfSheets();
    }

    @Override
    public List<RowData> parseSheet(Sheet sheet) {
        return parseSheet(sheet, RowData.class);
    }

    @SneakyThrows
    @Override
    public <R> List<R> parseSheet(Sheet sheet, Class<R> className) {
        //获取headerName -> FieldData
        Map<String, FieldData> fields = Arrays.stream(className.getDeclaredFields())
                .filter(field -> field.getAnnotation(ExcelHeader.class) != null)
                .map(field -> {
                    ExcelHeader excelHeader = field.getAnnotation(ExcelHeader.class);
                    String fieldName = field.getName();
                    String headerName = excelHeader.header();
                    return FieldData.of(headerName, fieldName,field.getType(),excelHeader.order());
                })
                .collect(Collectors.toMap(FieldData::getHeaderName, Function.identity()));

        int rows = sheet.getLastRowNum();
        List<R> rowDataLists = Lists.newArrayList();
        //表头
        Row headerRow = sheet.getRow(0);
        for (int i = 0; i <= rows; i++) {
            R rowData = className.newInstance();
            Row row = sheet.getRow(i);
            int columns = row.getLastCellNum();
            if(i ==0) headerCount = columns;
            if (!className.equals(RowData.class)){
                if (fields.size() != headerCount) throw new RuntimeException("Header not match entity,please look up your entity");
            }

            if(i == 0) continue;
            List<ColumnData> columnDataLists = Lists.newArrayList();
            LongAdder emptyCount = new LongAdder();
            for (int j = 0; j < columns; j++) {
                Cell cell = row.getCell(j);
                Object cellValue = null;
                if (cell != null) cellValue = CellUtil.getCellValue(cell);
                String headerName = (String) CellUtil.getCellValue(headerRow.getCell(j));

                if (rowData instanceof RowData){
                    //默认的数据结构
                    ColumnData columnData = new ColumnData();
                    if (cell != null){
                        columnData.setColumnValue(cellValue);
                        columnData.setColumnIndex(j);
                    }else {
                        columnData.setColumnValue(null);
                        columnData.setColumnIndex(j);
                        emptyCount.increment();
                    }
                    columnData.setColumnName(headerName);
                    columnDataLists.add(columnData);
                }else {
                    //自定义数据结构
                    FieldData fieldData = fields.get(headerName);
                    String fieldName = fieldData.getFieldName();
                    Class<?> value = fieldData.getFieldType();
                    FieldUtils.writeField(className.getDeclaredField(fieldName), rowData,CellUtil.convert(cellValue,value), true);
                }

            }

            if (rowData instanceof RowData){
                try {
                    Method setIsFull = rowData.getClass().getMethod("setIsFull",Boolean.class);
                    Method setRows = rowData.getClass().getMethod("setRows",List.class);
                    Method setRowIndex = rowData.getClass().getMethod("setRowIndex",Integer.class);
                    try {
                        setIsFull.invoke(rowData,columnDataLists.size() == headerCount & emptyCount.intValue() == 0);
                        setRows.invoke(rowData,columnDataLists);
                        setRowIndex.invoke(rowData,row.getRowNum());
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }

            rowDataLists.add(rowData);
        }
        return rowDataLists;
    }

}
