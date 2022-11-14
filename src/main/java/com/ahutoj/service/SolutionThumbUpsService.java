package com.ahutoj.service;

import com.ahutoj.dao.ahutojForum.SolutionDao;
import com.ahutoj.dao.ahutojForum.SolutionThumbUpDao;
import com.ahutoj.redisDao.BufferedSolutionThumbUpDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolutionThumbUpsService
{
    private SolutionThumbUpDao solutionThumbUpDao;
    private SolutionDao solutionDao;
    private BufferedSolutionThumbUpDao bufferedSolutionThumbUpDao;

    @Autowired
    public SolutionThumbUpsService(SolutionThumbUpDao solutionThumbUpDao, SolutionDao solutionDao, BufferedSolutionThumbUpDao bufferedSolutionThumbUpDao)
    {
        this.solutionThumbUpDao = solutionThumbUpDao;
        this.solutionDao = solutionDao;
        this.bufferedSolutionThumbUpDao = bufferedSolutionThumbUpDao;
    }

    /**
     * @Description 用户更新题解的点赞状态
     * @Params [SLTID, UID, State]
     * @Return -1:当前状态为取消点赞  1:点赞状态  0:更改失败（被限制）
     **/
    public Integer updateSolutionThumbUpState(Integer SLTID, String UID, Integer State)
    {
        //查看是否被限制
        if (bufferedSolutionThumbUpDao.astrictUserChangeThumbUpState(UID))
        {
            //被限制
            return 0;
        }
        //添加时间限制
        bufferedSolutionThumbUpDao.setAstrictUserChangeThumbUpState(UID);
        if (getSolutionUserThumbUpState(SLTID, UID) == 1)
        {
            solutionDao.changeSolutionThumbUp(SLTID, -1);
            State = -1; //在这里做修改是为了保证数据以后台为准
        }
        else
        {
            solutionDao.changeSolutionThumbUp(SLTID, 1);
            State = 1;
        }
        long currentTime = System.currentTimeMillis();
        solutionThumbUpDao.updateSolutionThumbUpState(SLTID, UID, State, currentTime);
        return State;
    }

    /**
     * @Description 用户对某一个题解点赞情况
     * @Params [SLTID, UID]
     * @Return 1:点赞 -1:非点赞
     **/
    public Integer getSolutionUserThumbUpState(Integer SLTID, String UID)
    {
        Integer IThumbUp = solutionThumbUpDao.getSolutionUserThumbUpState(SLTID, UID);
        //不存在就是未点赞
        if (IThumbUp == 0)
            IThumbUp = -1;
        return IThumbUp;
    }
}
