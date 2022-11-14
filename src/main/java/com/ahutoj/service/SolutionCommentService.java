package com.ahutoj.service;

import com.ahutoj.bean.SolutionComment;
import com.ahutoj.dao.ahutojForum.SolutionCommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolutionCommentService
{
    private SolutionCommentDao solutionCommentDao;

    @Autowired
    public SolutionCommentService(SolutionCommentDao solutionCommentDao)
    {
        this.solutionCommentDao = solutionCommentDao;
    }


    /**
     * @Description 获取某一题解的某一页题解
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
}
