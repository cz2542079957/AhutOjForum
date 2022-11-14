package com.ahutoj.redisDao;

import com.ahutoj.bean.User;
import com.ahutoj.config.ServerRuleConfig;
import com.ahutoj.utils.JedisUtil;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class BufferedUserDao extends RedisBaseDao
{
    public BufferedUserDao(JedisUtil jedisUtil, ServerRuleConfig serverRuleConfig, Gson gson)
    {
        super(jedisUtil, serverRuleConfig, gson);
    }

    public User getUserInfoByUID(String UID)
    {
        return gson.fromJson(jedisUtil.getHashValueCheckExpiration(User, UID), User.class);
    }

    //缓存数据
    public void bufferUserInfo(User user)
    {
        if (null != user)
        {
            jedisUtil.setHashValueWithExpiration(User, user.getUID(), user);
        }
    }
}
