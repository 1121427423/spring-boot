package com.springboot.oauth.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.springboot.oauth.server.domain.*;
import com.springboot.oauth.server.service.SysLoginService;
import com.springboot.oauth.server.service.SysMenuService;
import com.springboot.oauth.server.utils.CaptchaUtils;
import com.springboot.oauth.server.utils.RedisCache;
import com.springboot.oauth.server.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service("SysLoginService")
public class SysLoginServiceImpl implements SysLoginService {

    public static final String USER_CODE_EXCLUDE = "_.=";

    /**
     * 密码密钥（盐值）
     */
    public static final String PWD_SECRET_KEY = "GIN2022";

    private static final int CAPTCHA_ID_LENGTH = 16;

    private static final String CAPTCHA_URI_SCHEME = "data:image/png;base64,";

    private static final int CAPTCHA_TIMEOUT = 90;

    @Resource(name = "RedisCache")
    private RedisCache redisCache;

    @Resource(name = "TokenUtils")
    private TokenUtils tokenUtils;

    @Resource(name = "SysMenuService")
    private SysMenuService menuService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public LoginResponse login(LoginRequest request) {

        if (StringUtils.isBlank(request.getUserCode()) || StringUtils.isBlank(request.getPassword())
                || request.getUserCode().contains(USER_CODE_EXCLUDE)) {
            return new LoginResponse("0000", "Illegal account");
        }

        try {
            //MD5密码加密
            request.setPassword(encrypt32(request.getPassword(), PWD_SECRET_KEY, "utf-8"));
        } catch (Exception e) {
            log.info("MD5密码加密异常：" + e);
            return new LoginResponse("0000", "Encryption failed");
        }
        //校验图片验证码是否正确
        try {
            checkCaptcha(request.getCaptcha(), request.getCaptchaId());
        } catch (Exception e) {
            log.info("验证码校验失败" + e);
            return new LoginResponse("0000", "The verification code fails");
        }
        //spring security认证
        Authentication authentication;
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUserCode(), request.getPassword());
            authentication = authenticationManager.authenticate(token);
        } catch (Exception e) {
            log.info("认证失败:" + e);
            return new LoginResponse("0000", "Authentication failed");
        }

        //认证通过，使用userid生成一个token
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //把完整的用户信息存入redis
        String token = tokenUtils.createToken(loginUser);

        JSONObject json = new JSONObject();
        json.put("userInfo",loginUser.getSysUser());
        json.put("token",token);
        json.put("expireTime",loginUser.getExpireTime());
        return new LoginResponse("0000", "Authentication success", json);

    }

    @Override
    public void checkCaptcha(String captcha, String captchaId) {
        String captchaCache = redisCache.getCacheObject("captcha:" + captchaId);
        if (Objects.isNull(captchaCache) || !captcha.equalsIgnoreCase(captchaCache)) {
            throw new RuntimeException("验证码校验失败");
        }
        //验证码验证通过,删除缓存
        redisCache.deleteObject("captcha:" + captchaId);
    }

    @Override
    public SysMenuResponse selectMenuListByUserId(String userId) {
        List<TreeSelect> treeSelects = menuService.buildMenuTreeSelect(menuService.selectMenuListById(Long.parseLong(userId)));
        return new SysMenuResponse("0000", "Operation completed successfully", treeSelects);
    }

    @Override
    public CaptchaResponse fetchCaptcha(String captchaId) {
        // 校验captchaId合法性
        if(StringUtils.isBlank(captchaId) || captchaId.length() != CAPTCHA_ID_LENGTH) {
            return new CaptchaResponse("0000", "Illegal CaptchaId");
        }
        //生成随机四位数字验证码
        CaptchaUtils.Validate validate = CaptchaUtils.getRandomCode();
        if(StringUtils.isNotBlank(validate.getBase64Str())) {
            String key = "captcha:" + captchaId;
            if (StringUtils.isNotBlank(redisCache.getCacheObject(key))) {
                return new CaptchaResponse("0000", "Frequent operation, Please try again later");
            }
            redisCache.setCacheObject(key, validate.getValue(), CAPTCHA_TIMEOUT, TimeUnit.SECONDS);
        }
        return new CaptchaResponse("0000", "Operation completed successfully"
                , CAPTCHA_URI_SCHEME + validate.getBase64Str(), 4L);
    }

    public static String encrypt32(String text, String salt, String charset) {
        text = text + salt;
        return DigestUtils.md5Hex(getContentBytes(text, charset));
    }

    private static byte[] getContentBytes(String content, String charset) {
        if (charset != null && !"".equals(charset)) {
            try {
                return content.getBytes(charset);
            } catch (UnsupportedEncodingException var3) {
                throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
            }
        } else {
            return content.getBytes();
        }
    }
}
