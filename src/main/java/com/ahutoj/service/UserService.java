package com.ahutoj.service;

import com.ahutoj.bean.User;
import com.ahutoj.mapper.ahutoj.UserMapper;
import com.ahutoj.redisDao.BufferedUserDao;
import org.apache.catalina.UserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.server.UID;


@Service
public class UserService
{
    private UserMapper userMapper;
    private BufferedUserDao bufferedUserDao;

    @Autowired
    public UserService(UserMapper userMapper, BufferedUserDao bufferedUserDao)
    {
        this.userMapper = userMapper;
        this.bufferedUserDao = bufferedUserDao;
    }

    public User getUserInfoByUID(String UID)
    {
        //redis查询
        User user = bufferedUserDao.getUserInfoByUID(UID);
        if (user != null)
            return user;
        //数据库查询
        user = userMapper.getUserInfoByUID(UID);
        //缓存数据
        if (null != user)
        {
            bufferedUserDao.bufferUserInfo(user);
        }
        return user;
    }

    public int changeUserInfo()
    {
        return userMapper.changeUserInfo();
    }
}
