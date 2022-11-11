package com.ahutoj.utils;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public Jedis getJedis()
    {
        return jedisPool.getResource();
    }

    public void closeJedis(Jedis jedis)
    {
        if (jedis != null)
        {
            jedis.close();
        }
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