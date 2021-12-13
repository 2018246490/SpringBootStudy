package com.study.system.annotation;

/**
 * @Auther  吕伟林
 * @Des 权限标签常量
 * @Date 2021/6/25 11:56
 */
@Deprecated
public class RoleAnnotationType {
    /**
     * 比较方式 =  !=
     */
    public static final String EQUALS = "equals";
    public static final String NOT_EQUALS = "not_equals";

    /**
     * 多参数间的并列关系
     */
    public static final String AND = "and";
    public static final String OR = "or";
}
