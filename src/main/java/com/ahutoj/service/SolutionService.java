package com.ahutoj.service;

import com.ahutoj.bean.Solution;
import com.ahutoj.dao.ahutojForum.SolutionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolutionService
{
    public SolutionDao solutionDao;

    @Autowired
    public SolutionService(SolutionDao solutionDao)
    {
        this.solutionDao = solutionDao;
    }

    public List<Solution> getSolutions(Integer PID, String UID, Integer Page, Integer Limit)
    {
        Integer Begin = Page * Limit;
        if (null == PID)
        {
            //todo
        }
        return solutionDao.getSolutions(PID, UID, Begin, Limit);
    }

    public Integer addSolution(Integer PID, String UID, String Content)
    {
        long currentTime = System.currentTimeMillis();
        return solutionDao.addSolution(PID, UID, Content, currentTime);
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
