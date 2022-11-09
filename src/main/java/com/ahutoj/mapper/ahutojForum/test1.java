package com.ahutoj.mapper.ahutojForum;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface test1
{
    int insert(@Param("content") String content);
}
