package com.ahutoj.mapper.ahutoj;

import com.ahutoj.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper
{
    User getUserInfoByUID(@Param("UID") String UID);

    int changeUserInfo();
}
