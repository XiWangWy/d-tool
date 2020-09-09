package com.dmall.core.writer;

import com.dmall.lang.FieldData;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wangxi
 * @date 2020/9/2
 * @comment
 */
public class ExcelWriter extends AbstractExcelWriter{

    private static final ThreadLocal<SimpleDateFormat> threadLocalExcel = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss"));
    /**
     * 下载excel
     * @param workbook
     * @param fileName
     */
    public void downloadExcel(SXSSFWorkbook workbook, String fileName, HttpServletResponse response){
        try {
            Date day=new Date();
            String excel =  URLEncoder.encode(fileName + threadLocalExcel.get().format(day), "UTF-8") + ".xlsx";
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + excel);
            response.setHeader("Access-Control-Expose-Headers","content-disposition");
            // 响应类型,编码
            // 形成输出流
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.dispose();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public void exportExcelFile(SXSSFWorkbook workbook, String fileName){
        OutputStream outputStream = new FileOutputStream(fileName);
        workbook.write(outputStream);
        workbook.dispose();
        workbook.close();
    }

    @SneakyThrows
    public void exportExcelFile(List<?> source, String fileName){
        SXSSFWorkbook workbook = (SXSSFWorkbook) exportWorkBook(source);
        exportExcelFile(workbook,fileName);
    }

}
