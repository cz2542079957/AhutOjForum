package com.ahutoj.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.websocket.WsWebSocketContainer;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(
        basePackages = {"com.ahutoj.mapper.ahutoj"},
        sqlSessionFactoryRef = "ahutojSqlSessionFactory",
        sqlSessionTemplateRef = "ahutojSqlSessionTemplate")
public class AhutojDataSourceConfig
{
    @Bean(name="ahutojDataSource")
    @ConfigurationProperties(prefix="spring.datasource.ahutoj")
    public DataSource ahutojDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="ahutojSqlSessionFactory")
    public SqlSessionFactory ahutojSqlSessionFactory(@Qualifier("ahutojDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean=new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name="ahutojTransactionManager")
    public DataSourceTransactionManager ahutojTransactionManager(@Qualifier("ahutojDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name="ahutojSqlSessionTemplate")
    public SqlSessionTemplate ahutojSqlSessionTemplate(@Qualifier("ahutojSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
