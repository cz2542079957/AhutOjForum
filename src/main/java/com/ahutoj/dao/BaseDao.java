package com.ahutoj.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BaseDao extends SqlSessionDaoSupport
{
        @Resource
        public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory)
        {
                super.setSqlSessionFactory(sqlSessionFactory);
        }
}