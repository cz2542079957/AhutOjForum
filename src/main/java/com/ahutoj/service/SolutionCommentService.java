package com.ahutoj.service;

import com.ahutoj.bean.SolutionComment;
import com.ahutoj.dao.ahutojForum.SolutionCommentDao;
import com.ahutoj.redisDao.BufferedSolutionCommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.server.UID;
import java.util.List;

@Service
public class SolutionCommentService
{
    private SolutionCommentDao solutionCommentDao;
    private BufferedSolutionCommentDao bufferedSolutionCommentDao;

    @Autowired
    public SolutionCommentService(SolutionCommentDao solutionCommentDao, BufferedSolutionCommentDao bufferedSolutionCommentDao)
    {
        this.solutionCommentDao = solutionCommentDao;
        this.bufferedSolutionCommentDao = bufferedSolutionCommentDao;
    }

    /**
     * @Description 添加题解评论
     * @Params [SLTID, UID, Content]
     * @Retuen -1:被限制  1:成功  0:更改失败
     **/
    public Integer addSolutionComment(Integer SLTID, String UID, String Content)
    {
        //查看是否限制
        if (bufferedSolutionCommentDao.astrictUserPublishSolutionComment(UID))
        {
            return -1;
        }
        //评论数加一
        long currentTime = System.currentTimeMillis();
        Integer res = solutionCommentDao.changeSolutionCommentCount(SLTID, 1);
        if (res == 0)
        {
            return 0;
        }
        //添加限制
        bufferedSolutionCommentDao.setAstrictUserPublishSolutionComment(UID);
        return solutionCommentDao.addSolutionComment(SLTID, UID, Content, currentTime);
    }


    /**
     * @Description 获取某一题解的某一页评论
     * @Params [SLTID]
     * @Return 评论页列表
     **/
    public List<SolutionComment> getCommentsBySLTID(Integer SLTID, Integer Page, Integer Limit)
    {
        Integer Begin = Page * Limit;
        return solutionCommentDao.getCommentBySLTID(SLTID, Begin, Limit);
    }

    /**
     * @Description 获取某一题解的评论数
     * @Params [SLTID]
     * @Return 总数
     **/
    public Integer getCommentCountBySLTID(Integer SLTID)
    {
        return solutionCommentDao.getCommentCountBySLTID(SLTID);
    }


    /**
     * @Description 改变题解评论数
     * @Params [SLTID, Interval]
     **/
    public Integer changeSolutionCommentCount(Integer SLTID, Integer Interval)
    {
        return solutionCommentDao.changeSolutionCommentCount(SLTID, Interval);
    }

    /**
     * @Description 删除评论
     * @Params [SLTCMTID, UID]
     * @Return 1 删除成功  0 删除失败
     **/
    public Integer deleteSolutionComment(Integer SLTCMTID, Integer SLTID, String UID)
    {
        //题解评论数减一
        Integer res = solutionCommentDao.changeSolutionCommentCount(SLTID, -1);
        if (res == 0)
        {
            return 0;
        }
        return solutionCommentDao.deleteSolutionComment(SLTCMTID, UID);
    }
}
