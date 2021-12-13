package com.study.system.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther  吕伟林
 * @Des 配种文件中的文件上传参数
 * @Date 2021/6/25 12:00
 */
@Component
@ConfigurationProperties(prefix = "escope.upload")
public class UploadProperties {
    private String filePath;


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
