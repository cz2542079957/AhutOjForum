package com.ahutoj.redisDao;

import com.ahutoj.bean.User;
import com.ahutoj.utils.JedisUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BufferedUserDao extends RedisBaseDao
{
    private Gson gson;

    @Autowired
    public BufferedUserDao(JedisUtil jedisUtil, Gson gson)
    {
        super(jedisUtil);
        this.gson = gson;
    }

    public User getUserInfoByUID(String UID)
    {
        return gson.fromJson(jedisUtil.getHashValueCheckExpiration("User", UID), User.class);
    }

    //缓存数据
    public void bufferUserInfo(User user)
    {
        if (null != user)
        {
            jedisUtil.setHashValueWithExpiration("User", user.getUID(), user);
        }
    }
}
