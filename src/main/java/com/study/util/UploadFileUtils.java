package com.study.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.UUID;

public class UploadFileUtils {

    public static String createFileName(String fileName, int year) {
        StringBuffer sb = new StringBuffer();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //文件名称前缀加入年份
        sb.append(year).append(UUID.randomUUID()).append(suffix);
        return sb.toString();
    }

    public static Path createDir(String filePath) throws Exception {
        Path path = Paths.get(filePath).toAbsolutePath().normalize();
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("无法创建存储文件的目录.", e);
        }
        return path;
    }

    public static String writFile(MultipartFile file, String filePath) throws Exception {
        return writFile(file, filePath, null);
    }


    public static String writFile(MultipartFile file, String filePath, String modelName) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String newFileName = null;
        try {
            if (fileName.contains("..")) {
                throw new Exception("对不起的！文件名包含无效的路径序列 " + fileName);
            }
            Path path = createDir(filePath);
            if (!StringUtils.isEmpty(modelName)) {
                path = path.resolve(modelName);
            }
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            newFileName = createFileName(fileName, year);
            path = path.resolve(String.valueOf(year));//加入年份目录

            Files.createDirectories(path);//创建目录

            path = path.resolve(newFileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("无法存储文件" + fileName, e);
        }
        return newFileName;
    }

    public static Resource readFile(String fileName, String filePath) throws Exception {
        return readFile(fileName, filePath, null);
    }

    public static Resource readFile(String fileName, String filePath, String modelName) throws Exception {
        try {
            Path path = Paths.get(filePath).toAbsolutePath().normalize();
            if (!StringUtils.isEmpty(modelName)) {
                path = path.resolve(modelName);
            }

            String year = fileName.substring(0, 4);
            try {
                Integer.valueOf(year);
                path = path.resolve(year);//进入文件名称对应的年份文件夹
            } catch (Exception e) {
                e.printStackTrace();
            }

            path = path.resolve(fileName);
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new Exception("未找到文件 " + fileName);
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            throw new Exception("未找到文件 " + fileName, ex);
        }
    }

}
