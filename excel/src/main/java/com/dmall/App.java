package com.dmall;

import com.dmall.core.TestEntity;
import com.dmall.core.WareWhiteListVO;
import com.dmall.core.reader.ExcelReader;
import com.dmall.core.writer.ExcelWriter;
import com.dmall.lang.SheetData;
import com.dmall.utils.StrUtil;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ExcelReader excelReader = new ExcelReader();
        try {
//         List<SheetData> sheetDataList = excelReader.parse("/Users/wangxi/Desktop/wareWhiteList.xlsx");
//         StrUtil.listPrint(sheetDataList);
//
//            List<SheetData> sheetDataList2 = excelReader.parse("/Users/wangxi/Desktop/wareWhiteList.xlsx", TestEntity.class);
//            StrUtil.listPrint(sheetDataList2);

            List<SheetData> sheetDataList3 = excelReader.parse("/Users/wangxi/Desktop/wareWhiteList_2020-09-04-13_39_53.xlsx", WareWhiteListVO.class);
//            StrUtil.listPrint(sheetDataList3);
            ExcelWriter excelWriter = new ExcelWriter();
            excelWriter.exportExcelFile(sheetDataList3.get(0).getRows(), "/Users/wangxi/Desktop/wx.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
