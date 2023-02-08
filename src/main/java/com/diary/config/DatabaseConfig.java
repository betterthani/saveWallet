package com.diary.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.diary.user.model.Gender;

@Configuration
@MapperScan(basePackages="com.diary.*")
public class DatabaseConfig {
	@Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        
        Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*Mapper.xml");
        sessionFactory.setMapperLocations(res);
        
        // mybatis 설정시 SqlSessionFactoryBean 클래스에 TypeHandler 등록이 가능함
        sessionFactory.setTypeHandlers(new TypeHandler[] {
        	new Gender.TypeHandler()
        });

        return sessionFactory.getObject();
    }
}
