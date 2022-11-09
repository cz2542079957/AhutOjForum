package com.ahutoj.mapper.ahutojForum;

import com.ahutoj.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Repository
public interface test1
{
    int insert(@Param("content") String content);
}
