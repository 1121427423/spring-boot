package com.springboot.security.service;

import com.springboot.security.domain.CaptchaResponse;
import com.springboot.security.domain.LoginRequest;
import com.springboot.security.domain.LoginResponse;
import com.springboot.security.domain.SysMenuResponse;

public interface SysLoginService {

   LoginResponse login(LoginRequest request);

   CaptchaResponse fetchCaptcha(String captchaId);

   void checkCaptcha(String captcha, String captchaId);

   SysMenuResponse selectMenuListByUserId(String userId);
}
