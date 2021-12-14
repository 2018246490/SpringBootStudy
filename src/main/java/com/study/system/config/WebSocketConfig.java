package com.study.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * websocket配置
 * 2021/12/14
 */
public class WebSocketConfig {
    /**
     * webSocket注册.
     * 打war包需要注释下面，使用spring内置tomcat需要放开下面注释
     */
  @Bean
  public ServerEndpointExporter serverEndpointExporter() {
    return new ServerEndpointExporter();
  }
}
