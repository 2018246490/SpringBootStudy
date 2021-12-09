package com.escope.study.utils;

import java.util.*;

/**
 * 测试数据生成工具
 * 2021/8/11
 */
public class DemoDataUtil {
    public static List getDemoData() {
        ArrayList arr = new ArrayList();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", 1001);
        map1.put("value", runodNum(20, 60));
        arr.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("id", 1002);
        map2.put("value", runodNum(20, 60));
        arr.add(map2);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("id", 1003);
        map3.put("value", runodNum(20, 60));
        arr.add(map3);
        return arr;
    }

    private static int runodNum(int start, int end) {
        Random rand = new Random();
        return rand.nextInt(end - start + 1) + start;
    }
}
