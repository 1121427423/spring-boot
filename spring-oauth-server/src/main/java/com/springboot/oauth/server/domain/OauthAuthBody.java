package com.springboot.oauth.server.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class OauthAuthBody {

    @JSONField(name = "app_id")
    private String clientId;

    @JSONField(name = "response_type")
    private String responseType;

    @JSONField(name = "scope")
    private String scope;

    @JSONField(name = "callback")
    private String redirect_uri;

    @JSONField(name = "state")
    private String state;
}
