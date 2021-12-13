package com.study.system.listener;

import com.study.app.sys.model.SysParam;
import com.study.app.sys.service.SysParamService;
import com.study.tool.ParamManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 * web初始化监听  用于初始化全局资源
 * 吕伟林
 * 2021/7/1
 */
@Slf4j
public class WebInitListener implements ServletContextListener {

    /**
     * 系统初始化 .
     *
     * @param sce .
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("----执行web启动监听----");
        ServletContext context = sce.getServletContext();
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
        //加载web配置参数
        SysParamService sps = applicationContext.getBean(SysParamService.class);
        List<SysParam> list = sps.findAll();
        for(SysParam sp : list){
            ParamManager.SysParamMap.put(sp.getCode(), sp.getValue());
        }

    }

    /**
     * web服务销毁 .
     *
     * @param sce .
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("监听web服务销毁。。。");
    }

}
