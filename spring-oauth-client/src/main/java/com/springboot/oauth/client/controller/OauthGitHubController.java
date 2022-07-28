package com.springboot.oauth.client.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.oauth.client.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@RestController
public class OauthGitHubController {
    //        String redirectUri = request.getParameter("redirect_uri");
    //        String login = request.getParameter("login"); //用于登录和授权应用程序的特定帐户，如果你在该网站登陆过github并授权，则在此请求OAuth时免去登陆步骤
    //        String scope = request.getParameter("scope"); //用空格分隔的作用域列表
    //        String state = request.getParameter("state"); //不可猜测的随机字符串。它用于防止跨站点请求伪造攻击
    //        String allowSignup = request.getParameter("allow_signup"); //注册github  默认true


    private static final String GITHUB_OAUTH_AUTHORIZE_URL = "https://github.com/login/oauth/authorize";

    private static final String GITHUB_LOGIN_URL = "https://github.com/login";

    @GetMapping("login")
    public void login() {

    }

    @GetMapping("oauth/authorize")
    public void authorize(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String clientId = request.getParameter("client_id");
        if (StringUtils.isBlank(clientId)) {
            return;
        }

        String authorizeUrl = getAuthorizeUrl(request);
        response.setCharacterEncoding("utf-8");
        response.addHeader("Location", authorizeUrl);
        response.sendRedirect(authorizeUrl);

//        response.setCharacterEncoding("utf-8");
//        response.setStatus(302);
//        String loginUrl = GITHUB_LOGIN_URL + "?client_id=" + clientId + "&" + "return_to=" + URLEncoder.encode("/login/oauth/authorize","utf-8") + "?client_id=" + clientId;
//        response.addHeader("Location", loginUrl);
//        response.sendRedirect(loginUrl);
    }

    /**
     * https://docs.github.com/cn/rest/apps/oauth-applications#delete-an-app-token
     * {"access_token":"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","scope":"repo","token_type":"bearer"}
     */

    public String getAuthorizeUrl(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();
        builder
                .append(GITHUB_OAUTH_AUTHORIZE_URL)
                .append("?");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            builder
                    .append(parameterName)
                    .append("=")
                    .append(request.getParameter(parameterName))
                    .append("&");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

//   public Cookie setGithubCookie(Cookie cookie) {
//        cookie.setHttpOnly(true);
//        cookie.setDomain(".github.com");
//        cookie.setPath("/");
//        cookie.setSecure(true);
//        return cookie;
//   }


    @GetMapping("callback")
    public JSONObject callback(HttpServletRequest request) throws Exception {
        String code = request.getParameter("code");
        if (StringUtils.isNotBlank(code)) {
            List<Header> headers = new ArrayList<>();
            headers.add(new BasicHeader("Accept", "application/json"));
            String respEntity = HttpUtils.doPost("https://github.com/login/oauth/access_token?client_id=5ca9a14a1c4bb0efda10&client_secret=ab38fb6a0874003e134e3c7b57ab2e7af6b355bc&code=" + code, null, headers);
            return JSONObject.parseObject(respEntity);
        }
        return null;
    }

//    public static void main(String[] args) {
//        HashMap<String,Object> headerMap = new HashMap<>(16);
//        HttpUtils.doGet("https://github.com/login/oauth/authorize?client_id=5ca9a14a1c4bb0efda10&login=1121427423@qq.com", headerMap);
//        Header cookie = (Header) headerMap.get("Set-Cookie");
//        headerMap.clear();
//        headerMap.put("cookie", cookie);
//        HttpUtils.doGet("https://github.com/login?client_id=5ca9a14a1c4bb0efda10&return_to=%2Flogin%2Foauth%2Fauthorize%3Fclient_id%3D5ca9a14a1c4bb0efda10", headerMap);
//
//
//    }
}

