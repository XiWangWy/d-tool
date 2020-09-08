package com.dmall.core;

import com.dmall.core.ExcelHeader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangxi
 * @date 2020/9/2
 * @comment
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class TestEntity {

    @ExcelHeader(header = "商家編碼")
    private String vendorId;
    @ExcelHeader(header = "商家門店編碼")
    private String storeCode;
    @ExcelHeader(header = "商品編碼")
    private String matnr;
    @ExcelHeader(header = "时间")
    private String time;
    @ExcelHeader(header = "Float")
    private String floatData;
}
