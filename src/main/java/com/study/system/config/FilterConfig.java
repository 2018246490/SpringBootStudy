package com.study.system.config;

import com.study.system.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther  吕伟林
 * @Des web过滤器配置
 * @Date 2021/6/25 12:00
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean registLoginFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LoginFilter());
        registration.addUrlPatterns("/*");
        registration.setName("LoginFilter");
        registration.setOrder(1);
        return registration;
    }

}
