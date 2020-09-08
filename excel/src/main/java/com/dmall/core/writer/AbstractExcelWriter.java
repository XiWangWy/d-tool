package com.dmall.core.writer;

import com.dmall.core.ExcelHeader;
import com.dmall.lang.FieldData;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wangxi
 * @date 2020/9/2
 * @comment
 */
public abstract class AbstractExcelWriter implements Writer {
    @Override
    public Workbook createWorkBook() {
        //创建excel工作簿
        return new SXSSFWorkbook();
    }

    @Override
    public Sheet creatSheet(Workbook workbook) {
        //创建工作表sheet
        return workbook.createSheet();
    }

    @Override
    public List<FieldData> parseSource(List<?> source) {
        LinkedList<FieldData> linkedList = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(source)) {
            Object rowData = source.get(0);
            Field[] fields = rowData.getClass().getDeclaredFields();

            //取出所有需要导出的字段
            Arrays.stream(fields).forEach(field -> {
                ExcelHeader excelHeader = field.getAnnotation(ExcelHeader.class);
                if (excelHeader != null) {
                    String header = excelHeader.header();
                    int order = excelHeader.order();
                    if ("".equals(header)) header = field.getName();
                    linkedList.add(FieldData.of(header, field.getName(), field.getType(), order));
                }
            });
            linkedList.sort(Comparator.comparing(FieldData::getOrder));
        }
        return linkedList;
    }

    @Override
    public void createHeader(Sheet sheet, List<FieldData> source) {
        Row row = sheet.createRow(0);
        for (int i = 0; i < source.size(); i++) {
            FieldData head = source.get(i);
            Cell cell = row.createCell(i);
            cell.setCellValue(head.getHeaderName());
        }
    }

    @SneakyThrows
    @Override
    public void createValues(Sheet sheet, List<FieldData> fields, List<?> source) {
        for (int i = 0; i < source.size(); i++) {
            Row row = sheet.createRow(i+1);
            Object rowData = source.get(i);
            for (int j = 0; j <  fields.size(); j++) {
                FieldData fieldData = fields.get(j);
                Cell tmpCell = row.createCell(j);
                Field field = rowData.getClass().getDeclaredField(fieldData.getFieldName());
                String value;
                //Date类型的时间格式转换
                if (field.getType().getName().contains("Date")){
                    Date created = (Date) FieldUtils.readField(field, rowData, true);
                    value =  getDateFormat().format(created);
                }else {
                    Object object = FieldUtils.readField(field, rowData, true);
                    if (Objects.isNull(object)) object = "";
                    value = object.toString();
                }
                tmpCell.setCellValue(value);
            }
        }
    }

    @Override
    public void setDateFormat(SimpleDateFormat simpleDateFormat) {
        threadLocal.set(simpleDateFormat);
    }

    /**
     * 导出
     * @return
     */
    public abstract Workbook exportWorkBook(List<?> source);
}
