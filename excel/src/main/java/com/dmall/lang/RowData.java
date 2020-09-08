package com.dmall.lang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wangxi
 * @date 2020/9/2
 * @comment Row数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RowData {
    /**
     * 每行数据
     */
    private List<ColumnData> rows;
    /**
     * 行号
     */
    private Integer rowIndex;

    /**
     * 是否每一列都有数据
     */
    private Boolean isFull;
}
