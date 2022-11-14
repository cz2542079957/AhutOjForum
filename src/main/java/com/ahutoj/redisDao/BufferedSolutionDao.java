package com.ahutoj.redisDao;

import com.ahutoj.config.ServerRuleConfig;
import com.ahutoj.utils.JedisUtil;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class BufferedSolutionDao extends RedisBaseDao
{

    public BufferedSolutionDao(JedisUtil jedisUtil, ServerRuleConfig serverRuleConfig, Gson gson)
    {
        super(jedisUtil, serverRuleConfig, gson);
    }

    /**
     * @Description 限制用户频繁发布题解
     * @Params [UID]
     **/
    public boolean astrictUserPublishSolution(String UID)
    {
        //过期或者不存在则表示没有超出限制
        String res = jedisUtil.getHashValueCheckExpiration(AstrictUserPublishSolution, UID);
        return null != res;
    }

    /**
     * @Description 设置用户发布题解时间限制
     * @Params [UID]
     **/
    public void setAstrictUserPublishSolution(String UID)
    {
        //10秒限制
        jedisUtil.setHashValueWithExpiration(AstrictUserPublishSolution, UID, "1", serverRuleConfig.ASTRICT_USER_PUBLISH_SOLUTION_TIME);
    }
}
