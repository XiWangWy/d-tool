package com.dmall.core;

import java.lang.annotation.*;

/**
 * @author wangxi
 * @date 2020/9/2
 * @comment
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD})
public @interface ExcelHeader {
    String header()default "";
    String field() default "";
    String mapping() default "";
    int order() default 0;
}
