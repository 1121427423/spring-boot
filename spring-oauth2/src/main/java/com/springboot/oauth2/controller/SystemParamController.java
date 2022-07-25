package com.springboot.oauth2.controller;

import com.springboot.oauth2.domain.table.SystemParam;
import com.springboot.oauth2.service.SystemParamServiceImpl;
import com.springboot.oauth2.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("system")
public class SystemParamController {

    @Resource(name = "RedisCache")
    private RedisCache redisCache;

    @Resource(name = "SystemParamService")
    private SystemParamServiceImpl systemParamService;

    @GetMapping("getParams/{name}")
    public List<SystemParam> getParams(@PathVariable String name) {
        return systemParamService.selectSystemParamsByName(name);
    }

    @GetMapping("refreshParams/{key}")
    public Map<String, Object> refreshParams(@PathVariable String key) {
        LinkedHashMap<String, Object> respMap = new LinkedHashMap<>();
        respMap.put("code", "200");
        try {
            if(StringUtils.isNotBlank(key) && "expiration".equals(key)
                    && !Objects.isNull(redisCache.getCacheObject("system:params:" + key))) {
                redisCache.deleteObject("system:params:" + key);
                log.info("刷新系统元数据成功: " + System.currentTimeMillis());
                respMap.put("msg", "元数据刷新成功！");
                return respMap;
            }
            respMap.put("msg", "缓存已过期！");
            return respMap;
        }catch (Exception e) {
            log.info("刷新系统元数据失败: " + e);
            respMap.put("msg", "元数据刷新失败！");
            return respMap;
        }
    }
}

