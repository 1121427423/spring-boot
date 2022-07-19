package com.springboot.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component("DruidDataSourceProperties")
@ConfigurationProperties("spring.datasource.druid")
public class DruidDataSourceProperties {
    private String username;
    private String password;
    private String url;
    private String driverClassName;
    private Integer initialSize;
    private Integer maxActive;
    private Integer minIdle;
    private long maxWait;
    private boolean poolPreparedStatements;
    public String filters;
}