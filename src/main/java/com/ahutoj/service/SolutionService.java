package com.ahutoj.service;

import com.ahutoj.bean.Solution;
import com.ahutoj.bean.SolutionComment;
import com.ahutoj.dao.ahutojForum.SolutionDao;
import com.ahutoj.redisDao.BufferedSolutionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolutionService
{
    public SolutionDao solutionDao;
    public BufferedSolutionDao bufferedSolutionDao;

    @Autowired
    public SolutionService(SolutionDao solutionDao, BufferedSolutionDao bufferedSolutionDao)
    {
        this.solutionDao = solutionDao;
        this.bufferedSolutionDao = bufferedSolutionDao;
    }

    /**
     * @Description
     * @Params [PID, UID, Content]
     * @Return 1 添加成功 0 不成功 -1 受到限制
     **/
    public Integer addSolution(String PID, String UID, String Content)
    {
        //查看是否有时间限制
        if (bufferedSolutionDao.astrictUserPublishSolution(UID))
        {
            return -1;
        }
        bufferedSolutionDao.setAstrictUserPublishSolution(UID); //添加限制
        long currentTime = System.currentTimeMillis();
        return solutionDao.addSolution(PID, UID, Content, currentTime);
    }

    /**
     * @Description 根据PID或者UID搜索（默认找所有）
     * @Params [PID, UID, Page, Limit]
     * @Return
     **/
    public List<Solution> getSolutions(String PID, String UID, Integer Page, Integer Limit)
    {
        return getSolutions(PID, UID, Page, Limit, null);
    }

    /**
     * @Description 根据PID、UID、State搜索
     * @Params [PID, UID, Page, Limit, State]
     * @Return
     **/
    public List<Solution> getSolutions(String PID, String UID, Integer Page, Integer Limit, Integer State)
    {
        Integer Begin = Page * Limit;
        return solutionDao.getSolutions(PID, UID, Begin, Limit, State);
    }

    /**
     * @Description 根据 PID 获取solution 数量（默认是所有状态的数量）
     * @Params [PID]
     * @Return 数量
     **/
    public Integer getSolutionsCountByPID(String PID)
    {
        return getSolutionsCountByPID(PID, null);
    }

    /**
     * @Description 根据 PID 获取solution 数量
     * @Params [PID]
     * @Return 数量
     **/
    public Integer getSolutionsCountByPID(String PID, Integer State)
    {
        return solutionDao.getSolutionsCountByPID(PID, State);
    }


    public Integer changeSolutionContent(Integer ID, String Content, String UpdateTime)
    {
        return solutionDao.changeSolutionContent(ID, Content, UpdateTime);
    }

    public Integer changeSolutionThumbUp(Integer SLTID, Integer Interval)
    {
        return solutionDao.changeSolutionThumbUp(SLTID, Interval);
    }

}
