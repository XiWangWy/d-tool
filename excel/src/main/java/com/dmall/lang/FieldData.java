package com.dmall.lang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangxi
 * @date 2020/9/3
 * @comment
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class FieldData {
    /**
     * 表名
     */
    private String headerName;
    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 字段类型
     */
    private Class<?> fieldType;
    /**
     * 字段顺序；默认为0
     */
    private Integer order;
}
