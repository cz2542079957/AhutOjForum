package com.ahutoj.service;

import com.ahutoj.bean.Solution;
import com.ahutoj.mapper.ahutojForum.SolutionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolutionService
{
    public SolutionMapper solutionMapper;

    @Autowired
    public SolutionService(SolutionMapper solutionMapper)
    {
        this.solutionMapper = solutionMapper;
    }

    public List<Solution> getSolutionsByPID(Integer PID, Integer Page, Integer Limit)
    {
        Integer Begin = Page * Limit;
        return solutionMapper.getSolutionsByPID(PID, Begin, Limit);
    }

    public int addSolution(Integer PID, String UID, String Content)
    {
        long currentTime = System.currentTimeMillis();
        return solutionMapper.addSolution(PID, UID, Content, currentTime);
    }

    public int changeSolutionContent(Integer ID, String Content, String UpdateTime)
    {
        return solutionMapper.changeSolutionContent(ID, Content, UpdateTime);
    }
}
