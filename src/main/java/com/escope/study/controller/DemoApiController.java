package com.escope.study.controller;

import com.escope.study.model.DemoApiResultMsg;
import com.escope.study.utils.DemoDataUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Auther lvweilin
 * @Des demo示例使用到的数据接口
 * @Date 2021/6/18 11:04
 */
@RestController
@RequestMapping("/demoApi")
public class DemoApiController {

    /**
     * @Auther lvweilin
     * @Des json数据接口
     * @Date 2021/6/18 11:06
     * @Param
     * @Return
     */
    @CrossOrigin
    @RequestMapping(value = "/jsonData")
    public DemoApiResultMsg jsonData() {
        DemoApiResultMsg msg = new DemoApiResultMsg();
        msg.setStatus("2");
        msg.setData(DemoDataUtil.getDemoData());//制造随机数据
        return msg;
    }


    /**
     * @Auther lvweilin
     * @Des jsonp数据接口
     * @Date 2021/6/18 11:06
     * @Param
     * @Return
     */
    @RequestMapping(value = "/jsonpData")
    public void jsonpData(HttpServletRequest request, HttpServletResponse response) {
        DemoApiResultMsg msg = new DemoApiResultMsg();
        msg.setStatus("2");
        Gson gson = new GsonBuilder().create();

        msg.setData(DemoDataUtil.getDemoData());//制造随机数据
        String callback = request.getParameter("callback");
        callback = callback == null ? "" : callback;

        response.setContentType("application/x-javascript");
        response.setCharacterEncoding("UTF-8");

        try {
            PrintWriter out = response.getWriter();
            out.write(callback + "(" + gson.toJson(msg) + ")");
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
