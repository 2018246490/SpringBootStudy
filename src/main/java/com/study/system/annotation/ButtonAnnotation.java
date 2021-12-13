package com.study.system.annotation;

import java.lang.annotation.*;

/**
 * @Auther  吕伟林
 * @Des 功能标签标签  在controller中使用 用于标记方法属于什么功能
 * @Date 2021/6/25 12:00
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ButtonAnnotation {
    /**
     * 该功能属于什么菜单  属于多个菜单,隔开  sys_menu表的code字段
     * @return
     */
    String[] menuCode();

    /**
     * @Auther  吕伟林
     * @Des 接口功能类型   全部类型在 ButtonAnnotationType类中
     * @Date 2021/6/29 15:20
     */
    String[] buttonType();
}
