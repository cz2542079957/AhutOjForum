package com.ahutoj.utils;

import java.io.*;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
@Slf4j
public class JedisUtil
{
    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private Gson gson;

    private final String ETSuffix = "ExpiredTime";

    /**
     * @Description 通过key field获取redis的hash表中的可过期值
     * @Params [key, field]
     * @Return 字符串
     **/
    public String getHashValueCheckExpiration(String key, String field)
    {
        Jedis jedis = jedisPool.getResource();
        //判断该键以及过期标记是否存在
        if (jedis.hexists(key, field) && jedis.hexists(key + ETSuffix, field))
        {
            //存在，判断是否过期
            String tempExpiredTime = jedis.hget(key + ETSuffix, field);
            long expiredTime = Long.parseLong(null == tempExpiredTime ? "0" : tempExpiredTime);
            long currentTime = System.currentTimeMillis();
            if (currentTime > expiredTime)
            {
                //过期清空该数据
                jedis.hdel(key, field);
                jedis.hdel(key + ETSuffix, field);
                jedis.close();
                return null;
            }
            String ret = jedis.hget(key, field);
            jedis.close();
            return ret;
        }
        else
        {
            //不存在
            jedis.close();
            return null;
        }
    }

    /**
     * @Description 设置带过期时间hash值(ttl默认3分钟)
     * @Params [key, field, object]
     **/
    public void setHashValueWithExpiration(String key, String field, Object object)
    {
        //默认3分钟过期
        setHashValueWithExpiration(key, field, object, 60 * 3);
    }

    /**
     * @Description 设置带过期时间hash值
     * @Params [key hash键, field 字段, object 对象, ttl 存活时间]
     **/
    public void setHashValueWithExpiration(String key, String field, Object object, long ttl)
    {
        Jedis jedis = jedisPool.getResource();
        long expiredTime = System.currentTimeMillis() + 1000 * ttl;
        //设置过期时间
        jedis.hset(key + ETSuffix, field, gson.toJson(expiredTime));
        //设置值
        jedis.hset(key, field, gson.toJson(object));
        jedis.close();
    }

    //序列化
    public byte[] serialize(Object obj)
    {
        if (null == obj)
            return null;
        ByteArrayOutputStream bai = null;
        ObjectOutputStream obi = null;
        try
        {
            bai = new ByteArrayOutputStream();
            obi = new ObjectOutputStream(bai);
            obi.writeObject(obj);
            obi.close();
            bai.close();
            return bai.toByteArray();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    //反序列化
    public Object unserizlize(byte[] byt)
    {
        if (null == byt)
            return null;
        ObjectInputStream oii = null;
        ByteArrayInputStream bis = null;
        try
        {
            bis = new ByteArrayInputStream(byt);
            oii = new ObjectInputStream(bis);
            bis.close();
            oii.close();
            return oii.readObject();
        } catch (Exception e)
        {

            e.printStackTrace();
        }
        return null;
    }
}