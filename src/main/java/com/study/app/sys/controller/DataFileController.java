package com.study.app.sys.controller;

import com.study.app.sys.service.impl.DateFileService;
import com.study.tool.model.HttpResult;
import com.study.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 吕伟林
 * 数据文件下载
 * 2021/8/17
 */
@Controller
@RequestMapping("/dataFile")
public class DataFileController {
    @Autowired
    private DateFileService dateFileService;

    @RequestMapping(value = "/bluetoothData")
    public String bluetoothData(String bid, String weixinName, Model model) {
        List list = null;
        if (!StringUtils.isEmpty(bid) && !StringUtils.isEmpty(weixinName)) {
            list = dateFileService.getDateFile(bid, weixinName);
            model.addAttribute("msg", "未查询到微信用户" + weixinName + "关于地图" + bid + "的数据...");
        } else {
            list = new ArrayList();
            model.addAttribute("msg", "暂无数据...");
        }
        model.addAttribute("list", list);
        model.addAttribute("bid", bid);
        model.addAttribute("weixinName", weixinName);
        model.addAttribute("showTips", list.size() > 0);
        return "blue/data";
    }

    @RequestMapping("/download")
    public ResponseEntity<InputStreamResource> download(String bid, String weixinName, String fileName) throws IOException {
        if (!StringUtils.isEmpty(bid) && !StringUtils.isEmpty(weixinName) && !StringUtils.isEmpty(fileName)) {
            String pahtPrefix = FileUtil.getWebAppsPath();
            FileSystemResource file = new FileSystemResource(pahtPrefix + "/beaconData/" + bid + "/" + weixinName + "/" + fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            return ResponseEntity.ok().headers(headers)
                    .contentLength(file.contentLength())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(file.getInputStream()));
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/del")
    public HttpResult del(String bid, String weixinName, String fileName, HttpServletResponse response) {
        HttpResult result = null;
        if (!StringUtils.isEmpty(bid) && !StringUtils.isEmpty(weixinName) && !StringUtils.isEmpty(fileName)) {
            String pahtPrefix = FileUtil.getWebAppsPath();
            File file = new File(pahtPrefix + "/beaconData/" + bid + "/" + weixinName + "/" + fileName);
            if (file.exists()) {
                file.delete();
                result = HttpResult.ok();
            } else {
                result = HttpResult.error("文件不存在！");
            }
        } else {
            result = HttpResult.error("参数异常！");
        }

        return result;
    }
}
