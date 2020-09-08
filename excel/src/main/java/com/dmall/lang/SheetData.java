package com.dmall.lang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangxi
 * @date 2020/9/2
 * @comment Sheet数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SheetData {
    /**
     * sheet index
     */
    private Integer sheetIndex;
    /**
     * 每个sheet的所有行数据
     */
    private List<?> rows;

    private String sheetName;

    @Override
    public String toString() {
        return "sheetIndex=" + sheetIndex + "   sheetName=" + sheetName + "\n"+ rows.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }
}
