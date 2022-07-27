package com.springboot.oauth.server.service;

import com.springboot.oauth.server.domain.CaptchaResponse;
import com.springboot.oauth.server.domain.LoginRequest;
import com.springboot.oauth.server.domain.LoginResponse;
import com.springboot.oauth.server.domain.SysMenuResponse;

public interface SysLoginService {

   LoginResponse login(LoginRequest request);

   CaptchaResponse fetchCaptcha(String captchaId);

   void checkCaptcha(String captcha, String captchaId);

   SysMenuResponse selectMenuListByUserId(String userId);
}
