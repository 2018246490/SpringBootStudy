package com.study.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Auther  吕伟林
 * @Des 服务器cookise工具类
 * @Date 2021/6/25 12:01
 */
public class CookiesUtil {


    /**
     * 设置cookie
     *
     * @param response
     * @param name     cookie名字
     * @param value    cookie值
     * @param maxAge   cookie生命周期  以秒为单位
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        try {
            Cookie cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
            cookie.setPath("/");
            if (maxAge > 0)
                cookie.setMaxAge(maxAge);
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据名字获取cookie
     *
     * @param request
     * @param name    cookie名字
     * @return
     */
    public static String getCookieByName(HttpServletRequest request, String name) {
        Map<String, String> cookieMap = readCookieMap(request);
        try {
            if (cookieMap.containsKey(name)) {
                String cookie = (String) cookieMap.get(name);
                cookie = URLDecoder.decode(cookie, "UTF-8");
                return cookie;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将cookie封装到Map里面
     *
     * @param request
     * @return
     */
    public static Map<String, String> readCookieMap(HttpServletRequest request) {
        Map<String, String> cookieMap = new HashMap<String, String>();
        try {
            Cookie[] cookies = request.getCookies();
            if (null != cookies) {
                for (Cookie cookie : cookies) {
                    String cookiestr = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    cookieMap.put(cookie.getName(), cookiestr);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cookieMap;
    }

    /**
     * 将cookie封装到Map里面
     *
     * @param response
     * @param timeout  cookie生命周期  以秒为单位
     * @return
     */
    public static boolean writeCookieMap(HttpServletResponse response, int timeout, Map map) {
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object value = map.get(key); //类型强转失败会异常
            addCookie(response, key, String.valueOf(value), timeout);
        }
        return true;
    }
}
