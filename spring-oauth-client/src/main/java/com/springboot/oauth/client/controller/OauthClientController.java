package com.springboot.oauth.client.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.oauth.client.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author king
 */
@RestController
public class OauthClientController {

    @Value("${security.server.enabled}")
    private String securityEnabled;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response
            , @RequestParam("client_id") String clientId, @RequestParam("callback") String callback) {
        String oauthUrl = "http://127.0.0.1:8001/oauth/authorize?" +
                "response_type=code&" +
                "scope=all&" +
                "client_id=" + clientId + "&" +
                "redirect_uri=" + callback;
        if (Boolean.parseBoolean(securityEnabled)) {
            Map<String, Object> loginMap = new HashMap<>(2);
            loginMap.put("username", "86547462");
            loginMap.put("password", "123456");

            Map<String, Object> headerMap = new HashMap<>(16);

            try {
                HttpUtils.doPost("http://127.0.0.1:8001/login", loginMap, headerMap);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println(oauthUrl);
            Header cookieHeader = (Header) headerMap.get("Set-Cookie");
            String cookieValue = cookieHeader.getValue().split(";")[0];
            System.out.println(cookieValue);
//            headerMap.clear();
//            headerMap.put("Cookie", new BasicHeader("Cookie", cookieValue));
//            HttpUtils.doGet(oauthUrl, headerMap);
//            Header location = (Header) headerMap.get("Location");
//            String redirectUrl = location.getValue();
            Cookie cookie = new Cookie("JSESSIONID", cookieValue.split("=")[1]);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            response.setCharacterEncoding("utf-8");
            try {
                response.sendRedirect(oauthUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String,String> requestMap) {
        return null;
    }

    @GetMapping("/callback")
    public JSONObject callback(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) {
        String builder = "http://127.0.0.1:8001/oauth/token?" +
                "client_id=" + "1121427423" + "&" +
                "client_secret=" + "123456" + "&" +
                "grant_type=" + "authorization_code" + "&" +
                "code=" + (StringUtils.isBlank(code) ? "" : code) + "&" +
                "redirect_uri=" + "http://127.0.0.1:8002/callback";
        String respBody = HttpUtils.doPost(builder);

        //TODO redis set token

        //TODO
        /**
         * {"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NTg5NDYzNjksInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsic3lzdGVtOnVzZXIiLCJzeXN0ZW06bWVudSIsInN5c3RlbTphdXRob3JpdHkiLCJzeXN0ZW06ZGljdCJdLCJqdGkiOiI0NzY2ZDdiMi1iMjM4LTRiOTItOGRkZS1kZDQ1OWIxNzY3NGUiLCJjbGllbnRfaWQiOiIxMTIxNDI3NDIzIiwic2NvcGUiOlsiYWxsIl19.OVyZMqRf8LSJZCgCJvLEa02rW0pGfCvbYofblK5sloo","refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFsbCJdLCJhdGkiOiI0NzY2ZDdiMi1iMjM4LTRiOTItOGRkZS1kZDQ1OWIxNzY3NGUiLCJleHAiOjE2NTg5NDI3NjksImF1dGhvcml0aWVzIjpbInN5c3RlbTp1c2VyIiwic3lzdGVtOm1lbnUiLCJzeXN0ZW06YXV0aG9yaXR5Iiwic3lzdGVtOmRpY3QiXSwianRpIjoiODVhYjMzYTYtNmYwMS00NmM4LWFiZjctZTVkNzlmNTFhYTgyIiwiY2xpZW50X2lkIjoiMTEyMTQyNzQyMyJ9.A5P0yVxszK8NglpGlwcK1lHQOfqzDWhNfyj-uT5-NjU","scope":"all","token_type":"bearer","expires_in":7199,"jti":"4766d7b2-b238-4b92-8dde-dd459b17674e"}
         * access_token：访问令牌，携带此令牌访问资源
         * token_type：有MAC Token与Bearer Token两种类型，两种的校验算法不同，RFC 6750建议Oauth2采用 Bearer Token（http://www.rfcreader.com/#rfc6750）。
         * refresh_token：刷新令牌，使用此令牌可以延长访问令牌的过期时间。
         * expires_in：过期时间，单位为秒。
         * scope：范围，与定义的客户端范围一致。
         * jti：当前token的唯一标识
         */


        return JSONObject.parseObject(respBody);
    }
}
