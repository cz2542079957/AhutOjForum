package com.ahutoj.redisDao;

import com.ahutoj.config.ServerRuleConfig;
import com.ahutoj.utils.JedisUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BufferedSolutionThumbUpDao extends RedisBaseDao
{

    public BufferedSolutionThumbUpDao(JedisUtil jedisUtil, ServerRuleConfig serverRuleConfig, Gson gson)
    {
        super(jedisUtil, serverRuleConfig, gson);
    }

    /**
     * @Description 检查是否要限制该用户点赞
     * @Params [UID]
     * @Return true:限制  false:不限制
     **/
    public boolean astrictUserChangeThumbUpState(String UID)
    {
        //过期或者不存在则表示没有超出限制
        String res = jedisUtil.getHashValueCheckExpiration(AstrictUserChangeThumbUpState, UID);
        return null != res;
    }

    /**
     * @Description 为该用户添加限制
     * @Params [UID]
     **/
    public void setAstrictUserChangeThumbUpState(String UID)
    {

        //两秒可以操作一次
        jedisUtil.setHashValueWithExpiration(AstrictUserChangeThumbUpState, UID, "1", serverRuleConfig.ASTRICT_USER_THUMBSUP_SOLUTION_TIME);
    }
}
