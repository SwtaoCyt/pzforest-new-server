package com.song.pzforestserver.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.K;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/*
redis 工具类
 */
@Slf4j
@Component
public class CacheUtils {

    @Autowired(required = false)
    StringRedisTemplate redisTemplate;

    final   String DEFAULT_KEY_PREFIX="";
    final   int EXPIRE_TIME=6;
    final  TimeUnit EXPIRE_TIME_TYPE= TimeUnit.HOURS;

    public <K,V> void add(K key,V value)
    {
        try {
            if(value !=null){
                redisTemplate.opsForValue().set(DEFAULT_KEY_PREFIX + key, JSONUtil.toJsonStr(value), EXPIRE_TIME, EXPIRE_TIME_TYPE);
            }
        } catch (Exception e) {
            log.debug(e.getMessage(),e);
            throw new  RuntimeException("数据缓存至redis失败");
        }
    }


    public <K,V> void add(K key,V value,Integer expireTime,TimeUnit timeUnit)
    {
        try {
            if(value !=null){

                redisTemplate.opsForValue().set(DEFAULT_KEY_PREFIX + key, JSONUtil.toJsonStr(value), expireTime, timeUnit);
            }
        } catch (Exception e) {
            log.debug(e.getMessage(),e);
            throw new  RuntimeException("数据缓存至redis失败");
        }
    }
    /**
     * 以字符形式获取元素
     */
    public  <K> String get(K key){
        String value = null;
        try{
            value = redisTemplate.opsForValue().get(DEFAULT_KEY_PREFIX + key);
            if(value.equals("null"))
            {
                return null;
            }
        } catch (Exception e) {
            log.debug(e.getMessage(),e);
//            throw new  RuntimeException("获取数据失败");
        }
        return  value;
    }
    /**
     * 以对象形式获取元素
     */
    public <K, V> V getObject(K key, Class<V> clazz) {
        String value = this.get(key);
        V result = null;
        if (!StringUtils.isEmpty(value)) {
            result = JSONUtil.toBean(value, clazz);
        }
        return result;
    }

}
