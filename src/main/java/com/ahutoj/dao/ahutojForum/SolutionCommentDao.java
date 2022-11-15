package com.ahutoj.dao.ahutojForum;

import com.ahutoj.bean.Solution;
import com.ahutoj.bean.SolutionComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SolutionCommentDao
{
    Integer addSolutionComment(@Param("SLTID") Integer SLTID, @Param("UID") String UID, @Param("Content") String Content, @Param("CreateTime") long CreateTime);

    List<SolutionComment> getCommentBySLTID(@Param("SLTID") Integer SLTID, @Param("Begin") Integer Begin, @Param("Limit") Integer Limit);

    Integer getCommentCountBySLTID(@Param("SLTID") Integer SLTID);

    Integer changeSolutionCommentCount(@Param("SLTID") Integer SLTID, @Param("Interval") Integer Interval);

    Integer deleteSolutionComment(@Param("SLTCMTID") Integer SLTCMTID, @Param("UID") String UID);
}
