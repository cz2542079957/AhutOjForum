package com.ahutoj.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(
        basePackages = {"com.ahutoj.mapper.ahutojForum"},
        sqlSessionFactoryRef = "ahutojForumSqlSessionFactory",
        sqlSessionTemplateRef = "ahutojForumSqlSessionTemplate")
public class AhutojForumDataSourceConfig
{
    @Primary
    @Bean(name="ahutojForumDataSource")
    @ConfigurationProperties(prefix="spring.datasource.ahutojforum")
    public DataSource ahutojForumDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name="ahutojForumSqlSessionFactory")
    public SqlSessionFactory ahutojForumSqlSessionFactory(@Qualifier("ahutojForumDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean=new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Primary
    @Bean(name="ahutojForumTransactionManager")
    public DataSourceTransactionManager ahutojForumTransactionManager(@Qualifier("ahutojForumDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name="ahutojForumSqlSessionTemplate")
    public SqlSessionTemplate ahutojForumSqlSessionTemplate(@Qualifier("ahutojForumSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
