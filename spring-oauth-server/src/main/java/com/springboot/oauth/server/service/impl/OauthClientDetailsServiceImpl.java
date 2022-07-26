package com.springboot.oauth.server.service.impl;

import com.springboot.oauth.server.domain.table.OauthClientDetail;
import com.springboot.oauth.server.mapper.OauthClientDetailMapper;
import com.springboot.oauth.server.service.OauthClientDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("OauthClientDetailsService")
public class OauthClientDetailsServiceImpl implements OauthClientDetailsService {

    @Resource
    private OauthClientDetailMapper clientDetailMapper;

    @Override
    public OauthClientDetail selectClientDetailsByClientId(String clientId) {
        return clientDetailMapper.selectByPrimaryKey(clientId);
    }
}
