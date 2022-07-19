package com.springboot.security.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class CaptchaResponse {

    private String code;

    private String msg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String captcha;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long length;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long validTime;

    public CaptchaResponse(String code, String msg, String captcha, Long length) {
        this.code = code;
        this.msg = msg;
        this.captcha = captcha;
        this.length = length;
        this.validTime = 90L;
    }

    public CaptchaResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

