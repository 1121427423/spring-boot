package com.springboot.oauth2.service;

import com.springboot.oauth2.domain.table.SystemParam;
import com.springboot.oauth2.mapper.SystemParamMapper;
import com.springboot.oauth2.utils.RedisCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service("SystemParamService")
public class SystemParamServiceImpl {

    @Resource
    private SystemParamMapper systemParamMapper;

    @Resource(name = "RedisCache")
    private RedisCache redisCache;

    public List<SystemParam> selectSystemParamsByName(String name) {

        if (!Objects.isNull(redisCache.getCacheObject("system:params:expiration"))) {
            return redisCache.getCacheList("system:params:metaData");
        }

        if (!Objects.isNull(redisCache.getCacheList("system:params:metaData"))) {
            redisCache.deleteObject("system:params:metaData");
        }
        List<SystemParam> systemParams = systemParamMapper.selectSystemParamsByName(name);
        redisCache.setCacheObject("system:params:expiration", System.currentTimeMillis() + 12 * 3600 * 1000, 12, TimeUnit.HOURS);
        redisCache.setCacheList("system:params:metaData", systemParams);
        return systemParams;
    }
}
