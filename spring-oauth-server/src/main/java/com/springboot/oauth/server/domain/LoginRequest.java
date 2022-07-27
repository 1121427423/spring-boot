package com.springboot.oauth.server.domain;

import lombok.Data;

@Data
public class LoginRequest {

    private String userCode;

    private String password;

    private String captcha;

    private String captchaId;
}
