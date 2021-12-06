package com.escope.onlineexampleapi.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author lvweilin
 * @create 2020-07-28 10:48
 **/
@Configuration
public class WebConfig {
  /**
   * webSocket注册.
   * 打war包需要注释下面，使用spring内置tomcat需要放开下面注释
   */
//  @Bean
//  public ServerEndpointExporter serverEndpointExporter() {
//    return new ServerEndpointExporter();
//  }
}
