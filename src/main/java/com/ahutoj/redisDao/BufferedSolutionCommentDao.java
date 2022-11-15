package com.ahutoj.redisDao;

import com.ahutoj.config.ServerRuleConfig;
import com.ahutoj.utils.JedisUtil;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class BufferedSolutionCommentDao extends RedisBaseDao
{
    public BufferedSolutionCommentDao(JedisUtil jedisUtil, ServerRuleConfig serverRuleConfig, Gson gson)
    {
        super(jedisUtil, serverRuleConfig, gson);
    }

    /**
     * @Description 检查是否要限制该用户评论
     * @Params [UID]
     * @Return true:限制  false:不限制
     **/
    public boolean astrictUserPublishSolutionComment(String UID)
    {
        //过期或者不存在则表示没有超出限制
        String res = jedisUtil.getHashValueCheckExpiration(AstrictUserPublishSolutionComment, UID);
        return null != res;
    }

    /**
     * @Description 为该用户添加限制
     * @Params [UID]
     **/
    public void setAstrictUserPublishSolutionComment(String UID)
    {
        jedisUtil.setHashValueWithExpiration(AstrictUserPublishSolutionComment, UID, "1", serverRuleConfig.ASTRICT_USER_PUBLISH_SOLUTION_COMMENT_TIME);
    }
}
