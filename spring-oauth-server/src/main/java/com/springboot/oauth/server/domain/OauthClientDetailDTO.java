package com.springboot.oauth.server.domain;

import com.alibaba.fastjson.JSONObject;
import com.springboot.oauth.server.domain.table.OauthClientDetail;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

public class OauthClientDetailDTO implements ClientDetails {

    private static final String CLIENT_DETAILS_SEPARATOR = ",";
    @Setter
    private String clientId;
    @Setter
    @Getter
    private String clientSecret;
    @Setter
    private Set<String> scope;
    @Setter
    private Set<String> resourceIds;
    @Setter
    private Set<String> authorizedGrantTypes;
    @Setter
    private Set<String> registeredRedirectUris;
    @Getter
    @Setter
    private Set<String> autoApproveScopes;
    @Setter
    private List<GrantedAuthority> authorities;
    @Setter
    private Integer accessTokenValiditySeconds;
    @Setter
    private Integer refreshTokenValiditySeconds;
    @Setter
    private Map<String, Object> additionalInformation;


    public OauthClientDetailDTO(){};

    public OauthClientDetailDTO(OauthClientDetail clientDetail) {
        this.clientId = clientDetail.getClientId() == null ? null : clientDetail.getClientId();
        this.clientSecret = clientDetail.getClientSecret() == null ? null : clientDetail.getClientSecret();
        this.accessTokenValiditySeconds = clientDetail.getAccessTokenValidity() == null ? null : clientDetail.getAccessTokenValidity();
        this.refreshTokenValiditySeconds = clientDetail.getRefreshTokenValidity() == null ? null : clientDetail.getRefreshTokenValidity();
        this.scope = clientDetail.getScope() == null ? null : stringToSet(clientDetail.getScope());
        this.resourceIds = clientDetail.getResourceIds() == null ? null : stringToSet(clientDetail.getResourceIds());
        this.authorizedGrantTypes = clientDetail.getAuthorizedGrantTypes() == null ? null : stringToSet(clientDetail.getAuthorizedGrantTypes());
        this.registeredRedirectUris = clientDetail.getWebServerRedirectUri() == null ? null : stringToSet(clientDetail.getWebServerRedirectUri());
        this.autoApproveScopes = clientDetail.getAutoApproveScopes() == null ? null : stringToSet(clientDetail.getAutoApproveScopes());
        this.authorities = clientDetail.getAuthorities() == null ? null : stringToList(clientDetail.getAuthorities());
        this.additionalInformation = clientDetail.getAdditionalInformation() == null ? null : stringToMap(clientDetail.getAdditionalInformation());
    }

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return this.resourceIds;
    }

    @Override
    public boolean isSecretRequired() {
        return this.clientSecret != null;
    }

    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Override
    public boolean isScoped() {
        return this.scope != null && !this.scope.isEmpty();
    }

    @Override
    public Set<String> getScope() {
        return this.scope;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return this.authorizedGrantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return this.registeredRedirectUris;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return this.accessTokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return this.refreshTokenValiditySeconds;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return this.autoApproveScopes.contains(scope);
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return this.additionalInformation;
    }

    public Set<String> stringToSet(String s) {
       HashSet<String> set =  new HashSet<>();
        String[] ss = s.split(CLIENT_DETAILS_SEPARATOR);
        for (String str: ss) {
            set.add(str.trim());
        }
        return set;
    }

    public List<GrantedAuthority> stringToList(String s) {
        List<GrantedAuthority> list =  new ArrayList<>();
        String[] ss = s.split(CLIENT_DETAILS_SEPARATOR);
        for (String str: ss) {
            list.add(new SimpleGrantedAuthority(str.trim()));
        }
        return list;
    }

    public Map stringToMap(String s) {
        return JSONObject.parseObject(s,Map.class);
    }
}
