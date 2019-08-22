package com.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author: Xiaok
 * @Date: 2019/8/21 16:08
 * @version: 1.0
 * @Description:
 */
@Component
@Slf4j
public class RedisUtil {

    private static JedisPool jedisPool;

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        RedisUtil.jedisPool = jedisPool;
    }

    public static String get(String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.get(key);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    public static String save(String key, String value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.set(key,value);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }
}
