package com.springboot.security.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class LoginResponse {

    private String code;

    private String msg;

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
