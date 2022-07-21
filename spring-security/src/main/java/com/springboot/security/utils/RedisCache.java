package com.springboot.security.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component("RedisCache")
@Slf4j
public class RedisCache
{
    @Resource(name = "MyRedisTemplate")
    public RedisTemplate redisTemplate;

    private static final String LOCK_VALUE = "LOCK_1";
    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    public <T> ValueOperations<String, T> setCacheObject(String key, T value) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value);
        return operation;
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     * @return 缓存的对象
     */
    public <T> ValueOperations<String, T> setCacheObject(String key, T value, Integer timeout, TimeUnit timeUnit) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value, timeout, timeUnit);
        return operation;
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public void deleteObject(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection
     */
    public void deleteObject(Collection collection)
    {
        redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     *
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> ListOperations<String, T> setCacheList(String key, List<T> dataList) {
        ListOperations listOperation = redisTemplate.opsForList();
        if (null != dataList)
        {
            int size = dataList.size();
            for (int i = 0; i < size; i++)
            {
                listOperation.leftPush(key, dataList.get(i));
            }
        }
        return listOperation;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(String key) {
        List<T> dataList = new ArrayList<T>();
        ListOperations<String, T> listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);

        for (int i = 0; i < size; i++)
        {
            dataList.add(listOperation.index(key, i));
        }
        return dataList;
    }

    /**
     * 缓存Set
     *
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext())
        {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 缓存Set--将value放入Set中
     *
     * @param key 缓存键值
     * @param value 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> addSetCacheSet(String key, T value) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        setOperation.add(value);
        return setOperation;
    }

    /**
     * 删除缓存set中的值
     */
    public void delSetCacheValue(String key, Object value){
        BoundSetOperations<String, Object> setOperation = redisTemplate.boundSetOps(key);
        setOperation.remove(value);
    }


    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(String key) {
        Set<T> dataSet = new HashSet<T>();
        BoundSetOperations<String, T> operation = redisTemplate.boundSetOps(key);
        dataSet = operation.members();
        return dataSet;
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     * @return
     */
    public <T> HashOperations<String, String, T> setCacheMap(String key, Map<String, T> dataMap) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap)
        {
            for (Map.Entry<String, T> entry : dataMap.entrySet())
            {
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
        return hashOperations;
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(String key) {
        Map<String, T> map = redisTemplate.opsForHash().entries(key);
        return map;
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(String pattern)
    {
        return redisTemplate.keys(pattern);
    }

    /**
     * 新增一个有序集合，存在的话为false，不存在的话为true
     * @param key
     * @param obj
     * @param scoure
     */
    public boolean zAdd(String key, Object obj, double scoure) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.add(key, obj, scoure);
    }
     /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    public void hmDel(String key, Object hashKey){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.delete(key, hashKey);
    }


    public String getHashSetData(String key, String configKey) {
        try {
            String value = (String) hmGet(key,configKey);
            log.info(String.format("key值为：[%s]",key));
            log.info(String.format("configKey值为：[%s]",configKey));
            log.info(String.format("短信验证码[%s]的值为：[%s]",configKey,value));
            return value;
        } catch (Exception e) {
            log.error(String.format("获取redis [%s]键[%s]字段异常", key, configKey), e);
            throw e;
        }
    }

    public boolean setHaseSetData(String key, String configKey, String valueStr) {
        try {
            hmSet(key, configKey, valueStr);
            return true;
        } catch (Exception e) {
            log.error(String.format("添加redis [%s]键[%s]字段异常", key, configKey), e);
            throw e;
        }
    }

    public boolean delHashSetData(String key, String configKey) {
        try {
            hmDel(key, configKey);
            return true;
        } catch (Exception e) {
            log.error(String.format("删除redis [%s]键[%s]字段异常", key, configKey), e);
            throw e;
        }
    }



    /**
     * 加锁
     * @param key
     * @param lockValue
     * @param expireTime
     * @return
     */
    public boolean tryLock(String key, Object lockValue, long expireTime){
        if(redisTemplate.opsForValue().setIfAbsent(key, lockValue == null ? LOCK_VALUE : lockValue)){
            redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 锁释放
     * @param key
     * @param lockValue
     * @return
     */
    public boolean releaseLock(String key, Object lockValue){
        Object value = redisTemplate.opsForValue().get(key);
        Object lockV = lockValue == null ? LOCK_VALUE : lockValue;
        if(lockV.equals(value)){
            redisTemplate.delete(key);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获取锁
     * @param key
     * @param lockValue
     * @param expireTime   锁超时时间
     * @param spinTime   自旋超时时间，单位毫秒
     * @param waitTime  自旋等待间隔时间, 单位纳秒, 默认1ms
     * @return
     */
    public boolean lock(String key, Object lockValue, long expireTime, long spinTime, long waitTime){
        if(spinTime <= 0) {   //不等待
            return tryLock(key, lockValue, expireTime);
        }
        waitTime = waitTime <= 0 ? 1L * 1000 * 1000 : waitTime;
        ValueOperations ops = redisTemplate.opsForValue();
        //取超时时间
        long timeOutAt = System.currentTimeMillis() + spinTime;
        log.info(String.format("锁超时时间为：%s", timeOutAt));
        do {
            if(ops.setIfAbsent(key, lockValue == null ? LOCK_VALUE : lockValue)){
                redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
                return true;
            }
            //未获取到锁，自旋等待
            log.info("锁未获取成功，自旋等待");
            if(System.currentTimeMillis() >= timeOutAt) {
                log.warn(String.format("wait %s ms， can't get lock [%s], 当前时间[%s]", spinTime, key, System.currentTimeMillis()));
                return false;
            }
            try {
                TimeUnit.NANOSECONDS.sleep(waitTime);
            } catch (InterruptedException e) {
                log.warn("thread wait exception", e);
            }
        } while(true);
    }
}
