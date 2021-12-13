package com.study.system.properties;

/**
 * @Auther  吕伟林
 * @Des 阿里云短信配置类  暂时未使用
 * @Date 2021/6/25 11:58
 */
//@Component
//@ConfigurationProperties(prefix = "escope.aliyun.sms")
@Deprecated
public class SmsProperties {
    private String filePath;


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
