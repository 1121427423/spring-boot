package com.springboot.oauth.server.controller;

import com.springboot.oauth.server.domain.*;
import com.springboot.oauth.server.service.SysLoginService;
import com.springboot.oauth.server.utils.TokenUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@RestController
public class SysLoginController {

    @Resource(name = "TokenUtils")
    private TokenUtils tokenUtils;

    @Resource(name = "SysLoginService")
    private SysLoginService loginService;

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return loginService.login(request);
    }

    @GetMapping("login")
    public void login(HttpServletResponse response, LoginRequest loginBody) {
        LoginResponse loginResponse = loginService.login(loginBody);
        Cookie cookie = new Cookie("Oauth-Token", (String) loginResponse.getData().get("token"));
        response.addCookie(cookie);
        response.setHeader("Oauth-Token", (String) loginResponse.getData().get("token"));
    }


    @PostMapping("captcha/{id}")
    public CaptchaResponse fetchCaptcha(@PathVariable("id") String captchaId) {
        return loginService.fetchCaptcha(captchaId);
    }

    @PostMapping("getMenuList/{id}")
    public SysMenuResponse selectMenuListByUserId(@PathVariable("id") String userId) {
        return loginService.selectMenuListByUserId(userId);
    }

    @PreAuthorize("@ss.hasPermi('system:user')")
    @PostMapping("login/oauth/authorize")
    public void authorize(HttpServletRequest request, HttpServletResponse response, @RequestParam OauthAuthBody oauthAuthBody) {

        LoginUser loginUser = tokenUtils.getLoginUser(request);
        if(Objects.isNull(loginUser)) {
            response.setCharacterEncoding("utf-8");
            try {
                response.sendRedirect("http://127.0.0.0:8001/login");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        tokenUtils.verifyToken(loginUser);
        request.getHeader("Oauth-Token");
    }

}
