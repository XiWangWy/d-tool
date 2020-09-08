package com.dmall.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author wangxi
 * @date 2020/8/25
 * @comment
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class WareWhiteListVO {
    /**
     * 白名单主键id
     */
    private Long id;

    /**
     * 门店名字
     */
    @ExcelHeader(header = "Store")
    private String storeName;

    private Long storeId;

    /**
     * 门店编码
     */
    @ExcelHeader(header = "Store_code",mapping = "storeCode")
    private String storeCode;
    /**
     * 物料编码
     */
    @ExcelHeader(header = "Item_no",mapping = "matnr")
    private String matnr;
    /**
     * 创建时间
     */
    @ExcelHeader(header = "Add time",mapping = "created")
    private Date created;
    /**
     * 商品状态
     */
    @ExcelHeader(header = "Status",mapping = "status")
    private Integer status;
    /**
     *品牌英文名
     */
    @ExcelHeader(header = "Brand-English",mapping = "brandEn")
    private String brandEn;
    /**
     * 品牌中文名
     */
    @ExcelHeader(header = "Brand-Chinese",mapping = "brandCn")
    private String brandCn;

    private Long brandId;
    /**
     * 商品英文名
     */
    @ExcelHeader(header = "Item Desc-English",mapping = "wareEn")
    private String wareEn;
    /**
     * 商品中文名
     */
    @ExcelHeader(header = "Item Desc-Chinese",mapping = "wareCn")
    private String wareCn;

    private Long wareId;
    /**
     * 商品规格
     */
    @ExcelHeader(header = "Item Size",mapping = "wareSize")
    private String wareSize;
    /**
     *獲取商品分類級別level=-Department的分類名稱
     */
    @ExcelHeader(header = "HKMS Dept",mapping = "dept")
    private String dept;
    /**
     * 二级分类id
     */
    private Long deptId;
    /**
     * 品类名字
     */
    @ExcelHeader(header = "HKMS Category Name",mapping = "categoryName")
    private String categoryName;

    /**
     * 三级分类id
     */
    private Long categoryNameId;
    /**
     * 品类id
     */
    @ExcelHeader(header = "HKMS Category No",mapping = "categoryNo")
    private String categoryNo;
    /**
     * 备注
     */
    @ExcelHeader(header = "Mark",mapping = "mark")
    private String mark;
    /**
     * 操作者
     */
    private String operation;
    /**
     * 商家id
     */
    private String vendorId;
}
