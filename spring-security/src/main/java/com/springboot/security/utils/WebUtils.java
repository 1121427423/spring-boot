package com.springboot.security.utils;

import com.alibaba.fastjson.JSONObject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author king
 * @version 1.0
 * @className WebUtils
 * @description TODO
 * @date 2022/7/10
 */
public class WebUtils {

    public static String renderString(HttpServletResponse response, String msg) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject json = new JSONObject();
        json.put("code", "9999");
        json.put("msg", "System Error");
        return json.toJSONString();
    }
}
