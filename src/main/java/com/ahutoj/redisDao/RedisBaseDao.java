package com.ahutoj.redisDao;

import com.ahutoj.utils.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class RedisBaseDao
{
    public static String AstrictUserChangeThumbUpState = "AstrictUserChangeThumbUpState";
    public static String User = "User";

    public JedisUtil jedisUtil;

    @Autowired
    public RedisBaseDao(JedisUtil jedisUtil)
    {
        this.jedisUtil = jedisUtil;
    }
}
