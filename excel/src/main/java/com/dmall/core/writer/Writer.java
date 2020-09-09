package com.dmall.core.writer;

import com.dmall.lang.FieldData;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author wangxi
 * @date 2020/9/2
 * @comment
 */
public interface Writer {
    ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    /**
     * 创建workbook
     * @return
     */
    Workbook createWorkBook();

    /**
     * 创建sheet
     * @param workbook
     * @return
     */
    Sheet creatSheet(Workbook workbook);

    /**
     * 解析对象到可导出的
     * @param source
     * @return
     */
    List<FieldData> parseSource(List<?> source);

    /**
     * 构建表头
     * @param sheet
     * @param source
     */
    void createHeader(Sheet sheet,List<FieldData> source);

    /**
     * 构建数据
     * @param sheet
     * @param fields
     * @param source
     */
    @SneakyThrows
    void createValues(Sheet sheet, List<FieldData> fields, List<?> source);

    /**
     * 获取时间格式化simpformate
     * @return
     */
    default SimpleDateFormat getDateFormat(){
        return threadLocal.get();
    }

    /**
     * 设置时间格式化
     * @param simpleDateFormat
     */
    void setDateFormat(SimpleDateFormat simpleDateFormat);

    /**
     * 导出workbook
     * @param source
     * @return
     */
    default Workbook exportWorkBook(List<?> source){
        Workbook workbook = createWorkBook();
        Sheet sheet = creatSheet(workbook);
        List<FieldData> fieldDataList = parseSource(source);
        createHeader(sheet,fieldDataList);
        createValues(sheet,fieldDataList,source);
        return workbook;
    }
}
