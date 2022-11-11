package com.ahutoj.redisDao;

import com.ahutoj.bean.User;
import com.ahutoj.utils.JedisUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.rmi.server.UID;

@Component
public class BufferedUserDao
{
    @Autowired
    private JedisUtil jedisUtil;

    public User getUserInfoByUID(String UID)
    {
        User user = null;
        Jedis jedis = jedisUtil.getJedis();
        //查看是否过期
        String UserExpiredTime = jedis.hget("UserExpiredTime", UID);
        long expiredTime = Long.parseLong(null == UserExpiredTime ? "0" : UserExpiredTime);
        long currentTime = System.currentTimeMillis();
        if (currentTime > expiredTime)
        {
            //过期清空该数据
            jedis.hdel("UserExpiredTime", UID);
            jedis.hdel("User", UID);
            return null;
        }
        //没有过期从内存中拿取数据
        user = JSON.parseObject(jedis.hget("User", UID), User.class);
        jedis.close();
        return user;
    }

    //缓存数据
    public void bufferUserInfo(User user)
    {
        if (null == user)
            return;
        Jedis jedis = jedisUtil.getJedis();
        //3分钟过期
        long expiredTime = System.currentTimeMillis() + 1000 * 60 * 3;
        //设置过期时间 设置值
        jedis.hset("UserExpiredTime", user.getUID(), JSON.toJSONString(expiredTime));
        jedis.hset("User", user.getUID(), JSON.toJSONString(user, JSONWriter.Feature.WriteMapNullValue));
    }
}
