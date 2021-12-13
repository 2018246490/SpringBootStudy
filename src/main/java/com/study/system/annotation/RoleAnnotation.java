package com.study.system.annotation;

import java.lang.annotation.*;

/**
 * @Auther  吕伟林
 * @Des 角色权限标签  sys_role表的level
 * @Date 2021/6/25 12:00
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Deprecated
public @interface RoleAnnotation {
    /**
     * 需要检测的角色权限 多个用,隔开
     * @return
     */
    String value() default "";
    String compare() default RoleAnnotationType.EQUALS;
    String relation() default RoleAnnotationType.AND;
}
