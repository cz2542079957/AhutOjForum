package com.ahutoj.dao.ahutoj;

import com.ahutoj.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao
{
    User getUserInfoByUID(@Param("UID") String UID);

    int changeUserInfo();
}
