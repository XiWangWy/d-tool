package com.dmall.utils;

import java.util.List;

/**
 * @author wangxi
 * @date 2020/9/2
 * @comment
 */
public class StrUtil {
    /**
     * 打印list结果
     * @param list
     */
    public static void listPrint(List<?> list){
        for (Object ob :list) {
            System.out.println(ob.toString());
        }
    }
}
