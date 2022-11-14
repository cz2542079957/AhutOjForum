package com.ahutoj.redisDao;

import com.ahutoj.config.ServerRuleConfig;
import com.ahutoj.utils.JedisUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class RedisBaseDao
{
    public static String AstrictUserPublishSolution = "AstrictUserPublishSolution";
    public static String AstrictUserChangeThumbUpState = "AstrictUserChangeThumbUpState";
    public static String User = "User";

    public JedisUtil jedisUtil;
    public ServerRuleConfig serverRuleConfig;
    public Gson gson;


    @Autowired
    public RedisBaseDao(JedisUtil jedisUtil, ServerRuleConfig serverRuleConfig, Gson gson)
    {
        this.jedisUtil = jedisUtil;
        this.serverRuleConfig = serverRuleConfig;
        this.gson = gson;


    }
}
