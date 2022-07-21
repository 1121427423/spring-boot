package com.springboot.oauth2.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/getCurrentUser2")
    public Object getCurrentUser(Authentication authentication) {
        return authentication.getPrincipal();
    }


    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication, HttpServletRequest request) {
        //获取请求头的指定内容
        String header = request.getHeader("Authorization");
        //截取，去掉请求头的前6位，获取token
        String token = header.substring(header.indexOf("bearer") + 7);
        //解析Token，获取Claims对象
        Claims claims = Jwts.parser()
                .setSigningKey("oauth2.0".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

}
