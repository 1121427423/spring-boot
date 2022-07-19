package com.springboot.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.springboot.security.domain.LoginUser;
import com.springboot.security.utils.RedisCache;
import com.springboot.security.utils.TokenUtils;
import com.springboot.security.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author king
 * @version 1.0
 * @className LogoutSuccessHandler
 * @description TODO
 * @date 2022/7/9
 */
@Component
@Slf4j
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Resource(name = "RedisCache")
    private RedisCache redisCache;

    @Resource(name = "TokenUtils")
    private TokenUtils tokenUtils;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        try {
            LoginUser loginUser = tokenUtils.getLoginUser(request);
            String key = loginUser.getToken();
            redisCache.deleteObject(key);
        } catch (NullPointerException e){
            log.info("传的token值非法");
            throw new RuntimeException("request.getHeader(’token‘） 获取的值非法");
        }

        JSONObject json = new JSONObject();
        json.put("code", "0000");
        json.put("msg", "Logout operation complete");
        WebUtils.renderString(response, json.toJSONString());
    }
}
