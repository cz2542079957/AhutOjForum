package com.ahutoj.dao.ahutojForum;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SolutionThumbUpDao
{
    Integer updateSolutionThumbUpState(@Param("SLTID") Integer SLTID, @Param("UID") String UID, @Param("State") Integer State, @Param("CreateTime") long CreateTime);

    Integer getSolutionUserThumbUpState(@Param("SLTID") Integer SLTID, @Param("UID") String UID);
}
