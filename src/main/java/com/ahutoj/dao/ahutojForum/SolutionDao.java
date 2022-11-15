package com.ahutoj.dao.ahutojForum;

import com.ahutoj.bean.Solution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.type.IntegerTypeHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface SolutionDao
{

    Integer addSolution(@Param("PID") String PID, @Param("UID") String UID, @Param("Content") String Content, @Param("UpdateTime") long UpdateTime);

    Integer deleteSolution(@Param("SLTID") Integer SLTID);

    List<Solution> getSolutions(@Param("PID") String PID, @Param("UID") String UID, @Param("Begin") Integer Begin, @Param("Limit") Integer Limit, @Param("State") Integer State);

    Integer getSolutionsCountByPID(@Param("PID") String PID, @Param("State") Integer State);

    Integer changeSolutionContent(@Param("SLTID") Integer SLTID, @Param("Content") String Content, @Param("UpdateTime") String UpdateTime);

    Integer changeSolutionThumbUp(@Param("SLTID") Integer SLTID, @Param("Interval") Integer Interval);


    Integer changeSolutionState(@Param("SLTID") Integer SLTID, @Param("State") Integer State);
}
