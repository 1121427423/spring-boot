package com.springboot.oauth.server.filters;

import com.springboot.oauth.server.domain.LoginUser;
import com.springboot.oauth.server.utils.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author king
 * @version 1.0
 * @className JwtAuthenticationTokenFilter
 * @description TODO
 * @date 2022/7/10
 */
@Component("JwtAuthenticationTokenFilter")
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource(name = "TokenUtils")
    private TokenUtils tokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("Oauth-Token");
        Cookie[] cookies = request.getCookies();
        if (!Objects.isNull(cookies) && cookies.length > 0 && Objects.isNull(token)) {
            for (int i = 0; i < cookies.length; i++) {
                if("Oauth-Token".equals(cookies[i].getName())) {
                    token = cookies[i].getValue();
                }
            }
        }
        if(StringUtils.isBlank(token)) {
            //放行
            filterChain.doFilter(request, response);
            return;
        }

        //解析token,从redis中获取用户信息
        LoginUser loginUser = tokenUtils.getLoginUser(token);
        if(Objects.isNull(loginUser)) {
            throw new RuntimeException("用户未登陆");
        }
        //刷新令牌有效期
        tokenUtils.verifyToken(loginUser);
        //存入SecurityContextHolder
        //前后端分离，再次访问SecurityContextHolder已经不是登录时的SecurityContextHolder，要再次保存Authentication
        //   获取权限信息封装到Authentication
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(loginUser, loginUser.getPassword(), loginUser.getAuthorities()));

        filterChain.doFilter(request,response);
    }
}
