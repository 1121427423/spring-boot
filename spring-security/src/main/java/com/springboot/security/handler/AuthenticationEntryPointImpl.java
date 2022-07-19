package com.springboot.security.handler;

import com.alibaba.fastjson.JSON;
import com.springboot.security.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * @author king
 * @version 1.0
 * @className UnAuthenticationHandler
 * @description TODO
 * @date 2022/7/9
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        //处理认证失败异常
        LinkedHashMap<String, Object> resp = new LinkedHashMap<>();
        resp.put("code", HttpStatus.UNAUTHORIZED.value());
        resp.put("msg", "用户认证失败，请重新登录");
        WebUtils.renderString(response, JSON.toJSONString(resp));
    }
}
