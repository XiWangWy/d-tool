package com.dmall.core.reader;

import com.dmall.lang.RowData;
import com.dmall.lang.SheetData;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author wangxi
 * @date 2020/9/2
 * @comment
 */
public interface Reader {
    /**
     * 根据输入流获取workbook
     * @param inputStream
     * @return
     * @throws IOException
     */
    Workbook getWorkBook(InputStream inputStream) throws IOException;

    /**
     * 根据index获取sheet
     * @param workbook
     * @param sheetAt
     * @return
     */
    Sheet getSheet(Workbook workbook,Integer sheetAt);

    /**
     * 获取workbook中sheet数量
     * @param workbook
     * @return
     */
    Integer getNumberOfSheets(Workbook workbook);

    /**
     * 解析每个sheet
     * @param sheet
     * @return 返回每个sheet对应的所有的行数据
     */
    List<RowData> parseSheet(Sheet sheet) throws InstantiationException, IllegalAccessException;

    /**
     * 解析每个sheet
     * @param sheet
     * @return 返回每个sheet对应的所有的行数据
     */
   <R> List<R> parseSheet(Sheet sheet,Class<R> className) throws ClassNotFoundException, IllegalAccessException, InstantiationException;

    /**
     * 所有sheet的所有数据
     * @param inputStream
     * @return
     * @throws IOException
     */
    List<SheetData> parse(InputStream inputStream) throws IOException;

    /**
     * 所有sheet的所有数据
     * @param filePath
     * @return
     * @throws IOException
     */
    List<SheetData> parse(String filePath) throws IOException;

    /**
     * 解析数据并转化为对应class实体
     * @param filePath
     * @param className 转换的实体class
     * @return
     * @throws IOException
     */
    <R> List<SheetData> parse(String filePath,Class<R> className) throws IOException;

    /**
     * 解析数据并转化为对应class实体
     * @param inputStream
     * @param className 转换的实体class
     * @return
     * @throws IOException
     */
    <R> List<SheetData> parse(InputStream inputStream,Class<R> className) throws IOException;
}
