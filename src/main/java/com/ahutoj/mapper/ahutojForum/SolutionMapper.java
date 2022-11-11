package com.ahutoj.mapper.ahutojForum;

import com.ahutoj.bean.Solution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SolutionMapper
{
    List<Solution> getSolutionsByPID(@Param("PID") Integer PID, @Param("Begin") Integer Begin, @Param("Limit") Integer Limit);

    Integer addSolution(@Param("PID") Integer PID, @Param("UID") String UID, @Param("Content") String Content, @Param("UpdateTime") long UpdateTime);

    Integer changeSolutionContent(@Param("SLTID") Integer ID, @Param("Content") String Content, @Param("UpdateTime") String UpdateTime);

}
