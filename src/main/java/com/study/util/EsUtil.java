package com.study.util;

import java.lang.reflect.Field;

/**
 * @Auther  吕伟林
 * @Des 对象属性赋值类
 * @Date 2021/6/25 12:01
 */
public class EsUtil {

    /**
     * @Auther  吕伟林
     * @Des 将对象中的非空属性 赋值给 目标属性
     * @param origin 起源对象
     * @param destination 目标对象
     * @param <T>
     */
    public static <T> void mergeObject(T origin, T destination) {
        if (origin == null || destination == null)
            return;
        if (!origin.getClass().equals(destination.getClass()))
            return;

        Field[] fields = origin.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i].setAccessible(true);
                Object value = fields[i].get(origin);
                if (null != value) {
                    fields[i].set(destination, value);
                }
                fields[i].setAccessible(false);
            } catch (Exception e) {
            }
        }
    }
}
