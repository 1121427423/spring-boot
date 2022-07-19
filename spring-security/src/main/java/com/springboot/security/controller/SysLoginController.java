package com.springboot.security.controller;

import com.springboot.security.service.SysLoginService;
import com.springboot.security.domain.CaptchaResponse;
import com.springboot.security.domain.LoginRequest;
import com.springboot.security.domain.LoginResponse;
import com.springboot.security.domain.SysMenuResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class SysLoginController {

    @Resource(name = "SysLoginService")
    private SysLoginService loginService;

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return loginService.login(request);
    }

    @PostMapping("captcha/{id}")
    public CaptchaResponse fetchCaptcha(@PathVariable("id") String captchaId) {
        return loginService.fetchCaptcha(captchaId);
    }

    @PostMapping("getMenuList/{id}")
    public SysMenuResponse selectMenuListByUserId(@PathVariable("id") String userId) {
        return loginService.selectMenuListByUserId(userId);
    }

}
