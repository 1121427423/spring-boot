package com.springboot.oauth.server.service;

import com.springboot.oauth.server.domain.table.OauthClientDetail;

public interface OauthClientDetailsService {

    OauthClientDetail selectClientDetailsByClientId(String clientId);
}
