package com.study.system.filter;

import com.google.gson.GsonBuilder;
import com.study.tool.ParamManager;
import com.study.tool.login.LoginCacheManage;
import com.study.tool.login.LoginInfo;
import com.study.tool.model.HttpResult;
import com.study.util.CookiesUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 * @Auther  吕伟林
 * @Des 登录过滤器
 * @Date 2021/6/25 11:58
 */
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String url = httpServletRequest.getServletPath();


        //放行静态资源
        if (url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".jpg")
                || url.endsWith(".gif") || url.endsWith(".png")) {
            chain.doFilter(request, response);
            return;
        }

        if (url.startsWith("/user/")) {  //需要放行的请求
            chain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            //登录验证
            LoginInfo info = LoginCacheManage.getLoginUser();
            if(info != null){
                info.setTime(new Date().getTime());//更新登录时间
                int outtime = 10*60;
                String st = ParamManager.SysParamMap.get("login_timeout");//系统登录超时时间 单位分钟
                if(st != null){
                    try {
                        outtime = Integer.parseInt(st);
                        outtime = outtime * 60;
                    }catch (Exception e){
                    }
                }
                CookiesUtil.writeCookieMap(httpServletResponse, outtime, LoginCacheManage.resultLoginCacheInfo(info));
                chain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
            HttpResult hr = HttpResult.loginError();
            String result = new GsonBuilder().create().toJson(hr);
            OutputStream outputStream = httpServletResponse.getOutputStream();//获取OutputStream输出流
            httpServletResponse.setHeader("content-type", "application/json;charset=UTF-8;");
            byte[] dataByteArr = result.getBytes("UTF-8");//将字符转换成字节数组，指定以UTF-8编码进行转换
            outputStream.write(dataByteArr);//使用OutputStream流向客户端输出字节数组
            //httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login");
            return;
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void destroy() {

    }
}