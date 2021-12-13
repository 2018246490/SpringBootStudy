package com.study.system.config;

import com.study.system.listener.WebInitListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * web监听配置
 * 吕伟林
 * 2021/7/1
 */
@Configuration
public class ListenerConfig {

    /**
     * 添加系统启动监听 .
     */
    @Bean
    public ServletListenerRegistrationBean getServletListenerBean() {
        return new ServletListenerRegistrationBean(new WebInitListener());
    }
}
