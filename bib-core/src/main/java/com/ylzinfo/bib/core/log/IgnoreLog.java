package com.ylzinfo.bib.core.log;

import java.lang.annotation.*;

/********************************************************************************
 *
 * Title: 忽略日志
 *
 * Description: 此注解放入controller上 日志aop功能会忽略打印，即表明日志不打印
 *
 * @author chenlm
 * create date on 2019/3/28
 *
 *******************************************************************************/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreLog {
    /**
     * 是否忽略日志
     *
     * @return
     */
    boolean value() default true;
}
