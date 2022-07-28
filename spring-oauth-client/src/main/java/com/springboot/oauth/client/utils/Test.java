package com.springboot.oauth.client.utils;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test {

//    GET https://github.com/login/oauth/authorize
//
//    client_id=
//    redirect_uri=
//    login=
//    scope=
//    state=
//    allow_signup=true


    public static void main(String[] args) {
        List<Header> headers = new ArrayList<>();
        HttpUtils.doGet("https://github.com/login/oauth/authorize?client_id=5ca9a14a1c4bb0efda10", headers);
//        Header cookie = (Header)  .get("Set-Cookie");
//        headers.clear();
//        headers.put("cookie", cookie);
        HttpUtils.doGet("https://github.com/login?client_id=5ca9a14a1c4bb0efda10&return_to=%2Flogin%2Foauth%2Fauthorize%3Fclient_id%3D5ca9a14a1c4bb0efda10", headers);


    }

}
