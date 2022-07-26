package com.springboot.oauth.server.utils;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication oAuth2Authentication) {
        //自定义的内容存到map中
        Map<String,Object> map = new HashMap<>();
        map.put("city","西安");
        map.put("like","yu");
        map.put("age",20);
        map.put("name","泡泡茶壶");
        //下转型
        if(accessToken instanceof DefaultOAuth2AccessToken) {
            DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken)accessToken;
            defaultOAuth2AccessToken.setAdditionalInformation(map);
            return defaultOAuth2AccessToken;
        }
        return null;
    }
}


