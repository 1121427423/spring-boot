package com.springboot.oauth.server.domain.table;

import lombok.Data;

@Data
public class OauthClientDetail {

    private String clientId;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoApproveScopes;
}