package com.springboot.oauth.client.controller;

import com.springboot.oauth.client.utils.HttpUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("oauth/client")
public class OauthClientController {

    @Value("${security.server.enabled}")
    private String securityEnabled;

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


}
