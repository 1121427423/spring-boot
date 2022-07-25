package com.springboot.oauth2.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("oauth2.0"));
    }
}
