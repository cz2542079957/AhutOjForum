package com.ahutoj.service;

import com.ahutoj.bean.User;
import com.ahutoj.dao.ahutoj.UserDao;
import com.ahutoj.redisDao.BufferedUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService
{
    private UserDao userDao;
    private BufferedUserDao bufferedUserDao;

    @Autowired
    public UserService(UserDao userDao, BufferedUserDao bufferedUserDao)
    {
        this.userDao = userDao;
        this.bufferedUserDao = bufferedUserDao;
    }

    public User getUserInfoByUID(String UID)
    {
        //redis查询
        User user = bufferedUserDao.getUserInfoByUID(UID);
        if (null != user)
        {
            return user;
        }
        //数据库查询
        user = userDao.getUserInfoByUID(UID);
        //缓存数据
        if (null != user)
        {
            bufferedUserDao.bufferUserInfo(user);
        }
        return user;
    }

    public int changeUserInfo()
    {
        return userDao.changeUserInfo();
    }
}
