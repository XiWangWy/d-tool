package com.dmall.lang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangxi
 * @date 2020/9/2
 * @comment Column数据
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ColumnData {
    /**
     * 每行数据
     */
    private Object columnValue;
    /**
     * 列号
     */
    private Integer columnIndex;
    /**
     * 字段对应的表头
     */
    private String columnName;
}
