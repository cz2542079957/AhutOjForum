package com.ahutoj.dao.ahutojForum;

import com.ahutoj.bean.Solution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SolutionDao
{
    List<Solution> getSolutions(@Param("PID") Integer PID, @Param("UID") String UID, @Param("Begin") Integer Begin, @Param("Limit") Integer Limit);

    Integer addSolution(@Param("PID") Integer PID, @Param("UID") String UID, @Param("Content") String Content, @Param("UpdateTime") long UpdateTime);

    Integer changeSolutionContent(@Param("SLTID") Integer SLTID, @Param("Content") String Content, @Param("UpdateTime") String UpdateTime);

    Integer changeSolutionThumbUp(@Param("SLTID") Integer SLTID, @Param("Interval") Integer Interval);

}
