package com.study.system.config;

import com.study.system.interceptor.ButtonCompetenceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther  吕伟林
 * @Des web 拦截器配置
 * @Date 2021/6/25 12:01
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
//        InterceptorRegistration registration = registry.addInterceptor(new RoleCompetenceInterceptor());
//        registration.addPathPatterns("/**").excludePathPatterns("/static/*");
        InterceptorRegistration registration = registry.addInterceptor(new ButtonCompetenceInterceptor());
        registration.addPathPatterns("/**").excludePathPatterns("/static/*");
    }
}
