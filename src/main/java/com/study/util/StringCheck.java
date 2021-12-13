package com.study.util;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串验证
 * 吕伟林
 * 2021/6/24
 */
public class StringCheck {

    /**
     * @Auther  吕伟林
     * @Des 验证是否手机号码
     * @Date 2021/6/24 9:55
     */
    public static boolean isMobile(String str){
        if(!StringUtils.isEmpty(str)){
            Matcher m = Pattern.compile("^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$").matcher(str);
            if(m.find()){
                return true;
            }
        }
        return false;
    }

    /**
     * @Auther  吕伟林
     * @Des 验证是否邮箱
     * @Date 2021/6/24 9:57
     */
    public static boolean isEmail(String str){
        if(!StringUtils.isEmpty(str)) {
            Matcher m = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$").matcher(str);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Auther  吕伟林
     * @Des 验证是否身份证号
     * @Date 2021/6/24 9:57
     */
    public static boolean isIDCard(String str){
        if(!StringUtils.isEmpty(str)) {
            Matcher m = Pattern.compile("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)").matcher(str);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }

//    public static void main(String[] args) {
//        System.out.println(StringCheck.isMobile("15827618586"));
//    }
}
