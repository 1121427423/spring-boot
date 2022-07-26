package com.springboot.oauth.server.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.springboot.oauth.server.domain.table.OauthClientDetail;
import com.springboot.oauth.server.service.OauthClientDetailsService;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OauthClientDetailsController {

    @Resource(name = "DruidDataSource")
    private DruidDataSource dataSource;
    @Resource(name = "OauthClientDetailsService")
    private OauthClientDetailsService clientDetailsService;

    @GetMapping("/getClientDetails/{client_id}")
    public OauthClientDetail getClientDetails(@PathVariable String client_id) {

        return clientDetailsService.selectClientDetailsByClientId(client_id);
    }

    @GetMapping("/getClientDetail/{client_id}")
    public ClientDetails getClientDetail(@PathVariable String client_id) {

        return new JdbcClientDetailsService(dataSource).loadClientByClientId(client_id);
    }

}
