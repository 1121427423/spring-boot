package com.springboot.oauth.server.domain;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class LoginResponse {

    private String code;

    private String msg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private JSONObject data;

    public LoginResponse(String code, String msg, JSONObject data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public LoginResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
